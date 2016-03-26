/*---------------------------------------------*/
/* 				Initialization				   */
/*---------------------------------------------*/

/* initialise the local environment */
function initProfile(){

	environment = {};
	environment.profile = {};
	environment.users = {};
	environment.comments = {};
	environment.friends = {};
	
	testFriends();	
	testComment();
	testUser();
}

function initLogin(){
	$('#form-login').submit(login);	
}

/*---------------------------------------------*/
/* User Object */
/*---------------------------------------------*/

function User(id, login, friend, bio, image, about, photos){
	this.id = id;
	this.login = login;
	this.friend = true;
	
	this.bio = bio;
	this.image = image;
	this.about = about;
	this.photos = photos;
	
	if (friend != undefined){
		this.friend = friend;
	}
	
	/* add user to the environmnet */
	if (environment.users[id] == undefined){
		environment.users[id] = this;
		}	
}

/* modify friend status */
User.prototype.modifyStatus = function (){
	this.friend = !this.friend;
}

User.prototype.getMainSectionHtml = function (){
	
	var html = '';
	
	if (this.bio != undefined){
		
		var friends = environment.friends.getCountHtml();		
		
		html += '<div class="panel panel-primary info">' +
					'<div class="panel-heading">' +
						'<h3 class="panel-title">Hi</h3>' +
					'</div>' +
					
					'<div class="panel-body">' +
						'<img src="' + this.image + '" alt="User"' +
							'class="img-responsive img-circle img-user">' +
							'<h4>' + this.login + '</h4>' +
							'<h5>' + this.bio + '</h5>' +
							'<hr>' + 
							'<h4>Friends</h4>' +
							'<h5>' + friends + '</h5>' +
					'</div>' + 
				'</div>';
	}
		
	return html;	
}

User.prototype.getAboutHtml = function(){
	
	var html = '';
	
	if (this.about != undefined){

		html += '<div class="panel panel-primary info">' +
					'<div class="panel-heading">' +
						'<h3 class="panel-title">About</h3>' +
					'</div>' +
			
					'<div class="panel-body">' +
						'<p>' + this.about + '</p>' +
					'</div>' + 
				'</div>';
	}
	
	return html;	
}

User.prototype.getCommentHtml = function(){
	
	var html = '';
	
	html += '<hr>' + 
	
		'<form class="submit-comment comment">' +
			'<div class="form-group">' +
				'<label class="sr-only" for="comment">Comment:</label>' +
				'<textarea class="form-control" rows="2" name="comment"' +
					'placeholder="I am thinking about ..." required autofocus></textarea>' +				
				'<button type="submit" class="btn btn-info btn-block">submit</button>' + 
			'</div>' + 
		'</form>' +
	
		'<hr>' +
		'<div class="comment-zone" id="comment-zone">' +
		
		'</div>';
	
	return html;
}

User.prototype.getFriendsHtml = function(){
	
	var html = '';
	var friendsHtml = environment.friends.getHtml();
		
	html +=	'<div class="panel panel-primary info">' +
				'<div class="panel-heading">' + 
					'<h3 class="panel-title">Follows</h3>' +				
				'</div>' +
			'<div class="panel-body">' +
				friendsHtml +
			'</div>' +
		'</div>';
	
	return html;
}

User.prototype.getPhotosHtml = function(){
	
	var html = '';
	
	if(this.photos != undefined){

		html += '<div class="panel panel-primary info">' +
					'<div class="panel-heading">' +
						'<h3 class="panel-title">Photos</h3>' +
					'</div>' +
					'<div class="panel-body">' +
						'<p>' + this.photos + '</p>' +
					'</div>' + 
				'</div>';		
	}
	
	return html;
}

/*---------------------------------------------*/
/* Comment Object */
/*---------------------------------------------*/

function Comment(authorId, authorLogin, date, text, replyToId, likes, hashtags, imageUrl, videoUrl){
	this.authorId = authorId;
	this.authorLogin = authorLogin;
	this.date = date;
	this.text = text;
	
	this.replyToId = replyToId || -1;
	this.likes = likes || [];
	this.hashtags = hashtags || [];
	this.imageUrl = imageUrl || '';
	this.videoUrl = videoUrl || '';	
}

Comment.prototype.getDateHtml = function (date){
	
	var html = '';	
	html = date.toISOString();
	
	return html;
}

Comment.prototype.getReplyToIdHtml = function (id){ return ''; }
Comment.prototype.getLikesHtml = function (likes) { return ''; }

Comment.prototype.getHashTagHtml = function (hashtags){	
	
	var html = '<br>';	
	
	for (var i = 0; i < hashtags.length; i++){
		html += '<a href="#" class="label-info hash-url">' + hashtags[i] + '</a> ';
	}	
	
	return html;
}
Comment.prototype.getImageHtml = function (image){
	
	var html = '';
	
	if(image){		
		html = '<img src="' + image + '" class="img-thumbnail img-comment" alt="">';
	}		
	
	return html;
}

	
Comment.prototype.getVideoHtml = function (video){ 
	
	var html = '';
	
	if (video){
		html = '<div class="embed-responsive embed-responsive-16by9 video-comment">' + 
					'<iframe width="560" height="315" src="' + video + '?rel=0&amp;controls=0" frameborder="0" allowfullscreen></iframe>'
				+'</div>';
		
	}	
	return html;
}


