import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import javax.swing.table.*;

public class EnrollmentSystem {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static JFrame currentFrame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void main(String[] args) {
        showLoginScreen();
    }

    private static void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        currentFrame = loginFrame;
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
        currentFrame = frame;
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

        // Card panel for content switching
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(new Color(240, 240, 240));

        // Create all content panels
        JPanel dashboardPanel = createDashboardPanel();
        JPanel registrationPanel = createRegistrationPanel();
        JPanel coursePanel = createCourseManagementPanel();
        JPanel enrollmentPanel = createEnrollmentPanel();
        JPanel reportsPanel = createReportsPanel();
        JPanel settingsPanel = createSettingsPanel();

        // Add panels to card panel
        cardPanel.add(dashboardPanel, "Dashboard");
        cardPanel.add(registrationPanel, "Student Registration");
        cardPanel.add(coursePanel, "Course Management");
        cardPanel.add(enrollmentPanel, "Enrollment");
        cardPanel.add(reportsPanel, "Reports");
        cardPanel.add(settingsPanel, "Settings");

        // Navigation buttons
        String[] navItems = {"Dashboard", "Student Registration", "Course Management",
                "Enrollment", "Reports", "Settings"};

        for (String item : navItems) {
            JButton navButton = createNavButton(item);
            navButton.addActionListener(e -> {
                cardLayout.show(cardPanel, item);
                titleLabel.setText(item.toUpperCase());
            });
            navPanel.add(navButton);
            navPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Add logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        logoutButton.setPreferredSize(new Dimension(200, 50));
        logoutButton.setMaximumSize(new Dimension(200, 50));
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(70, 70, 70));
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        navPanel.add(Box.createVerticalGlue());
        navPanel.add(logoutButton);

        // Add panels to frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(navPanel, BorderLayout.WEST);
        frame.add(cardPanel, BorderLayout.CENTER);

        // Center and show frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

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

        panel.add(cardsPanel, BorderLayout.CENTER);

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

