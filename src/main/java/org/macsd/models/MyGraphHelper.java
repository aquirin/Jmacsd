/*
 * Provide any useful graph function not already provided by the JGraphT API.
 */

package models;

import java.util.Set;
import java.util.Vector;

import org.jgrapht.graph.DefaultDirectedGraph;

public class MyGraphHelper {

	/*
	 * For a given graph and a given edge, count how many times we can find
	 * the edge in the graph.
	 * Edge equality is based if the nodes have the same index.
	 */
	public static int countEdge(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g, SubdueEdge me) {
		
		int mn1 = g.getEdgeSource(me).getIndex();
		int mn2 = g.getEdgeTarget(me).getIndex();
		int count = 0;
		
		Set<SubdueEdge> sedges = g.edgeSet();
		for(SubdueEdge ce: sedges) {
			
			int cn1 = g.getEdgeSource(ce).getIndex();
			int cn2 = g.getEdgeTarget(ce).getIndex();
			if((cn1==mn1) && (cn2==mn2))
				count++;
		}

		return count;
		
	}
	
	/*
	 * For a given DB and a given edge, count how many times we can find
	 * the edge in the DB.
	 */
	public static int countEdge(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db, SubdueEdge me) {
		
		int count = 0;
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			count += countEdge(g, me);
		}

		return count;
		
	}
	
	/*
	 * Count the total number of edges in the DB.
	 */
	public static int countEdge(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {
		int count = 0;
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			count += g.edgeSet().size();
		}
		return count;
	}
}
