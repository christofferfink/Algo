import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Opgave2 {
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
            int w = Integer.parseInt(input[2]);
            adjList[v].add(new int[]{u, w});
            adjList[u].add(new int[]{v, w});
        }

        // Brug Dijkstra's algoritme til at beregne den korteste rejsetid mellem alle togstationer
        int[][] trainDistances = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(trainDistances[i], Integer.MAX_VALUE);
            trainDistances[i][i] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
            pq.add(new int[]{i, 0});
            while (!pq.isEmpty()) {
                int[] curr = pq.poll();
                int node = curr[0];
                int dist = curr[1];
                if (dist > trainDistances[i][node]) {
                    continue;
                }
                for (int[] edge : adjList[node]) {
                    int neighbor = edge[0];
                    int distance = edge[1];
                    int newDist = dist + distance;
                    if (newDist < trainDistances[i][neighbor]) {
                        trainDistances[i][neighbor] = newDist;
                        pq.add(new int[]{neighbor, newDist});
                    }
                }
            }
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