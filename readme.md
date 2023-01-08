# Electronic Calendar
This is a fully functional electronic calendar that can Add, Edit and Remove events.
It is programmed using Java and utilises Java Swing for the GUI.

It uses Events as a Object to store individual events and an ArrayList
to store all the events. The Events are sorted based on time so they will
appear in a chronological order. The program is based off a Model - View -
Controller (MVC) pattern.


Make sure that *Image* folder is included or the program will not run.

The Program can be run from *CalendarMain.java*.

The username is *admin* and password is *12345* for the Login Page.

Main Calendar Page will show the Time and Title of the events in
individual cells and the calendar can be resized to fit your preference. Other
windows are non-resizable. Events are ordered in chronological order
and each cell can technically hold an infinite amount of events
*although in reality there will be less than 10 events for a given day*. 
The program has an autosave function that saves events into savefile.txt
after any Event modification so no action is needed from the user.

There are 4 Functions to this program
* Add Event
* Show Event
* Edit Event
* Remove Event

*Add Event* can be triggered by the + Button on the Main Screen.

To trigger *Show Event*, click on any of the Day cells to see
what events there are on that day. Hovering on the Day cells will
change your cursor to a hand pointer - *It is pretty straightforward.*
From the *Show Events Window* there is a option to Edit or Remove the Event.


*Add & Edit Event codes uses the same interface in AddEventWindow and uses different
parameters to differentiate the GUI output.*

*Show and Remove Event codes can be found in ShowEventWindow.*

*The application is created on Windows using IntelliJ, certain sizes will differ 
if the program is run on a Mac*
