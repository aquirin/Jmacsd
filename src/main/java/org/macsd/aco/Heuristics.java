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
		int s = MyGraphHelper.countEdge(db);
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
		int sp = MyGraphHelper.countEdge(db.pos());
		int sn = MyGraphHelper.countEdge(db.neg());
		System.out.println("Count tot: " + sp + " " + sn);
		
		return (double)cp/sp - (double)cn/sn;

	}
}
