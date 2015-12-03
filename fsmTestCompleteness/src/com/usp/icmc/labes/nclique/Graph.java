package com.usp.icmc.labes.nclique;
import java.util.Iterator;
import java.util.Set;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.BronKerboschCliqueFinder;

public class Graph {	
	
	static UndirectedGraph<Object, DefaultEdge> graph;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//ListenableGraph<Object, DefaultEdge> graph = new ListenableUndirectedGraph<Object, DefaultEdge>(DefaultEdge.class);
		graph = new SimpleGraph<Object, DefaultEdge>(DefaultEdge.class);		
		
		// add vertex 0-15
		for (int i=0; i<=15; i++)
			graph.addVertex(Integer.toString(i));
        
        // add vertex 0
		graph.addEdge( "0", "1" );
		graph.addEdge( "0", "3" );
		graph.addEdge( "0", "4" );
		graph.addEdge( "0", "6" );
		graph.addEdge( "0", "7" );
		graph.addEdge( "0", "8" );
		graph.addEdge( "0", "9" );
		graph.addEdge( "0", "12" );
		graph.addEdge( "0", "14" );
        
		// add vertex 1
		graph.addEdge( "1", "4" );
		graph.addEdge( "1", "6" );
		graph.addEdge( "1", "9" );
		graph.addEdge( "1", "13" );
		graph.addEdge( "1", "14" );
        
		// add vertex 2
		graph.addEdge( "2", "4" );
		graph.addEdge( "2", "6" );
		graph.addEdge( "2", "10" );
		graph.addEdge( "2", "14" );
        
		// add vertex 3		
		graph.addEdge( "3", "11" );
        
		// add vertex 4
		graph.addEdge( "4", "7" );
		graph.addEdge( "4", "8" );
		graph.addEdge( "4", "9" );
		graph.addEdge( "4", "10" );
		graph.addEdge( "4", "12" );
		graph.addEdge( "4", "13" );
        
		// add vertex 5
		graph.addEdge( "6", "7" );
		graph.addEdge( "6", "8" );
		graph.addEdge( "6", "9" );
		graph.addEdge( "6", "10" );
		graph.addEdge( "6", "12" );
		graph.addEdge( "6", "13" );
        
		// add vertex 6
		graph.addEdge( "7", "12" );
		graph.addEdge( "7", "13" );
		graph.addEdge( "7", "14" );
        
		// add vertex 7
		graph.addEdge( "8", "12" );
		graph.addEdge( "8", "13" );
		graph.addEdge( "8", "14" );
        
		// add vertex 9
		graph.addEdge( "9", "13" );
		graph.addEdge( "9", "14" );
        
		// add vertex 9
		graph.addEdge( "10", "14" );
        
		// add vertex 12
		graph.addEdge( "12", "13" );
		graph.addEdge( "12", "14" );
        
		// add vertex 13
		graph.addEdge( "13", "14" );
        		
		
		// show graph({V},{E})
		System.out.format("Grafo: %s\n\n", graph.toString());
                
		// clique finder
		BronKerboschCliqueFinder<Object, DefaultEdge> finder = new BronKerboschCliqueFinder<Object, DefaultEdge>( graph );        
		
		// get cliques
        Iterator<Set<Object>> itr = finder.getBiggestMaximalCliques().iterator();
                
        // show cliques        
        System.out.println("Cliques -> ");
        while(itr.hasNext()) {
           Object element = itr.next();           
           System.out.println(element + " : split -> " + parserClique(element)[1] + " " + parserClique(element)[2] 
        		   					  + " " + parserClique(element)[3] + " " + parserClique(element)[4]);
        }
        
        
        // vertex_0 contains an edge with vertex_6
        Object v1 = 0;
        Object v2 = 6;
        Object v3 = 5;
        System.out.println();
        System.out.println("edge between 0 and 6: " + graph.containsEdge(v1.toString(), v2.toString()));
        System.out.println("edge between 0 and 5:" + graph.containsEdge(v1.toString(), v3.toString()));
        
        // remove vertex 0
        graph.removeVertex("0");

        // show graph({V},{E}) no vertex 0
     	System.out.format("\nGrafo sem o nó 0 (arestas foram excluídas): %s", graph.toString());	
	}
	
	public static Object[] parserClique (Object s) {
		return s.toString().split("\\[|\\,|\\]");
		
	}
	
}
