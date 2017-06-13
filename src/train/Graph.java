package trains;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Graph {
	public int W[][];
	public HashMap<String, Integer> H = new HashMap<String, Integer>();
	public static String[] towns = {"A", "B", "C", "D", "E"};
	
	public Graph(String file){
		// TODO: Generalize H for all possible keys. Ask possible names.
		W = new int[5][5];
		
		for(int i = 0; i<5; i++){
			W[i][i] = Integer.MAX_VALUE;
			H.put(towns[i], i);
		}
	}
	
	public void readGraph(String file){
		File f_graph = new File(file);
	
		try {
			Scanner sc = new Scanner(f_graph);
			while(sc.hasNextLine()){
				String l = sc.nextLine();
				W[H.get(l.substring(0, 1))][H.get(l.substring(1, 2))] = Integer.parseInt(l.substring(2, l.length()));
			}
			
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		for(int i = 0; i < 5;i++){
			for(int j = 0; j< 5; j++){
				System.out.print(W[i][j]+ " ");
			}
			System.out.println(" ");
		}
		
	}
	
	public String distance(String route){
		int sum = 0, w;
		String left = route.substring(0,1);
		route = route.substring(2, route.length());
		
		for(String right : route.split("-")){
			w = W[H.get(left)][H.get(right)];
			if(w > 0)
				sum += w;
			else
				return "NO SUCH ROUTE"; 
			left = right;
		}
		return Integer.toString(sum);
	}
	
	public int trips(String type, String start, String end){
		Set<String> valid_routes = new HashSet<>();
		Set<String> actual_routes = new HashSet<>();
		
				
		return 0;
	}
		
	public int dijkstra(String start, String end, boolean debug){
		String actual = start;
		
	    // Weird case of do while because (u, u) = infty for all u (should be 0).
		
		// Initialize variables
		ArrayList<String>  Q = new ArrayList<String>();
		HashMap<String, ArrayList<String>> prev = new HashMap<String, ArrayList<String>>();
		HashMap<String, Integer> dist = new HashMap<String, Integer>();
		
		Integer min_iter = Integer.MAX_VALUE;
		Integer disti = Integer.MAX_VALUE;
		String towna;
		
		int w = 0;
		
		for(String town : towns){
			Q.add(town);
			dist.put(town, Integer.MAX_VALUE);
			prev.put(town, new ArrayList<>());
		}
		
		dist.put(start, 0);
		prev.get(start).add(start);
			
		do{
			min_iter = Integer.MAX_VALUE;
			disti = Integer.MAX_VALUE;
			towna = "";
			
			for (HashMap.Entry<String, Integer> entry : dist.entrySet()) {
			    towna = entry.getKey();
			    disti = entry.getValue();
			    
			    // If the node is in Q and distance is minimum, node is actual
			    if(Q.contains(towna) && disti < min_iter)
			    	actual = towna;
			    	min_iter = disti;
			}
			
			// Remove best option from Q
			Q.remove(actual);
			
			if(debug){
				for(String q : Q){
					System.out.print(q + " ");
				}
				
			    System.out.println("Actual es: "+ actual);	
			}
			
			// For every neighbor of actual
			// TODO: We need a linked list.
			for(String town : towns){
				w = W[H.get(actual)][H.get(town)];
				if(w > 0 && w < Integer.MAX_VALUE){
					if(debug){
						System.out.println(W[H.get(actual)][H.get(town)]);
						System.out.println(dist.get(town));
						System.out.println(H.get(town));
					}
					if(disti + w  < dist.get(town)){
						// New distance, clear neighbors. Set distance
						if(debug)
							System.out.println(town +" desde <");
						dist.put(town, w);
						prev.put(town, new ArrayList<>());
						prev.get(start).add(actual);						
					}else if(disti + w == dist.get(town)){
						// We have a tie in the best distance. So we add a to the previous.
						if(debug)
							System.out.println(town+" desde ==");
						prev.get(start).add(actual);
					}	
				}
			}
		}while(actual != end);
		
		return dist.get(end);
	}
	
}
