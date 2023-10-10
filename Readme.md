Terminal Password Manager App Readme

Introduction    
The Terminal Password Manager App is a Java application that allows users to manage their passwords securely in a terminal-based environment. This password manager provides the ability to store, retrieve and delete automaticaly generated and user defined passwords for various websites or applications. It is built using Java and SQLite for database storage.

Features  
The Terminal Password Manager App includes the following features:

Database Management:  
The application uses an SQLite database to store password information, ensuring data persistence between sessions.

Password Storage:  
Users can save passwords for different websites or applications. Passwords are associated with a site name, a title, and a strength indicator (strong or weak).

Password Generation:  
The app can generate strong or weak passwords or a user defined password based on user preferences.

Commands:	

-h or --help = shows help message with avialable option and commands;	

-V = shows the verison of the application;	
	
save = activates the save new password subcommand;	

	-s or --site = (-s=example.com), sets the site;		
 
	-t or --title = (-t=example), sets the title;	
 
	--strong = (--strong=1), 1 for 16 random 16 character "strong" password, 0 for 8 character random "weak" password	
 
	-c or --custom = (-c=customPassword), set custom password	
 
	
get = activates the get password subcommand;	

	-s or --site = (-s=example.com) will retrieve password by site;	
 
	-t or --title = (-t=example) will retrieve password by title;	
 
	--id = (--id=8) will retrieve password by id;	
 
	
delete = activates the delete password subcommand;	

	-s or --site = (-s=example.com) will delete password by site;	
 
	-t or --title = (-t=example) will delete password by title;	
 
	--id = (--id=8) will delete password by id;	
 
	
Example uses:	

save -s=testSite -t=testTitle = will save random generated password with 8 characters with given "site" and "title"
	
save -s=testSiteStrong -t=testTitleStrong --strong=1 = will save random generated password with 16 characters with given "site" and "title"
	
save -s=testSiteCustom -t=testTitleCustom -c = will prompt the user to enter custom password and save it with given "site" and "title"
	
get -s=testSite = will copy the password with site value = "testSite" into the clipboard
	
get -t=testTitle = will copy the password with title value = "testTitle" into the clipboard
	
get --id=99 = will copy the password with id value = 99 into the clipboard
	
delete -s=testSite = will delete password with site value = "testSite"
	
delete -t=testTitle = will delete password with title value = "testTitle"
	
delete --id=8 = will delete password with id value = 8
	
Note:  

Please take in consideration that this project is in its beginning phase and its development is ongoing. All newly developed features and other changes will be commited to this repository after being finished.
	
You can find built .jar file for testing in the out/ directory.
