/*---------------------------------------------*/
/* 				Generic functions 			   */
/*---------------------------------------------*/
		
var root = "http://li328.lip6.fr:8280/girraffe";

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

function createCookie(name,value,days) {
	if (days) {
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

function eraseCookie(name) {
	createCookie(name,"",-1);
}
	
/*---------------------------------------------*/
/* 				    User					   */
/*---------------------------------------------*/

function login(element){
	
	var url = "/user/login";
	var user = $('#user').val();
	var pass = $('#pass').val();
	
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
			
			createCookie("userId", json.id, 1);
			createCookie("userKey", json.key, 1);
			createCookie("userLogin", json.username, 1);
			
			//alert(json.id + json.key + json.username);
			
			alert(readCookie("userId"));
			alert(readCookie("userKey"));
			alert(readCookie("userLogin"));
			
			//window.location.replace("profile.html");
					
		}
	}
	
	serverRequest(url, data, doneFun);
	return false;
}
	

/*---------------------------------------------*/
/* 				Friends						   */
/*---------------------------------------------*/
		
function commentGetForFriends(element){
	
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