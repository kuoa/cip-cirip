/*---------------------------------------------*/
/* 				Generic functions 			   */
/*---------------------------------------------*/
		
var root = "http://li328.lip6.fr:8280/Giraffe";

function serverRequest(url, data, doneFun){
	
	var request = $.ajax({
		url: root + url,		
		data: data,
		dataType: "json"	
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
			var image = "Don't forget to make api for image."
			var about = "Don't forget to make api for about."
			var photos = "Don't forget to make api for photos."
			
			user = new User(id, login, friend, bio, image, about, photos);
			environment.profile = user;
			environment.profile.key = key;			
			environment.users[id] = undefined;
			
			
			initComments();
			initFriends();
			generatePage();
			
			$('#login-modal').modal('toggle');
		}
	}
	
	serverRequest(url, data, doneFun);
	return false;	
}
	

/*---------------------------------------------*/
/* 				Friends						   */
/*---------------------------------------------*/
		
function commentGetForFriends(event){
	
	var url = "/comments/get-for-friends";
	
	var data = {
		key : "769221ad24cc4ba6b493b0b4a977e6ad",
		userLogin: "TestUser"
	};
	
	var doneFun = function(msg) {
		console.log("Done");
		console.log(msg);
	};
	
	serverRequest(url, data, doneFun);				
}