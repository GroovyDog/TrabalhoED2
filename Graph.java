import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RunnableScheduledFuture;

public class Graph {
	List<Vertex> V;
	List<Edge> E = new ArrayList<Edge>();

	public Graph(List<Edge> edges) {
		this.E = edges;
	}

	// use E.set(5,(edge)) para adicionar no quinto espaço do vetor
	// use E.get(5) para saber o valor do quinto espaço do vetor
	public <string> List<Edge> ordenate(List<Edge> arestas, string metodo){
		if(metodo.equals("bogo")){
			System.out.println("---Utilizando BogoSort---");
			return bogosort(arestas);
		}else if(metodo.equals("insert")) {
			System.out.println("---Utilizando InsertionSort---");
			return insertionSort(arestas);
		}else if(metodo.equals("quick")) {
			System.out.println("---Utilizando QuickSort---");
			return quickSort(arestas, 0, arestas.size()-1);
		}else if(metodo.equals("merge")){
			System.out.println("---Utilizando MergeSort---");
			return  mergeSort(arestas);
		}else if(metodo.equals("shell")){
			System.out.println("---Utilizando ShellSort---");
			return  shellSort(arestas);
		}else if(metodo.equals("heap")){
			System.out.println("---Utilizando HeapSort---");
			return heapSort(arestas, arestas.size()-1);
		}else if(metodo.equals("parcial")){
			System.out.println("---Utilizando QuickSort(Inserção Parcial)---");
			return quickParcialInsert(arestas,0, arestas.size()-1);
		}else if(metodo.equals("final")){
			System.out.println("---Utilizando InsertionSort(Inserção Final)---");
			return quickFinalInsert(arestas, 0, arestas.size()-1);
		}
		System.out.println("-------- Nenhum médoto selecionado---------");
		return arestas;
	}

	public List<Edge>bogosort(List<Edge> arestas){
		Collections.shuffle(arestas);
		while(!isSorted(arestas)) {
			Collections.shuffle(arestas);
		}
		return arestas;


	}

	public List<Edge>insertionSort(List<Edge> arestas){
		int i, j;
		for (i = 1; i < arestas.size(); i++) {
			Edge key = arestas.get(i);
			j = i;
			while((j > 0) && (arestas.get(j - 1).peso > key.peso)) {
				arestas.set(j,arestas.get(j - 1));
				j--;
			}
			arestas.set(j,key);
		}
		return arestas;
	}
	/*
	public List<Edge> quickSort(List<Edge> arestas) {
		if(arestas.size() <= 1){
			return  arestas;
		}

		int meio = (int) Math.ceil((double)arestas.size()/2);
		Edge pivo = arestas.get(meio);

		List<Edge> less = new ArrayList<Edge>();
		List<Edge> greater = new ArrayList<Edge>();

		for(int i = 0; i <arestas.size(); i++){
			if(arestas.get(i).peso <= pivo.peso){
				if(i == meio){
					continue;
				}
				less.add(arestas.get(i));
			}else {
				greater.add(arestas.get(i));
			}
		}

		return concatenar(quickSort(less), pivo, quickSort(greater));
	}

	public List<Edge> concatenar(List<Edge> less, Edge pivo, List<Edge>greater){
		List<Edge> lista = new ArrayList<Edge>();

		for (int i = 0; i < less.size(); i++) {
			lista.add(less.get(i));
		}

		lista.add(pivo);

		for (int i = 0; i < greater.size(); i++) {
			lista.add(greater.get(i));
		}

		return lista;
	}
	*/

	public int partition (List<Edge> arestas, int inicio, int fim) { // fim = arestas.size()
		Edge pivot = arestas.get(fim);
		int pIndex = inicio;

		for (int i = inicio; i < fim; i++) {
			if (arestas.get(i).peso <= pivot.peso) {
				Edge temp = arestas.get(i);
				arestas.set(i, arestas.get(pIndex));
				arestas.set(pIndex, temp);
				pIndex++;
			}
		}

		Edge temp = arestas.get(fim);
		arestas.set(fim, arestas.get(pIndex));
		arestas.set(pIndex, temp);

		return pIndex;
	}

	public List<Edge> quickSort(List<Edge> arestas, int inicio, int fim) {
		if (inicio >= fim)
			return arestas;

		int pivot = partition(arestas, inicio, fim);
		quickSort(arestas, inicio, pivot - 1);
		quickSort(arestas, pivot + 1, fim);

		return  arestas;
	}

	public List<Edge> quickParcialInsert(List<Edge> arestas, int inicio, int fim) {

		while (inicio < fim) {
			if (fim - inicio < 10) {
				insertionSort(arestas);
				break;
			}

			else {
				int pivot = partition(arestas, inicio, fim);

				if (pivot - inicio < fim - pivot) {
					quickParcialInsert(arestas, inicio, pivot - 1);
					inicio = pivot + 1;
				} else {
					quickParcialInsert(arestas, pivot + 1, fim);
					fim = pivot - 1;
				}
			}
		}
		return arestas;
	}

	public List<Edge> quickFinalInsert(List<Edge> arestas, int inicio, int fim) {
		quickFinal(arestas, inicio, fim);
		return insertionSort(arestas); // termina a ordenação do vetor quase ordenado
	}

	public List<Edge> quickFinal(List<Edge> arestas, int inicio, int fim) {
		if (fim - inicio >= 10){ // quando for < 10 ele não fará nada.
			int pivot = partition(arestas, inicio, fim);
			quickSort(arestas, inicio, pivot - 1);
			quickSort(arestas, pivot + 1, fim);
		}
		return  arestas;
	}

