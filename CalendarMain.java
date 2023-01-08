import java.awt.*;
import java.awt.event.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class CalendarMain extends JFrame implements ItemListener, ActionListener {

    /**
     * DAYS, WEEKDAYS, MONTHS are used as reference when populating the Calendar
     * String eventDay, eventMonth and eventYear captures the current date when the user clicks on the Calendar to see
     * the events of the day. It will be referenced from ShowEventWindow class to populate the events of that date.
     * daysInMonth is used to set dateComboBox in AddWindow
     */
    static JPanel datePanel, logoutPanel,  calendarPanel;
    static JComboBox monthComboBox;
    static JComboBox yearComboBox;
    static JButton button1 ,button2,buttonAdd,buttonLogOut;
    static JLabel currentDateLabel;
    static final int DAYS[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static final String WEEKDAYS[] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    static final String MONTHS[] = {"January", "February", "March", "April", "May", "June", "July",
            "August", "September", "October", "November", "December"};
//    static String eventDay, eventMonth, eventYear;
    static private int daysInMonth;
    public CalendarMain() {
        loadFromFile();
        /*
        Date Panel
        Consists of the Month and Year Selector in ComboBox
        Both ComboBox has a (ItemListener) that will fire a new Table when the date changes
        Left and Right Button to jump the calendar by 1 month (ActionListener)
        Date Label stating the current Month and Year shown on the Calendar
         */
        datePanel = new JPanel();
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

        currentDateLabel = new JLabel();
        currentDateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        button1 = new JButton("<");
        button1.addActionListener(this);
        button2 = new JButton(">");
        button2.addActionListener(this);
        buttonAdd = new JButton("+");
        buttonAdd.addActionListener(this);

        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);
        datePanel.add(button1);
        datePanel.add(currentDateLabel);
        datePanel.add(button2);
        datePanel.add(buttonAdd);
        datePanel.setBackground(new Color(189, 178, 255));

        /*
        Logout Panel
        A JButton that will bring the user back to the Login Screen (ActionListener)
         */
        logoutPanel = new JPanel();
        buttonLogOut = new JButton("LogOut");
        buttonLogOut.setPreferredSize(new Dimension(80,30));
        logoutPanel.add(buttonLogOut);
        buttonLogOut.addActionListener(this);
        logoutPanel.setBackground(new Color(255, 173, 173));

        /*
        Calendar Panel
        Uses a GridLayout to place each JTextArea onto a Panel
        Has a updateCalender function to initialize a new Table when there is any ItemEventChanged
         */
        calendarPanel = new JPanel();
        calendarPanel.setLayout(new GridLayout(0, 7, 5, 5));
        Date date = new Date();
        updateCalendar(MONTHS[date.getMonth()], (1900 + date.getYear()));
        yearComboBox.setSelectedItem((1900 + date.getYear()));
        monthComboBox.setSelectedItem(MONTHS[date.getMonth()]);
        calendarPanel.setBackground(new Color(202, 255, 191));

        /*
        JFrame Initialization
        Uses BorderLayout to manage the 3 Panels
        Resizable to suit your preference
         */
        setTitle("My Calender");
        setLayout(new BorderLayout());
        add(datePanel, BorderLayout.NORTH);
        add(logoutPanel, BorderLayout.SOUTH);
        add(calendarPanel,BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(true);
        setLocationRelativeTo(null);
        datePanel.setVisible(false);
        logoutPanel.setVisible(false);
        calendarPanel.setVisible(false);
        setVisible(true);
    }

    /**
     * Main will instantiate TimeTable and LogInWindow
     * It will also print out the number of events that are loaded from savefile.txt into evenArrayList
     */
    public static void main(String[] args) {

        CalendarMain frame = new CalendarMain();
        LogInWindow logInWindow = new LogInWindow();
        System.out.println("There are " + Event.getEventArrayList().size() + " events.");
    }

    /**
     * itemStateChanged adds a EventListener to the month and year ComboBox
     * It is mainly from this EventListener that will trigger the Calendar Table to update
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            updateCalendar((String) monthComboBox.getSelectedItem(), (Integer) yearComboBox.getSelectedItem());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * actionPerformed EventListener for both the Left and Right Button (button1 and button2) to switch Calendar Months.
         * Every button pushed will change the monthComboBox by referencing its index and trigger updateCalender()
         * If Else statements will check if the Calendar is at January of December and jump the Year by 1.
         * It also checks if the date is January 2010 or December 2050 and does nothing for certain buttons
         */
        if (e.getSource() == button1) {
            if (monthComboBox.getSelectedItem().toString() == "January") {
                if (yearComboBox.getSelectedItem().equals(2010)) {

                } else {
                    monthComboBox.setSelectedIndex(monthComboBox.getSelectedIndex() + 11);
                    yearComboBox.setSelectedIndex(yearComboBox.getSelectedIndex() - 1);
                }
            } else {
                monthComboBox.setSelectedIndex(monthComboBox.getSelectedIndex() - 1);
            }
        }
        if (e.getSource() == button2) {
            if (monthComboBox.getSelectedItem().toString() == "December") {
                if (yearComboBox.getSelectedItem().equals(2050)) {

                } else {
                    monthComboBox.setSelectedIndex(monthComboBox.getSelectedIndex() - 11);
                    yearComboBox.setSelectedIndex(yearComboBox.getSelectedIndex() + 1);
                }
            } else {
                monthComboBox.setSelectedIndex(monthComboBox.getSelectedIndex() + 1);
            }
        }
        /**
         * buttonAdd will open up a AddEventWindow
         */
        if (e.getSource() == buttonAdd) {
            AddEventWindow eventWindow = new AddEventWindow(daysInMonth,"Add New Event", "Add Event");
        }
        /**
         * buttonLogOut will set the Main page to Not Visible and initialise the Login Window
         */
        if (e.getSource() == buttonLogOut) {
            CalendarMain.datePanel.setVisible(false);
            CalendarMain.logoutPanel.setVisible(false);
            CalendarMain.calendarPanel.setVisible(false);
            LogInWindow logInWindow = new LogInWindow();
        }
    }

    /**
     * Save File function to iterate eventArrayList and save each line into savefile.txt.
     * This method is called every time updateCalendar() is triggered, essentially an Auto Save function
     * Try Catch with IOException Handling.
     * If no file is found in the directory it will automatically create a new savefile.txt.
     */
    public static void saveToFile() {
        try {
            File myfile = new File("savefile.txt");
            FileWriter writer = new FileWriter(myfile);
            for (Event event : Event.getEventArrayList()) {
                writer.write(event.toString() + System.lineSeparator());
            }
            writer.close();
        } catch (IOException c) {
            c.printStackTrace();
            JOptionPane.showMessageDialog(null, "File could not be saved", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Load File function to load from savefile.txt.
     * Uses BufferedReader to read and write into String data, instantiate an Event object and push to eventArrayList
     * Has IOException to catch error if savefile.txt cannot be found in directory.
     * NullPointerException catches error if savefile.txt is corrupted.
     */
    public void loadFromFile() {
        try {
            File file = new File("savefile.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                String[] data = str.split(";");
                Event event = new Event(data[0], data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(),
                        data[5].trim(), data[6].trim(), data[7].trim(), data[8].trim());
                Event.getEventArrayList().add(event);
            }
        }
        catch (IOException c) {
            c.printStackTrace();
            JOptionPane.showMessageDialog(null, "File could not be loaded", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        catch (NullPointerException c) {
            c.printStackTrace();
            System.out.println("No events saved in file");
        }
    }

    /**
     * saveToFile method is first called to save any events that were created beforehand into savefile.txt.
     * IF function finds out how many dates there are in the corresponding month
     * Referencing from WEEKDAYS, DAYS, date.getYear and date.getMonth it will populate a textArea for the month using
     * a for LOOP.
     * Each Day will get its own textArea with the Day number printed on the first line.
     */
    public static void updateCalendar(String month, int year) {
        saveToFile();
        calendarPanel.removeAll();
        for (int i = 0; i < WEEKDAYS.length; i++) {
            JLabel label = new JLabel(WEEKDAYS[i]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            calendarPanel.add(label);
        }
        Date date = new Date("01-" + month + "-" + year);
        int numOfDays = DAYS[date.getMonth()];
        if (date.getYear() % 4 == 0 && date.getMonth() == 1) {
            numOfDays = 29;
        }
        for (int i = 1, day = 1; day <= numOfDays; i++) {
            for (int j = 0; j < 7; j++) {
                String stringDay = String.valueOf(day);
                String stringYear = String.valueOf(year);
                if (day == 1) {
                    if (j == date.getDay()) {
                        JTextArea textArea = new JTextArea(String.valueOf(day)); //Here is where to input events***
                        textArea.setPreferredSize(new Dimension(100,100));
                        textArea.setBackground(new Color(253, 255, 182));
                        textArea.setAlignmentX(SwingConstants.RIGHT);
                        textArea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        textArea.setEditable(false);
                        /**
                         * ArrayList will be iterated with to find any events for that day and append Event Time and Title
                         * on a new line.
                         * Checks to ensure Array or variables not null
                         * Multiple events can be placed into the textArea
                         */
                        if(Event.getEventArrayList() != null) {
                            for (Event ev : Event.getEventArrayList()) {
                                if (ev.getEventDay().equals(stringDay) && ev.getEventMonth().equals(month)
                                        && ev.getEventYear().equals(stringYear)) {
                                    if (ev.getEventTime() != null && ev.getEventAMPM() != null && ev.getEventTitle() != null) {
                                        textArea.append("\n" + ev.getEventTime() + ev.getEventAMPM() + "  " + ev.getEventTitle());
                                    }
                                }
                            }
                        }
                        calendarPanel.add(textArea);
                        day++;

                        /**
                         * Mouse Adaptor for each cell
                         * Clicking on cell will save 3 variables to be used as arguments for ShowEvenWindow constructor
                         * Calls Event Window for the specific day
                         */
                        textArea.addMouseListener(new MouseAdapter() {
                            public void mouseReleased(MouseEvent e) {
                                try {
                                    String eventDay = textArea.getText().split("\n")[0];
                                    String eventMonth = monthComboBox.getSelectedItem().toString();
                                    String eventYear = yearComboBox.getSelectedItem().toString();
                                    ShowEventWindow showEventWindow = new ShowEventWindow(eventDay, eventMonth,eventYear);
                                }
                                catch (NullPointerException c) {
                                    c.printStackTrace();
                                }
                            }
                        });
                    }
                    else {
                        JLabel label = new JLabel("");
                        calendarPanel.add(label);
                    }
                }
                /**
                 * Else statement that will populate textAreas for days that are not 1
                 * Following steps are similar to the first block of code
                 */
                else {
                    JTextArea textArea = new JTextArea(String.valueOf(day));
                    textArea.setPreferredSize(new Dimension(100,100));
                    textArea.setBackground(new Color(253, 255, 182));
                    textArea.setAlignmentX(SwingConstants.RIGHT);
                    textArea.setEditable(false);
                    textArea.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    textArea.addMouseListener(new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            try {
                                String eventDay = textArea.getText().split("\n")[0];
                                String eventMonth = monthComboBox.getSelectedItem().toString();
                                String eventYear = yearComboBox.getSelectedItem().toString();
                                ShowEventWindow showEventWindow = new ShowEventWindow(eventDay, eventMonth,eventYear);
                            }
                            catch ( NullPointerException c) {
                                c.printStackTrace();
                            }

                        }
                    });
                    if(Event.getEventArrayList() != null) {
                        for (Event ev : Event.getEventArrayList()) {
                            if (ev.getEventDay().equals(stringDay) && ev.getEventMonth().equals(month)
                                    && ev.getEventYear().equals(stringYear)) {
                                if (ev.getEventTime() != null && ev.getEventAMPM() != null && ev.getEventTitle() != null) {
                                    textArea.append("\n" + ev.getEventTime() + ev.getEventAMPM() + " " + ev.getEventTitle());
                                }
                            }
                        }
                    }
                    calendarPanel.add(textArea);
                    day++;
                }
                if (day > numOfDays) {
                    daysInMonth = numOfDays;
                    break;
                }
            }
        }
        try {
            currentDateLabel.setText(monthComboBox.getSelectedItem().toString() + " " + yearComboBox.getSelectedItem().toString());
        }
        catch (NullPointerException c) {
            c.printStackTrace();
        }

        calendarPanel.updateUI();
    }
}
