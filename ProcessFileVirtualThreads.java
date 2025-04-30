import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ProcessFileVirtualThreads {

    public static void main(String[] args) throws IOException {

        Path pathFile = Path.of("file.txt");
        Path pathFile2 = Path.of("file2.txt");

        if (!Files.exists(pathFile2)) {
            Files.createFile(pathFile2);
        }

        long startTime = System.nanoTime();

        List<String> lines = Files.readAllLines(pathFile);
        int sizeBlock = 150;

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 1; i <= lines.size(); i += sizeBlock) {
                int start = i;
                int end = Math.min(i + sizeBlock, lines.size());

                for (int j = start; j < end; j++) {
                    String linha = lines.get(j);
                    Files.writeString(
                        pathFile2,
                        linha + System.lineSeparator(),
                        StandardOpenOption.APPEND
                    );
                }
                
            }
        }

        long endTime = System.nanoTime();

        long segundos = (endTime - startTime) / 1_000_000_000;
        long durationInMillis = (endTime - startTime) / 1_000_000;

        System.out.println("Segundos: " + segundos);
        System.out.println("duração em milli segundos. " + durationInMillis);
    }
}
