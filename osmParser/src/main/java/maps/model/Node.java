package maps.model;

import java.util.Map;

/**
 * A Node is a single point defined through longitude and latitude
 * @author rroesch
 * 
 */
public class Node extends Element {

	private double lon;
	private double lat;

	public Node(double lon, double lat) {
		super();
		this.lon = lon;
		this.lat = lat;
	}

	public Node(double lon, double lat, Map<String, String> tags) {
		super();
		this.lon = lon;
		this.lat = lat;
		this.tags = tags;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

}
