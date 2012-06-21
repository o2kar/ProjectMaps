package maps.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Way is a ordered list of Nodes
 * 
 * @author rroesch
 *
 */
public class Way extends Element {

	private List<Node> nodes = new ArrayList<Node>();
	@Deprecated
	private String name;
	private String id;
	@Deprecated
	private String highway;

	public Way() {
		super();
	}

	public Way(String name) {
		super();
		this.name = name;
	}

	public boolean addNode(Node e) {
		return nodes.add(e);
	}

	public String getName() {
		return name == null ? "" : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHighway() {
		return highway == null ? "" : highway;
	}

	public void setHighway(String highway) {
		this.highway = highway;
	}

	public List<Node> getNodes() {
		return nodes;
	}

}
