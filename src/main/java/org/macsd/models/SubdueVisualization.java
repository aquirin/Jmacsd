package models;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.organic.JGraphFastOrganicLayout;



public class SubdueVisualization {

	private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
	private static final Dimension DEFAULT_SIZE = new Dimension(500, 500);

	//public void test(ListenableDirectedWeightedGraph<String, DefaultWeightedEdge> theGraph) {
	public static void layout(DefaultDirectedGraph<SubdueVertex, SubdueEdge> graph, String title) {
		
		JPanel panel = new JPanel();
		//panel.setPreferredSize(DEFAULT_SIZE);

		JScrollPane scrollpane = new JScrollPane(panel);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		

		JFrame frame = new JFrame();

		frame.add(scrollpane, BorderLayout.CENTER);
		frame.setTitle(title + " - Graph of " + graph.vertexSet().size() + " nodes");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		//frame.setPreferredSize(DEFAULT_SIZE);

		JGraphModelAdapter<SubdueVertex, SubdueEdge> jgAdapter = new JGraphModelAdapter<SubdueVertex, SubdueEdge>(graph);

		JGraph jgraph = new JGraph(jgAdapter);
		//panel.setSize(1000, 1000);
		//jgraph.setMoveable(false);

		panel.add(jgraph);
		frame.setSize(DEFAULT_SIZE);

		// Let's see if we can lay it out
		JGraphFacade jgf = new JGraphFacade(jgraph);
		JGraphFastOrganicLayout layoutifier = new JGraphFastOrganicLayout();
		layoutifier.setForceConstant(100);
		layoutifier.run(jgf);
		//System.out.println("Layout complete");

		final Map nestedMap = jgf.createNestedMap(true, true);
		jgraph.getGraphLayoutCache().edit(nestedMap);

		jgraph.getGraphLayoutCache().update();
		jgraph.refresh();

		frame.setVisible(true);
		panel.setVisible(true);
		scrollpane.setVisible(true);
	}

}
