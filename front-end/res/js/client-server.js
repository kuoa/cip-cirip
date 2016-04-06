/*---------------------------------------------*/
/* 				Generic functions 			   */
/*---------------------------------------------*/
		
var root = "http://li328.lip6.fr:8280/Giraffe";

function serverRequest(url, data, doneFun){
	
	var request = $.ajax({
		url : root + url,		
		data : data,
		dataType : "json"	
	});
			
	request.done(doneFun);
	
	request.fail(function(msg) {
		console.log("Fail");
		console.log(msg);
	});
}

function serverRequestAsync(url, data, doneFun){
	
	var request = $.ajax({
		url : root + url,		
		data : data,
		dataType : "json",
		async : false
	});
			
	request.done(doneFun);
	
	request.fail(function(msg) {
		console.log("Fail");
		console.log(msg);
	});
}

/*---------------------------------------------*/
/* 				    User					   */
/*---------------------------------------------*/

function login(event){
	
	var url = "/user/login";
	var user = $('#user-login').val();
	var pass = $('#pass-login').val();
	
	var data = {
		user : user,
		pass : pass
	};
	
	var doneFun = function(json){		
						
		if(json.status === "error"){
			var message = json.message;			
			$('#login-msg').text(message);
		}
		else{						
			//get bio, image, about, photos
			
			var id = json.id;
			var key = json.key;
			var friend = false;
			var login = json.username;
			var bio = "Don't forget to make api for bio."
			var image = "res/images/user.jpg";
			var about = "Don't forget to make api for about."
			var photos = "Don't forget to make api for photos."
			
			user = new User(id, login, friend, bio, image, about, photos);
			environment.profile = user;
			environment.profile.key = key;			
			environment.users[id] = undefined;
			
			createSession(user, key);
						
			$('#login-modal').modal('hide');
			
			initComments();
			initFriends();			
			
			generatePage();
			generateEvents();									
		}
	}
	
	serverRequest(url, data, doneFun);
	return false;	
}


function signin(event){
	
	var url = "/user/create";
	var user = $('#user-signin').val();
	var pass = $('#pass-signin').val();
	var passRe = $('#pass-re-signin').val();
	var mail = $('#mail-signin').val();
	
	if(pass != passRe){
		var msg = "Please provide identical passwords."
		
		$('#signin-msg').text(msg);				
		return false;
	}
	
	var data = {
		user : user,
		pass : pass,
		mail : mail,
	};
	
	var doneFun = function(json){		
						
		if(json.status === "error"){
			var message = json.message;				
			$('#signin-msg').text(message);			
		}
		else{						
			
			var msg = user + " you can now login.";
			$('#signin-msg').text(msg);
			
			$(document).ready(function() {
			    setTimeout(function() {		    	
			      $('#signIn-modal').modal('hide');
			    }, 4000); // milliseconds
			});
		}
	}	
	serverRequest(url, data, doneFun);
	return false;	
}


function logout(event){	
	
	var url = "/user/logout";
		
	var key = environment.profile.key;			
	
	var data = {
		key : key
	};
	
	var doneFun = function(json){		
						
		if(json.status === "error"){
			var message = json.message;				
			console.log(message);
		}
		
		else {
			
			removeSession();
			
			initLocalData();
			initComments();
			
			generatePage();
			generateEvents();
		}
	}	
	serverRequest(url, data, doneFun);
	return false;	
}
	
/*---------------------------------------------*/
/* 				Comments					   */
/*---------------------------------------------*/

function addComment(event){		
	
	event.preventDefault();
	
	var url = "/comments/add";
	
	var key = environment.profile.key;			
	var comment = $('#comment-value').val();
	
	var data = {
		key : key,
		comment : comment
	};
	
	var doneFun = function(json){		
						
		if(json.status === "error"){
			var message = json.message;				
			console.log(message);
		}
		
		else {			
			
			$(document).ready(function() {
			    setTimeout(function() {		    	
			      $('#comment-modal').modal('hide');
			    }, 1000); // milliseconds
			});									
			
			initComments();			
			generateCenterPanel();
			generateEvents();						
		}
	}	
	serverRequest(url, data, doneFun);	
	return false;
}

function removeComment(event){		
	
	event.preventDefault();
	
	var url = "/comments/remove";
	
	var key = environment.profile.key;				
	var commentId = $(event.target).parent().attr('id');
	
	var data = {
			key : key,
			commentId : commentId
	};
	
	var doneFun = function(json){		
						
		if(json.status === "error"){
			var message = json.message;				
			console.log(message);
		}
		
		else {						
			// remove comment localy			
			var comments = environment.comments.list;
			
			for(i = 0; i < comments.length; i++){				
				if (comments[i].commentId == commentId){
					comments.splice(i, 1);
					break;
				}
			}
			
			generateCenterPanel();
			generateEvents();						
		}
	}	
	serverRequest(url, data, doneFun);	
	return false;
}



