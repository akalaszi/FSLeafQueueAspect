set -e -x
testfile=amshare-test-env-1.0-SNAPSHOT.jar
dot=`pwd`
cd ../testenvironment
mvn clean package
cp target/$testfile $dot
cd $dot
rm -f aspect.jar
ajc -cp $testfile:aspectjrt.jar -outjar aspect.jar src/org/apache/hadoop/yarn/server/resourcemanager/scheduler/fair/FSLeafQueueAspect.aj
jar uf aspect.jar META-INF/
java -javaagent:aspectjweaver.jar -classpath "aspect.jar:aspectjrt.jar:$testfile" org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FSAppAttempt
