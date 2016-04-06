/*---------------------------------------------*/
/* Main										   */
/*---------------------------------------------*/

function initProfile(){
			
	initLocalData();
	initSession();
	
	initComments(); 	// latest general comments
	initFriends();
	
	
	generatePage();
	generateEvents();	
}

function initLocalData(){
	
	environment = {};
	environment.profile = false;
	environment.users = {};
	environment.comments = {};
	environment.friends = {};	
}


function initComments(){	
	
	var user = environment.profile;
	
	if(user){		
		getFriendsComments();
	}
	else{		
		searchComment();		
	}
	
}

function initFriends(){
	
	var user = environment.profile;
	
	if(user){
		getFriendsList();
	}		
}

function initSession(){

	var profile = JSON.parse (localStorage.getItem("profile"));
	var key = localStorage.getItem("key");	
			
	if(profile && key){				
		u = new User(profile.id, profile.login, profile.friend, profile.bio, profile.image, profile.about, profile.photos);
		environment.users[profile.id] = undefined;
		environment.profile = u;
		environment.profile.key = key;
	}
}

function createSession(profile, key){		
	localStorage.setItem("profile", JSON.stringify(profile));
	localStorage.setItem("key", key);
}

function removeSession(){
	localStorage.removeItem("profile")
	localStorage.removeItem("key");	
}

function generatePage(){	
	
	generateTopPanel();
	generateCenterPanel();
	
	generateLeftPanel();	
	generateRightPanel();	
}

function generateTopPanel(){
				
	var topHtml = getNavbarHtml();	
	var modalHtml = getAuthModalHtml();
	
	$('.navbar').empty().append(topHtml);	
	$('.my-modal').prepend(modalHtml);	
	
	var user = environment.profile;
	
	if(user){
		$('#auth').hide();
	}
	else{
		$('#logout').hide();
		$('#settings').hide()		
	}	
}


function generateLeftPanel(){
	
	var user = environment.profile;
	
	if (user){				
		
		var mainHtml = user.getMainSectionHtml();
		var aboutHtml = user.getAboutHtml();
		
		$("#left-panel").empty().append(mainHtml);
		$("#left-panel").append(aboutHtml);
	}
	else{
		$("#left-panel").empty();
		$("#left-panel").empty();
	}

}

function generateCenterPanel(){
		
	var user = environment.profile;
	var comments = environment.comments;
	
	if (user){		
		var commentHtml = user.getCommentHtml();		
		$('#center-panel').empty().append(commentHtml);
		
		var commentsHtml = comments.getHtml(5);
		$("#center-panel").append(commentsHtml);		
	}
	else{		
		var commentsHtml = comments.getHtml(5);		
		var headerHtml = getHeaderHtml();					
		
		$('#center-panel').empty().append(headerHtml);
		$("#center-panel").append(commentsHtml);
	}		
}

function generateRightPanel(){
	
	var user = environment.profile;
	
	if (user){
		var friendsHtml = user.getFriendsHtml();
		var photosHtml = user.getPhotosHtml();
	
		$('#right-panel').empty().append(friendsHtml);
		$('#right-panel').append(photosHtml);
	}	
	else{
		$('#right-panel').empty();
		$('#right-panel').empty();
	}
}

function generateEvents(){

	$('#btn-login').click(login);
	$('#btn-signin').click(signin);
	$('#logout').click(logout);
	
	$('#form-comment').submit(addComment);
	
	$('a#delete-comment').click(removeComment);
	
	$('a#friend-status').click(changeFriendStatus);
	
}