function searchComment(event, params){			
	
	var url = "/comments/search";
	var key = '';	
	var query = '';
	var forFriends = '';	
	
	if (params == undefined){
		event.preventDefault();
		
		key = environment.profile.key;		
		query = $('#search-term').val();
		forFriends = $('#for-friends').is(":checked");
		myself = false;			
		
	}
	else{		
		
		key = params.key;
		query = params.query;
		forFriends = params.forFriends;
		myself = params.myself;
	}	

	var data = {
			key : key,			
			query : query,
			forFriends : forFriends,
			myself : myself
	};
	
			
	function doneFun(json){		
						
		if(json.status === "error"){
			var message = json.message;				
			console.log(message);
		}
		
		else {
			new CommentList(json);
			
			generateCenterPanel();
			generateEvents();
			
			if(environment.comments.list.length == 0){
				var msg = '<div id="error-msg" style="display: none; text-align: center">' +
							'<h4>No comments match your search criterias</h4>' +
						  '</div>';
				
				$('#comment-zone').append(msg);
				$('#error-msg').show('slow');
			}
		}
	}
	
	serverRequestAsync(url, data, doneFun);
	
	return false;
}

function searchHashComments(event){
	
	var hash = $(event).text();
	
	var params = {
			key : "",
			query : hash,
			forFriends : false,
			myself : false			
	}
	
	searchComment(undefined, params);
	
	return false;	
}

function getCommentsForUser(event){		
			
	var url = "/comments/get-for-user";
	
	var key = environment.profile.key;			
	var userLogin = $(event).text();	
	
	var data = {
		key : key,
		userLogin : userLogin,
	};
	
	var doneFun = function(json){		
						
		if(json.status === "error"){
			var message = json.message;				
			console.log(message);
		}
		
		else {			
			new CommentList(json);
			
			generateCenterPanel();
			generateEvents();			
		}
		
	}	
	
	serverRequest(url, data, doneFun);	
	return false;
}

/*---------------------------------------------*/
/* 				Friends						   */
/*---------------------------------------------*/


function changeFriendStatus(event){
	
	var url = '';
	var urlRemove = "/friends/remove";
	var urlAdd = "/friends/add";
	
	var parent = $(event.target).parent();
	var key = environment.profile.key;
	
	var friendLogin = parent.children('#user-login').text();
	var friendId = parent.attr('user-id');
	var status = $(event.target).text();
	
	if(status === '-'){
		url = urlRemove;
	}else{
		url = urlAdd;
	}
	
			
	var data = {
		key : key,
		friendLogin : friendLogin
	};		
	
	function doneFun(json){
		
		if(json.status === "error"){
			var message = json.message;				
			console.log(message);
		}
		
		else {
			
			if(status === '-'){				
				
				// change friend status
				var exFriend = environment.users[friendId];
				exFriend.modifyStatus();
				
				// remove from friendList
				var friendsList = environment.friends.list;
				var indexFriends = friendsList.indexOf(exFriend);
				
				friendsList.splice(indexFriends, 1);

				// remove from commentList
				var comments = environment.comments.list;	
				for(var i = comments.length - 1; i >= 0; i--) {		
				   
			    	if(comments[i].authorId == friendId) {	      
				    	
				    	comments.splice(i, 1);
				    }
				}	
				
			}else{
				// change user status
				var newFriend = environment.users[friendId];
				newFriend.modifyStatus();
				
				// add to friendList
				var friendList = environment.friends.list;
				friendList.push(newFriend);				
			}
			
							
			generateLeftPanel();
			generateCenterPanel();
			generateRightPanel();
			generateEvents();
		}
	}
		
	serverRequest(url, data, doneFun);
	return false;	
}

function getFriendsList(event){
	
	var url = "/friends/get";
	
	var key = environment.profile.key;
	var userLogin = environment.profile.login;
	
	var data = {
		key : key,
		userLogin: userLogin
	};
	
	function doneFun(json){
		
		if(json.status === "error"){
			var message = json.message;				
			console.log(message);
		}
		
		else {					
			new FriendList(json);
			
			generateRightPanel();
			generateEvents();
		}
	}
	
	serverRequestAsync(url, data, doneFun);
	return false;		
}