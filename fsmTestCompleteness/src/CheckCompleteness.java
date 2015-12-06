import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BronKerboschCliqueFinder;

import com.usp.icmc.labes.fsm.FsmModel;
import com.usp.icmc.labes.fsm.FsmState;
import com.usp.icmc.labes.fsm.FsmTest;
import com.usp.icmc.labes.fsm.FsmTestTree;
import com.usp.icmc.labes.utils.CheckingCompletenessUtils;
import com.usp.icmc.labes.utils.DistinguishGraphUtils;
import com.usp.icmc.labes.utils.FsmTestingUtils;

public class CheckCompleteness {

	public static void main(String[] args) {

		/* verifies the number of arguments */
		if(args.length != 2) {
			System.out.println("*** Invalid arguments! See README.md file in the github repositorie. ***");
			System.exit(1);
		}
		
		CheckingCompletenessUtils ccutils = CheckingCompletenessUtils.getInstance(); 
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

		BronKerboschCliqueFinder<FsmState, DefaultEdge> cliqueFinder = new BronKerboschCliqueFinder<FsmState, DefaultEdge>(dg);
		Collection<Set<FsmState>> maxCliques = cliqueFinder.getAllMaximalCliques();

		Set<FsmState> k_set = new HashSet<FsmState>();
		Set<FsmState> t = new HashSet<FsmState>(); t.addAll(testTree.getStates());
		Map<Integer,Set<FsmState>> labels = new HashMap<Integer,Set<FsmState>>();

		for (Set<FsmState> set : maxCliques) {
						
			if(!ccutils.checkClique(set, model.getStates().size())) continue;
			
			/* Optimization of the choose of click (all items of the clique already labeled) */
			//if (k_set.containsAll(set)) continue;

			k_set.clear(); k_set.addAll(set);

			if(!Collections.disjoint(l_set,k_set)) continue;

			labels.clear();
			int label_int = 0;
			for (FsmState s : k_set) {
				labels.putIfAbsent(label_int, new HashSet<FsmState>());
				labels.get(label_int).add(s);
				label_int++;
			}

			for (int i = 0; i < testTree.getStates().size()-model.getStates().size(); i++) {
				// 4. Find a sequence \alpha \in T \ K
				for (FsmState alpha: t) {
					if(k_set.contains(t)) continue; 
					// such that either Lemma 2 or Lemma 3 can be applied.

					if(ccutils.canApplyLemma2(alpha,labels,k_set,dg) || ccutils.canApplyLemma3(alpha,labels,k_set,dg)){
						/* 5. Include \alpha in K and go to Step 4. */
						k_set.add(alpha);
					}
				}
			}
			
			/* If no such a sequence exists, go to Step 6. */
			/* 6. If K satisfies Theorem 1, then terminate with the answer True. */
			if(ccutils.satisfiesTheorem1(k_set,model,testTree)) {
				/* Output: True, if T is n-complete according to Theorems 1 and 2. */
				//for (FsmState s : k_set)  System.out.println(testutils.getSequence(s));
				
				File testTreeFile 	= new File(testFile_str+".test_tree.dot");
				ccutils.saveTestTreeAsDotFile(testTree,testTreeFile);

				File dgFile 	= new File(testFile_str+".dg.dot");
				ccutils.saveDistinguishingGraphAsDotFile(dg,dgFile);

				System.out.println("the set is n-complete!");
				System.exit(0);
			}

			/* 7. Include K in L and go to Step 3. */
			l_set.addAll(k_set);
		}
		/* Output: False, if T is not n-complete according to Theorems 1 and 2. */
		System.out.println("the set is not n-complete");
		System.exit(1);
	}


}
