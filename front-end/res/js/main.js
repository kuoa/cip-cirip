

/* initialise the local environment */
function initialise(){

	environment = new Object();
	environment.users = new Object();
	
	testComment();
}

/* User object */
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
	this.contact = !this.contact;
}

/* Comment object */
function Comment(authorId, authorLogin, date, text, replyToId, hashtags, imageUrl, videoUrl){
	this.authorId = authorId;
	this.authorLogin = authorLogin;
	this.date = date;
	this.text = text;
	
	this.replyToId = replyToId || null;
	this.hashtags = hashtags || [];
	this.imageUrl = imageUrl || null;
	this.videoUrl = videoUrl || null;		
}

Comment.prototype.getHtml = function(){
	
	var hashtml = '';	
	
	for (var i = 0; i < this.hashtags.length; i++){
		hashtml += ' <a href="" class="label-info hash-url">' + this.hashtags[i] + '</a> ';
	}
	
	var datehtml = this.date.toISOString();
	
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

var testComment = function (){
	comment = new Comment(1, "UserLogin", new Date(), "This is a comment.", 10, ["#hello", "#hi"]);
	$("#main").append(comment.getHtml());
}




