import java.util.Scanner;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.Comparator;

public class PrimsAlgo {
    
    class Node1 {
 
        // Stores destination vertex in adjacency list
        int dest;
 
        // Stores weight of a vertex in the adjacency list
        int weight;
 
        // Constructor
        Node1(int a, int b)
        {
            dest = a;
            weight = b;
        }
    }

    static class Graph {
 
        // Number of vertices in the graph
        int V;
 
        // List of adjacent nodes of a given vertex
        LinkedList<Node1>[] adj;
 
        // Constructor
        Graph(int e)
        {
            V = e;
            adj = new LinkedList[V];
            for (int o = 0; o < V; o++)
                adj[o] = new LinkedList<>();
        }
    }
 
    // class to represent a node in PriorityQueue
    // Stores a vertex and its corresponding
    // key value
    class Node {
        int vertex;
        int key;
    }
 
    // Comparator class created for PriorityQueue
    // returns 1 if node0.key > node1.key
    // returns 0 if node0.key < node1.key and
    // returns -1 otherwise
    class comparator implements Comparator<Node> {
 
        @Override
        public int compare(Node node0, Node node1)
        {
            return node0.key - node1.key;
        }
    }
 
    // method to add an edge
    // between two vertices
    void addEdge(Graph graph, int src, int dest, int weight)
    {
        Node1 node0 = new Node1(dest, weight);
        Node1 node = new Node1(src, weight);
        graph.adj[src].addLast(node0);
        graph.adj[dest].addLast(node);
    }
 
    // method used to find the mst
    void prims_mst(Graph graph)
    {
        int totalCost = 0;
 
        // Whether a vertex is in PriorityQueue or not
        Boolean[] mstset = new Boolean[graph.V];
        Node[] e = new Node[graph.V];
 
        // Stores the parents of a vertex
        int[] parent = new int[graph.V];
 
        for (int o = 0; o < graph.V; o++)
            e[o] = new Node();
 
        for (int o = 0; o < graph.V; o++) {
 
            // Initialize to false
            mstset[o] = false;
 
            // Initialize key values to infinity
            e[o].key = Integer.MAX_VALUE;
            e[o].vertex = o;
            parent[o] = -1;
        }
 
        // Include the source vertex in mstset
        mstset[0] = true;
 
        // Set key value to 0
        // so that it is extracted first
        // out of PriorityQueue
        e[0].key = 0;
 
        TreeSet<Node> queue = new TreeSet<Node>(new comparator());
 
        for (int o = 0; o < graph.V; o++)
            queue.add(e[o]);
 
        // Loops until the queue is not empty
        while (!queue.isEmpty()) {
 
            // Extracts a node with min key value
            Node node0 = queue.pollFirst();
 
            // Include that node into mstset
            mstset[node0.vertex] = true;
 
            // For all adjacent vertex of the extracted vertex V
            for (Node1 iterator : graph.adj[node0.vertex]) {
 
                // If V is in queue
                if (mstset[iterator.dest] == false) {
                    // If the key value of the adjacent vertex is
                    // more than the extracted key
                    // update the key value of adjacent vertex
                    // to update first remove and add the updated vertex
                    if (e[iterator.dest].key > iterator.weight) {
                        queue.remove(e[iterator.dest]);
                        e[iterator.dest].key = iterator.weight;
                        queue.add(e[iterator.dest]);
                        parent[iterator.dest] = node0.vertex;
                        totalCost = totalCost + iterator.weight;
                    }
                }
            }
        }
 
        // Prints the vertex pair of mst
        for (int o = 1; o < graph.V; o++)
            System.out.println(parent[o] + " "
                               + "-"
                               + " " + o);
        
        System.out.println("\nThe total cost = "+ totalCost);
    }
 
    public static void main(String[] args)
    {
        /*
        V = 9
 
         0, 1, 4
         0, 7, 8
         1, 2, 8
         1, 7, 11
         2, 3, 7
         2, 8, 2
         2, 5, 4
         3, 4, 9
         3, 5, 14
         4, 5, 10
         5, 6, 2
         6, 7, 1
         6, 8, 6
         7, 8, 7

        */

        Scanner sc = new Scanner(System.in);
        int V;
        int source, destination, weight;

        System.out.print("\nEnter the number of vertices: ");
        V = sc.nextInt();

        // Creating the graph
        Graph graph = new Graph(V);

        PrimsAlgo p = new PrimsAlgo();

        while (true) {
            System.out.println("\nEnter -1 to stop.");
            System.out.print("\nEnter source: ");
            source = sc.nextInt();
            
            if (source == -1)
                break;
            else {
                System.out.print("\nEnter destination: ");
                destination = sc.nextInt();
                System.out.print("\nEnter weight: ");
                weight = sc.nextInt();
            
                // calling the function to add the edges
                p.addEdge(graph, source, destination, weight);
            }
        }
        sc.close();

        // Calling the MST function
        p.prims_mst(graph);
    }
}