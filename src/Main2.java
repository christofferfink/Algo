import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Integer.parseInt;

public class Main2 {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] input = reader.readLine().split(" ");
        int N = parseInt(input[0]);
        int M = parseInt(input[1]);
        int F = parseInt(input[2]);

        // Initialize adjacency list for the train network
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            graph.add(new ArrayList<>());
        }

        // Read train routes and update adjacency list
        for (int i = 0; i < M; i++) {
            input = reader.readLine().split(" ");
            if (input.length == 3) {
                int v = parseInt(input[0]);
                int u = parseInt(input[1]);
                int w = parseInt(input[2]);
                graph.get(v).add(new Edge(u, w));
                graph.get(u).add(new Edge(v, w));
            } else {
                System.err.println("Invalid input: " + Arrays.toString(input));
            }
        }

        // Calculate shortest distances using Dijkstra's algorithm
        int[][] dist = new int[N][N];
        for (int i = 0; i < N; i++) {
            dist[i] = dijkstra(graph, i);
        }

        for (int i = 0; i < F; i++) {
            input = reader.readLine().split(" ");
            if (input.length == 2) {
                int v = parseInt(input[0]);
                int u = parseInt(input[1]);
                if (dist[v][u] > 120) {
                    System.out.println("keep");
                } else {
                    System.out.println("cancel");
                }
            } else {
                System.err.println("Invalid input: " + Arrays.toString(input));
            }
        }
    }

    static int[] dijkstra(List<List<Edge>> graph, int start) {
        int[] dist = new int[graph.size()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge curr = pq.poll();
            int node = curr.dest;
            int weight = curr.weight;

            if (weight > dist[node]) {
                continue;
            }

            for (Edge next : graph.get(node)) {
                int alt = dist[node] + next.weight;
                if (alt < dist[next.dest]) {
                    dist[next.dest] = alt;
                    pq.offer(new Edge(next.dest, alt));
                }
            }
        }

        return dist;
    }

    static class Edge implements Comparable<Edge> {
        int dest;
        int weight;

        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }
}
