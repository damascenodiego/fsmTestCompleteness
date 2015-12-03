import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;

public class FSMModel {

	static DirectedGraph<Object, DefaultEdge> fsm;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		fsm = new DirectedMultigraph<Object, DefaultEdge>(DefaultEdge.class);		
		
		loadFile();
	}
	
	static public void loadFile () {
		
		JFileChooser chooser = new JFileChooser();
		
		//int result = chooser.showDialog(null, "Open");
		int result = 0;
		
		if (result == JFileChooser.APPROVE_OPTION){
			
			Scanner scanner = null;
			
			try {
				//scanner = new Scanner(new File( chooser.getSelectedFile().getAbsolutePath() ));
				scanner = new Scanner(new File("./data/mef-class.txt"));
				
				String source = "";
				String target = "";
				String in = "";
				String out = "";
				
				while (scanner.hasNext()) {
					String line[] = scanner.nextLine().split("\\--|\\/|\\->");
		           
		            source = line[0].trim();
		            in = line[1].trim();
		            out = line[2].trim();
		            target = line[3].trim();
					
					System.out.println(source + "," + in + "," + out + "," + target);
		        }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			
			} finally {				
				scanner.close();
			}
		
		}
	}
	
}