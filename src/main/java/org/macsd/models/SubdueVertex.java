package models;

public class SubdueVertex {

	int index = -1;
	String label = null;

	public SubdueVertex(int _index, String _label) {
		this.index = _index;
		this.label = _label;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		return "[" + index + "] " + label;
	}
}
