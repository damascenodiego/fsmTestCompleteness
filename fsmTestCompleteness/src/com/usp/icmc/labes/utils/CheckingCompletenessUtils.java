package com.usp.icmc.labes.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import com.usp.icmc.labes.fsm.FsmModel;
import com.usp.icmc.labes.fsm.FsmState;
import com.usp.icmc.labes.fsm.FsmTestTree;
import com.usp.icmc.labes.fsm.FsmTransition;

public class CheckingCompletenessUtils {
	
	public static final String label_name = "_label_";
	
	private static CheckingCompletenessUtils instance;
	
	private CheckingCompletenessUtils() { }
	
	public static CheckingCompletenessUtils getInstance() {
		if(instance==null) instance = new CheckingCompletenessUtils();
		return instance;
	}

	public boolean canApplyLemma2(FsmState alpha, Set<FsmState> k_set, UndirectedGraph<FsmState, DefaultEdge> dg) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canApplyLemma3(FsmState alpha, Set<FsmState> k_set, UndirectedGraph<FsmState, DefaultEdge> dg) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean satisfiesTheorem1(Set<FsmState> k_set, FsmModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
