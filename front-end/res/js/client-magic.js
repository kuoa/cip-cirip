/*---------------------------------------------*/
/* Main										   */
/*---------------------------------------------*/

function initProfile(){

	environment = {};
	environment.profile = {};
	environment.users = {};
	environment.comments = {};
	environment.friends = {};
	
	initUser();
	initFriends();
	initComments();
	
	generatePage();
	
}

function initUser(){
	
	u = new User(101, "Jack_Rabbit", false, "I wish i was taller", "res/images/user.jpg", 
			"Aenean lacinia bibendum nulla sed consectetur. Vestibulum id ligula porta felis euismod semper." +
			" Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Cras justo odio, dapibus ac facilisis in," +
			" egestas eget quam. Vestibulum id ligula porta felis euismod semper.", "Photosasdas");
	
	environment.profile = u;
	//environment.profile = false;
}


function initComments(){	
	
	comm = '{"comments":[{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304559022},"image":"","hashtags":["#yolo","#swag","#SWA0"],"text":"Hey this is pretty cool right? #yolo #swag #SWA0","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62f2d0fed20d64d9deb"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304558981},"image":"","hashtags":["#video"],"text":"Hey check this #video https://www.youtube.com/embed/U5zZ1l4scgM coll right?","video":"https://www.youtube.com/embed/U5zZ1l4scgM","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62e2d0fed20d64d9dea"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304558570},"image":"https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg","hashtags":["#image"],"text":"Hey check out this #image https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg cool right?","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62e2d0fed20d64d9de9"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458304524803},"image":"","hashtags":[],"text":"I am heros second comment","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf60c2d0fed209d35fb47"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458304524740},"image":"","hashtags":[],"text":"I am heros comment","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf60c2d0fed209d35fb46"}}],"status":"succes"}';
	new CommentList(comm);
	
}

var initFriends = function (){
	friends = '{"friends":[{"id":4,"login":"Hero"},{"id":5,"login":"Zeus"}],"status":"succes"}';
	
	new FriendList(friends);		
	
}

function generatePage(){	
	
	generateTopPanel();
	generateLeftPanel();
	generateCenterPanel();
	generateRightPanel();	
}

function generateTopPanel(){
	
	var topHtml = getNavbarHtml();
	var headerHtml = getHeaderHtml();	
	
	$('body').prepend(topHtml);
	$('#center-panel').prepend(headerHtml);		
}


function generateLeftPanel(){
	
	var user = environment.profile;
	
	if (user){
		
		var mainHtml = user.getMainSectionHtml();
		var aboutHtml = user.getAboutHtml();
		
		$("#left-panel").append(mainHtml);
		$("#left-panel").append(aboutHtml);
	}

}

function generateCenterPanel(){
		
	var user = environment.profile;
	var comments = environment.comments;
	
	if (user){
		
		var commentHtml = user.getCommentHtml();		
		$('#center-panel').append(commentHtml);		
	}
	
	var commentsHtml = comments.getHtml(5);
	$("#center-panel").append(commentsHtml);
}

function generateRightPanel(){
	
	var user = environment.profile;
	
	if (user){
		var friendsHtml = user.getFriendsHtml();
		var photosHtml = user.getPhotosHtml();
	
		$('#right-panel').prepend(friendsHtml);
		$('#right-panel').append(photosHtml);
	}
	
}