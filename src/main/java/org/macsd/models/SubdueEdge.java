package models;

import org.jgrapht.graph.DefaultEdge;

public class SubdueEdge extends AntEdge {

	public enum EdgeType {
		DIRECTED_EDGE, UNDIRECTED_EDGE
	};

	private static final long serialVersionUID = 1L;
	String label = null;
	EdgeType type = null;

	public SubdueEdge(EdgeType _type, String _label) {
		this.type = _type;
		this.label = _label;
	}
	
	public String getLabel() {
		return label;
	}

	/*public int hashCode() {
		return label.hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SubdueEdge)) {
			return false;
		}

		SubdueEdge edge = (SubdueEdge) obj;
		return label.equals(edge.label);
	}*/

	@Override
	public String toString() {
		return label;
	}
}
