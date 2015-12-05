package com.usp.icmc.labes.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;

import com.usp.icmc.labes.fsm.FsmModel;
import com.usp.icmc.labes.fsm.FsmState;

public class CheckingCompletenessUtils {
	
	public static final String label_name = "_label_";
	
	private static CheckingCompletenessUtils instance;
	
	private CheckingCompletenessUtils() { }
	
	public static CheckingCompletenessUtils getInstance() {
		if(instance==null) instance = new CheckingCompletenessUtils();
		return instance;
	}

	/**
	 * @param alpha This is the FsmState of a FsmTestTree which represents a test sequence.
	 * @param k_set This is the set of cliques obtained from a distinguishability graph and which alpha must match n-1 elements to be linked 
	 * @param dg This is the graph where alpha will be connected to one of the elements from k_set if it can be distinguished from n-1 elements
	 * @return TRUE if alpha can be distinguished from n-1 states from k_set 
	 */
	public boolean canApplyLemma2(FsmState alpha, Set<FsmState> k_set, UndirectedGraph<FsmState, DefaultEdge> dg) {		
		Set<FsmState> setDistinguished = new HashSet<FsmState>();		
		setDistinguished.clear();
		
		for (FsmState s: k_set) {			
			if(s.equals(alpha)) return true;
			if(dg.containsEdge(alpha,s)) setDistinguished.add(s);		
		}
		
		if(setDistinguished.size() != k_set.size()-1) return false;
		else return true;
	}
	
	/**
	 * @param alpha This is the FsmState of a FsmTestTree which represents a test sequence.
	 * @param labels This collections maps states to its labels.
	 * @param k_set This is the set of cliques obtained from a distinguishability graph and which alpha must match n-1 elements to be linked 
	 * @param dg This is the graph where alpha will be connected to one of the elements from k_set if it can be distinguished from n-1 elements
	 * @return TRUE if alpha can be distinguished from n-1 states from k_set 
	 */	
	public boolean canApplyLemma2(FsmState alpha, Map<Integer, Set<FsmState>> labels, Set<FsmState> k_set, UndirectedGraph<FsmState, DefaultEdge> dg) {		
		Set<FsmState> setDistinguished = new HashSet<FsmState>();		
		setDistinguished.clear();
		
		for (FsmState s: k_set) {			
			if(s.equals(alpha)) return true;	
			if(dg.containsEdge(alpha,s)) setDistinguished.add(s);		
		}
		
		/* k_clone is used for finding the label of the alpha */
		Set<FsmState> k_clone = new HashSet<FsmState>(k_set);			
		
		/* set difference between k_set and k_clone */
		k_clone.removeAll(setDistinguished);
		
		/* when distinguishable add alpha in the labels */
		for (Integer key : labels.keySet())				
			if(labels.get(key).containsAll(k_clone)) { 
				labels.get(key).add(alpha);
				return true;
			}
			
		return false;
	}

	/**
 	 * @param alpha This is the FsmState of a FsmTestTree which represents a test sequence.
	 * @param labels This collections maps states to its labels.
	 * @param 	dg This is the graph where two beta and chi sequences converging to a same state will be searched. 
	 * 			Alpha is prefix of chi extended with gamma. All beta,beta+gamma,chi, and chi+gamma (alpha) all belong to dg
	 * @return returns true if beta,beta+gamma,chi, and chi+gamma (alpha) all exist return TRUE
	 */
	public boolean canApplyLemma3(FsmState alpha, Map<Integer, Set<FsmState>> labels, UndirectedGraph<FsmState, DefaultEdge> dg) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @param k_set The set of sequences which must contain state and transition cover in order to return true.
	 * @param model	the model where each sequence will be applied in order to check state and transition coverage
	 * @return TRUE if k_set contains state and transition cover. In the paper it is described as empty sequence, alpha and alpha+x.
	 */
	public boolean satisfiesTheorem1(Set<FsmState> k_set, FsmModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
