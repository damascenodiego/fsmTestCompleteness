import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.usp.icmc.labes.fsm.FsmModel;
import com.usp.icmc.labes.fsm.FsmState;
import com.usp.icmc.labes.fsm.FsmTransition;

public class CheckCompleteness {

	
	public static final String property_name = "_name_";
	
	public static void main(String[] args) {

		String fsmFile_str 	= args	[0];
		String testFile_str	= args	[1];
		
		File fsmFile 	= new File(fsmFile_str);
		File testFile 	= new File(testFile_str);
		
		FsmModel fsm = loadFsmModel(fsmFile);
		FsmTest test = loadFsmModel(fsmFile);
		
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

}
