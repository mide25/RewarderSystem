# RewarderSystem
#### Project Description

You work for an e-commerce startup that has customers across Nigeria. To create more engagement, the management of the company has approved giving away voucher codes to every customer on their birthday. However, the catch is the voucher code has a max amount and expiry period set based on how much value the customer has brought to the company. The rules can be described as follow:
 
| Voucher Amount | Customer Orders Value | Validity (days) |
|---|---|---|
|100|1000-5000|1|
|500|5000-10000|5|
|1000|10000 and above|10|
 
Your database admin was kind enough to give you a list of all customers and how much money they’ve spent on your website in the following format.
 
|Customer ID|Customer First Name|Order Value|
|---|---|---|
|1|Lanre|100|
 
Design a web application that accepts a customer list (as csv) as input and creates the corresponding vouchers for each customer that is eligible.

#### Setup
- Application is set to run on port *4000* by default, this can be changed in the *application.properties* file located in src/main/resources via the property `server.port`
- The configuration that is used to determine the value of a voucher based on customer spend is located in *config/VoucherConfig.json* and can be modified as required
- Sample files are located in the *samples* folder
- The application is a java application and [JRE](https://docs.oracle.com/goldengate/1212/gg-winux/GDRAD/java.htm#BGBFJHAB) is needed on host computer to run. To make things easy I have packaged a jar into the *dist* folder and the application can be run by entering `java -jar rewarder-1.0-SNAPSHOT.jar` on the command line   