        panel.add(tablePanel, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createRegistrationPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Student Registration");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        formPanel.setBackground(Color.WHITE);

        // Form fields
        JTextField idField = (JTextField) addStyledField(formPanel, "Student ID", "");
        JTextField nameField = (JTextField) addStyledField(formPanel, "Full Name", "");
        JTextField emailField = (JTextField) addStyledField(formPanel, "Email", "");
        JTextField phoneField = (JTextField) addStyledField(formPanel, "Phone", "");
        JComboBox<String> programField = new JComboBox<>(new String[]{"Computer Science", "Mathematics", "Physics", "Biology", "Chemistry"});
        programField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        programField.setMaximumSize(new Dimension(320, 40));
        programField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel programPanel = new JPanel();
        programPanel.setLayout(new BoxLayout(programPanel, BoxLayout.Y_AXIS));
        programPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        programPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel programLabel = new JLabel("Program");
        programLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        programLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        programPanel.add(programLabel);
        programPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        programPanel.add(programField);
        formPanel.add(programPanel);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBackground(Color.WHITE);

        JButton registerButton = new JButton("Register Student");
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(new Color(0, 120, 215));
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> {
            if (validateRegistrationFields(idField, nameField, emailField, phoneField)) {
                JOptionPane.showMessageDialog(panel, "Student registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Clear fields
                idField.setText("");
                nameField.setText("");
                emailField.setText("");
                phoneField.setText("");
                programField.setSelectedIndex(0);
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearButton.setForeground(new Color(0, 120, 215));
        clearButton.setBackground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215)));
        clearButton.addActionListener(e -> {
            idField.setText("");
            nameField.setText("");
            emailField.setText("");
            phoneField.setText("");
            programField.setSelectedIndex(0);
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(clearButton);

        formPanel.add(buttonPanel);

        // Student list table
        String[] studentColumns = {"Student ID", "Name", "Program", "Email", "Phone"};
        Object[][] studentData = {
                {"S1001", "John Smith", "Computer Science", "john@example.com", "555-0101"},
                {"S1002", "Emily Johnson", "Mathematics", "emily@example.com", "555-0102"},
                {"S1003", "Michael Brown", "Physics", "michael@example.com", "555-0103"}
        };

        JTable studentTable = new JTable(studentData, studentColumns);
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentTable.setRowHeight(30);
        JScrollPane tableScroll = new JScrollPane(studentTable);

        // Split pane for form and table
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formPanel, tableScroll);
        splitPane.setDividerLocation(350);
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    private static boolean validateRegistrationFields(JTextField idField, JTextField nameField,
                                                      JTextField emailField, JTextField phoneField) {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(currentFrame, "Student ID is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(currentFrame, "Full name is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (emailField.getText().isEmpty() || !emailField.getText().contains("@")) {
            JOptionPane.showMessageDialog(currentFrame, "Valid email is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (phoneField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(currentFrame, "Phone number is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private static JPanel createCourseManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Course Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        searchPanel.setBackground(Color.WHITE);

        JTextField searchField = new JTextField();
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> {
            // Search functionality would go here
            JOptionPane.showMessageDialog(panel, "Search functionality would be implemented here", "Search", JOptionPane.INFORMATION_MESSAGE);
        });

        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(searchButton);

        contentPanel.add(searchPanel);

        // Course table
        String[] courseColumns = {"Course Code", "Course Name", "Department", "Credits", "Instructor", "Enrolled"};
        Object[][] courseData = {
                {"CS101", "Introduction to Programming", "Computer Science", "3", "Dr. Smith", "45"},
                {"MATH201", "Calculus II", "Mathematics", "4", "Prof. Johnson", "32"},
                {"PHYS101", "General Physics", "Physics", "4", "Dr. Brown", "28"},
                {"BIO101", "Biology Fundamentals", "Biology", "3", "Prof. Davis", "36"}
        };

        JTable courseTable = new JTable(courseData, courseColumns);
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseTable.setRowHeight(30);
        JScrollPane tableScroll = new JScrollPane(courseTable);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBackground(Color.WHITE);

        JButton addButton = new JButton("Add Course");
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(0, 120, 215));
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            showAddCourseDialog();
        });

        JButton editButton = new JButton("Edit Course");
        editButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        editButton.setForeground(new Color(0, 120, 215));
        editButton.setBackground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215)));
        editButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel, "Please select a course to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            } else {
                showEditCourseDialog(courseTable, selectedRow);
            }
        });

        JButton deleteButton = new JButton("Delete Course");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(200, 50, 50));
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(panel, "Please select a course to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            } else {
                int confirm = JOptionPane.showConfirmDialog(panel,
                        "Are you sure you want to delete this course?",
                        "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // In a real application, we would remove from the data model
                    JOptionPane.showMessageDialog(panel, "Course deleted (simulated)", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(deleteButton);

        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(tableScroll);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private static void showAddCourseDialog() {
        JDialog dialog = new JDialog(currentFrame, "Add New Course", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextField codeField = (JTextField) addStyledField(formPanel, "Course Code", "");
        JTextField nameField = (JTextField) addStyledField(formPanel, "Course Name", "");
        JComboBox<String> deptField = new JComboBox<>(new String[]{"Computer Science", "Mathematics", "Physics", "Biology", "Chemistry"});
        deptField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deptField.setMaximumSize(new Dimension(320, 40));
        deptField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel deptPanel = new JPanel();
        deptPanel.setLayout(new BoxLayout(deptPanel, BoxLayout.Y_AXIS));
        deptPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        deptPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel deptLabel = new JLabel("Department");
        deptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deptLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        deptPanel.add(deptLabel);
        deptPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        deptPanel.add(deptField);
        formPanel.add(deptPanel);

        JTextField creditsField = (JTextField) addStyledField(formPanel, "Credit Hours", "");
        JTextField instructorField = (JTextField) addStyledField(formPanel, "Instructor", "");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(0, 120, 215));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            // Validate and save
            if (!codeField.getText().isEmpty() && !nameField.getText().isEmpty() &&
                    !creditsField.getText().isEmpty() && !instructorField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Course added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setForeground(new Color(0, 120, 215));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215)));
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(currentFrame);
        dialog.setVisible(true);
    }

    private static void showEditCourseDialog(JTable courseTable, int selectedRow) {
        JDialog dialog = new JDialog(currentFrame, "Edit Course", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Get data from selected row
        String code = (String) courseTable.getValueAt(selectedRow, 0);
        String name = (String) courseTable.getValueAt(selectedRow, 1);
        String dept = (String) courseTable.getValueAt(selectedRow, 2);
        String credits = (String) courseTable.getValueAt(selectedRow, 3);
        String instructor = (String) courseTable.getValueAt(selectedRow, 4);

        JTextField codeField = (JTextField) addStyledField(formPanel, "Course Code", code);
        JTextField nameField = (JTextField) addStyledField(formPanel, "Course Name", name);

        JComboBox<String> deptField = new JComboBox<>(new String[]{"Computer Science", "Mathematics", "Physics", "Biology", "Chemistry"});
        deptField.setSelectedItem(dept);
        deptField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deptField.setMaximumSize(new Dimension(320, 40));
        deptField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel deptPanel = new JPanel();
        deptPanel.setLayout(new BoxLayout(deptPanel, BoxLayout.Y_AXIS));
        deptPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        deptPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel deptLabel = new JLabel("Department");
        deptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        deptLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        deptPanel.add(deptLabel);
        deptPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        deptPanel.add(deptField);
        formPanel.add(deptPanel);

        JTextField creditsField = (JTextField) addStyledField(formPanel, "Credit Hours", credits);
        JTextField instructorField = (JTextField) addStyledField(formPanel, "Instructor", instructor);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(new Color(0, 120, 215));
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            // Validate and save
            if (!codeField.getText().isEmpty() && !nameField.getText().isEmpty() &&
                    !creditsField.getText().isEmpty() && !instructorField.getText().isEmpty()) {
                // In a real application, we would update the data model
                JOptionPane.showMessageDialog(dialog, "Course updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Please fill all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setForeground(new Color(0, 120, 215));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215)));
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(cancelButton);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(currentFrame);
        dialog.setVisible(true);
    }

    private static JPanel createEnrollmentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Enrollment Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        searchPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        searchPanel.setBackground(Color.WHITE);

        JTextField searchField = new JTextField();
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> {
            // Search functionality would go here
            JOptionPane.showMessageDialog(panel, "Search functionality would be implemented here", "Search", JOptionPane.INFORMATION_MESSAGE);
        });

        searchPanel.add(searchField);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        searchPanel.add(searchButton);

        contentPanel.add(searchPanel);

        // Enrollment table
        String[] enrollColumns = {"Enrollment ID", "Student Name", "Course", "Date", "Status", "Actions"};
        Object[][] enrollData = {
                {"E1001", "John Smith", "CS101 - Intro to Programming", "2023-10-15", "Approved", "View"},
                {"E1002", "Emily Johnson", "MATH201 - Calculus II", "2023-10-14", "Pending", "View"},
                {"E1003", "Michael Brown", "PHYS101 - General Physics", "2023-10-14", "Approved", "View"},
                {"E1004", "Sarah Davis", "BIO101 - Biology Fundamentals", "2023-10-13", "Rejected", "View"}
        };

        JTable enrollTable = new JTable(enrollData, enrollColumns);
        enrollTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        enrollTable.setRowHeight(30);

        // Add button renderer for the Actions column
        enrollTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        enrollTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane tableScroll = new JScrollPane(enrollTable);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBackground(Color.WHITE);

        JButton approveAllButton = new JButton("Approve Selected");
        approveAllButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        approveAllButton.setForeground(Color.WHITE);
        approveAllButton.setBackground(new Color(0, 150, 0));
        approveAllButton.setFocusPainted(false);
        approveAllButton.addActionListener(e -> {
            // Approve selected enrollments
            JOptionPane.showMessageDialog(panel, "Selected enrollments approved (simulated)", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        JButton rejectAllButton = new JButton("Reject Selected");
        rejectAllButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rejectAllButton.setForeground(Color.WHITE);
        rejectAllButton.setBackground(new Color(200, 50, 50));
        rejectAllButton.setFocusPainted(false);
        rejectAllButton.addActionListener(e -> {
            // Reject selected enrollments
            JOptionPane.showMessageDialog(panel, "Selected enrollments rejected (simulated)", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonPanel.add(approveAllButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(rejectAllButton);

        contentPanel.add(buttonPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        contentPanel.add(tableScroll);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private static JPanel createReportsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("Reports");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Report selection panel
        JPanel reportSelectionPanel = new JPanel();
        reportSelectionPanel.setLayout(new BoxLayout(reportSelectionPanel, BoxLayout.Y_AXIS));
        reportSelectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        reportSelectionPanel.setBackground(Color.WHITE);

        String[] reports = {
                "Student Enrollment Summary",
                "Course Enrollment Statistics",
                "Student Demographic Report",
                "Faculty Workload Report",
                "Financial Revenue Report",
                "Graduation Rate Analysis"
        };

        ButtonGroup reportGroup = new ButtonGroup();
        for (String report : reports) {
            JRadioButton radioButton = new JRadioButton(report);
            radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            radioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            radioButton.setBackground(Color.WHITE);
            radioButton.setBorder(new EmptyBorder(5, 5, 5, 5));
            reportGroup.add(radioButton);
            reportSelectionPanel.add(radioButton);
        }

        // Date range panel
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        datePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        datePanel.setBorder(new EmptyBorder(20, 5, 20, 5));
        datePanel.setBackground(Color.WHITE);

        datePanel.add(new JLabel("From:"));
        JTextField fromDate = new JTextField("2023-09-01");
        fromDate.setPreferredSize(new Dimension(100, 30));
        datePanel.add(fromDate);

        datePanel.add(Box.createRigidArea(new Dimension(20, 0)));
        datePanel.add(new JLabel("To:"));
        JTextField toDate = new JTextField("2023-12-31");
        toDate.setPreferredSize(new Dimension(100, 30));
        datePanel.add(toDate);

        reportSelectionPanel.add(datePanel);

        // Generate button
        JButton generateButton = new JButton("Generate Report");
        generateButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        generateButton.setForeground(Color.WHITE);
        generateButton.setBackground(new Color(0, 120, 215));
        generateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        generateButton.setFocusPainted(false);
        generateButton.addActionListener(e -> {
            // Generate report
            JOptionPane.showMessageDialog(panel, "Report generated (simulated)\nDate range: " +
                    fromDate.getText() + " to " + toDate.getText(), "Report Generated", JOptionPane.INFORMATION_MESSAGE);
        });

        reportSelectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        reportSelectionPanel.add(generateButton);

        // Chart panel (placeholder)
        JPanel chartPanel = new JPanel();
        chartPanel.setBorder(BorderFactory.createTitledBorder("Report Preview"));
        chartPanel.setBackground(Color.WHITE);
        chartPanel.add(new JLabel(new ImageIcon("chart_placeholder.png"))); // This would be a real chart in a real app

        // Split pane for selection and preview
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, reportSelectionPanel, chartPanel);
        splitPane.setDividerLocation(350);
        splitPane.setBorder(BorderFactory.createEmptyBorder());

        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    private static JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // Title
        JLabel title = new JLabel("System Settings");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        // Tabbed pane for different settings
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // User Account tab
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
        accountPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        accountPanel.setBackground(Color.WHITE);

        JLabel accountTitle = new JLabel("Account Settings");
        accountTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        accountTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        accountPanel.add(accountTitle);
        accountPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JTextField usernameField = (JTextField) addStyledField(accountPanel, "Username", ADMIN_USERNAME);
        JPasswordField passwordField = (JPasswordField) addStyledField(accountPanel, "Password", "********", true);
        JTextField emailField = (JTextField) addStyledField(accountPanel, "Email", "admin@school.edu");

        JButton saveAccountButton = new JButton("Save Changes");
        saveAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveAccountButton.setForeground(Color.WHITE);
        saveAccountButton.setBackground(new Color(0, 120, 215));
        saveAccountButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        saveAccountButton.setFocusPainted(false);
        saveAccountButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Account settings saved (simulated)", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        accountPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        accountPanel.add(saveAccountButton);

        // System Preferences tab
        JPanel prefPanel = new JPanel();
        prefPanel.setLayout(new BoxLayout(prefPanel, BoxLayout.Y_AXIS));
        prefPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        prefPanel.setBackground(Color.WHITE);

        JLabel prefTitle = new JLabel("System Preferences");
        prefTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        prefTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        prefPanel.add(prefTitle);
        prefPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JCheckBox emailNotifyCheck = new JCheckBox("Enable email notifications");
        emailNotifyCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailNotifyCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailNotifyCheck.setSelected(true);
        prefPanel.add(emailNotifyCheck);
        prefPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JCheckBox autoBackupCheck = new JCheckBox("Enable automatic backups");
        autoBackupCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        autoBackupCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        autoBackupCheck.setSelected(true);
        prefPanel.add(autoBackupCheck);
        prefPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel backupPanel = new JPanel();
        backupPanel.setLayout(new BoxLayout(backupPanel, BoxLayout.X_AXIS));
        backupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backupPanel.setBackground(Color.WHITE);

        backupPanel.add(new JLabel("Backup frequency:"));
        backupPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        JComboBox<String> backupFreq = new JComboBox<>(new String[]{"Daily", "Weekly", "Monthly"});
        backupFreq.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backupPanel.add(backupFreq);

        prefPanel.add(backupPanel);
        prefPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton savePrefButton = new JButton("Save Preferences");
        savePrefButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        savePrefButton.setForeground(Color.WHITE);
        savePrefButton.setBackground(new Color(0, 120, 215));
        savePrefButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        savePrefButton.setFocusPainted(false);
        savePrefButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Preferences saved (simulated)", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        prefPanel.add(savePrefButton);

        // About tab
        JPanel aboutPanel = new JPanel();
        aboutPanel.setLayout(new BoxLayout(aboutPanel, BoxLayout.Y_AXIS));
        aboutPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        aboutPanel.setBackground(Color.WHITE);

        JLabel aboutTitle = new JLabel("About");
        aboutTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        aboutTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        aboutPanel.add(aboutTitle);
        aboutPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel versionLabel = new JLabel("Enrollment System v1.0.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        aboutPanel.add(versionLabel);
        aboutPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel copyrightLabel = new JLabel("© 2023 School Management Inc.");
        copyrightLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        copyrightLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        aboutPanel.add(copyrightLabel);
        aboutPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton checkUpdateButton = new JButton("Check for Updates");
        checkUpdateButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        checkUpdateButton.setForeground(Color.WHITE);
        checkUpdateButton.setBackground(new Color(0, 120, 215));
        checkUpdateButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        checkUpdateButton.setFocusPainted(false);
        checkUpdateButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "You have the latest version", "Update Check", JOptionPane.INFORMATION_MESSAGE);
        });

        aboutPanel.add(checkUpdateButton);

        // Add tabs
        tabbedPane.addTab("Account", accountPanel);
        tabbedPane.addTab("Preferences", prefPanel);
        tabbedPane.addTab("About", aboutPanel);

        panel.add(tabbedPane, BorderLayout.CENTER);

        return panel;
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

    // Button renderer for table cells
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
            setForeground(Color.WHITE);
            setBackground(new Color(0, 120, 215));
            setFocusPainted(false);
            return this;
        }
    }

    // Button editor for table cells
    static class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(0, 120, 215));
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                JOptionPane.showMessageDialog(button, "Viewing enrollment details for row");
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}