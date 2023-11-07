import java.util.Map;

public class ComparisonResult {
    private TextFile file1;
    private TextFile file2;
    private double phraseMatchPercentage;
    private Map<String, Integer> wordFrequency1;
    private Map<String, Integer> wordFrequency2;

    public ComparisonResult(TextFile file1, TextFile file2, double phraseMatchPercentage, Map<String, Integer> wordFrequency1, Map<String, Integer> wordFrequency2) {
        this.file1 = file1;
        this.file2 = file2;
        this.phraseMatchPercentage = phraseMatchPercentage;
        this.wordFrequency1 = wordFrequency1;
        this.wordFrequency2 = wordFrequency2;
    }

    public TextFile getFile1() {
        return file1;
    }

    public TextFile getFile2() {
        return file2;
    }

    public double getPhraseMatchPercentage() {
        return phraseMatchPercentage;
    }

    public Map<String, Integer> getWordFrequency1() {
        return wordFrequency1;
    }

    public Map<String, Integer> getWordFrequency2() {
        return wordFrequency2;
    }
}
