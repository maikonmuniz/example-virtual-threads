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
            for (int linePosition = 1; linePosition <= lines.size(); linePosition += sizeBlock) {
                int start = linePosition;
                int end = Math.min(linePosition + sizeBlock, lines.size());

            addLine (start, end, lines);
            }
        }

        long endTime = System.nanoTime();

        long segundos = (endTime - startTime) / 1_000_000_000;
        long durationInMillis = (endTime - startTime) / 1_000_000;

        System.out.println("Segundos: " + segundos);
        System.out.println("duração em milli segundos. " + durationInMillis);
    }

    public static void addLine (
        int start,
        int end,
        List<String> lines
        ) throws IOException {
        
        Path pathFile2 = Path.of("file2.txt");

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
