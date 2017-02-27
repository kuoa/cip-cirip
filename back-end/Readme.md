# Web Site Functionality
--------------------

## API Description

- #### Description

- 
    ```
    SERVICE_ACCEPTED    : { status : "accepted" }
    SERVICE_REFUSED     : { status : "error", code : "error_code" , message : "custom error message" }
    ```
    ```
    NOTE                : All the output forms contain one of these templates and eventually some additional information
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
--------------------

- #### Create User :
    ```
    Url         : /user/create
    Description : creates a user
    Input       : { user, pass, mail }
    Output      : { }
    ```
    ```
    Error Codes
    EXISTING_LOGIN      : 1;
    ````
    
- ### Delete User
   ```
    Url         : /user/delete
    Description : deletes an existing user
    Input       : { user, pass, mail }
    Output      : { }
    ```
    ```
    Error Codes
    INCORECT_LOGIN      : 1;
    INCORECT_PASSWORD   : 2;
    INCORECT_MAIL       : 3;
    ````
    
- ### Login User
   ```
    Url         : /user/login
    Description : login an existing user
    Input       : { user, pass }
    Output      : { id : "id", key : "key", username : "username" }
    ```
    ```
    Error Codes
    INCORECT_LOGIN      : 1;
    INCORECT_PASSWORD   : 2;
    ````
    
- ### Logout User
   ```
    Url         : /user/logout
    Description : logout an existing user
    Input       : { key : "key" }
    Output      : { }
    ```
    
    ****
    
- ### Add Friend
   ```
    Url         : /friends/add
    Description : add a new friend
    Input       : { key : "key", friendLogin : "friendLogin" }
    Output      : { }
    ```
    ```
    Error Codes
    USER_LOGGED_OUT         : 1;
    INCORECT_FRIEND_LOGIN   : 2;
    ALREADY_FRIENDS         : 3;
    ````

- ### Remove Friend
  ```
    Url         : /friends/remove
    Description : remove a friend
    Input       : { key : "key", friendLogin : "friendLogin" }
    Output      : { }
    ```
    ```
    Error Codes
    USER_LOGGED_OUT         : 1;
    INCORECT_FRIEND_LOGIN   : 2;
    NOT_FRIENDS             : 3;
    ````

- ### Get User Friends

  ```
    Url         : /friends/get
    Description : get the list of friends for this user
    Input       : { key : "key", userLogin : "userLogin" }
    Output      : { friends : [ { id : "id1", login : "login1"} ... { id : "idN", login : "loginN"} ] }
    ```
    ```
    Error Codes
    USER_LOGGED_OUT	    : 1;
    INCORECT_LOGIN	    : 2;
    ````
    --------------------
    
- ### Add Comment
    ```
    Url         : /comments/add
    Description : add a new comment
    Input       : { key : "key", comment : "comment" }
    Output      : { }
    ```
    ```
    Error Codes
    USER_LOGGED_OUT         : 1;
    ````
    
- ### Remove Comment

    ```
    Url         : /comments/remove
    Description : remove an existing comment
    Input       : { key : "key", commentId : "commentId" }
    Output      : { }
    ```
    ```
    Error Codes
    USER_LOGGED_OUT         : 1;
    ````
    
- ### Get User Comments

    ```
    Url         : /comments/get-for-user
    Description : get the list of comments for this user
    Input       : { key : "key", userLogin : "userLogin" }
    Output      : { comments : [
				                 {
                				   author : { id : "id", login: "login" },
                    		       comment : { date : { $date : "date" },
                				               image : "image-url",
                					           hashtags : ["hash1" .. "hashN"],
                					           text : "text", 
                					           video :"video-url",
                					           reply_to_id : "reply_to_id",
                					           likes : "likes" },
                			       _id : { $oid : "comment-id" }
				                 }
                				   ....
                				   ....
			                   ]
		         }
				   
    ```
    ```
    Error Codes
    USER_LOGGED_OUT     : 1;
    INCORECT_LOGIN	: 2;
    ````


- ### Search comments
    ```
    Url         : /comments/search
    Description : search comments containing a specific query
    Input       : { key : "key", query : "query", forFriends : "true | false", myself : "true | false "}
    Output      : { comments : [
				                 {
                				   author : { id : "id", login: "login" },
                    		       comment : { date : { $date : "date" },
                				               image : "image-url",
                					           hashtags : ["hash1" .. "hashN"],
                					           text : "text", 
                					           video :"video-url",
                					           reply_to_id : "reply_to_id",
                					           likes : "likes" },				   	       
                			       _id : { $oid : "comment-id" }
				                 }
                				   ....
                				   ....
			                  ]
		         }
				    
    ```
    ```
    Error Codes
    USER_LOGGED_OUT     : 1;
    INCORECT_LOGIN	    : 2;
    ````
