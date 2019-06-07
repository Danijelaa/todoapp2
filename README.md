# ToDoApp2
## Description
This is simple web application (similar to **_trello_**) which enables all users to create an account and logged users to represent their projects with _dashboards_.
 Projects are separated on smaller _tasks_.
Each task belongs to specific column (named _column dashboard_ in this application) depending on its current state (to do, in progress, done,...).  

Logged user can:  
- create, update, delete his/her dashboard;
- create, update, delete columns (dashboard columns) on one of his/her dashboards;
- create, update (including changing dashboard column), delete tasks inside one of dashboard columns.

Backend of the application is developed in **Spring Boot, Spring Security (with JWT implementation), Hibernate/JPA** framework. Application uses SQL database **MySQL** to store data.
## Installation
1. Start MySQL server.
2. Create database named _todoapp2_ by command `CREATE DATABASE todoapp2`.
3. Clone or download the project and unpack the folder. Navigate to file _application.properties_ and set username, password, 
IP address and port according to your configuration of MySQL server.
4. Navigate to project where pom.xml file is. Start the application using Maven command:
`mvn spring-boot:run`.
5. Open Postman or some other advanced REST client and connect to some of the bellow specified REST APIs. 
Application server is running on local machine on port 8080.
6. If you want to log in already existing account, you can type in one of the following credentials for username and password, respectively:  
`user1`  `password1`  
`user2`  `password2`
## REST api
| Method | URL |Request Body (JSON)| Description |
|--------|-----|-------------------|-------------|
|POST  |`/api/users/login`|userLogin*|Login to application.|
|POST  |`/api/users/register`|userRegister*|Creating an account.|
|GET  |`/api/dashboards`||Get all dashboards of logged user.|
|GET  |`/api/dashboards/{dashboardId}`<br> _dashboardId_-type Numeric (integer)||Retrieving dashboard (of logged user) with specified ID.|
|DELETE  |`/api/dashboards/{dashboardId}`<br> _dashboardId_-type Numeric (integer)||Deleting dashboard (of logged user) with specified ID.|
|POST  |`/api/dashboards`|dashboard*|Creating new dashboard.|
|PUT  |`/api/dashboards/{dashboardId}`<br> _dashboardId_-type Numeric (integer)|dashboard**|Updating dashboard (of logged user) with specified ID.|
|GET  |`/api/dashboards/{dashboardId}/dashboard-columns`<br> _dashboardId_-type Numeric (integer)||Retrieving dashboard columns of dashboard (of logged user) with specified ID.|
|GET  |`/api/dashboard-columns/{dashboardColumnId}`<br> _dashboardColumnId_-type Numeric (integer)||Retrieving dashboard column (of logged user) with specified ID.|
|DELETE  |`/api/dashboard-columns/{dashboardColumnId}`<br> _dashboardColumnId_-type Numeric (integer)||Deleting dashboard column (of logged user) with specified ID.|
|POST  |`/api/dashboard-columns`|dashboard-column*|Creating new dashboard column on dashboard with ID specified in JSON object.|
|PUT  |`/api/dashboard-columns/{dashboardColumnId}`<br> _dashboardColumnId_-type Numeric (integer)|dashboard-column**|Updating dashboard column (of logged user) with specified ID.|
|GET  |`/api/dashboard-columns/{dashboardColumnId}/tasks`<br> _id_-type Numeric (integer)||Retrieving tasks of dashboard column (of logged user) with specified ID.|
|GET  |`/api/tasks/{taskId}`<br> _taskId_-type Numeric (integer)||Retrieving task (of logged user) with specified ID.|
|DELETE  |`/api/tasks/{taskId}`<br> _taskId_-type Numeric (integer)||Deleting task (of logged user) with specified ID.|
|POST  |`/api/tasks`|*task|Creating new task in dashboard column whose ID is specified in JSON object.|
|PUT  |`/tasks/{taskId}`<br> _taskId_-type Numeric (integer)|**task|Upading task (of logged user) with specified ID.|

**All above listed endpoints (except _/api/users/login_ and _/api/users/register_) must be requested by adding header to HTTP request named _token_ with JWT value sent in 
HTTP response body after login. This token/header is necessary for user authentication.**

#### Formats of JSON objects required in request body
*userLogin

| Field | Type |
|-------|------|
|username|String|
|password|String|  

*userRegister

| Field | Type |
|-------|------|
|username|String|
|password|String|
|passwordConfirm|String| 

*dashboard

| Field | Type |
|-------|------|
|title|String|

**dashboard

| Field | Type |
|-------|------|
|id|Numeric (integer)|
|title|String|  

*dashboard-column

| Field | Type |
|-------|------|
|title|String|
|dashboardId|Numeric (integer)|

**dashboard-column

| Field | Type |
|-------|------|
|id|Numeric (integer)|
|title|String| 

*task

| Field | Type |
|-------|------|
|title|String|
|description|String|
|dashboardColumnId|Numeric (integer)|

**task

| Field | Type |
|-------|------|
|id|Numeric (integer)|
|title|String|
|description|String|
|dashboardColumnId|Numeric (integer)|
