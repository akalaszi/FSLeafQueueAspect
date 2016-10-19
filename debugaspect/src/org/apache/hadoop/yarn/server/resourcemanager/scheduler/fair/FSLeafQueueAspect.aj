package org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair;

import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FSLeafQueue;
import org.apache.hadoop.yarn.util.resource.Resources;

public aspect FSLeafQueueAspect {

	before(FSLeafQueue q, Resource amResource): target(q) && args(amResource) && call(public boolean canRunAppAM(Resource)) {
		float maxAMShare = q.scheduler.getAllocationConfiguration().getQueueMaxAMShare(q.getName());
		if (Math.abs(maxAMShare - -1.0f) < 0.0001) {
			return;
		}
		Resource maxAMResource = Resources.multiply(q.getFairShare(), maxAMShare);
		Resource ifRunAMResource = Resources.add(q.getAmResourceUsage(), amResource);
		System.err.println(
				"POOL NAME: " + q.getName() + " maxAMShare: " + maxAMResource + " currentAMShare:" + ifRunAMResource);
	}

}
