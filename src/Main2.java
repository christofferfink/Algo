import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main2 {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Read the input
        String[] input = reader.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);
        int F = Integer.parseInt(input[2]);

        // Initialize the adjacency list for the train network
        ArrayList<int[]>[] adjList = new ArrayList[N];
        for (int i = 0; i < N; i++) {
            adjList[i] = new ArrayList<int[]>();
        }

        // Read the train routes and build the adjacency list
        for (int i = 0; i < M; i++) {
            input = reader.readLine().split(" ");
            int v = Integer.parseInt(input[0]);
            int u = Integer.parseInt(input[1]);
            int w = Integer.parseInt(input[2]);
            adjList[v].add(new int[]{u, w});
            adjList[u].add(new int[]{v, w});
        }

        // Use Dijkstra's algorithm to calculate the shortest travel time between all pairs of train stations
        int[][] trainTimes = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(trainTimes[i], Integer.MAX_VALUE);
            trainTimes[i][i] = 0;
            PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[1] - b[1]);
            pq.add(new int[]{i, 0});
            while (!pq.isEmpty()) {
                int[] curr = pq.poll();
                int node = curr[0];
                int dist = curr[1];
                if (dist > trainTimes[i][node]) {
                    continue;
                }
                for (int[] edge : adjList[node]) {
                    int neighbor = edge[0];
                    int weight = edge[1];
                    int newDist = dist + weight;
                    if (newDist < trainTimes[i][neighbor]) {
                        trainTimes[i][neighbor] = newDist;
                        pq.add(new int[]{neighbor, newDist});
                    }
                }
            }
        }

        for (int i = 0; i < F; i++) {
            input = reader.readLine().split(" ");
            int v = Integer.parseInt(input[0]);
            int u = Integer.parseInt(input[1]);
            if (trainTimes[v][u] > 120) {
                System.out.println("keep");
            } else {
                System.out.println("cancel");
            }
        }
    }
}