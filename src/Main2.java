import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main2 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] input = br.readLine().split(" ");
        int Nstations = Integer.parseInt(input[0]);
        int Mtrainroute = Integer.parseInt(input[1]);
        int Fflyroute = Integer.parseInt(input[2]);
        int[][] trainMap = new int[Nstations][Nstations];

        // Udfyld togKort med input-tog-ruterne
        for (int i = 0; i < Mtrainroute; i++) {
            input = br.readLine().split(" ");
            int vStation = Integer.parseInt(input[0]);
            int uStation = Integer.parseInt(input[1]);
            int wStation = Integer.parseInt(input[2]);
            trainMap[vStation][uStation] = wStation;
            trainMap[uStation][vStation] = wStation;
        }


        int[][] flyRoute = new int[Fflyroute][2];
        for (int i = 0; i < Fflyroute; i++) {
            input = br.readLine().split(" ");
            flyRoute[i][0] = Integer.parseInt(input[0]);
            flyRoute[i][1] = Integer.parseInt(input[1]);
        }

        // Tjek hver fly-rute og afgør, om den skal beholdes eller annulleres
        for (int[] rute : flyRoute) {
            int travelTime = shortestTravelTime(trainMap, rute[0], rute[1]);
            System.out.println(travelTime > 120 ? "keep" : "cancel");
        }
    }

    /**
     * Beregner den korteste rejsetid mellem start- og slutstationer
     * ved hjælp af Dijkstras algoritme.*
     */
    private static int shortestTravelTime(int[][] trainMap, int start, int end) {
        int N = trainMap.length;
        int[] travelTime = new int[N];
        Arrays.fill(travelTime, Integer.MAX_VALUE);
        travelTime[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] node = pq.poll();
            int station = node[0];
            int tid = node[1];

            if (station == end) return tid;

            // Check all connected stations and update their travel times
            for (int i = 0; i < N; i++) {
                if (trainMap[station][i] != 0) {
                    int newTime = tid + trainMap[station][i];
                    if (newTime < travelTime[i]) {
                        travelTime[i] = newTime;
                        pq.add(new int[]{i, newTime});
                    }
                }
            }
        }

        return Integer.MAX_VALUE;
    }
}