import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LogInWindow extends JFrame implements ActionListener {
    /**
     * String username and password is the UserName and Password used for logging in
     */
    JPanel panel1, panel2, panel3;
    JButton submitButton;
    JTextField userField, passwordField;
    ImageIcon titleImage;

    final private String username = "admin";
    final private String password = "12345";

    LogInWindow() {

        /*
        A basic Layout of the Login Page using FLowLayout Manager
        Consists of an Image, two textAreas for entering the username and password
        A Login Button will have a (ActionListener)
        userField and passwordField has (KeyListener) that will click on submit button when Enter is pressed
         */
        panel1 = new JPanel();
        titleImage = new ImageIcon(this.getClass().getResource("/images/calender.png"));
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(titleImage);
        panel1.add(imageLabel);
        panel1.setOpaque(false);

        panel2 = new JPanel(new FlowLayout());
        JLabel userLabel = new JLabel("Username: ");
        userField = new JTextField();
        userField.setPreferredSize(new Dimension(150, 30));
        panel2.add(userLabel);
        panel2.add(userField);
        panel2.setOpaque(false);
        userField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c == KeyEvent.VK_ENTER)) {
                    submitButton.doClick();
                }
            }
        });

        panel3 = new JPanel(new FlowLayout());
        JLabel passwordLabel = new JLabel("Password: ");
        passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(150, 30));
        panel3.add(passwordLabel);
        panel3.add(passwordField);
        panel3.setOpaque(false);
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c == KeyEvent.VK_ENTER)){
                    submitButton.doClick();
                }
            }
        });

        submitButton = new JButton("LOGIN");
        submitButton.setFont(new Font(null, Font.BOLD, 15));
        submitButton.setPreferredSize(new Dimension(90, 30));
        submitButton.addActionListener(this);

        setTitle("Login Page");
        setLayout(new FlowLayout());
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(202, 255, 191));
        setResizable(false);
        setLocationRelativeTo(null);
        add(panel1);
        add(panel2);
        add(panel3);
        add(submitButton);
        setVisible(true);
    }

    /**
     * Login Button with actionListener
     * Checks if the Username and Password entered matches String username and password.
     * If they are equal, TimeTable will be set to visible and the login window will be disposed().
     * If details are entered incorrectly, an Error JOptionPane will pop up.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            if (userField.getText().equals(username) && passwordField.getText().equals(password)) {
                CalendarMain.datePanel.setVisible(true);
                CalendarMain.logoutPanel.setVisible(true);
                CalendarMain.calendarPanel.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Wrong UserName or Password", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

