import java.util.Set;
import java.util.Vector;

import org.jgrapht.graph.DefaultDirectedGraph;

import aco.Heuristics;
import aco.MathHelper;
import models.MyGraphHelper;
import models.SubdueEdge;
import models.SubdueVertex;
import models.SubdueVisualization;
import filters.SubdueDB;

public class MACSD_Main {

	/*
	 * Test 1
	 */
	public static void test1() {

		String filenames1[] = { "src/main/resources/10.g",
				"src/main/resources/43.g", "src/main/resources/35.g",
				"src/main/resources/44.g", "src/main/resources/house.g",
				"src/main/resources/sample.g" };
		
		String filenames[] = { "src/main/resources/house.g" };

		for (String filename : filenames) {
			SubdueDB sf = new SubdueDB(filename);

			System.out.println("Imported: " + sf.size() + " graphs.");

			// Visualization
			int n = 1;
			System.out.println("Show positive graphs...");
			for (DefaultDirectedGraph<SubdueVertex, SubdueEdge> g : sf.pos()) {
				SubdueVisualization.layout(g, n + " (pos)");
				n++;
			}
			System.out.println("Show negative graphs...");
			for (DefaultDirectedGraph<SubdueVertex, SubdueEdge> g : sf.neg()) {
				SubdueVisualization.layout(g, n + " (neg)");
				n++;
			}
		}

	}

	public static void test2() {
		String s[] = { "v 1 node", "v    1 node", "v    1   node",
		"v    10 node" };
		String pattern = "v +1 .+";
		for (String ss : s) {
			if (ss.matches(pattern))
				System.out.println("OK " + ss);
		}
		
		for(int i=0; i<20; i++) {
			System.out.println("log2fact(" + i + ") = " + MathHelper.log2fact(i));
		}

	}
	
	/*
	 * 
	 */
	public static void test3() {

		String filename = "src/main/resources/house.g";

		SubdueDB sf = new SubdueDB(filename);

		System.out.println("Imported: " + sf.size() + " graphs.");
		
		// Visualize the first graph
		DefaultDirectedGraph<SubdueVertex, SubdueEdge> graph = sf.pos().get(0);
		SubdueVisualization.layout(graph, "");

		// Pick the second edge in the 1st graph
		Set<SubdueEdge> sedges = graph.edgeSet();
		SubdueEdge e2 = sedges.toArray(new SubdueEdge[sedges.size()])[2];
		SubdueVertex n1 = graph.getEdgeSource(e2);
		SubdueVertex n2 = graph.getEdgeTarget(e2);
		System.out.println(n1 + " => " + n2 + " (" + e2 + ")");
		
		// Edge count
		SubdueEdge.setComparisonType(SubdueEdge.ComparisonType.INDEX);
		int c1 = MyGraphHelper.countEdge(sf.pos(), e2);
		System.out.println("Edge Count (type=INDEX): " + c1);
		SubdueEdge.setComparisonType(SubdueEdge.ComparisonType.LABEL);
		int c2 = MyGraphHelper.countEdge(sf.pos(), e2);
		System.out.println("Edge Count (type=LABEL): " + c2);

		// Node count
		SubdueVertex.setComparisonType(SubdueVertex.ComparisonType.INDEX);
		int c3 = MyGraphHelper.countNode(sf.pos(), n1);
		System.out.println("Node Count (type=INDEX): " + c3);
		SubdueVertex.setComparisonType(SubdueVertex.ComparisonType.LABEL);
		int c4 = MyGraphHelper.countNode(sf.pos(), n1);
		System.out.println("Node Count (type=LABEL): " + c4);

		
		SubdueEdge.setComparisonType(SubdueEdge.ComparisonType.LABEL);
		System.out.println("Heuristic static (+): " + Heuristics.hstatic(sf.pos(), e2));
		System.out.println("Heuristic static (+/-): " + Heuristics.hstatic(sf, e2));
		
		System.out.println("MDL(+): " + Heuristics.mdl(sf.pos()));
		System.out.println("MDL(-): " + Heuristics.mdl(sf.neg()));

		// Local Degree
		System.out.println("Local Degree n1: " + MyGraphHelper.degree(graph,n1));
		System.out.println("Local Degree n2: " + MyGraphHelper.degree(graph,n2));

		// Global Degree
		SubdueVertex.setComparisonType(SubdueVertex.ComparisonType.LABEL);
		int d1 = MyGraphHelper.degree(sf.pos(), n1);
		int d2 = MyGraphHelper.degree(sf.pos(), n2);
		
		System.out.println("Global Degree n1: " + d1);
		System.out.println("Global Degree n2: " + d2);

	}
	
	/*
	 * Check graph cloning.
	 */
	public static void clone_test1() {
		
		String filename = "src/main/resources/house.g";

		SubdueDB sf = new SubdueDB(filename);

		System.out.println("Imported: " + sf.size() + " graphs.");
		
		// Visualize the first graph
		DefaultDirectedGraph<SubdueVertex, SubdueEdge> graph1 = sf.pos().get(0);
		SubdueVisualization.layout(graph1, "graph1");

		// Clone
		DefaultDirectedGraph<SubdueVertex, SubdueEdge> graph2 = (DefaultDirectedGraph<SubdueVertex, SubdueEdge>) graph1.clone();
		SubdueVisualization.layout(graph2, "graph2");
		
		// Pick a node
		Set<SubdueVertex> snodes = graph1.vertexSet();
		SubdueVertex n1 = snodes.toArray(new SubdueVertex[snodes.size()])[0];

		// Remove a node
		graph1.removeVertex(n1);

		// Pick an edge
		Set<SubdueEdge> sedges = graph1.edgeSet();
		SubdueEdge e1 = sedges.toArray(new SubdueEdge[sedges.size()])[0];

		// Remove an edge
		graph1.removeEdge(e1);

		SubdueVisualization.layout(graph1, "graph1 (2)");
		SubdueVisualization.layout(graph2, "graph2 (2)");
		

	}

	public static void main(String[] args) {

		//test1();
		//test2();
		//test3();
		clone_test1();
	}

}
