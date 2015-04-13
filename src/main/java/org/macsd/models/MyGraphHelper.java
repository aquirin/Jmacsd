/*
 * Provide useful graph functions not already provided by the JGraphT API.
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
	 * Need to call: SubdueEdge.setComparisonType();
	 */
	public static int countEdge(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g, SubdueEdge me) {

		int count = 0;
		Set<SubdueEdge> sedges = g.edgeSet();
		for(SubdueEdge ce: sedges) {
			if(ce.compareTo(me)==0)
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
	 * For a given graph and a given node, count how many times we can find
	 * the node in the graph.
	 * Need to call: SubdueVertex.setComparisonType();
	 */
	public static int countNode(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g, SubdueVertex mn) {

		int count = 0;
		Set<SubdueVertex> snodes = g.vertexSet();
		for(SubdueVertex cn: snodes) {
			if(cn.compareTo(mn)==0)
				count++;
		}

		return count;

	}
	
	/*
	 * For a given DB and a given node, count how many times we can find
	 * the node in the DB.
	 */
	public static int countNode(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db, SubdueVertex mn) {

		int count = 0;
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			count += countNode(g, mn);
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
	 * Count the number of distinct edge labels in the graph.
	 */
	public static int countEdgeLabels(DefaultDirectedGraph<SubdueVertex, SubdueEdge> graph) {
		Set<String> sl = new TreeSet<String>();
		Set<SubdueEdge> sedges = graph.edgeSet();
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
			if(g.containsVertex(n)) {
				degree += g.inDegreeOf(n);
				degree += g.outDegreeOf(n);
				//degree += g.degreeOf(n);
			}
		}
		return degree;
	}
	
	/*
	 * Compute the degree of a node.
	 */
	public static int degree(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g, SubdueVertex n) {
		return g.inDegreeOf(n) + g.outDegreeOf(n);
	}

	/*
	 * Return all the nodes of the given DB.
	 */
	public static Set<SubdueVertex> nodeSet(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {
		Set<SubdueVertex> snodes = new TreeSet<SubdueVertex>();
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			snodes.addAll(g.vertexSet());
		}
		return snodes;
	}

	/*
	 * Return all the edges of the given DB.
	 */
	public static Set<SubdueEdge> edgeSet(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {
		Set<SubdueEdge> sedges = new TreeSet<SubdueEdge>();
		for(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g: db) {
			sedges.addAll(g.edgeSet());
		}
		return sedges;
	}
	
	/*
	 * Return in the given graph the vertex with the given index.
	 */
	public static SubdueVertex findVertex(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g, int i) {
		for(SubdueVertex n: g.vertexSet()) {
			if(n.getIndex() == i)
				return n;
		}
		return null;
	}

}
