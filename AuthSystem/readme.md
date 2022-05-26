## AuthSystem
**AuthSystem is an api for authentification using Servlet**
**#Usage:**

[POST]localhost:8080/AuthSystem/api/login

 - username(parameter)
 - password(parameter)

***the result return the status code and a message in case of success it will be username, password and token, if not an erro message it will be shown***

[POST]localhost:8080/AuthSystem/api/signup

 - username(parameter)
 - password(parameter)
 
***the result return the status code and a message in case of success it will be username, password and token, if not an erro message it will be shown***

[PUT]localhost:8080/AuthSystem/api/create

 - username(parameter)
 - password(parameter)
 
***the result return the status code and a message in case of success it will be username and password , if not an erro message it will be shown***

[DELETE]localhost:8080/AuthSystem/api/delete

 - username(parameter)
 
***the result return the status code and a message in case of success it will be the deleted username, if not an erro message it will be shown***
