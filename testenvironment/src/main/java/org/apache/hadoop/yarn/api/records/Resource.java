package org.apache.hadoop.yarn.api.records;

public class Resource {
	private double resource;

	public Resource(double resource) {
		this.resource = resource;
	}

	public double getResource() {
		return resource;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "r:" + resource;
	}
}
