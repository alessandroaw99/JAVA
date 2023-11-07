import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TextFile {
    private File file;
    private String content;
    private Map<String, Integer> wordFrequency;

    public TextFile(String filePath) {
        this(new File(filePath));
    }

    public TextFile(File file) {
        this.file = file;
        this.wordFrequency = new HashMap<>();
        loadContent();
        generateWordFrequency();
    }

    private void loadContent() {
        try {
            content = new String(Files.readAllBytes(Paths.get(file.getPath())));
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getPath());
            e.printStackTrace();
        }
    }

    private void generateWordFrequency() {
        String[] words = content.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");

        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
    }

    public String getFilePath() {
        return file.getPath();
    }

    public String getContent() {
        return content;
    }

    public Map<String, Integer> getWordFrequency() {
        return wordFrequency;
    }

    public String getName() {
        return file.getName();
    }
}
