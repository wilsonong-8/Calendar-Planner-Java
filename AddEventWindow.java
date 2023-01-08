import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class AddEventWindow extends JFrame implements ActionListener, ItemListener {

    JPanel panel1,panel2,panel3,panel4,panel5,panel6;
    JComboBox dayComboBox, monthComboBox,yearComboBox,timeComboBox, amPMComboBox, eventTypeComboBox,statusComboBox;
    JTextField titleField;
    JTextArea descriptionArea;
    JButton submitButton;

    /**
     * MONTHS, TIME and AMPM arrays are used to populate the Time and Date Combo Boxes
     * private newEvent is used to store the new event object that will be pushed into eventArrayList
     * hasEvent boolean is used as a check for the actionEvent
     *
     * Edit Mode
     * editEvent is used to copy the reference of object Event
     * isEditEvent is a boolean to alter some conditions in Edit Mode
     */
    private Event newEvent,editEvent;
    final String[] MONTHS = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
    final String[] TIME = {"1200","1230","0100","0130","0200","0230","0300","0330","0400","0430","0500","0530","0600",
                    "0630","0700","0730","0800","0830","0900","1000","1030","1100","1130"};
    final String[] AMPM = {"AM","PM"};
    boolean hasEvent,isEditEvent;

    /**
     * AddEventWindow takes in 3 arguments.
     * This Window is used for both Adding and Editing the Event
     * @param daysInMonth
     * @param windowTitle
     * @param buttonTitle
     */
    public AddEventWindow (int daysInMonth, String windowTitle, String buttonTitle) {
        /*
        It is made up of 6 Panels in FlowLayout added on top of the JFrame that has
        a 6x1 GridLayout.

        panel1 consists of day, month, year, time and AMPM ComboBox

        monthComboBox and yearComboBox has itemListener to detect changes of date and will
        trigger the dayComboBox to populate the correct days according to the Month and Year
         */
        panel1 = new JPanel(new FlowLayout());
        dayComboBox = new JComboBox();
        for (int i = 1; i < daysInMonth; i++) {
            dayComboBox.addItem(i);
        }
        monthComboBox = new JComboBox();
        for (int i = 0; i < MONTHS.length; i++) {
            monthComboBox.addItem(MONTHS[i]);
        }
        monthComboBox.addItemListener(this);

        yearComboBox = new JComboBox();
        for (int i = 2010; i <= 2050; i++) {
            yearComboBox.addItem(i);
        }
        yearComboBox.addItemListener(this);
        timeComboBox = new JComboBox(TIME);
        amPMComboBox = new JComboBox(AMPM);

        JLabel dateLabel = new JLabel("Date: ",SwingConstants.CENTER);
        JLabel timeLabel = new JLabel("    Time: ");
        panel1.add(dateLabel);
        panel1.add(dayComboBox);
        panel1.add(monthComboBox);
        monthComboBox.setSelectedItem(CalendarMain.monthComboBox.getSelectedItem());
        panel1.add(yearComboBox);
        yearComboBox.setSelectedItem(CalendarMain.yearComboBox.getSelectedItem());
        panel1.add(timeLabel);
        panel1.add(timeComboBox);
        panel1.add(amPMComboBox);
        panel1.setBackground(new Color(255, 214, 165));

        /*
        panel2 consists of eventTypeComboBox
         */
        panel2 = new JPanel(new FlowLayout());
        String[] eventArray = {"Task To Do", "Appointment To Attend"};
        eventTypeComboBox = new JComboBox(eventArray);
        JLabel eventLabel = new JLabel("Event Type");
        panel2.add(eventLabel);
        panel2.add(eventTypeComboBox);
        panel2.setBackground(new Color(253, 255, 182));

        /*
        Panel 3 has a textField that implements KeyListener, only allowing a-Z, A-Z, 1-3 and certain function keys,
        the rest of the keys when entered will be *e.consumed()*
        Length is also restricted to 30 max characters
         */
        panel3 = new JPanel(new FlowLayout());
        titleField = new JTextField();
        titleField.setPreferredSize(new Dimension(290,30));
        titleField.setDocument(new LengthRestrictedDocument(30));
        titleField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= 'a') && (c <= 'z') || (c >= 'A') && (c <= 'Z') || (c >= '0') && (c <= '9') ||
                        (c == KeyEvent.VK_SPACE) || (c == KeyEvent.VK_SHIFT) || (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
        JLabel titleLabel = new JLabel("Title: ");
        panel3.add(titleLabel);
        panel3.add(titleField);
        panel3.setBackground(new Color(202, 255, 191));

        /*
        Panel 4 consists of a JTextArea on a Scrollpane
        It is restricted to 200words
         */
        panel4 = new JPanel(new FlowLayout());
        descriptionArea = new JTextArea();
        descriptionArea.setColumns(4);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setDocument(new LengthRestrictedDocument(200));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setPreferredSize(new Dimension(250,50));
        JLabel descriptionLabel = new JLabel("Description: ");
        panel4.add(descriptionLabel);
        panel4.add(scrollPane);
        panel4.setBackground(new Color(155, 246, 255));

        /*
        panel5 consists of statusComboBox
         */
        panel5 = new JPanel(new FlowLayout());
        String[] statusArray = {"Pending", "Completed", "KIV"};
        statusComboBox = new JComboBox(statusArray);
        JLabel statusLabel = new JLabel("Status: ");
        panel5.add(statusLabel);
        panel5.add(statusComboBox);
        panel5.setBackground(new Color(160, 196, 255));

        /*
        panel6 consists of submitButton with (actionListener)
         */
        panel6 = new JPanel();
        panel6.setPreferredSize(new Dimension(80,40));
        submitButton = new JButton(buttonTitle);
        submitButton.setFont(new Font(null, Font.BOLD, 18));
        submitButton.addActionListener(this);
        panel6.add(submitButton);
        panel6.setBackground(new Color(189, 178, 255));

        setTitle(windowTitle);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6,1));
        setSize(500,400);
        setResizable(false);
        setLocationRelativeTo(null);
        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
        add(panel5);
        add(panel6);
        setVisible(true);
    }
    /**
     * ActionPerformed by clicking the submit button will check if both Title and Description are filled.
     * Replaces newline with "." in *descriptionArea.getText().replaceAll("\\r\\n|\\r|\\n", ". ");*
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            if ((titleField.getText().trim().length() == 0) || (descriptionArea.getText().trim().length() == 0)) {
                JOptionPane.showMessageDialog(null, "You need to fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
            /**
             * newEvent is created with the variables and added into eventArrayList.
             * Every time an event is created, sortArray will be called to sort the events by Time so that they will appear
             * in Chronological order in the dayTextArea cell on calendar when updateCalendar() function is called.
             */
            else {
                String descriptionText = descriptionArea.getText().replaceAll("\\r\\n|\\r|\\n", ". ");
                newEvent = new Event(dayComboBox.getSelectedItem().toString(),monthComboBox.getSelectedItem().toString(),
                        yearComboBox.getSelectedItem().toString(),timeComboBox.getSelectedItem().toString(),
                        amPMComboBox.getSelectedItem().toString(),eventTypeComboBox.getSelectedItem().toString(),
                        titleField.getText(), descriptionText,statusComboBox.getSelectedItem().toString());
                Event.addEvent(newEvent);
                if (isEditEvent == true) {
                    Event.removeEvent(editEvent);
                }
                if (Event.getEventArrayList().size() >1) {
                    Event.sortArrayList(Event.getEventArrayList());
                }
                hasEvent = true;
            }
            /**
             * There are two functions here.
             * Edit-Mode : updates Calaendar and sets the month + year ComboBox to the Event Date, after which will
             * open a popup message and disposes JFrame on click.
             *
             * Add-Mode : Same as edit mode except the popup message is different
             */
            if (isEditEvent == true) {
                CalendarMain.updateCalendar((String) monthComboBox.getSelectedItem(), (Integer) yearComboBox.getSelectedItem());
                CalendarMain.monthComboBox.setSelectedItem(monthComboBox.getSelectedItem());
                CalendarMain.yearComboBox.setSelectedItem(yearComboBox.getSelectedItem());
                JOptionPane.showMessageDialog(null, "Event changed", "Success!", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
            else if (hasEvent == true) {
                CalendarMain.updateCalendar((String) monthComboBox.getSelectedItem(), (Integer) yearComboBox.getSelectedItem());
                CalendarMain.monthComboBox.setSelectedItem(monthComboBox.getSelectedItem());
                CalendarMain.yearComboBox.setSelectedItem(yearComboBox.getSelectedItem());
                JOptionPane.showMessageDialog(null, "Event successfully added", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
            hasEvent = false;
            isEditEvent = false;
        }
    }

    /**
     * itemStateChanged listens for activity on monthComboBox and yearComboBox. Any changes in the dates will
     * trigger the dayComboBox to correctly set the number of days in the newly selected month/year. This prevents
     * a non-existent day being shown.
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Date date = new Date("01-" + monthComboBox.getSelectedItem() + "-" + yearComboBox.getSelectedItem());
            int numOfDays = CalendarMain.DAYS[date.getMonth()];
            if (date.getYear() % 4 == 0 && date.getMonth() == 1) {
                numOfDays = 29;
                }
            dayComboBox.removeAllItems();
            for (int i = 1; i <=numOfDays; i++) {
                dayComboBox.addItem(i);
            }
        }
    }

    /**
     * LengthRestrictDocument is a class that restricts the number of characters in the JTextArea or JTextField
     */
    public final class LengthRestrictedDocument extends PlainDocument {

        private final int limit;

        public LengthRestrictedDocument(int limit) {
            this.limit = limit;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (str == null)
                return;

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offs, str, a);
            }
        }
    }

    /**
     * setEvent is a method that is called when EDIT button is pressed on the ShowEventWindow. It takes Event as
     * argument and sets the JComponents accordingly
     * @param obj
     */
    public void setEvent(Event obj) {
        dayComboBox.setSelectedItem(Integer.parseInt(obj.getEventDay()));
        monthComboBox.setSelectedItem(obj.getEventMonth());
        yearComboBox.setSelectedItem(obj.getEventYear());
        timeComboBox.setSelectedItem(obj.getEventTime());
        amPMComboBox.setSelectedItem(obj.getEventAMPM());
        eventTypeComboBox.setSelectedItem(obj.getEventType());
        titleField.setText(obj.getEventTitle());
        descriptionArea.setText(obj.getEventDescription());
        statusComboBox.setSelectedItem(obj.getEventStatus());
        isEditEvent = true;
        editEvent = obj;
    }
}

