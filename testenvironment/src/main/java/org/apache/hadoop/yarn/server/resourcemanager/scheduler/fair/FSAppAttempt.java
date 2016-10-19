package org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.util.resource.Resources;

public class FSAppAttempt {
	private Resource assignContainer(FSSchedulerNode node, boolean reserved) {
		synchronized (this) {

			// Check the AM resource usage for the leaf queue
			if (getLiveContainers().size() == 0 && !getUnmanagedAM()) {
				if (!getQueue().canRunAppAM(getAMResource())) {
					return Resources.none();
				}
			}

		}
		return null;
	}

	private boolean getUnmanagedAM() {
		// TODO Auto-generated method stub
		return false;
	}

	private Resource getAMResource() {
		return new Resource(11);
	}

	private FSLeafQueue getQueue() {
		return new FSLeafQueue();
	}

	@SuppressWarnings("rawtypes")
	private List getLiveContainers() {
		return new ArrayList();
	}

	public static void main(String[] args) {
		new FSAppAttempt().assignContainer(null, false);
	}
}