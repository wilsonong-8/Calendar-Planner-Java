import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ShowEventWindow extends JFrame {

    JPanel panel1, panel2;

    /**
     * ShowEventWindow takes in 3 arguments
     * @param eventDay
     * @param eventMonth
     * @param eventYear
     */
    public ShowEventWindow(String eventDay, String eventMonth, String eventYear) {
        /*
        The Window consists of two panels
        panel1 is default panel that shows no event for cells that are empty
        panel2 is a 20x1 GridLayout that shows events in every row in a vertical axis
         */
        panel1 = new JPanel(new FlowLayout());
        ImageIcon oopsIcon = new ImageIcon(this.getClass().getResource("/images/oops.png"));
        JLabel oopsImage = new JLabel();
        oopsImage.setIcon(oopsIcon);
        panel1.add(oopsImage);
        JLabel oopsTitle = new JLabel("No Event");
        panel1.setBackground(new Color(189, 178, 255));
        panel1.add(oopsTitle);

        panel2 = new JPanel(new GridLayout(20,1));
        panel2.setSize(850,400);
        panel2.setBackground(new Color(189, 178, 255));
        JScrollPane scrollPanePanel = new JScrollPane(panel2);
        add(scrollPanePanel);
        panel2.add(panel1);

        /*
        panel2 implementation will check the eventArrayList for any events for the textArea cell clicked by referencing
        from eventDay,eventMonth and eventYear for the correct date and set into individual components
         */
        if (Event.getEventArrayList() != null) {
            for (Event ev : Event.getEventArrayList()) {
                if (ev.getEventDay().equals(eventDay) && ev.getEventMonth().equals(eventMonth)
                        && ev.getEventYear().equals(eventYear)) {
                    panel2.remove(panel1);
                    JPanel eventPanel = new JPanel(new FlowLayout(SwingConstants.RIGHT,10,5));
                    eventPanel.setBackground(new Color(189, 178, 255));

                    JLabel timeLabel = new JLabel("  " + ev.getEventTime() + ev.getEventAMPM() + "  ");
                    timeLabel.setFont(new Font(null,Font.BOLD,13));
                    timeLabel.setBackground(new Color(253, 255, 182));
                    timeLabel.setOpaque(true);
                    eventPanel.add(timeLabel);

                    JLabel typeLabel = new JLabel("  " + ev.getEventType() + "  ");
                    typeLabel.setBackground(new Color(255, 173, 173));
                    typeLabel.setFont(new Font(null,Font.BOLD,12));
                    typeLabel.setOpaque(true);
                    eventPanel.add(typeLabel);

                    JLabel titleLabel = new JLabel("  " + ev.getEventTitle() + "  ");
                    titleLabel.setFont(new Font(null,Font.BOLD,12));
                    titleLabel.setBackground(new Color(255, 214, 165));
                    titleLabel.setOpaque(true);
                    eventPanel.add(titleLabel);

                    JTextArea descriptionArea = new JTextArea();
                    descriptionArea.setBackground(new Color(202, 255, 191));
                    descriptionArea.setEditable(false);
                    descriptionArea.setColumns(4);
                    descriptionArea.setLineWrap(true);
                    descriptionArea.setWrapStyleWord(true);
                    descriptionArea.setText(ev.getEventDescription());
                    JScrollPane scrollPane = new JScrollPane(descriptionArea);
                    scrollPane.setPreferredSize(new Dimension(150, 50));
                    eventPanel.add(scrollPane);

                    String[] statusArray = {"Pending", "Completed", "KIV"};
                    JComboBox statusComboBox = new JComboBox(statusArray);
                    statusComboBox.setSelectedItem(ev.getEventStatus());
                    eventPanel.add(statusComboBox);

                    /*
                    Event status will be set into the Combo box with (actionListener)
                    Event status will be saved immediately
                     */
                    statusComboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ev.setEventStatus(statusComboBox.getSelectedItem().toString());
                            CalendarMain.updateCalendar(eventMonth, Integer.parseInt(eventYear));
                        }
                    });

                    /*
                    editButton calls up AddNewWindow in Edit Mode taking in 3 arguments.
                     */
                    JButton editButton = new JButton("Edit");
                    editButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            AddEventWindow editEvent = new AddEventWindow(0,
                                    "Edit Event Window", "Confirm");
                            editEvent.setEvent(ev);
                            dispose();
                        }
                    });
                    eventPanel.add(editButton);

                    /*
                    Remove button is also added to remove any events by removing the object from eventArrayList
                    updateCalendar() saves the remove
                    updateUI() refreshed panel
                     */
                    JButton removeButton = new JButton("Remove");
                    removeButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Event.removeEvent(ev);
                            CalendarMain.updateCalendar(eventMonth, Integer.parseInt(eventYear));
                            panel2.remove(eventPanel);
                            panel2.updateUI();
                        }
                    });
                    eventPanel.add(removeButton);
                    panel2.add(eventPanel);
                }
            }
        }
                setTitle("Events on " + eventDay + " " + eventMonth + " " + eventYear);
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setSize(850,400);
                setLocationRelativeTo(null);
                setVisible(true);
        }
}
