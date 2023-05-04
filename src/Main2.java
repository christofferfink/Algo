import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main2 {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] input = reader.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);
        int F = Integer.parseInt(input[2]);

        // Initialiser adjacenslisten for tognetværket
        ArrayList<int[]>[] adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<int[]>();
        }

        for (int i = 0; i < M; i++) {
            input = reader.readLine().split(" ");
            int v = Integer.parseInt(input[0]);
            int u = Integer.parseInt(input[1]);
            int d = Integer.parseInt(input[2]);
            adjList[v].add(new int[]{u, d});
            adjList[u].add(new int[]{v, d});
        }

        // Brug Dijkstra's algoritme til at beregne den korteste afstand mellem alle togstationer
        int[][] trainDistances = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(trainDistances[i], Integer.MAX_VALUE);
            trainDistances[i][i] = 0;
            int[] dist = new int[N];
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[i] = 0;
            boolean[] visited = new boolean[N];
            for (int j = 0; j < N; j++) {
                int u = -1;
                for (int k = 0; k < N; k++) {
                    if (!visited[k] && (u == -1 || dist[k] < dist[u])) {
                        u = k;
                    }
                }
                visited[u] = true;
                for (int[] edge : adjList[u]) {
                    int v = edge[0];
                    int distance = edge[1];
                    if (dist[u] != Integer.MAX_VALUE && dist[u] + distance < dist[v]) {
                        dist[v] = dist[u] + distance;
                    }
                }
            }
            trainDistances[i] = dist;
        }

        // Test, om turen kan gennemføres inden for 2 timer
        for (int i = 0; i < F; i++) {
            input = reader.readLine().split(" ");
            int v = Integer.parseInt(input[0]);
            int u = Integer.parseInt(input[1]);
            if (trainDistances[v][u] > 120) {
                System.out.println("keep");
            } else {
                System.out.println("cancel");
            }
        }
    }
}
