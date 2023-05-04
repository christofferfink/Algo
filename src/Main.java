import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Læs N og M  ud fra første linje
        String[] input = reader.readLine().split(" ");
        int N = Integer.parseInt(input[0]);
        int M = Integer.parseInt(input[1]);

        // Læs størrelserne af komponenter fra anden linje
        input = reader.readLine().split(" ");
        int[] componentSize = new int[M];
        for (int i = 0; i < M; i++) {
            componentSize[i] = Integer.parseInt(input[i]);
        }

        // Beregn det mindste antal sammenkoblede komponenter, som Bob skal købe
        int minKomponenter = calculateMinComponents(N, componentSize);

        System.out.println(minKomponenter);
    }

    private static int calculateMinComponents(int N, int[] componoentSize) {
        // Sorter komponentstørrelserne i faldende rækkefølge
        Arrays.sort(componoentSize);
        int numberOfComponents = 0;
        int samletNoder = 0;

        // Start fra den største komponent og fortsæt med at tilføje, indtil N noder er nået
        for (int i = componoentSize.length - 1; i >= 0; i--) {
            samletNoder += componoentSize[i];
            numberOfComponents++;

            if (samletNoder >= N) {
                break;
            }
        }

        return numberOfComponents;
    }
}