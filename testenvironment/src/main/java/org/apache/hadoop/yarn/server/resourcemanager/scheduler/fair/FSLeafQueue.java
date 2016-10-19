package org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair;

import org.apache.hadoop.yarn.api.records.Resource;

public class FSLeafQueue {

	protected final FairScheduler scheduler = new FairScheduler();

	public String getName() {
		return "foo";
	}

	public Resource getAmResourceUsage() {
		return new Resource(33);
	}

	public Resource getFairShare() {
		return new Resource(44);
	}

	public boolean canRunAppAM(Resource amResource) {
		System.err.println("Doing something, that is not logged.");
		return true;
	}

}
