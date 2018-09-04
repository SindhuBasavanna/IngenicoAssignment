# Transfer service API
### Description
This project is built for adding accounts and transferring funds between accounts.
The APIs contains two main entities
* Account
* Transaction

For simplicity and speed, and for the purpose of this assignment, h2 DB will be used. When the application starts it contains no data, and when the application shuts down all data is lost.

### Tools used
1. Java 8
2. Spring Boot
3. H2 - In memory embedded database
4. JPA
5. Tomcat - Embedded Web Server

### Assumptions

1. Security is considered out of scope for this application
2. All the amounts are in double to keep API simple

### How to run the app
Import the project as Maven project. All the necessary dependency jars will be downloaded automatically.
Once it imports all the jars run "MoneyTransferApplication.java" as "java application". 
Note:No need to do server setup as I am using springboot and springboot internally uses tomcat server so we can run this application as standalone.
1. To add account to h2 DB
Step1: In POSTMAN, 
	   provide url "http://localhost:8080/accounts" 
	   Select "POST" Provide header in Headers section: KEY = Content-Type and VALUE=application/json. 
Step2: Provide Body By adding account details:
		{
		"userName": [user-name],
		"balance": [amount]
		}
Step 3: Click on Send

2. To get Account/Accounts
Step1: In POSTMAN, 
	provide url "http://localhost:8080/accounts"  OR "http://localhost:8080/accounts/{accountId}"
	Select "GET"
	Click on Send.
	Records will be displayed in POSTMAN screen.

3. To transfer amount from one account to another
Step 1: In POSTMAN, provide url "http://localhost:8080/transfers"
Select "POST" Provide header in Headers section: KEY = Content-Type and VALUE=application/json. 
Step2: Provide Body By adding transfer details:
		{
		"sourceAccountId" : [sourceaccountId],
		"destinationAccountId" : [destinationaccountId],
		"amount": [amount-to-be-transfered]
		}
Step 3: Click on Send
	   

### Links ### H2 Database

* [H2 Database Console](http://localhost:8080/h2-console)
Driver Class:	org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password: blank
Table name: ACCOUNT 