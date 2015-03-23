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

		String filenames[] = { "src/main/resources/10.g",
				"src/main/resources/43.g", "src/main/resources/35.g",
				"src/main/resources/44.g", "src/main/resources/house.g",
				"src/main/resources/sample.g" };

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
		
		DefaultDirectedGraph<SubdueVertex, SubdueEdge> graph = sf.pos().get(0);
		SubdueVisualization.layout(graph, "");

		// Pick the second edge in the 1st graph
		Set<SubdueEdge> sedges = graph.edgeSet();
		SubdueEdge e2 = sedges.toArray(new SubdueEdge[sedges.size()])[2];
		SubdueVertex n1 = graph.getEdgeSource(e2);
		SubdueVertex n2 = graph.getEdgeTarget(e2);
		System.out.println(n1 + " => " + n2 + " (" + e2 + ")");
		
		System.out.println("Heuristic static: " + Heuristics.hstatic(sf.pos(), e2));
		System.out.println("Heuristic static +/-: " + Heuristics.hstatic(sf, e2));
		
		System.out.println("MDL(+): " + Heuristics.mdl(sf.pos()));
		System.out.println("MDL(-): " + Heuristics.mdl(sf.neg()));

		// Degree
		int d1 = MyGraphHelper.degree(sf.pos(), n1);
		int d2 = MyGraphHelper.degree(sf.pos(), n2);
		
		System.out.println("Degree n1: " + d1);
		System.out.println("Degree n2: " + d2);

	}

	public static void main(String[] args) {

		//test1();
		//test2();
		test3();
	}

}
