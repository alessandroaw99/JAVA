import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

// ... (existing imports)

public class PasswordSafeGUI extends JFrame {
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    private static final String ACCOUNTS_FILE = "passwordSafe.dat";
    private static final Insets STANDARD_INSETS = new Insets(5, 5, 5, 5);


    private boolean showFullPassword = false;
    private int attemptCount = 0;
    private String currentUsername;
    private String currentPassword;
    private Map<String, String> accounts = new HashMap<>();
    private Map<String, String[]> securityQuestionsDatabase = new HashMap<>();

    private JPanel mainPanel;
    private GridBagConstraints gbc;

    public PasswordSafeGUI() {
        super("Password Safe");

        loadAccounts();
        setupLookAndFeel();

        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        mainPanel = createLoginPanel();
        add(mainPanel, gbc);

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel statusLabel = new JLabel("Enter username and password:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(statusLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin(usernameField, passwordField, statusLabel));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);
        
        
        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(e -> showCreateAccountForm());
        gbc.gridy = 4;
        panel.add(createAccountButton, gbc);

        JButton forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.addActionListener(e -> showForgotPasswordForm());
        gbc.gridy = 5;
        panel.add(forgotPasswordButton, gbc);

        return panel;
    }
    private void handleLogin(JTextField usernameField, JPasswordField passwordField, JLabel statusLabel) {
        currentUsername = usernameField.getText();
        char[] inputPassword = passwordField.getPassword();
        currentPassword = new String(inputPassword);
    
        if (accounts.containsKey(currentUsername) && validatePassword(currentPassword, accounts.get(currentUsername))) {
            statusLabel.setText("Correct login. Welcome back!");
            showSecurityQuestions();  // This is where showSecurityQuestions() is called
        } else {
            attemptCount++;
            if (attemptCount >= MAX_LOGIN_ATTEMPTS) {
                statusLabel.setText("Too many incorrect attempts. Try again later.");
                disableLoginButton();
            } else {
                statusLabel.setText("Incorrect login. Attempts left: " + (MAX_LOGIN_ATTEMPTS - attemptCount));
            }
        }
    }

    private void disableLoginButton() {
        JButton loginButton = getLoginButton();
        if (loginButton != null) {
            loginButton.setEnabled(false);
        }
    }

    private JButton getLoginButton() {
        Component[] components = mainPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if ("Login".equals(button.getText())) {
                    return button;
                }
            }
        }
        return null;
    }

    private void addAccount(String username, String hashedPassword) {
        accounts.put(username, hashedPassword);
    }
    
    private void showCreateAccountForm() {
        JFrame createAccountFrame = new JFrame("Create Account");
        createAccountFrame.setLayout(new GridLayout(7, 2));
    
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JLabel recoveryEmailLabel = new JLabel("Recovery Email:");
        JTextField recoveryEmailField = new JTextField();
        JLabel securityQuestionLabel = new JLabel("Security Question:");
        
        // Use a dropdown for Security Question
        String[] securityQuestions = getSecurityQuestions(username); // Retrieve security questions
        JComboBox<String> securityQuestionDropdown = new JComboBox<>(securityQuestions);
    
        JLabel dobLabel = new JLabel("Date of Birth:");
    
        // Use dropdown for Date of Birth
        String[] days = new String[31];
        for (int i = 1; i <= 31; i++) {
            days[i - 1] = String.valueOf(i);
        }
        JComboBox<String> dayDropdown = new JComboBox<>(days);
    
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthDropdown = new JComboBox<>(months);
    
        String[] years = new String[100];
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(1923 + i);
        }
        JComboBox<String> yearDropdown = new JComboBox<>(years);
    
        JButton createAccountButton = new JButton("Create Account");
    
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText();
                String newPassword = new String(passwordField.getPassword());
                String recoveryEmail = recoveryEmailField.getText();
                String securityQuestion = Objects.requireNonNull(securityQuestionDropdown.getSelectedItem()).toString();
    
                // Retrieve selected DOB values
                String selectedDay = Objects.requireNonNull(dayDropdown.getSelectedItem()).toString();
                String selectedMonth = Objects.requireNonNull(monthDropdown.getSelectedItem()).toString();
                String selectedYear = Objects.requireNonNull(yearDropdown.getSelectedItem()).toString();
                String dob = selectedDay + " " + selectedMonth + " " + selectedYear;
    
                if (newUsername.isEmpty() || newPassword.isEmpty() || recoveryEmail.isEmpty() ||
                        securityQuestion.isEmpty()) {
                    JOptionPane.showMessageDialog(createAccountFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Process the account creation logic
                    addAccount(newUsername, hashPassword(newPassword));
                    // Store the security question in the securityQuestionsDatabase
                    securityQuestionsDatabase.put(newUsername, new String[]{securityQuestion});
                    saveAccounts();
                    JOptionPane.showMessageDialog(createAccountFrame, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    createAccountFrame.dispose();
                }
            }
        });
    
        createAccountFrame.add(usernameLabel);
        createAccountFrame.add(usernameField);
        createAccountFrame.add(passwordLabel);
        createAccountFrame.add(passwordField);
        createAccountFrame.add(recoveryEmailLabel);
        createAccountFrame.add(recoveryEmailField);
        createAccountFrame.add(securityQuestionLabel);
        createAccountFrame.add(securityQuestionDropdown);
        createAccountFrame.add(dobLabel);
        createAccountFrame.add(dayDropdown);
        createAccountFrame.add(monthDropdown);
        createAccountFrame.add(yearDropdown);
        createAccountFrame.add(createAccountButton);
    
        createAccountFrame.setSize(400, 300);
        createAccountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createAccountFrame.setLocationRelativeTo(null);
        createAccountFrame.setVisible(true);
    }
    
    private String[] getSecurityQuestions(String username) {
        // Replace this with your logic to retrieve security questions for the given username
        // Example: return a predefined set of questions for demonstration purposes
    
        // Check if the username exists in the securityQuestionsDatabase
        if (securityQuestionsDatabase.containsKey(username)) {
            return securityQuestionsDatabase.get(username);
        } else {
            return new String[0]; // Return an empty array if no questions are found for the user
        }
    }
    
    
    private void showSecurityQuestions() {
        // Retrieve security questions for the current user (replace with your logic)
        String[] securityQuestions = getSecurityQuestions(currentUsername);

        // If there are security questions for the user, prompt the user to answer them
        if (securityQuestions != null && securityQuestions.length > 0) {
            JPanel securityPanel = new JPanel(new GridLayout(securityQuestions.length + 1, 2));

            // Display security questions
            for (String question : securityQuestions) {
                JLabel questionLabel = new JLabel(question);
                JTextField answerField = new JTextField();
                securityPanel.add(questionLabel);
                securityPanel.add(answerField);
            }

            // Add a button to submit answers
            JButton submitButton = new JButton("Submit Answers");
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Process the user's answers (replace with your logic)
                    processSecurityAnswers(currentUsername, securityQuestions);
                }
            });

            securityPanel.add(new JLabel(" "));
            securityPanel.add(submitButton);

            // Show the security questions panel
            int result = JOptionPane.showConfirmDialog(this, securityPanel, "Security Questions", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // User clicked OK
                
            }
        } else {
            // No security questions for the user; show a message or perform other actions
            JOptionPane.showMessageDialog(this, "No security questions available for this user.", "Security Questions", JOptionPane.INFORMATION_MESSAGE);
        }
    }

   

    private void processSecurityAnswers(String username, String[] securityQuestions) {
        StringBuilder answerMessage = new StringBuilder("Your answers:\n");
    
        for (String question : securityQuestions) {
            String userAnswer = getUserAnswer(question);
            String correctAnswer = getCorrectAnswer(username, question);
    
            if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                answerMessage.append(question).append(": ").append(userAnswer).append(" (Correct)\n");
            } else {
                answerMessage.append(question).append(": ").append(userAnswer).append(" (Incorrect)\n");
                // You might want to handle the case where the answer is incorrect, for example, by limiting attempts
                JOptionPane.showMessageDialog(this, "Incorrect answer to security question.\nPlease try again or contact support.", "Security Answers", JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if an answer is incorrect
            }
        }
    
        // All answers are correct, you can proceed with whatever action you want
        JOptionPane.showMessageDialog(this, "All security answers are correct.\nAccess granted!", "Security Answers", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String getUserAnswer(String question) {
        return JOptionPane.showInputDialog(this, question);
    }

    private String getCorrectAnswer(String username, String question) {
        String[] securityQuestions = securityQuestionsDatabase.get(username);
        if (securityQuestions != null) {
            int questionIndex = Arrays.asList(getSecurityQuestions(username)).indexOf(question);
            if (questionIndex != -1) {
                return securityQuestions[questionIndex];
            }
        }
        return null; // Return null if the question or user is not found
    }
    private void showEntryDetails(String selectedEntry) {
        String hashedPassword = accounts.get(selectedEntry);
        String passwordToDisplay = showFullPassword ? hashedPassword : maskPassword(hashedPassword);
        int optionType = showFullPassword ? JOptionPane.YES_NO_OPTION : JOptionPane.INFORMATION_MESSAGE;
        int result = JOptionPane.showConfirmDialog(this, "Details of " + selectedEntry + ":\nPassword: " + passwordToDisplay, "Entry Details", optionType);

        if (result == JOptionPane.YES_OPTION) {
            showFullPassword = !showFullPassword;
        }
    }

    private void showForgotPasswordForm() {
        JFrame forgotPasswordFrame = new JFrame("Forgot Password");
        forgotPasswordFrame.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel securityQuestionLabel = new JLabel("Security Question:");
        JTextField securityQuestionField = new JTextField();
        JLabel newPasswordLabel = new JLabel("New Password:");
        JPasswordField newPasswordField = new JPasswordField();
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton resetPasswordButton = new JButton("Reset Password");

        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String securityQuestionAnswer = securityQuestionField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (username.isEmpty() || securityQuestionAnswer.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(forgotPasswordFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Process the password reset logic
                    // For now, just display a message
                    JOptionPane.showMessageDialog(forgotPasswordFrame, "Password reset successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    forgotPasswordFrame.dispose();
                }
            }
        });

        forgotPasswordFrame.add(usernameLabel);
        forgotPasswordFrame.add(usernameField);
        forgotPasswordFrame.add(securityQuestionLabel);
        forgotPasswordFrame.add(securityQuestionField);
        forgotPasswordFrame.add(newPasswordLabel);
        forgotPasswordFrame.add(newPasswordField);
        forgotPasswordFrame.add(confirmPasswordLabel);
        forgotPasswordFrame.add(confirmPasswordField);
        forgotPasswordFrame.add(resetPasswordButton);

        forgotPasswordFrame.setSize(400, 250);
        forgotPasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        forgotPasswordFrame.setLocationRelativeTo(null);
        forgotPasswordFrame.setVisible(true);
    }

    private String maskPassword(String hashedPassword) {
        StringBuilder maskedPasswordBuilder = new StringBuilder();
        for (int i = 0; i < hashedPassword.length(); i++) {
            maskedPasswordBuilder.append('*');
        }
        return maskedPasswordBuilder.toString();
    }

    private void updateEntryList() {
        JList<String> entryList = new JList<>(getStoredEntries());
        JScrollPane listScrollPane = new JScrollPane(entryList);

        mainPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        mainPanel.add(listScrollPane, gbc);
        revalidate();
        repaint();
    }

    private Vector<String> getStoredEntries() {
        return new Vector<>(accounts.keySet());
    }

    private void setupLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    private void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ACCOUNTS_FILE))) {
            Object obj = ois.readObject();
    
            if (obj instanceof Map<?, ?>) {
                accounts = (Map<String, String>) obj;
            } else {
                // Handle the case where the object is not a Map
                System.err.println("Unexpected object type in the accounts file.");
            }
        } catch (FileNotFoundException e) {
            // File doesn't exist yet; it will be created when saving
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    

    private void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validatePassword(String inputPassword, String storedPassword) {
        return hashPassword(inputPassword).equals(storedPassword);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder hexStringBuilder = new StringBuilder();
            for (byte b : hashBytes) {
                hexStringBuilder.append(String.format("%02x", b));
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
            
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PasswordSafeGUI());
    }
}
