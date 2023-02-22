README
======
Licensepro is an application to track employees driving licenses and alcohol and drug tests for drivers that uses city vehicles.


The application is writen in java programming language using jsp and structs 2. Mysql is the database of choice.
To install you will need to compile the src code using java compiler and deploy using one of servlet/jsp servers running apache tomcat or other servlet containers.

In the WEB-INF directory, there is a file called web.xml.example, you will need to copy to a file with the name web.xml and set the url's in this file accordingly.

The database setup is in the file context.xml.example in the directory META-INF. You would need to change the file name to context.xml and set the url and password for your database.

The application assumes that you have a CAS authentication server. You will need the url for this server in WEB-INF/web.xml file mentioned before.

You will need to create the database (mysql) with the name licensepro and your own password. Use the file scripts/mysql.sql file to create these tables.
When database is created you will need to add a list of users who are allowed to use the system, look into database table users and add the users accordingly.

When everything is set, you can point your browser to

http://your server url/licensepro

You will be promted for Username and Password from your CAS server.



 
