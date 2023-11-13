import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ContadorPalabrasFich {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String input;
            while (true) {
                System.out.print("cuentaPalabrasFich> ");
                input = br.readLine();

                if (input.isEmpty()) {
                    break;
                }

                ejecutarComandoWC(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ejecutarComandoWC(String filename) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("wc", "-l", "-w", "-m", filename);
            processBuilder.redirectErrorStream(true); // Redirige la salida de error al mismo flujo de entrada
            Process process = processBuilder.start();

            // Lee la salida del proceso
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                List<String> outputLines = new ArrayList<>();
                while ((line = reader.readLine()) != null) {
                    outputLines.add(line);
                }

                // Espera a que el proceso hijo termine
                int exitCode = process.waitFor();

                // Imprime la salida del comando wc
                for (String outputLine : outputLines) {
                    System.out.println(outputLine);
                }

                System.out.println("Proceso hijo terminado con código de salida: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}