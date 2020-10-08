import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {




	public static void main(String[] args) {
		
		List<Edge> edges;
		Scanner scan = new Scanner(System.in);
		String command;



		System.out.println("Olá, qual número de arestas?");
		command = scan.nextLine(); // o usuário deve passar o numero de arestas e depois o metodo de ordenação
		String numVertices = command.substring(0, command.indexOf(' '));
		String metodo = command.substring(command.indexOf(' ') + 1);
		System.out.println("metodo: "+metodo);
		System.out.println("vertices: "+numVertices);



		edges = readCsvFile("src/"+numVertices+"_vertices.csv");
		
		Graph graph = new Graph(edges);
		List<Edge> mst= graph.Kruskal(metodo);
		for(Edge e : mst) {
			System.out.println(e.v+" ,"+e.peso+","+e.w);			
		}

	}
	
	
	public static List<Edge> readCsvFile(String path){
		String Str;
		List<Edge> tableLine = new ArrayList<Edge>();


		//A estrutura try-catch é usada pois o objeto BufferedWriter exige que as
		//excessões sejam tratadas
		try {

		//Criação de um buffer para a ler de uma stream
		BufferedReader StrR = new BufferedReader(new FileReader(path));

	
		//Essa estrutura do looping while é clássica para ler cada linha
		//do arquivo 
		while((Str = StrR.readLine())!= null){
		//Aqui usamos o método split que divide a linha lida em um array de String
		//passando como parametro o divisor ";".
		String[] row = Str.split(",");

		
		tableLine.add(new Edge(Integer.parseInt(row[0]),Integer.parseInt(row[1]),Integer.parseInt(row[2])));
		


		}
		StrR.close();
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		} catch (IOException ex){
		ex.printStackTrace();
		}
		return tableLine;

		}

}
