/*
 * Class to represent an edge.
 * An edge is equal to another if:
 * - the node labels are the same for both nodes
 * - the edge labels are the same
 */

package models;

import java.util.Comparator;

import models.SubdueVertex.IndexComparator;

import org.jgrapht.graph.DefaultEdge;

import aco.MathHelper;

public class SubdueEdge extends AntEdge implements Comparable<SubdueEdge> {

	public enum EdgeType {
		DIRECTED_EDGE, UNDIRECTED_EDGE
	};
	
	public enum ComparisonType {
		INDEX, LABEL
	};

	private static final long serialVersionUID = 1L;
	String label = null;
	EdgeType type = null;
	//int n1, n2;		// The node indices of the edge ends, always sorted.
	//String l1, l2;		// The two node labels, always sorted.
	SubdueVertex start, end;	// The two nodes.
	static Comparator<SubdueEdge> defaultComparator = new IndexComparator();

	/**
	 * @param _type: type of edge
	 * @param _label: label of the edge
	 * @param _n1: index of the first node
	 * @param _n2: index of the second node
	 */
	public SubdueEdge(EdgeType _type, String _label, SubdueVertex _start, SubdueVertex _end) {
		this.type = _type;
		this.label = _label;
		this.start = _start;
		this.end = _end;
	}
	
	public String getLabel() {
		return label;
	}
	
	public SubdueVertex getStart() {
		return start;
	}
	
	public SubdueVertex getEnd() {
		return end;
	}
	
	/*
	 * Change the mode of the comparison of two edges.
	 * Mandatory before any operations needing equals() or compareTo()
	 */
	public static void setComparisonType(ComparisonType t) {
		if(t == ComparisonType.INDEX)
			defaultComparator = new IndexComparator();
		else if(t == ComparisonType.LABEL)
			defaultComparator = new LabelComparator();
	}

	@Override
	public int hashCode() {
		String str = start.getLabel() + "," + end.getLabel() + "," + label;
		return str.hashCode();
	}

	@Override
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
		return defaultComparator.compare(this,  edge) == 0;
	}

	@Override
	public String toString() {
		//return "[" + start + " => " + end + "] " + label;
		return label;
	}

	/*
	 * Implement a lexicographic order between edges.
	 */
	@Override
	public int compareTo(SubdueEdge o) {
		return defaultComparator.compare(this,  o);
	}
	
	// Comparators
	
	// Compare the node and edge labels
	static class LabelComparator implements Comparator<SubdueEdge> {
	    @Override
	    public int compare(SubdueEdge a, SubdueEdge b) {
	    	int c1 = a.getLabel().compareTo(b.getLabel());
	    	if(c1!=0) return c1;
	    	int c2 = a.getStart().getLabel().compareTo(b.getStart().getLabel());
	    	if(c2!=0) return c2;
	    	else return a.getEnd().getLabel().compareTo(b.getEnd().getLabel());
	    }
	}
	
	// Compare the node indices
	static class IndexComparator implements Comparator<SubdueEdge> {
	    @Override
	    public int compare(SubdueEdge a, SubdueEdge b) {
	    	int c1 = MathHelper.compare(a.getStart().getIndex(), b.getStart().getIndex());
	    	if(c1!=0) return c1;
	    	else return MathHelper.compare(a.getEnd().getIndex(), b.getEnd().getIndex());
	    }
	}
}
