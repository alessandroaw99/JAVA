import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class PlagiarismCheckerGUI {
    public static void main(String[] args) {
        int phraseLength = 5; // You can set your preferred phrase length here.

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int returnValue = fileChooser.showOpenDialog(null);

        List<File> files = new ArrayList<>();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            for (File file : selectedFiles) {
                files.add(file);
            }
        }

        DocumentComparison documentComparison = new DocumentComparison(files, phraseLength);
        List<ComparisonResult> comparisonResults = documentComparison.compareDocuments();


        GUIController guiController = new GUIController(comparisonResults);
        guiController.display();
    }
}
