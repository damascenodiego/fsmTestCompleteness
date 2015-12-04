package com.usp.icmc.labes.fsm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.usp.icmc.labes.fsm.testing.FsmSUT;

public class FsmTestTree extends FsmModel{

	FsmModel fsm;
	FsmSUT sut;
	FsmTest testSequence;

	public FsmTestTree(FsmModel model, FsmTest test){
		fsm = model;
		testSequence = test;
		FsmState empty = getOrCreateState(0);
		addState(empty);
		setInitialState(empty);
		sut = new FsmSUT(fsm);
		setupTestTree();
	}

	private void setupTestTree(){
		FsmSUT testTreeSut = new FsmSUT(this);
		int counter=0;
		for (String string : testSequence.getTestSequence()) {
			int i = 0;
			for (; i < string.length() && isValidInput(testTreeSut,string.charAt(i)); i++) {
				sut.input(string.charAt(i));
				testTreeSut.input(string.charAt(i));
			}
			for (; i < string.length() ; i++) {
				int fromId = sut.getCurrentState().getId();
				sut.input(string.charAt(i));
				FsmTransition tr = new FsmTransition(getOrCreateState(testTreeSut.getCurrentState()), sut.getLastInput(), sut.getLastOutput(), getOrCreateState(++counter));
				addTransition(tr);
				int toId = sut.getCurrentState().getId();
				testTreeSut.input(string.charAt(i));
				
				tr.getTo().getProperties().put("name", toId);
				tr.getFrom().getProperties().put("name", fromId);
			}
			sut.setCurrentState(sut.getSut().getInitialState());
			testTreeSut.setCurrentState(testTreeSut.getSut().getInitialState());
		}
	}

	private FsmState getOrCreateState(FsmState currentState) {
		return getOrCreateState(currentState.getId());
	}
	
	private FsmState getOrCreateState(int id) {
		for (FsmState s: getStates()) {
			if(s.getId() == id) return s;
		}
		FsmState s = new FsmState(id);
		addState(s);
		return s;
	}

	private boolean isValidInput(FsmSUT s, char input) {
		for (FsmTransition tr : s.getCurrentState().getOut()) {
			if(tr.getInput().charAt(0)==(input)) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return preorder(getInitialState());
	}

	private String preorder(FsmState state) {
		String out = "["+state.getId();
		for (int i = 0; i < state.getOut().size(); i++) {
			FsmTransition tr = state.getOut().get(i);
			out+= "["+tr.getInput()+"/"+tr.getOutput()+" "+preorder(tr.getTo())+"]";
		}
		out+="]";
		return out;
	}

	
}
