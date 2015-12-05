package com.usp.icmc.labes.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;

import com.usp.icmc.labes.fsm.FsmModel;
import com.usp.icmc.labes.fsm.FsmState;
import com.usp.icmc.labes.fsm.FsmTransition;
import com.usp.icmc.labes.fsm.testing.FsmSUT;

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
	 * @param labels This collections maps states to its labels.
	 * @param k_set This is the set of cliques obtained from a distinguishability graph and which alpha must match n-1 elements to be linked 
	 * @param dg This is the graph where alpha will be connected to one of the elements from k_set if it can be distinguished from n-1 elements
	 * @return TRUE if alpha can be distinguished from n-1 states from k_set 
	 */	
	public boolean canApplyLemma2(FsmState alpha, Map<Integer, Set<FsmState>> labels, Set<FsmState> k_set, UndirectedGraph<FsmState, DefaultEdge> dg) {		
		/* when distinguishable add alpha in the labels */
		List<Integer> notConnected = new ArrayList<Integer>();
		notConnected.addAll(labels.keySet());
		for (Integer key : labels.keySet()){
			for(FsmState s: labels.get(key))
				if(dg.containsEdge(alpha,s)){
					notConnected.remove(key);
					break;
				}
		}

		if(notConnected.size()==1){
			labels.get(notConnected.get(0)).add(alpha);
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
	public boolean canApplyLemma3(FsmState alphaState, Map<Integer, Set<FsmState>> labels, Set<FsmState> k_set, UndirectedGraph<FsmState, DefaultEdge> dg) {
		//TODO search until the root of the tree

		// get phi sequence
		List<FsmTransition> phi = getPhi(alphaState,k_set);

		if(phi.size()>0){
			FsmState chiState = phi.get(0).getFrom();
			Integer chiLabel = null;

			Set<FsmState> labeledAsChi = new HashSet<FsmState>();

			for (Integer l : labels.keySet()) {
				if(labels.get(l).contains(chiState)){
					chiLabel = l;
					labeledAsChi.addAll(labels.get(l)); 
					break;
				}
			}

			for (FsmState beta : labeledAsChi) {
				FsmState betaPhi = applySequence(beta,phi);
				if(betaPhi != null){
					Integer betaPhiLabel = getLabel(betaPhi,labels);
					if(betaPhiLabel != null){
						labels.get(betaPhiLabel).add(alphaState);
						return true;
					}

				}
			}
		}

		return false;
	}


	private Integer getLabel(FsmState betaPhi, Map<Integer, Set<FsmState>> labels) {

		for (Integer l : labels.keySet()) {
			if(labels.get(l).contains(betaPhi)) return l;
		}
		return null;
	}

	private FsmState applySequence(FsmState beta, List<FsmTransition> phi) {
		FsmState s = beta;

		for (FsmTransition in : phi) {
			if(s.getOut().size()==0) return null;
			for (FsmTransition sTo : s.getOut()) {
				if(sTo.getInput().equals(in.getInput())) {
					s = sTo.getTo();
					break;
				}else{
					return null;
				}
			}
		}
		return s;
	}

	private List<FsmTransition> getPhi(FsmState alpha, Set<FsmState> k) {
		List<FsmTransition> phi = new ArrayList<FsmTransition>();

		FsmState s = alpha;

		while (!k.contains(s) && s.getIn().size()>0) {
			phi.add(s.getIn().get(0));
			s = s.getIn().get(0).getFrom();
		}
		Collections.reverse(phi);
		return phi;
	}

	/**
	 * @param k_set The set of sequences which must  contain state and transition cover in order to return true.
	 * @param model	the model where each sequence will be applied in order to check state and transition coverage
	 * @return TRUE if k_set contains state and transition cover. In the paper it is described as empty sequence, alpha and alpha+x.
	 */
	public boolean satisfiesTheorem1(Set<FsmState> k_set, FsmModel model, FsmModel testTree) {
		FsmTestingUtils testutils = FsmTestingUtils.getInstance();
		
		FsmSUT sut = new FsmSUT(model);

		Set<FsmTransition> 	coveredTr = new HashSet<FsmTransition> ();
		Set<FsmState> 		coveredSt = new HashSet<FsmState> ();
		
		FsmTransition currentTr = null;

		// check if empty sequence belongs to k
		if(k_set.contains(testTree.getInitialState())) coveredSt.add(model.getInitialState());
		
		for (FsmState seq : k_set) {
			// get sequence seq by traversing test tree
			List<FsmTransition> inSeq = testutils.getSequence(seq);
			for (FsmTransition in : inSeq) {
				// get each covered transitions
				currentTr = sut.inputReturnsFsmTransition(in.getInput());
				if(currentTr!=null){
					// keep covered states
					coveredSt.add(currentTr.getFrom()); coveredSt.add(currentTr.getTo());
					// keep covered transitions
					coveredTr.add(currentTr);
				}
			}
			sut.setCurrentState(sut.getSut().getInitialState());			
		}
		return (model.getStates().containsAll(coveredSt) && model.getTransitions().containsAll(coveredTr));
	}

	/**
	 * @param set
	 * @param n 
	 * @return TRUE if set contains "n" distinguishable states
	 */	
	public boolean checkClique(Set<FsmState> set, Integer n) {
		return (set.size() == n);
	}

}
