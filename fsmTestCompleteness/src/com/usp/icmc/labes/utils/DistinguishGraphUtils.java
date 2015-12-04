package com.usp.icmc.labes.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import com.usp.icmc.labes.fsm.FsmState;
import com.usp.icmc.labes.fsm.FsmTestTree;
import com.usp.icmc.labes.fsm.FsmTransition;

public class DistinguishGraphUtils {
	
	public static final String label_name = "_label_";
	
	private static DistinguishGraphUtils instance;
	
	private DistinguishGraphUtils() { }
	
	public static DistinguishGraphUtils getInstance() {
		if(instance==null) instance = new DistinguishGraphUtils();
		return instance;
	}

	public UndirectedGraph<FsmState, DefaultEdge> createDistinguishabilityGraph(FsmTestTree testTree) {
		UndirectedGraph<FsmState, DefaultEdge> dg = new SimpleGraph<FsmState, DefaultEdge>(DefaultEdge.class);
		
		for (FsmState node : testTree.getStates()) {
			dg.addVertex(node);
		}
		
		FsmState statei = null;
		FsmState statej = null;
		for (int i = 0; i < testTree.getStates().size()-1; i++) {
			for (int j = i+1; j < testTree.getStates().size(); j++) {
				statei = testTree.getStates().get(i);
				statej = testTree.getStates().get(j);
				if(isT_Distinguishable(statei,statej)){
					dg.addEdge(statei,statej);
				}
			}
		}
		return dg;
	}
	
	public boolean isT_Distinguishable(FsmState statei, FsmState statej) {
		FsmTransition tri = null;
		FsmTransition trj = null;
		for (int i = 0; i < statei.getOut().size(); i++) {
			tri = statei.getOut().get(i);
			trj = getTransitionMatchingInput(tri,statej);
			if(trj!=null){
				if(!tri.getOutput().equals(trj.getOutput())) return true;
				else return isT_Distinguishable(tri.getTo(),trj.getTo());
			}
		}
		return false;
	}

	public FsmTransition getTransitionMatchingInput(FsmTransition tri, FsmState statej) {
		for (int j = 0; j < statej.getOut().size(); j++) {
			FsmTransition trj = statej.getOut().get(j);
			if(trj.getInput().equals(tri.getInput())) return trj;
		}
		return null;
	}
	
	public void saveDistinguishabilityGraph(UndirectedGraph<FsmState, DefaultEdge> graph,
			File f) throws FileNotFoundException {
		
		PrintWriter pw = new PrintWriter(f);

		int from;
		String in;
		String out;
		int to;

//		List<FsmTransition> transit = new ArrayList<FsmTransition>();
//		transit.addAll(fsm.getTransitions());

		pw.println("graph g {");
		
		
//				List<Integer> ids = new ArrayList<Integer>();
//				for (FsmState st : fsm.getStates()) ids.add((Integer) st.getProperties().get("name"));
//
//				pw.println("\t{");
//				for (FsmState st : fsm.getStates()) {
//					pw.println("\t\t"+ 
//							Integer.toString(st.getId())
//							+" [style=filled, fillcolor="+getColor(ids.indexOf(st.getProperties().get("name")))+ "];");
//				}
//				pw.println("\t}");
//
//				for (FsmState st : fsm.getStates()) {
//					int realState = (Integer)st.getProperties().get("name");
//					pw.println("\tsubgraph cluster_sub"
//							+Integer.toString(st.getId())
//							+"{ "+Integer.toString(st.getId())+";"
//							+"\tlabel = \"state "+realState+"\";"
//							+ " color=white}");
//				}


		for (DefaultEdge edge : graph.edgeSet()) {
			
			from 	= ((FsmState)graph.getEdgeTarget(edge)).getId();
			to 		= ((FsmState)graph.getEdgeSource(edge)).getId();
			pw.println("\t"+Integer.toString(from)
			+" -- "
			+Integer.toString(to) 			
			//+" [label=\""+in+" / "+out+"\"]"
					+ ";");
		}
		pw.println("}");
		pw.close();
	}	

	
}
