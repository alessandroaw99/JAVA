import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GUIController {

    private List<ComparisonResult> comparisonResults;
    private JFrame frame;
    private JTable table;

    public GUIController(List<ComparisonResult> comparisonResults) {
        this.comparisonResults = comparisonResults;
    }

    public void display() {
        frame = new JFrame("Plagiarism Checker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        String[] columnNames = {"File 1", "File 2", "Match Percentage"};
        Object[][] rowData = new Object[comparisonResults.size()][3];

        for (int i = 0; i < comparisonResults.size(); i++) {
            ComparisonResult result = comparisonResults.get(i);
            rowData[i][0] = result.getFile1().getName();
            rowData[i][1] = result.getFile2().getName();
            rowData[i][2] = String.format("%.2f", result.getPhraseMatchPercentage()) + "%";
        }

        table = new JTable();
        table.setModel(new DefaultTableModel(rowData, columnNames));
        table.setPreferredScrollableViewportSize(new Dimension(750, 400));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    ComparisonResult comparisonResult = comparisonResults.get(row);
                    JOptionPane.showMessageDialog(frame,
                            "Phrase Match Percentage: " + String.format("%.2f", comparisonResult.getPhraseMatchPercentage()) + "%" +
                                    "\n\nFile 1 Word Frequency:\n" + comparisonResult.getWordFrequency1() +
                                    "\n\nFile 2 Word Frequency:\n" + comparisonResult.getWordFrequency2(),
                            comparisonResult.getFile1().getName() + " vs " + comparisonResult.getFile2().getName(),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}

