package org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair;

public class AllocationConfiguration {
	public float getQueueMaxAMShare(String queue) {
		return 0.5f;
	}
}
