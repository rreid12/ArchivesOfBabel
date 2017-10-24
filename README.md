# Archives of Babel

-This project was completed for a Database Management Systems course by myself and two other students
-It is intended to function as a general library's database and has been written in SQLite
-A front end for both members and employees of the library is also included and is written in Java
-Much of the documentation of the design process is included as PDFs

SET-UP:
Enter the directory on your system which contains the Executable JAR file submitted with the project and enter the 
following command:
	
	java -jar AoBApplication.jar <path to database file>


Be sure to add your system’s absolute path to the database file at the end of the command to ensure that the program 
can properly connect to the AoB Database. If done correctly, the front page of the application should appear.

PLEASE NOTE: 
The GUI application appears as it should on Windows systems, as shown in the screenshots from the packet,
powerpoint, and in the actual in-class presentation. However, when tested in UNIX based environments, the labels 
on the GUI have a tendency to be slightly cut off. It is very possible that this will occur when the program is run 
in Mac OSX as well. In future prototypes/releases of this product, it would be top priority to correct this error.



















---------------------------------------------------------(FIXED)----------------------------------------------------
BUGS:
Enhancement Bugs:
-Adding ability to add new books to the library
	*Create SQL statement to insert into the Book/DVD tables in the database that will add new tuples to the tables
	*Turned out to be a little bit more complex than was hoped, adding tuples to tables with multiple foreign keys
	*Not sure how to add tuples to not only the Book table, but also Location, and Author, for example

-------------------------------------------------------(UNRESOLVED)--------------------------------------------------
BUGS:
Major Bugs:
-Labels get cut off in UNIX-based systems
	*Every time the application is run in Linux, the graphical labels/titles are cut off/truncated
	*Issue does not appear when running in Windows

Enhancement Bugs:	
-Only allowing user to select 1 checkbox on the 'Employee Toolkit' Page
	*Could create radio buttons or a checkbox group to ensure that user can only select one search type at a time


