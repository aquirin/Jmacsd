/*
 * Edge in the Ant Colony System.
 * An edge is associated with parameters specific to the ACS:
 * - a pheromone trail tau
 */

package models;

import org.jgrapht.graph.DefaultEdge;

public class AntEdge extends DefaultEdge {

	private static final long serialVersionUID = 1L;
	double tau = 0;

	public AntEdge() {
	}

	@Override
	public String toString() {
		return "" + tau;
	}
}
