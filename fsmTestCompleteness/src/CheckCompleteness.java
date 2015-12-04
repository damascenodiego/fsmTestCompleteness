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

import com.usp.icmc.labes.fsm.FsmModel;
import com.usp.icmc.labes.fsm.FsmState;
import com.usp.icmc.labes.fsm.FsmTest;
import com.usp.icmc.labes.fsm.FsmTestTree;
import com.usp.icmc.labes.fsm.FsmTransition;

public class CheckCompleteness {

	public static final String property_name = "_name_";
	public static final String label_name = "_label_";

	public static void main(String[] args) {

		String fsmFile_str 	= args	[0];
		String testFile_str	= args	[1];

		File fsmFile 	= new File(fsmFile_str);
		File testFile 	= new File(testFile_str);

		FsmModel model = loadFsmModel(fsmFile);
		FsmTest test = loadFsmTest(testFile);

		FsmTestTree testTree = new FsmTestTree(model, test);


		try {
			File testTreeFile 	= new File(testFile_str+"_tree.dot");
			saveTestTree(testTree,testTreeFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(model);
	}

	private static FsmTest loadFsmTest(File testFile) {
		FsmTest test = new FsmTest();
		test.getProperties().put(File.class, testFile);
		test.getProperties().put(property_name,testFile.getName());

		try {
			BufferedReader br = new BufferedReader(new FileReader(testFile));


			while (br.ready()) {
				String line = br.readLine();
				test.getTestSequence().add(line);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return test;

	}

	private static FsmModel loadFsmModel(File fsmFile) {
		FsmModel fsm = new FsmModel(fsmFile.getName());
		fsm.getProperties().put(File.class, fsmFile);
		fsm.getProperties().put(property_name,fsmFile.getName());

		try {
			BufferedReader br = new BufferedReader(new FileReader(fsmFile));

			String source = "";
			String target = "";
			String in = "";
			String out = "";

			Map<Integer,FsmState> states = new HashMap<Integer,FsmState>();

			while (br.ready()) {
				String line[] = br.readLine().split("\\--|\\/|\\->");

				source = line[0].trim();
				in = line[1].trim();
				out = line[2].trim();
				target = line[3].trim();
				//System.out.println(source + "," + in + "," + out + "," + target);

				states.putIfAbsent(source.hashCode(), createState(source));
				states.putIfAbsent(target.hashCode(), createState(target));

				FsmState source_state = states.get(source.hashCode());
				FsmState target_state = states.get(target.hashCode());
				FsmTransition transition = new FsmTransition(source_state,in,out,target_state);

				if(fsm.getInitialState()==null) fsm.setInitialState(source_state);

				fsm.addInput(in);
				fsm.addOutput(out);
				fsm.addState(source_state);
				fsm.addState(target_state);
				fsm.addTransition(transition);

			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return fsm;

	}

	private static FsmState createState(String name) {
		FsmState out = new FsmState(name.hashCode());
		out.getProperties().put(property_name, name);
		return out;
	}


	public static void saveTestTree(FsmTestTree fsm, File f) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(f);

		int from;
		String in;
		String out;
		int to;

		List<FsmTransition> transit = new ArrayList<FsmTransition>();
		transit.addAll(fsm.getTransitions());

		pw.println("digraph g {");

		pw.println("\t{");
		List<Integer> ids = new ArrayList<Integer>();
		for (FsmState st : fsm.getStates()) ids.add((Integer) st.getProperties().get("name")); 		
		for (FsmState st : fsm.getStates()) {
			pw.println("\t\t"+ 
					Integer.toString(st.getId())
					+" [style=filled, fillcolor="+getColor(ids.indexOf(st.getProperties().get("name")))+ "];");
		}
		pw.println("\t}");


		for (FsmTransition tr : transit) {
			from 	= tr.getFrom().getId();
			in 		= tr.getInput();
			out 	= tr.getOutput();
			to 		= tr.getTo().getId();
			pw.println("\t"+Integer.toString(from)
			+" -> "
			+Integer.toString(to) 			
			+" [label=\""+in+" / "+out+"\"];");
		}
		pw.println("}");
		pw.close();
	}
	private static String getColor(int num){
		String[] colors = {"aliceblue","beige","blueviolet","chocolate","cyan","darkgreen","darkorange","darkslateblue","deeppink","firebrick","ghostwhite","green","indigo","lawngreen","lightgoldenrodyellow","lightsalmon","lightsteelblue","magenta","mediumpurple","mediumvioletred","navajowhite","orange","paleturquoise","pink","rosybrown","seagreen","slateblue","steelblue","turquoise","yellow","antiquewhite","bisque","brown","coral","darkblue","darkgrey","darkorchid","darkslategray","deepskyblue","floralwhite","gold","greenyellow","ivory","lemonchiffon","lightgray","lightseagreen","lightyellow","maroon","mediumseagreen","midnightblue","navy","orangered","palevioletred","plum","royalblue","seashell","slategray","tan","violet","yellowgreen","aqua","black","burlywood","cornflowerblue","darkcyan","darkkhaki","darkred","darkslategrey","dimgray","forestgreen","goldenrod","honeydew","khaki","lightblue","lightgreen","lightskyblue","lime","mediumaquamarine","mediumslateblue","mintcream","oldlace","orchid","papayawhip","powderblue","saddlebrown","sienna","slategrey","teal","wheat","aquamarine","blanchedalmond","cadetblue","cornsilk","darkgoldenrod","darkmagenta","darksalmon","darkturquoise","dimgrey","fuchsia","gray","hotpink","lavender","lightcoral","lightgrey","lightslategray","limegreen","mediumblue","mediumspringgreen","mistyrose","olive","palegoldenrod","peachpuff","purple","salmon","silver","snow","thistle","white","azure","blue","chartreuse","crimson","darkgray","darkolivegreen","darkseagreen","darkviolet","dodgerblue","gainsboro","grey","indianred","lavenderblush","lightcyan","lightpink","lightslategrey","linen","mediumorchid","mediumturquoise","moccasin","olivedrab","palegreen","peru","red","sandybrown","skyblue","springgreen","tomato","whitesmoke"};
		
		if(colors.length<num) return colors[0];
		else return colors[num];
		
	}
	
}
