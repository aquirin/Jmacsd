/*
 * Compute the heuristics eta.
 * An heuristic depends only on a given edge and the full database.
 */

package aco;

import java.util.Set;
import java.util.Vector;

import models.MyGraphHelper;
import models.SubdueEdge;
import models.SubdueVertex;

import org.jgrapht.graph.DefaultDirectedGraph;

import filters.SubdueDB;

public class Heuristics {

	/*
	 * Static heuristic.
	 */
	public static double hstatic(
			Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db, SubdueEdge edge) {

		// Number of edge in the DB
		int c = MyGraphHelper.countEdge(db, edge);
		System.out.println("Count: " + c);

		// Size of the DB
		int s = MyGraphHelper.countEdges(db);
		System.out.println("Count tot: " + s);

		return (double)c/s;
	}

	/*
	 * Static heuristic for pos/neg DB.
	 */
	public static double hstatic(
			SubdueDB db, SubdueEdge edge) {

		// Number of edge in the pos DB
		int cp = MyGraphHelper.countEdge(db.pos(), edge);

		// Number of edge in the neg DB
		int cn = MyGraphHelper.countEdge(db.neg(), edge);
		System.out.println("Count: " + cp + " " + cn);

		// Size of the DB
		int sp = MyGraphHelper.countEdges(db.pos());
		int sn = MyGraphHelper.countEdges(db.neg());
		System.out.println("Count tot: " + sp + " " + sn);

		return (double)cp/sp - (double)cn/sn;

	}

	/*
	 * Compute the MDL value of the given graph.
	 */
	public static double mdl(Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db) {

		// Number of nodes, edges, labels
		int V = MyGraphHelper.countNodes(db);
		int E = MyGraphHelper.countNodes(db);
		int L = MyGraphHelper.countEdgeLabels(db);

		double vertexBits = MathHelper.log2(V) + (V * MathHelper.log2(V));
		double rowBits = V * MathHelper.log2fact(V);
		double edgeBits = E * (1 + MathHelper.log2(L));

		int B = 0;
		int K = 0;
		int M = 0;
		
		
		
		Set<SubdueVertex> nodeSet = MyGraphHelper.nodeSet(db);
		for(SubdueVertex v1: nodeSet) {
			int ki = MyGraphHelper.degree(db, v1);
			rowBits -= (MathHelper.log2fact(ki) +
					MathHelper.log2fact((V - ki)));
			if (ki > B)
				B = ki;
			K += ki;
			// MaxEdgesToSingleVertex is assumeded equals to 1
			int tmpM = 1;
			if (tmpM > M) 
				M = tmpM;
		}
		rowBits += ((V + 1) * MathHelper.log2(B + 1));
		edgeBits += ((K + 1) * MathHelper.log2(M));

		return vertexBits + rowBits + edgeBits;

	}
}
