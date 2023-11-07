import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DocumentComparison {

    private int phraseLength;
    private List<TextFile> textFiles;

    public DocumentComparison(List<File> files, int phraseLength) {
        this.phraseLength = phraseLength;
        this.textFiles = new ArrayList<>();
        for (File file : files) {
            textFiles.add(new TextFile(file));
        }
    }

    public List<ComparisonResult> compareDocuments() {
        List<ComparisonResult> comparisonResults = new ArrayList<>();
        PhraseMatcher phraseMatcher = new PhraseMatcher(phraseLength);

        for (int i = 0; i < textFiles.size(); i++) {
            TextFile doc1 = textFiles.get(i);
            for (int j = i + 1; j < textFiles.size(); j++) {
                TextFile doc2 = textFiles.get(j);

                double phraseMatchPercentage = phraseMatcher.match(doc1.getContent(), doc2.getContent());

                Map<String, Integer> wordFrequency1 = doc1.getWordFrequency();
                Map<String, Integer> wordFrequency2 = doc2.getWordFrequency();

                ComparisonResult result = new ComparisonResult(doc1, doc2, phraseMatchPercentage, wordFrequency1, wordFrequency2);
                comparisonResults.add(result);
            }
        }

        Collections.sort(comparisonResults, new Comparator<ComparisonResult>() {
            @Override
            public int compare(ComparisonResult o1, ComparisonResult o2) {
                return Double.compare(o2.getPhraseMatchPercentage(), o1.getPhraseMatchPercentage());
            }
        });

        return comparisonResults;
    }
}
