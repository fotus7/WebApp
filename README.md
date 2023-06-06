# WebApp
Parses Excel data and adds it to MySQL database for further use with website created using Angular.
The purpose of this application is to parse company sales data, which is stored in Excel files and daily updated, to be conviniently displayed, sorted and searched on the analysis website.
Recieved sales data for each year is stored in Excel spreadsheets formatted in a particular way. Past and current data is stored on the internally located server computer in the company.
Current data file is updated daily so the database also has to be updated, while past data has to be stored untouched.
Application was also deployed on a local server computer, so only the employees have acces to the server, started by the application.
It utilizes Spring Boot, Spring Data JPA, Hibernate and Apache POI library. 
Application is succesfully used for company's business and analytics affairs.
For the purpose of confidentiality, exemplary files are modified so they contain no actual names of customers or products.
