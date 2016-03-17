/*---------------------------------------------*/
/* 				Initialization				   */
/*---------------------------------------------*/

/* initialise the local environment */
function initialise(){

	environment = new Object();
	environment.users = new Object();
	environment.comments = new Object();
	
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
	environment.users[id] = this;		
}

/* modify friend status */
User.prototype.modifyStatus = function (){
	this.friend = !this.friend;
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
	
	environment.comments[Comment.id] = this;
	Comment.id ++;
}

/* Comment static methods */

Comment.id = 0;

Comment.init = function () {
	id = 0;
}

Comment.getDateHtml = function (date){
	
	var html = '';
	
	html = date.toISOString();
	
	return html;
}

Comment.getReplyToIdHtml = function (id){ return ''; }
Comment.getLikesHtml = function (likes) { return ''; }

Comment.getHashTagHtml = function (hashtags){	
	
	var html = '';	
	
	for (var i = 0; i < hashtags.length; i++){
		html += ' <a href="" class="label-info hash-url">' + hashtags[i] + '</a> ';
	}	
	
	return html;
}
Comment.getImageHtml = function (image) { return ''; }
Comment.getVideoHtml = function (video) { return ''; }


Comment.prototype.getHtml = function(){
	
	var hashtml = Comment.getHashTagHtml(this.hashtags);
	var datehtml = Comment.getDateHtml(this.date);
	
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
/* Comment List */
/*---------------------------------------------*/

function CommentList () {};

CommentList.prototype.getHtml = function(n){	
	
	var size = Math.min (n, this.comments);
	var html = '';
	
	for (var i = 0; i < size; i++){
		
		var comment = comments[i];		
		html += comment.getHtml();
	}
	
	return html;	
}

CommentList.fromJson = function(bruteComments){
	
	var comments = JSON.parse(bruteComments);
	CommentList.parseComment(comments);
	
	if (comments.error != undefined){
		alert("Error in parsing comments, null!");
		return undefined;
	}	
	
	return comments;
}

CommentList.parseComment = function (c){
	var author = c.author;
	var comment = c.comment;
	var id = c._id;
	var date = new Date (comment.date.$date);
	
	u = new User(author.id, author.login, true);
	comm = new Comment(author.id, author.login, date, comment.text, 
			comment.reply_to_id, comment.likes, comment.hashtags, comment.image, comment.video);
	
}



/*---------------------------------------------*/
/* Test Zone */
/*---------------------------------------------*/

var testUsers = function () {
	var u1 = new User(1, "F1", true);
	var u2 = new User(2, "F2", true);	
	
	c1 = '{ "_id" : { "$oid" : "56ea8bdf2d0fed117a9b00e7" }, "author" : { "id" : 5, "login" : "Zeus" }, "comment" : { "date" : { "$date" : 1458211807943 }, "text" : "New COmment", "reply_to_id" : 0, "likes" : "null", "hashtags" : null, "image" : null, "video" : null } }';
}

var testComment = function (){
	var comment = new Comment(1, "UserLogin", new Date(), "This is a comment.", 10, [], ["#hello", "#hi"]);
	$("#main").append(comment.getHtml());
}


