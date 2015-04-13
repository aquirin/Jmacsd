/*
 * Class to represent a node.
 * A node is equal to another if it has the same index and label.
 */

package models;

import java.util.Comparator;
import java.util.Date;

import models.SubdueEdge.ComparisonType;
import models.SubdueEdge.IndexComparator;
import models.SubdueEdge.LabelComparator;

public class SubdueVertex implements Comparable<SubdueVertex> {

	public enum ComparisonType {
		INDEX, LABEL
	};
	
	int index = -1;
	String label = null;
	static Comparator<SubdueVertex> defaultComparator = new IndexComparator();

	public SubdueVertex(int _index, String _label) {
		this.index = _index;
		this.label = _label;
	}

	public int getIndex() {
		return index;
	}
	
	public String getLabel() {
		return label;
	}
	
	/*
	 * Change the mode of the comparison of two nodes.
	 * Mandatory before any operations needing equals() or compareTo()
	 */
	public static void setComparisonType(ComparisonType t) {
		if(t == ComparisonType.INDEX)
			defaultComparator = new IndexComparator();
		else if(t == ComparisonType.LABEL)
			defaultComparator = new LabelComparator();
	}

	@Override
	public String toString() {
		return "[" + index + "] " + label;
	}

	@Override
	public int compareTo(SubdueVertex o) {
		return defaultComparator.compare(this,  o);
	}
	
	public int hashCodeIndexLabel() {
		String str = "" + index + "," + label;
		return str.hashCode();
	}
	
	public int hashCodeLabel() {
		return label.hashCode();
	}

	@Override
	public int hashCode() {
		return hashCodeIndexLabel();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SubdueVertex)) {
			return false;
		}

		SubdueVertex vertex = (SubdueVertex) obj;
		return defaultComparator.compare(this,  vertex) == 0;
	}
	
	// Comparators
	
	// Compare the labels
	static class LabelComparator implements Comparator<SubdueVertex> {
	    @Override
	    public int compare(SubdueVertex a, SubdueVertex b) {
	        return a.label.compareTo(b.label);
	    }
	}
	
	// Compare the indices
	static class IndexComparator implements Comparator<SubdueVertex> {
	    @Override
	    public int compare(SubdueVertex a, SubdueVertex b) {
			if(a.index<b.index) return -1;
			else if(a.index>b.index) return 1;
			else return 0;
	    }
	}
}
