# :red_car: SmartCar inc.

The purpose of this project is to create the connection between different departments of the company “SmartCar inc.”. The main business of the company “SmartCar inc.” is car production. The firm has five departments (sales, finance, accounting, production and stock), which are strongly interconnected. All processes of buying and getting information about the production execution are supported by web application.

**Tutor**: contact [Christian Ohrfandl](mailto:christian.ohrfandl@tuwien.ac.at) in case of fire :fire:

| Organisational                                                                                                                       | Headlines                                                             |
|--------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------|
| [:open_file_folder: Project Folder](https://drive.google.com/folderview?id=0ByYSCn1MSy8pUVlmdXZ5eU80eFk&usp=sharing)                 | [:page_facing_up: BPMN](#bpmn)                                        |
| [:clock230: Time tracking](https://docs.google.com/spreadsheets/d/1QY5_ztN_1TH2CoPAQqrZzUwK_UmveQsCXCblBRQb8Qk/edit?usp=sharing)     | [:package: How to run](#how-to-run)                                   |
| [:white_check_mark: Task List](https://docs.google.com/spreadsheets/d/1_TjLz4qZToyakAwf80rEB3D2yF0PxDMvkqzXGqtEBdk/edit?usp=sharing) | [:triangular_flag_on_post: Process Description](#process-description) |

## BPMN
![BPMN](http://i.imgur.com/ELWxpMa.png)

## How to run
1. copy git repo to your local machine using a git client of your choice (https://github.com/mathck/wmpm.git)
2. where the pom.xml file is located run "mvn clean install" ([install maven](https://maven.apache.org/install.html))
3. import maven project into any Java IDE of your choice (IntelliJ, Eclipse, ...)
4. define the run configuration: Java Application, main class: SmartCarApp
5. Press :arrow_forward: RUN!

## Process Description
##### Process order
Starting condition: Order has been received
Result: Order has been logged in the system, customer is informed on order retrieval and payment is initialized
Process: Order information is extracted from the order and stored in customer database. According to order information, check for financial solvency is initialized or advance payment is initialized.
##### Check for financial solvency
Starting condition: Message including customer information (Name, Address, Birthdate) as well as amount of money
Result: Positive or negative solvency check.
Process: Using several interfaces, relevant data for the solvency check will be received. During the consolidation process, decision on the solvency will be based on a predefined ruleset and fed back to the main process.
##### Query stock of machine elements
Starting condition: The processor will receive the order message after initial payment was completed
Result:Required machine elements will be ordered if necessary or reserved for production
Process: The information on the product ordered is extracted from the message and the database is queried for required parts in the production table. Availability of parts is checked in the database. If not enough parts are available, the ordering system is messaged. Otherwise, the production process is notified that production of the order can start right away..
##### Order Elements
Starting condition: Receive message containing missing parts
Result: Missing parts are ordered and production can be scheduled according to scheduled delivery date
Process: Extract information on required parts, notify production facility using a SOAP interface, retrieve information on scheduled delivery date. Forward information to plan production processor.
##### Plan Production
Starting conditions: Processor receives message containing the exact order. All machine elements have been reserved for production prior to message dispatch.
Result: Production process is scheduled. Finalize order node will be notified once production is completed
Process: Extract order information. Schedule machine time using a FIFO approach. Send expected delivery date to finalize order node.
##### Finalize Order
Starting condition: Production finished an order
Result: Payment will be requested. If successful, order will be dispatched
Process: Information on finished orders is received from production planning node. Payment will be requested using SOAP interface of invoice department.If successful, order will be dispatched.
##### Inform customer
Starting condition: Any component sends a message to the processor consisting of message body and receiver.
Result: Consumer will receive an email consisting of the relevant mail.
Process: Extract information on message body and recipient from message. Retrieve mail address from user database. Create and dispatch mail using SMTP.
