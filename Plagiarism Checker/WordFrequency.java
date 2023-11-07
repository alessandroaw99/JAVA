import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WordFrequency {
    public static Map<String, Integer> computeFrequency(String content) {
        String[] words = content.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");
        Map<String, Integer> wordFrequency = new HashMap<>();

        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }

        return wordFrequency;
    }

    public static Map<String, Integer> sortByFrequency(Map<String, Integer> wordFrequency) {
        Map<String, Integer> sortedWordFrequency = new TreeMap<>((word1, word2) -> {
            int frequency1 = wordFrequency.get(word1);
            int frequency2 = wordFrequency.get(word2);

            if (frequency1 != frequency2) {
                return frequency2 - frequency1;
            } else {
                return word1.compareTo(word2);
            }
        });

        sortedWordFrequency.putAll(wordFrequency);
        return sortedWordFrequency;
    }
}
