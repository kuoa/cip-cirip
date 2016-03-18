/*---------------------------------------------*/
/* 				Initialization				   */
/*---------------------------------------------*/

/* initialise the local environment */
function initialise(){

	environment = {};
	environment.users = {};
	environment.comments = {};
	environment.friends = {};
	
	testFriends();
	testUsers();
	testComment();	
}

/*---------------------------------------------*/
/* User Object */
/*---------------------------------------------*/

function User(id, login, friend){
	this.id = id;
	this.login = login;
	this.friend = true;
	
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

User.prototype.getHtml = function(){
	
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
	
	var html = '';	
	
	for (var i = 0; i < hashtags.length; i++){
		html += ' <a href="" class="label-info hash-url">' + hashtags[i] + '</a> ';
	}	
	
	return html;
}
Comment.prototype.getImageHtml = function (image) { return ''; }
Comment.prototype.getVideoHtml = function (video) { return ''; }


Comment.prototype.getHtml = function(){
	
	var hashtml = this.getHashTagHtml(this.hashtags);
	var datehtml = this.getDateHtml(this.date);
	
	var html =		
		'<div class="panel panel-primary comment">' +
			'<div class="panel-body">' +
				this.text + hashtml +
			'</div>' +
			'<div class="panel-footer">' +
				'<a href= "" class="badge user-url">' + this.authorLogin + '</a> ' +
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
		html += '<a href="">' + this.list[i].login + '</a> ';
	}
	
	return html;

}

FriendList.prototype.getCountHtml = function (){
	return this.list.length;
}


/*---------------------------------------------*/
/* Test Zone 								   */
/*---------------------------------------------*/

var testUsers = function () {
	//var u1 = new User(1, "F1", true);
	//var u2 = new User(2, "F2", true);	
		
	//c1 = '{ "_id" : { "$oid" : "56ea8bdf2d0fed117a9b00e7" }, "author" : { "id" : 5, "login" : "Zeus" }, "comment" : { "date" : { "$date" : 1458211807943 }, "text" : "New COmment", "reply_to_id" : 0, "likes" : "null", "hashtags" : null, "image" : null, "video" : null } }';
}

var testComment = function (){	
	
	comm = '{"comments":[{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458211807943},"image":null,"hashtags":null,"text":"New COmment","video":null,"reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ea8bdf2d0fed117a9b00e7"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458211423311},"image":null,"hashtags":null,"text":"New COmment","video":null,"reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ea8a5f2d0fed1110fa293f"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458211365162},"image":null,"hashtags":null,"text":"I am heros second comment","video":null,"reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ea8a252d0fed10e052f717"}},{"author":{"id":5,"login":"Zeus"},"comment":{"date":{"$date":1458211365121},"image":null,"hashtags":null,"text":"I am Zeuss comment","video":null,"reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ea8a252d0fed10e052f716"}},{"author":{"id":4,"login":"Hero"},"comment":{"date":{"$date":1458211364706},"image":null,"hashtags":null,"text":"I am heros comment","video":null,"reply_to_id":0,"likes":"null"},"_id":{"$oid":"56ea8a242d0fed10e052f715"}}],"status":"succes"}';
	new CommentList(comm);
	var cHtml = environment.comments.getHtml(5);
	$("#comment-zone").append(cHtml);
}

var testFriends = function (){
	friends = '{"friends":[{"id":4,"login":"Hero"},{"id":5,"login":"Zeus"}],"status":"succes"}';
	new FriendList(friends);
	var fHtml = environment.friends.getHtml();
	var cHtml = environment.friends.getCountHtml();
	
	$("#friends").append(fHtml);
	$("#friendCount").append(cHtml);
	
}
