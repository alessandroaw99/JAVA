import java.util.HashSet;
import java.util.Set;

public class PhraseMatcher {

    private int phraseLength;

    public PhraseMatcher(int phraseLength) {
        this.phraseLength = phraseLength;
    }

    public double match(String content1, String content2) {
        Set<String> phrases1 = generatePhrases(content1);
        Set<String> phrases2 = generatePhrases(content2);

        int matchedPhrases = 0;
        for (String phrase : phrases1) {
            if (phrases2.contains(phrase)) {
                matchedPhrases++;
                System.out.println("Matched phrase: " + phrase); // Debug statement
            }
        }

        int totalPhrases = phrases1.size() + phrases2.size();
        double matchPercentage = (double) matchedPhrases * 2 / totalPhrases * 100;
        System.out.println("Matched phrases: " + matchedPhrases + ", Total phrases: " + totalPhrases + ", Match percentage: " + matchPercentage); // Debug statement

        return matchPercentage;
    }

    private Set<String> generatePhrases(String content) {
        String[] words = content.toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");
        Set<String> phrases = new HashSet<>();

        for (int i = 0; i < words.length - phraseLength + 1; i++) {
            StringBuilder phrase = new StringBuilder();
            for (int j = 0; j < phraseLength; j++) {
                phrase.append(words[i + j]);
                if (j < phraseLength - 1) {
                    phrase.append(" ");
                }
            }
            phrases.add(phrase.toString());
        }

        return phrases;
    }
}
