/*
 * Subdue format.
 * To separate two graphs:
 * - or use XP/XN
 * - or use "v 1" for the next node definition
 * 
 * These are not reliable
 * - use blank line to separate graphs (some files separate nodes and eges
 *   by a blank line)
 * - consider the new graph start by the next node definition (some files
 *   mix node and edge definitions)
 *   
 * Definitions:
 * - A graph is a connected collection of nodes and edges.
 * - A database (DB) is a set of disconnected graphs.
 * - A subdue DB is both a positive DB and a negative DB.
 */

package filters;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import models.SubdueVisualization;
import models.SubdueEdge;
import models.SubdueVertex;
import models.SubdueEdge.EdgeType;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class SubdueDB {

	public enum GraphType {
	    POSITIVE_GRAPH, NEGATIVE_GRAPH
	};
	
	// Graph database
	Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db_pos = null;
	Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> db_neg = null;
	
	/*
	 * Read a subdue file.
	 */
	public SubdueDB(String filename) {

		db_pos = new Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>>();
		db_neg = new Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>>();

		read(filename);
	}
	
	/*
	 * Read the file
	 */
	private void read(String filename) {
		DefaultDirectedGraph<SubdueVertex, SubdueEdge> graph = null;

		String line = null;
		int n = 1;
		GraphType gtype = GraphType.POSITIVE_GRAPH;
		boolean isLastEdge = false;		// True if we just read an edge

		// Read the file line by line
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
			while ((line = br.readLine()) != null) {
				//System.out.println(n + ". " + line);
				//n++;
				
				if(line.equalsIgnoreCase("XP")) {
					// New positive graph
					 gtype = GraphType.POSITIVE_GRAPH;
					 graph = null;
				} else if(line.equalsIgnoreCase("XN")) {
					// New negative graph
					 gtype = GraphType.NEGATIVE_GRAPH;
					 graph = null;
				}
				
				if(!(line.startsWith("v") || line.startsWith("e")
						|| line.startsWith("d") || line.startsWith("u"))) {
					// Comment line
					// Any comment (%) or blank line are ignored
					// and we create a new graph
					//gtype = GraphType.POSITIVE_GRAPH;
					//graph = null;
					continue;
				} else if((graph == null)
						|| (line.matches("v +1 .+"))	// If we define a node 1, create a new graph
						) {
					// Create a new graph
					graph = new DefaultDirectedGraph<SubdueVertex, SubdueEdge>(SubdueEdge.class);
					isLastEdge = false;
					if(gtype == GraphType.POSITIVE_GRAPH)
						db_pos.add(graph);
					else
						db_neg.add(graph);
				}
	
				String[] fields = line.split(" +");
				
				if(line.startsWith("v")) {
					// Node
					SubdueVertex v1 = new SubdueVertex(Integer.parseInt(fields[1]), fields[2]);
					graph.addVertex(v1);
					isLastEdge = false;
				} else {
					EdgeType etype = EdgeType.DIRECTED_EDGE;
					isLastEdge = true;
					if(line.startsWith("u")) {
						// Undirected edge
						etype = EdgeType.UNDIRECTED_EDGE;
					} else if(line.startsWith("d") || line.startsWith("e")) {
						// Directed edge
						etype = EdgeType.DIRECTED_EDGE;
					}
					
					SubdueEdge edge = new SubdueEdge(etype, fields[3]);
					int n1 = Integer.parseInt(fields[1]);
					int n2 = Integer.parseInt(fields[2]);
					SubdueVertex v1 = findVertex(graph, n1);
					SubdueVertex v2 = findVertex(graph, n2);
					graph.addEdge(v1, v2, edge);
				}
			}
		} catch (IOException e) {
			System.out.println("Problem with: " + n + ". " + line);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("Problem with: " + n + ". " + line);
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Problem with: " + n + ". " + line);
			e.printStackTrace();
		}
	}
	
	/*
	 * Return in the given graph the vertex with the given index.
	 */
	private static SubdueVertex findVertex(DefaultDirectedGraph<SubdueVertex, SubdueEdge> g, int i) {
		for(SubdueVertex n: g.vertexSet()) {
			if(n.getIndex() == i)
				return n;
		}
		return null;
	}
	
	/*
	 * Return number of graphs.
	 */
	public int size() {
		return db_pos.size() + db_neg.size();
	}
	
	/*
	 * Return the set of positive graphs.
	 */
	public Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> pos() {
		return db_pos;
	}
	
	/*
	 * Return the set of negative graphs.
	 */
	public Vector<DefaultDirectedGraph<SubdueVertex, SubdueEdge>> neg() {
		return db_neg;
	}

}
