import java.util.*;

public class Main2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int Nstations = sc.nextInt();
        int Mtrainroute = sc.nextInt();
        int Fflyroute = sc.nextInt();
        int[][] togKort = new int[Nstations][Nstations]; // Nabo-matrix for tog-ruter

        // Udfyld togKort med input-tog-ruterne
        for (int i = 0; i < Mtrainroute; i++) {
            int vStation = sc.nextInt();
            int uStation = sc.nextInt();
            int wStation = sc.nextInt();
            togKort[vStation][uStation] = wStation;
            togKort[uStation][vStation] = wStation;
        }


        int[][] flyRoute = new int[Fflyroute][2];
        for (int i = 0; i < Fflyroute; i++) {
            flyRoute[i][0] = sc.nextInt(); // Station v
            flyRoute[i][1] = sc.nextInt(); // Station u
        }

        // Tjek hver fly-rute og afgør, om den skal beholdes eller annulleres
        for (int[] rute : flyRoute) {
            int rejseTid = shortestTravelTime(togKort, rute[0], rute[1]);
            System.out.println(rejseTid > 120 ? "behold" : "annuller");
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