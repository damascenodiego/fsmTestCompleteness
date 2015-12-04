import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.SimpleGraph;

import com.usp.icmc.labes.fsm.FsmModel;
import com.usp.icmc.labes.fsm.FsmState;
import com.usp.icmc.labes.fsm.FsmTest;
import com.usp.icmc.labes.fsm.FsmTestTree;
import com.usp.icmc.labes.fsm.FsmTransition;
import com.usp.icmc.labes.utils.DistinguishGraphUtils;
import com.usp.icmc.labes.utils.FsmTestingUtils;

public class CheckCompleteness {

	public static void main(String[] args) {

		DistinguishGraphUtils dgutils = DistinguishGraphUtils.getInstance();
		FsmTestingUtils testutils = FsmTestingUtils.getInstance();
		
		/* Algorithm 1. */
		/* Input: An FSM M and a test suite T .  */

		String fsmFile_str 	= args	[0];
		String testFile_str	= args	[1];

		File fsmFile 	= new File(fsmFile_str);
		File testFile 	= new File(testFile_str);

		FsmModel model = testutils.loadFsmModel(fsmFile);
		FsmTest test = testutils.loadFsmTest(testFile);

		/* 1. Build the distinguishability graph G of T . */
		FsmTestTree testTree = new FsmTestTree(model, test);
		UndirectedGraph<FsmState, DefaultEdge> dg;
		dg = dgutils.createDistinguishabilityGraph(testTree);
		
		/* 2. Let L be the empty set. */
		Set<FsmState> l_set = new HashSet<FsmState>();
		
		
		
		/* 3. Determine (by using the branch-and-bound approach)
		 * an n-clique K of G, such that there does not exist
		 * K' \in L with K \subseteq K'. 
		 * If no such a clique exists, 
		 * then terminate with the answer False.
		 */
		
		/*  
		 * 4. Find a sequence \alpha \in T \ K, such that either Lemma 2
		 * or Lemma 3 can be applied. If no such a sequence
		 * exists, go to Step 6.
		 */
		
		/*  
		 * 5. Include \alpha in K and go to Step 4. 
		 * */
		
		/* 
		 * 6. If K satisfies Theorem 1, then terminate with the
		 * answer True. 
		 * */
		
		/* 
		 * 7. Include K in L and go to Step 3. 
		 * */
//		l_set.addAll(clique);

		try {
			File testTreeFile 	= new File(testFile_str+"_tree.dot");
			testutils.saveTestTree(testTree,testTreeFile);
			
			File dgFile 	= new File(testFile_str+"_dg.dot");
			dgutils.saveDistinguishabilityGraph(dg,dgFile);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		/* Output: True, if T is n-complete according to Theorems 1 and 2. */
		System.out.println(model);
	}

	
}
