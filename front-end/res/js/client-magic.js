/*---------------------------------------------*/
/* Main										   */
/*---------------------------------------------*/

function initProfile(){

	initLocalData();
	initComments(); 	// latest general comments
	
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
		// friend comments		
		comm = '{"comments":[{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304559022},"image":"","hashtags":["#yolo","#swag","#SWA0"],"text":"Hey this is pretty cool right? #yolo #swag #SWA0","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62f2d0fed20d64d9deb"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304558981},"image":"","hashtags":["#video"],"text":"Hey check this #video https://www.youtube.com/embed/U5zZ1l4scgM coll right?","video":"https://www.youtube.com/embed/U5zZ1l4scgM","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62e2d0fed20d64d9dea"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304558570},"image":"https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg","hashtags":["#image"],"text":"Hey check out this #image https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg cool right?","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62e2d0fed20d64d9de9"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458304524803},"image":"","hashtags":[],"text":"I am heros second comment","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf60c2d0fed209d35fb47"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458304524740},"image":"","hashtags":[],"text":"I am heros comment","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf60c2d0fed209d35fb46"}}],"status":"succes"}';
		new CommentList(comm);
	}
	else{
		// main comments
		comm = '{"comments":[{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304559022},"image":"","hashtags":["#yolo","#swag","#SWA0"],"text":"Hey this is pretty cool right? #yolo #swag #SWA0","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62f2d0fed20d64d9deb"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304558981},"image":"","hashtags":["#video"],"text":"Hey check this #video https://www.youtube.com/embed/U5zZ1l4scgM coll right?","video":"https://www.youtube.com/embed/U5zZ1l4scgM","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62e2d0fed20d64d9dea"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304558570},"image":"https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg","hashtags":["#image"],"text":"Hey check out this #image https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg cool right?","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62e2d0fed20d64d9de9"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458304524803},"image":"","hashtags":[],"text":"I am heros second comment","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf60c2d0fed209d35fb47"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458304524740},"image":"","hashtags":[],"text":"I am heros comment","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf60c2d0fed209d35fb46"}}],"status":"succes"}';
		new CommentList(comm);
	}
	
}

function initFriends (){
	
	var user = environment.profile;
	
	if(user){
		friends = '{"friends":[{"id":4,"login":"Hero"},{"id":5,"login":"Zeus"}],"status":"succes"}';
		new FriendList(friends);		
	}			
}

function generatePage(){	
	
	generateTopPanel();
	generateCenterPanel();
	
	generateLeftPanel();	
	generateRightPanel();	
}

function generateTopPanel(){
	
	var topHtml = getNavbarHtml();
	var headerHtml = getHeaderHtml();	
	var modalHtml = getAuthModalHtml();
	
	$('.navbar').empty().append(topHtml);	
	$('.my-modal').append(modalHtml);
	$('#center-panel').prepend(headerHtml);
	
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
		
		$("#left-panel").append(mainHtml);
		$("#left-panel").append(aboutHtml);
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
		$("#center-panel").append(commentsHtml);
	}		
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

function generateEvents(){
	$('#btn-login').click(login);
}