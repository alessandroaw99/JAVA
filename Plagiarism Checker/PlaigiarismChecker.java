import java.util.List;

public class PlagiarismChecker {
    public static void main(String[] args) {
        // Load text files
        List<TextFile> textFiles = TextFileLoader.loadTextFiles("path/to/your/textFiles");

        // Set the phrase threshold
        int phraseThreshold = 4; // You can change this value as needed

        // Create a DocumentComparison instance
        DocumentComparison documentComparison = new DocumentComparison(textFiles, phraseThreshold);

        // Compare documents
        List<ComparisonResult> comparisonResults = documentComparison.compareDocuments();

        // Sort the comparison results based on phrase match percentage
        comparisonResults.sort((result1, result2) ->
                Double.compare(result2.getPhraseMatchPercentage(), result1.getPhraseMatchPercentage()));

        // Visualize the results using a GUI
        ResultVisualizer visualizer = new ResultVisualizer(comparisonResults);
        visualizer.displayResults();
    }
}