Comment.prototype.getHtml = function(){
	
	var hashtml = this.getHashTagHtml(this.hashtags);
	var datehtml = this.getDateHtml(this.date);
	var imagehtml = this.getImageHtml(this.imageUrl);
	var videohtml = this.getVideoHtml(this.videoUrl);
	
	var html =		
		'<div class="panel panel-primary comment">' +
			'<div class="panel-body">' +
				this.text + hashtml + imagehtml + videohtml +
			'</div>' +
			'<div class="panel-footer">' +
				'<a href= "#" class="badge user-url">' + this.authorLogin + '</a> ' +
				 '<span class="time">' + datehtml + '</span>' +
			'</div>' + 
		'</div>';
	
	return html;
}

/*---------------------------------------------*/
/* Comment List 							   */
/*---------------------------------------------*/

function CommentList (bruteComments) {
	
	this.list = CommentList.fromJSON(bruteComments);
	environment.comments = this;
};

CommentList.parseComment = function (c){
	var author = c.author;
	var comment = c.comment;
	var id = c._id;
	var date = new Date (comment.date.$date);
	
	// since we load friends first, this friend is not assigned in env here
	new User(author.id, author.login, false);
	
	comm = new Comment(author.id, author.login, date, comment.text, 
			comment.reply_to_id, comment.likes, comment.hashtags, comment.image, comment.video);
	
	return comm;
}

CommentList.fromJSON = function(bruteComments){
	
	var comments = [];
	var stringComments = JSON.parse(bruteComments).comments;
	
	for (var i = 0; i < stringComments.length; i++){
		var comment = stringComments[i];
		
		var c = CommentList.parseComment(comment);
		comments.push(c);
	}			
	
	return comments;
}

CommentList.prototype.getHtml = function(n){	
	
	var size = Math.min (n, this.list.length);
	var html = '';
	
	for (var i = 0; i < size; i++){
		
		var comment = this.list[i];		
		html += comment.getHtml();
	}
	
	return html;	
}


/*---------------------------------------------*/
/* FriendList								   */
/*---------------------------------------------*/

function FriendList(bruteFriends){
	
	this.list = FriendList.fromJSON(bruteFriends);	
	environment.friends = this;
}

FriendList.fromJSON = function (bruteFriends){
	
	var friends = [];
	var stringFriends = JSON.parse(bruteFriends).friends;
	
	for (var i = 0; i < stringFriends.length; i++){
		f = FriendList.parse(stringFriends[i]);
		friends.push(f);
	}
	
	return friends;
}

FriendList.parse = function(f){	
		
	var friend = new User(f.id, f.login, true);
	
	return friend;
}

FriendList.prototype.getHtml = function (){
	var html = '';
	
	for (var i = 0; i < this.list.length; i++){
		html += '<a href="#">' + this.list[i].login + '</a> ';
	}
	
	return html;

}

FriendList.prototype.getCountHtml = function (){
	return this.list.length;
}


/*---------------------------------------------*/
/* Test Zone 								   */
/*---------------------------------------------*/

var testUser = function () {
	u = new User(101, "Jack_Rabbit", false, "I wish i was taller", "res/images/user.jpg", 
			"Aenean lacinia bibendum nulla sed consectetur. Vestibulum id ligula porta felis euismod semper." +
			" Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Cras justo odio, dapibus ac facilisis in," +
			" egestas eget quam. Vestibulum id ligula porta felis euismod semper.", "Photosasdas");
	
	var uHtml = u.getMainSectionHtml();
	var aHtml = u.getAboutHtml();
	var cHtml = u.getCommentHtml();
	var fHtml = u.getFriendsHtml();
	var phHtml = u.getPhotosHtml();
	
	$('#center-panel').prepend(cHtml);
	
	$("#left-panel").append(uHtml);
	$("#left-panel").append(aHtml);
	
	$('#right-panel').prepend(fHtml);
	$('#right-panel').append(phHtml);
		
}

var testComment = function (){	
	
	comm = '{"comments":[{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304559022},"image":"","hashtags":["#yolo","#swag","#SWA0"],"text":"Hey this is pretty cool right? #yolo #swag #SWA0","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62f2d0fed20d64d9deb"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304558981},"image":"","hashtags":["#video"],"text":"Hey check this #video https://www.youtube.com/embed/U5zZ1l4scgM coll right?","video":"https://www.youtube.com/embed/U5zZ1l4scgM","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62e2d0fed20d64d9dea"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458304558570},"image":"https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg","hashtags":["#image"],"text":"Hey check out this #image https://s-media-cache-ak0.pinimg.com/736x/82/2f/c2/822fc271f3457af71e88d80b51346769.jpg cool right?","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf62e2d0fed20d64d9de9"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458304524803},"image":"","hashtags":[],"text":"I am heros second comment","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf60c2d0fed209d35fb47"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458304524740},"image":"","hashtags":[],"text":"I am heros comment","video":"","reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ebf60c2d0fed209d35fb46"}}],"status":"succes"}';
	new CommentList(comm);
	var cHtml = environment.comments.getHtml(5);
	$("#center-panel").append(cHtml);
}

var testFriends = function (){
	friends = '{"friends":[{"id":4,"login":"Hero"},{"id":5,"login":"Zeus"}],"status":"succes"}';
	
	new FriendList(friends);		
	
}
