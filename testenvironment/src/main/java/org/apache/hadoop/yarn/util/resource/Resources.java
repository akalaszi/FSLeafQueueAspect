package org.apache.hadoop.yarn.util.resource;

import org.apache.hadoop.yarn.api.records.Resource;

public class Resources {

	public static Resource none() {
		// TODO Auto-generated method stub
		return new Resource(0f);
	}

	public static Resource multiply(Resource fairShare, float maxAMShare) {
		return new Resource(fairShare.getResource() * maxAMShare);
	}

	public static Resource add(Resource amResourceUsage, Resource amResource) {
		return new Resource(amResourceUsage.getResource() + amResource.getResource());
	}


}
