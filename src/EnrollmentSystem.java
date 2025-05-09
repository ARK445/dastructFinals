import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class EnrollmentSystem {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    public static void main(String[] args) {
        showLoginScreen();
    }

    private static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 550);
        loginFrame.setLayout(new BorderLayout());

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        loginFrame.add(mainPanel, BorderLayout.CENTER);

        // Title
        JLabel title = new JLabel("Sign In");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 0, 30, 0));
        mainPanel.add(title);

        // Form fields
        JTextField emailField = (JTextField) addStyledField(mainPanel, "Email", "");
        JPasswordField passwordField = (JPasswordField)addStyledField(mainPanel, "Password", "", true);

        // Sign In button
        JButton signInButton = new JButton("Sign In");
        signInButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        signInButton.setPreferredSize(new Dimension(320, 45));
        signInButton.setMaximumSize(new Dimension(320, 45));
        signInButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signInButton.setForeground(Color.WHITE);
        signInButton.setBackground(new Color(0, 120, 215));
        signInButton.setFocusPainted(false);
        signInButton.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Login action
        signInButton.addActionListener(e -> {
            String username = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                loginFrame.dispose();
                showDashboard();
            } else {
                JOptionPane.showMessageDialog(loginFrame,
                        "Invalid credentials",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(signInButton);

        // Center and show frame
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private static void showDashboard() {
        JFrame frame = new JFrame("Student Enrollment System - Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 120, 215));
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 60));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("ADMIN DASHBOARD");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Navigation Panel
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(50, 50, 50));
        navPanel.setPreferredSize(new Dimension(220, frame.getHeight()));
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));

        // Navigation buttons
        String[] navItems = {"Dashboard", "Student Registration", "Course Management",
                "Enrollment", "Reports", "Settings"};

        for (String item : navItems) {
            JButton navButton = createNavButton(item);
            navPanel.add(navButton);
            navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Main Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(240, 240, 240));
        contentPanel.setLayout(new BorderLayout());

        // Dashboard Cards
        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        cardsPanel.setBackground(new Color(240, 240, 240));

        String[] cardTitles = {"Total Students", "Active Courses", "Current Enrollment",
                "Pending Approvals", "Faculty Count", "Recent Activity"};
        String[] cardValues = {"1,245", "48", "876", "32", "94", "15 new"};

        for (int i = 0; i < cardTitles.length; i++) {
            cardsPanel.add(createDashboardCard(cardTitles[i], cardValues[i]));
        }

        contentPanel.add(cardsPanel, BorderLayout.CENTER);

        // Recent Enrollments Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(0, 20, 20, 20));

        String[] columnNames = {"Student ID", "Name", "Course", "Date", "Status"};
        Object[][] data = {
                {"S1001", "John Smith", "Computer Science", "2023-10-15", "Approved"},
                {"S1002", "Emily Johnson", "Mathematics", "2023-10-14", "Pending"},
                {"S1003", "Michael Brown", "Physics", "2023-10-14", "Approved"},
                {"S1004", "Sarah Davis", "Biology", "2023-10-13", "Rejected"},
                {"S1005", "Robert Wilson", "Chemistry", "2023-10-12", "Approved"}
        };

        JTable enrollmentsTable = new JTable(data, columnNames);
        enrollmentsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        enrollmentsTable.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(enrollmentsTable);
        tablePanel.add(new JLabel("Recent Enrollments"), BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(tablePanel, BorderLayout.SOUTH);

        // Add panels to frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(navPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);

        // Center and show frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JComponent addStyledField(JPanel panel, String label, String value) {
        return addStyledField(panel, label, value, false);
    }

    private static JComponent addStyledField(JPanel panel, String label, String value, boolean isPassword) {
        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Label
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.add(fieldLabel);
        fieldPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        // Input field
        JComponent inputField;
        if (isPassword) {
            inputField = new JPasswordField();
            ((JPasswordField)inputField).setText(value);
        } else {
            inputField = new JTextField();
            ((JTextField)inputField).setText(value);
        }

        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputField.setMaximumSize(new Dimension(320, 40));
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        fieldPanel.add(inputField);

        panel.add(fieldPanel);
        return inputField;
    }

    private static JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 70, 70));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(90, 90, 90));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 70, 70));
            }
        });

        return button;
    }

    private static JPanel createDashboardCard(String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(100, 100, 100));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(new Color(0, 120, 215));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}