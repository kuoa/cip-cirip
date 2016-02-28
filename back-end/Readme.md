# Current Project State
--------------------
## General Output Template
- #### Description
    ```
    SERVICE_ACCEPTED    : { status : "accepted" }
    SERVICE_REFUSED     : { status : "error", code : error_code , message : "custom error message" }
    ```
## General Error Codes
- #### Description
    ```
    MISSING_ARGUMENT    : -1;
    JSON_ERROR          : 100;
    SQL_ERROR           : 1000;
    JAVA_ERROR          : 10000;
    ```

## Implemented Services 
- #### Create User :
    ```
    Url         : /user/create
    Description : creates a new user
    Input       : { user, password, mail }
    Output      : { }
    ```
    ```
    Error Codes
    EXISTING_LOGIN      : 1;
    ````
    
- ### Delete User
- ### Login User
- ### Logout User
- ### Add Friend
- ### Remove Friend
- ### Add Comment
- ### Remove Comment