package com.usp.icmc.labes.fsm;

import java.util.ArrayList;
import java.util.List;

public class FsmTest extends FsmElement{

	List<String> testSequence;
	
	public FsmTest() {
		super();
		testSequence = new ArrayList<String>();
	}
	
	public List<String> getTestSequence() {
		return testSequence;
	}
	
	public void setTestSequence(List<String> testSequence) {
		this.testSequence = testSequence;
	}
	
	
}
