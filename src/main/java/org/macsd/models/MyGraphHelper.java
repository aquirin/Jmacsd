/*
 * Provide any useful graph function not already provided by the JGraphT API.
 */

package models;

import java.util.Set;
import java.util.TreeSet;
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
	 * Count the total number of nodes in the DB.
	 */
	public static int countNodes(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {
		int count = 0;
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			count += g.vertexSet().size();
		}
		return count;
	}
	
	/*
	 * Count the total number of edges in the DB.
	 */
	public static int countEdges(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {
		int count = 0;
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			count += g.edgeSet().size();
		}
		return count;
	}
	
	/*
	 * Count the number of distinct edge labels in the DB.
	 */
	public static int countEdgeLabels(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {
		Set<String> sl = new TreeSet<String>();
		Set<SubdueEdge> sedges = edgeSet(db);
		for(SubdueEdge e: sedges) {
			sl.add(e.getLabel());
		}
		return sl.size();
	}
	
	/*
	 * Compute the degree of a node.
	 */
	public static int degree(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db, SubdueVertex n) {
		int degree = 0;
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			degree += g.inDegreeOf(n);
			degree += g.outDegreeOf(n);
		}
		return degree;
	}
	
	/*
	 * Set of the nodes of all the given DB.
	 */
	public static Set<SubdueVertex> nodeSet(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {
		Set<SubdueVertex> snodes = new TreeSet<SubdueVertex>();
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			snodes.addAll(g.vertexSet());
		}
		return snodes;
	}
	
	/*
	 * Set of the edges of all the given DB.
	 */
	public static Set<SubdueEdge> edgeSet(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {
		Set<SubdueEdge> sedges = new TreeSet<SubdueEdge>();
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			sedges.addAll(g.edgeSet());
		}
		return sedges;
	}
	
}
