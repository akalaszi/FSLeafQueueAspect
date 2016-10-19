package org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair;

import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.util.resource.Resources;

public class FSLeafQueue {
	private final String name = "foo";

	public String getName() {
		return name;
	}

	public boolean canRunAppAM(Resource amResource) {
		float maxAMShare = 0.5f;
		Resource fairShare = new Resource(10);
		Resource amResourceUsage = new Resource(12);
		Resource maxAMResource = Resources.multiply(fairShare, maxAMShare);
		Resource ifRunAMResource = Resources.add(amResourceUsage, amResource);
		
		System.err.println("canRunAppAM done");
		return true;
	}

}
