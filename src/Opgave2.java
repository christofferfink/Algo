import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Opgave2 {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] input = reader.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);
        int F = Integer.parseInt(input[2]);

        // Initialize adjacency list for the train network
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }

        // Read train routes and update adjacency list
        for (int i = 0; i < M; i++) {
            input = reader.readLine().split(" ");
            int v = Integer.parseInt(input[0]);
            int u = Integer.parseInt(input[1]);
            int w = Integer.parseInt(input[2]);
            graph.get(v).add(new Edge(u, w));
            graph.get(u).add(new Edge(v, w));
        }

        // Calculate shortest distances using Floyd-Warshall algorithm
        int[][] dist = floydWarshall(graph);

        for (int i = 0; i < F; i++) {
            input = reader.readLine().split(" ");
            int v = Integer.parseInt(input[0]);
            int u = Integer.parseInt(input[1]);
            if (dist[v][u] > 120) {
                System.out.println("keep");
            } else {
                System.out.println("cancel");
            }
        }
    }

    static int[][] floydWarshall(List<List<Edge>> graph) {
        int N = graph.size();
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
            for (Edge edge : graph.get(i)) {
                dist[i][edge.dest] = edge.weight;
            }
        }
        for (int k = 0; k < N; k++) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
        return dist;
    }

    static class Edge {
        int dest;
        int weight;

        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }
}