	public List<Edge> mergeSort(List<Edge> arestas) {
		List<Edge>  temp = new ArrayList<Edge>();
		return mergeMain(arestas, temp, 0, arestas.size()-1);
	}

	public  List<Edge> mergeMain(List<Edge> arestas, List<Edge> temp, int inicio, int fim){
		if(inicio < fim) {
			int meio = (inicio+fim)/2;
			mergeMain(arestas, temp, inicio, meio); //sort left half
			mergeMain(arestas, temp, meio+1, fim); //sort right half
			merge(arestas, temp, inicio, meio, fim); // merge
		}
		return arestas;
	}

	public void merge(List<Edge> arestas, List<Edge> temp, int inicio, int meio, int fim) {
		//NÃO ESQUECER: esse loop preenche a lista e impede que dê exception
		for(int i = inicio; i < fim + 1; i++) {
			temp.add(i, arestas.get(i));
		}

		int tempPos = inicio;
		int tempFim = meio + 1;
		int current = inicio;

		while(tempPos <= meio && tempFim <= fim) {
			if(temp.get(tempPos).peso <= temp.get(tempFim).peso) {
				arestas.set(current, temp.get(tempPos));
				tempPos++;
			} else {
				arestas.set(current, temp.get(tempFim));
				tempFim++;
			}
			current++;
		}

		int resto = meio - tempPos;
		for(int j=0; j <= resto; j++) {
			arestas.set(current+j, temp.get(tempPos+j));
		}

	}

	public List<Edge> shellSort(List<Edge> arestas){
		int h, j;
		Edge temp;

		for(h = 1; h < arestas.size(); h =(3*h) +1);

		while (h > 0){
			h = (h - 1) / 3;

			for (int i = h; i < arestas.size()-1; i++){
				temp = arestas.get(i);
				j = i;

				while (arestas.get(j-h).peso > temp.peso){
					arestas.set(j, arestas.get(j-h));
					j = j- h;
					if (j < h) break;
				}
				arestas.set(j, temp);
			}
		}
		return  arestas;
	}

	public List<Edge> heapSort(List<Edge> arestas, int lastLeaf){
		buildMaxHeap(arestas, lastLeaf);
		while (lastLeaf > 0) {
			swap(arestas, 0, lastLeaf);
			lastLeaf--;
			maxHeapify(arestas, 0, lastLeaf);
		}
		return arestas;
	}

	public void buildMaxHeap(List<Edge> a, int lastLeaf) {
		int lastNonLeaf = (lastLeaf - 1) / 2; // nodes lastNonLeaf+1 to lastLeaf are leaves
		for (int j = lastNonLeaf; j >= 0; j--)
			maxHeapify(a, j, lastLeaf);
	}

	private void swap(List<Edge> arestas, int i, int j) {
		Edge temp = arestas.get(i);
		arestas.set(i, arestas.get(j));
		arestas.set(j, temp);
	}

	private void maxHeapify(List<Edge> arestas, int i, int lastLeaf) {
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		int largest;

		if (left <= lastLeaf && arestas.get(left).peso > arestas.get(i).peso)
			largest = left;
		else
			largest = i;

		if (right <= lastLeaf && arestas.get(right).peso > arestas.get(largest).peso)
			largest = right;

		if (largest != i) {
			swap(arestas, i, largest);
			maxHeapify(arestas, largest, lastLeaf);
		}
	}


	public boolean isSorted(List<Edge> lista) {

		for(int i = 0; i < lista.size()-1; i++ ) {
			if(lista.get(i).peso>lista.get(i+1).peso) {
				return false;
			}
		}
		return true;

	}

	public List<Edge> Kruskal(String metodo){
		List<List<Integer>> floresta = createFloresta();
		List<Edge> mst = new ArrayList<Edge>();
		List<Edge> arestas = this.ordenate(this.E, metodo);


		while(!arestas.isEmpty()) {
			Edge aux = arestas.remove(0);
			List<Integer> tree1 = getTree(aux.v,floresta);
			List<Integer> tree2 = getTree(aux.w,floresta);
			if(tree1 != tree2) {
				floresta = removeTreeByVertex(aux.v,floresta);
				floresta = removeTreeByVertex(aux.w,floresta);

				tree1.addAll(tree2);

				floresta.add(tree1);
				mst.add(aux);
			}



		}

		return mst;
	}

	public  List<List<Integer>> removeTreeByVertex(int vertex, List<List<Integer>> floresta){
		List<Integer> removed;
		removed = getTree(vertex,floresta);
		floresta.remove(floresta.indexOf(removed));
		return floresta;
	}


	public boolean isConector(Edge aresta,List<List<Integer>> floresta) {
		List<Integer> source = getTree(aresta.v,floresta);
		List<Integer> target = getTree(aresta.w,floresta);
		if(source != target ) {
			if(isInTree(aresta.w,source) && isInTree(aresta.v,target)) {
				return true;
			}
		}

		return false;
	}
	public boolean isInTree(int vertice, List<Integer> tree) {
		if(tree.contains(vertice)){
			return true;
		}
		return false;
	}

	public List<Integer> getTree(int vertice,  List<List<Integer>> floresta){
		for(List<Integer> row:floresta) {
			if(row.contains(vertice)) {
				return row;
			}
		}
		return null;
	}



	public List<List<Integer>> createFloresta(){
		List<List<Integer>> floresta = new ArrayList<List<Integer>>();
		for(Edge e : this.E ) {
			List<Integer> aux1 = new ArrayList<Integer>();
			List<Integer> aux2 = new ArrayList<Integer>();
			aux1.add(e.v);
			aux2.add(e.w);
			if(!floresta.contains(aux1))
				floresta.add(aux1);
			if(!floresta.contains(aux2))
				floresta.add(aux2);
		}

		return floresta;
	}



}