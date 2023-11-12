import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class PasswordSafeGUI extends JFrame {

    private static final int MAX_ATTEMPTS = 5;
    private int attemptCount = 0;
    private String currentUsername;
    private String currentPassword;
    private Map<String, String> accounts = new HashMap<>();

    private JLabel statusLabel;
    private JComboBox<String> securityQuestionComboBox;

    public PasswordSafeGUI() {
        super("Password Safe");

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        statusLabel = new JLabel("Enter username and password:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(statusLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridy = 1;
        mainPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(10);
        gbc.gridy = 2;
        mainPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridy = 3;
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(10);
        gbc.gridy = 4;
        mainPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUsername = usernameField.getText();
                char[] inputPassword = passwordField.getPassword();
                currentPassword = new String(inputPassword);

                if (accounts.containsKey(currentUsername) && accounts.get(currentUsername).equals(currentPassword)) {
                    statusLabel.setText("Correct login. Welcome back!");
                    showSecurityQuestions();
                } else {
                    attemptCount++;
                    if (attemptCount >= MAX_ATTEMPTS) {
                        statusLabel.setText("Too many incorrect attempts. Try again later.");
                        loginButton.setEnabled(false);
                    } else {
                        statusLabel.setText("Incorrect login. Attempts left: " + (MAX_ATTEMPTS - attemptCount));
                    }
                }
            }
        });
        gbc.gridy = 5;
        mainPanel.add(loginButton, gbc);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = JOptionPane.showInputDialog("Enter a new username:");
                if (newUsername != null && newUsername.length() > 0 && !accounts.containsKey(newUsername)) {
                    String newPassword = JOptionPane.showInputDialog("Enter a new password:");
                    if (newPassword != null && newPassword.length() > 0) {
                        accounts.put(newUsername, newPassword);
                        statusLabel.setText("Account created successfully. Please log in.");
                    } else {
                        statusLabel.setText("Password not provided. Account creation failed.");
                    }
                } else {
                    statusLabel.setText("Username already exists or not provided. Account creation failed.");
                }
            }
        });
        gbc.gridy = 6;
        mainPanel.add(createAccountButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showSecurityQuestions() {
        JPanel securityPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel securityQuestionLabel = new JLabel("Select a security question:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        securityPanel.add(securityQuestionLabel, gbc);

        String[] securityQuestions = {"What is your favorite color?", "What is your pet's name?", "Where were you born?"};
        securityQuestionComboBox = new JComboBox<>(securityQuestions);
        gbc.gridy = 1;
        securityPanel.add(securityQuestionComboBox, gbc);

        JLabel answerLabel = new JLabel("Your answer:");
        gbc.gridy = 2;
        securityPanel.add(answerLabel, gbc);

        JTextField answerField = new JTextField(10);
        gbc.gridy = 3;
        securityPanel.add(answerField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedQuestion = (String) securityQuestionComboBox.getSelectedItem();
                String securityAnswer = answerField.getText();

                // Validate the answer (in a real-world scenario, you might want to use a secure validation mechanism)
                if (securityAnswer.equals("sampleAnswer")) {
                    statusLabel.setText("Security questions verified. You are now logged in!");
                    showPasswordResetOption();
                } else {
                    statusLabel.setText("Incorrect answer. Please try again.");
                }
            }
        });
        gbc.gridy = 4;
        securityPanel.add(submitButton, gbc);

        remove(getContentPane().getComponent(0)); // Remove the previous main panel
        add(securityPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showPasswordResetOption() {
        JButton resetButton = new JButton("Reset Password");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = JOptionPane.showInputDialog("Enter a new password:");
                if (newPassword != null && newPassword.length() > 0) {
                    accounts.put(currentUsername, newPassword);
                    statusLabel.setText("Password reset successfully. Please log in with the new password.");
                    resetUI();
                } else {
                    statusLabel.setText("Password not changed. Please try again.");
                }
            }
        });

        add(resetButton, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    private void resetUI() {
        remove(getContentPane().getComponent(0)); // Remove the security panel
        remove(getContentPane().getComponent(1)); // Remove the reset button
        add(new JPanel(), BorderLayout.CENTER); // Add an empty panel
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordSafeGUI());
    }
}
