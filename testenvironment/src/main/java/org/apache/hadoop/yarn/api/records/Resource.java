package org.apache.hadoop.yarn.api.records;

public class Resource {
	private float resource;

	public Resource(float resource) {
		this.resource = resource;
	}

	public float getResource() {
		return resource;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "r:" + resource;
	}
}
