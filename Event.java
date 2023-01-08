import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Event {
    /**
     * Event object with 9 variables
     * Events are stored in Arraylist<Event> eventArrayList
     * TIME[] is referenced by sortArrayList as a index comparator
     */
    private String eventDay, eventMonth, eventYear, eventTime, eventAMPM;
    private String eventType, eventTitle, eventDescription, eventStatus;
    static final String[] TIME = {"1200", "1230", "0100", "0130", "0200", "0230", "0300", "0330", "0400", "0430", "0500", "0530", "0600",
            "0630", "0700", "0730", "0800", "0830", "0900", "1000", "1030", "1100", "1130"};
    private static ArrayList<Event> eventArrayList = new ArrayList<>();

    Event() {

    }

    /**
     * Event Constructor with 9 parameters
     *
     * @param eventDay
     * @param eventMonth
     * @param eventYear
     * @param eventTime
     * @param eventAMPM
     * @param eventType
     * @param eventTitle
     * @param eventDescription
     * @param eventStatus
     */
    public Event(String eventDay, String eventMonth, String eventYear, String eventTime, String eventAMPM, String eventType,
                 String eventTitle, String eventDescription, String eventStatus) {

        this.eventDay = eventDay;
        this.eventMonth = eventMonth;
        this.eventYear = eventYear;
        this.eventTime = eventTime;
        this.eventAMPM = eventAMPM;
        this.eventType = eventType;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventStatus = eventStatus;
    }

    /**
     * Getters and Setters for Event Object
     */
    public String getEventDay() {
        return eventDay;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventMonth() {
        return eventMonth;
    }

    public void setEventMonth(String eventMonth) {
        this.eventMonth = eventMonth;
    }

    public String getEventYear() {
        return eventYear;
    }

    public void setEventYear(String eventYear) {
        this.eventYear = eventYear;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventAMPM() {
        return eventAMPM;
    }

    public void setEventAMPM(String eventAMPM) {
        this.eventAMPM = eventAMPM;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    /**
     * toString() function is used when writing from object to savefile.txt
     * @return
     */
    @Override
    public String toString() {
        return eventDay +
                "; " + eventMonth +
                "; " + eventYear +
                "; " + eventTime +
                "; " + eventAMPM +
                "; " + eventType +
                "; " + eventTitle +
                "; " + eventDescription +
                "; " + eventStatus;
    }

    /**
     * Getter that returns eventArrayList
     * @return
     */
    public static ArrayList<Event> getEventArrayList() {
        return eventArrayList;
    }

    /**
     * Adds Event object to eventArrayList
     * @param obj
     */
    public static void addEvent(Event obj) {
        eventArrayList.add(obj);
    }

    /**
     * Removes Event object from eventArrayList
     * @param obj
     */
    public static void removeEvent(Event obj) {
        eventArrayList.remove(obj);
    }

    /**
     * SORT function for eventArrayList
     * It does 2 SORTS.
     * First SORT compares the AM/PM of the Time and sorts AM first.
     * Second SORT compares uses the index of the TIME[] to sort eventTime.
     * @param events
     */
    public static void sortArrayList(ArrayList<Event> events) {
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                String time1 = o1.getEventAMPM();
                String time2 = o2.getEventAMPM();
                int compare1 = time1.compareTo(time2);
                if (compare1 != 0) {
                    return compare1;
                }
                Integer time3 = Arrays.binarySearch(TIME, o1.getEventTime());
                Integer time4 = Arrays.binarySearch(TIME, o2.getEventTime());
                return time3.compareTo(time4);
            }
        });
    }
}
