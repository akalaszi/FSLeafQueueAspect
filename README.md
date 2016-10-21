#Debugging environment and Aspect for getting current AMShare and MaxAMShare for a YARN pool upon new AM is added.

###Background
YARN managed clusters often seems under utilizing the cluster while many new Applications are pending in ACCEPTED state.
This is usually due to that the maxAMShare limit per pool is exceeded .
The current maxAmShare and it limit is calculated as:


```
    Resource maxAMResource = Resources.multiply(maxResource, maxAMShare);
    Resource ifRunAMResource = Resources.add(amResourceUsage, amResource);
```
https://github.com/apache/hadoop/blob/trunk/hadoop-yarn-project/hadoop-yarn/hadoop-yarn-server/hadoop-yarn-server-resourcemanager/src/main/java/org/apache/hadoop/yarn/server/resourcemanager/scheduler/fair/FSLeafQueue.java#L506

This aspect prints the maxAMResource and ifRunAMResource to the stderr upon trying to schedule a new ApplicationConainer

###How to Compile Stub Environment
- cd testenvironment
- mvn package
- java -cp target/amshare-test-env-1.0-SNAPSHOT.jar org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FSAppAttempt
this should print: 
```
Doing something, that is not logged.
```

###How to Install AspectJ 
- Download latest aspectj jar: https://eclipse.org/aspectj/downloads.php
- Launch the installer jar: mkdir /some/dir/aspect;java -jar aspectj-1.8.9.jar -to /some/dir/aspect
- edit the ajc shell script: vi /some/dir/aspect/bin/add $ASPECTJ_HOME/lib/aspectjrt.jar to the classpath
- add /some/dir/aspect/bin to the PATH

###How to build & run AspectJ 
- cd CatalogServiceCatalogAspect/debugaspect/
- Copy here the two jars from AspectJ: cp /some/dir/aspect/lib/aspectjweaver.jar /some/dir/aspect/lib/aspectjrt.jar .
- run test.sh or:
```
rm -f aspect.jar
ajc -cp ../testenvironment/target/amshare-test-env-1.0-SNAPSHOT.jar:aspectjrt.jar -outjar aspect.jar src/org/apache/hadoop/yarn/server/resourcemanager/scheduler/fair/FSLeafQueueAspect.aj
jar uf aspect.jar META-INF/
java -javaagent:aspectjweaver.jar -classpath "aspect.jar:aspectjrt.jar:../testenvironment/target/amshare-test-env-1.0-SNAPSHOT.jar:" org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FSAppAttempt
```

this should print:
```
POOL NAME: foo maxAMShare: r:22.0 currentAMShare:r:44.0
Doing something, that is not logged.
```

### Add that to Yarn ResourceManager
Copy aspect.jar, aspectjrt.jar and aspectjweaver.jar to /tmp on the catalogd host, and temporarily add the followings to:
- ClouderaManager->Yarn->Configuration->Java Configuration Options for ResourceManager: -javaagent:/tmp/aspectjweaver.jar
- ClouderaManager->Yarn->Configuration->ResourceManager Environment Advanced Configuration Snippet (Safety Valve)-> HADOOP_CLASSPATH=$HADOOP_CLASSPATH:"/tmp/aspect.jar:/tmp/aspectjrt.jar"
- Disable Fair Scheduler Continuous Scheduling / yarn.scheduler.fair.continuous-scheduling-enabled
- Restart YARN

### Usage
- ssh to RM host
- cd /var/run/cloudera-scm-agent/process/<latest>-yarn-RESOURCEMANAGER/logs
- tail -f stderr.log | grep POOL
- On every new launch of Yarn applications (spark or MapReduce) the similar should be printed:
```
POOL NAME: root.spark maxAMShare: <memory:3072, vCores:9> currentAMShare:<memory:3072, vCores:3>
```

###Links
- https://eclipse.org/aspectj/doc/next/devguide/ajc-ref.html
- http://andrewclement.blogspot.hu/2009/02/load-time-weaving-basics.html

###Tested with 
- java version "1.8.0_60"
Java(TM) SE Runtime Environment (build 1.8.0_60-b27)
Java HotSpot(TM) 64-Bit Server VM (build 25.60-b23, mixed mode)
- Hadoop 2.6.0 in CDH 5.7.3
