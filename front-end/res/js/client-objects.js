/*---------------------------------------------*/
/* 				Initialization				   */
/*---------------------------------------------*/

/* initialise the local environment */


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
	
		'<form class="submit-comment comment" id="form-comment">' +
			'<div class="form-group">' +
				'<label class="sr-only" for="comment">Comment:</label>' +
				'<textarea class="form-control" rows="2" name="comment" id="comment-value"' +
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

function Comment(authorId, authorLogin, commentId, date, text, replyToId, likes, hashtags, imageUrl, videoUrl){
	this.authorId = authorId;
	this.authorLogin = authorLogin;
	this.commentId = commentId;
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
	var monthNames = [
	                  "January", "February", "March",
	                  "April", "May", "June", "July",
	                  "August", "September", "October",
	                  "November", "December"
	                ];
	
	var day = date.getDate();
	var month = date.getMonth();
	var year = date.getFullYear();
	
	var hour = date.getHours();
	var minutes = date.getMinutes();
	
	html = hour + ':' + minutes  + ' | '+ day + ' ' + monthNames[month] + ' ' + year; 
		
	
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
	
	var user = environment.profile;
	var users = environment.users;
	var hashtml = this.getHashTagHtml(this.hashtags);
	var datehtml = this.getDateHtml(this.date);
	var imagehtml = this.getImageHtml(this.imageUrl);
	var videohtml = this.getVideoHtml(this.videoUrl);
	var deleteCommentHtml = '';
	var friendHtml = '';

	if (user){
		
		if (users[this.authorId].friend){			
			friendHtml = '-';
		}
		else{			
			friendHtml = '+';
		}	
		
		if (user.id == this.authorId){
			deleteCommentHtml = ' <a href= "#" id="delete-comment" class="badge ">delete comment</a>';	
			friendHtml = '';
		}
		else{
			deleteCommentHtml = '';
		}		
	}
	
	var html =		
		'<div class="panel panel-primary comment">' +
			'<div class="panel-body">' +
				this.text + hashtml + imagehtml + videohtml +
			'</div>' +
			'<div class="panel-footer" id="' + this.commentId + '" user-id="' + this.authorId + '">' +
				'<a href= "#" class="badge user-url" id="user-login">' + this.authorLogin + '</a> ' +	
				'<a href= "#" id="friend-status" class="badge ">' + friendHtml + '</a> ' +
				'<span class="time">' + datehtml + '</span>' +
					deleteCommentHtml + 
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
	var commentId = c._id.$oid; //"_id":{"$oid":"56f905672d0fed1422d0c673"}} 
				
	// since we load friends first, this friend is not assigned in env here
	u = new User(author.id, author.login, false);
			
	comm = new Comment(author.id, author.login, commentId, date, comment.text, 
			comment.reply_to_id, comment.likes, comment.hashtags, comment.image, comment.video);
	
	return comm;
}

CommentList.fromJSON = function(bruteComments){
	
	var comments = [];
	var stringComments = bruteComments.comments;
	
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
	var stringFriends = bruteFriends.friends;
	
	for (var i = 0; i < stringFriends.length; i++){
		f = FriendList.parse(stringFriends[i]);
		friends.push(f);
	}
	
	return friends;
}

FriendList.parse = function(f){	
	
	var friend = environment.users[f.id];
	
	if (friend == undefined){
		friend = new User(f.id, f.login, true);
	}
	else{
		friend.modifyStatus();		
	}		
	
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
/* Page										   */
/*---------------------------------------------*/

function getNavbarHtml(){
	
	var friendsOnly = '';
	
	if(environment.profile){
		friendsOnly = '<label>' +
						'<input type="checkbox" id="for-friends" value="true" checked> friends only' +
					  '</label>';
	}
	
	var html = ''; 	
	
	html = 			
			'<div class="container">' +
					'<div class="pull-right">' +
						'<form class="navbar-form" role="search" id="form-search">' +
							'<div class="input-group">' +
								'<input type="text" class="form-control-input" placeholder="Search"' +
									'name="search-term" id="search-term"> ' +
									friendsOnly +
							'</div>' +
						'</form>' +
					'</div>' +
				
					'<div class="header clearfix">' +
						'<nav>' +
							'<ul class="navbar nav nav-pills pull-right">' +
								'<li role="presentation" class="active"><a href="">Home</a></li>' +
								'<li role="presentation"><a href="#" id="auth" data-toggle="modal" data-target="#login-modal">Auth</a></li>' +
								 '<li role="presentation"><a href="#" id="settings" data-toggle="modal" data-target="#modal-settings">Settings</a></li>' +
								 '<li role="presentation"><a href="#" id="logout">Logout</a></li>' +														
							'</ul>' + 
						'</nav>' +
					'</div>' +
				'</div>';			
	
	return html;							
}

function getHeaderHtml(){
	
	var user = environment.profile;
	var html = '';
	
	if(!user){
		html += '<div class="container comment">' + 			
					'<img src="" alt=""' +
						'class="img-responsive img-circle img-logo">' +
					'<h2 class="main-heading">lately</h2>' +
				'</div>';					
	}
	return html;		
}

function getAuthModalHtml(){
		
	var user = environment.profile;
	var html = '';
	
	if(!user){
		html += 	
		'<!-- Modal login-in-->' +
		'<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">' +
		  '<div class="modal-dialog" role="document">' +
		    '<div class="modal-content">   ' +
		      
		      '<div class="modal-header">' +
			'<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
			  '<span aria-hidden="true">&times;</span>' +
			'</button>' +
		      '</div>' +
		      '<div class="modal-body">' +
			'<form class="form-login" id="form-login">' +	
			  '<img src="res/css/images/logo.jpg" alt="Welcome!"' +
			      ' class="img-responsive img-circle">' +
			  
			  '<h2 class="form-login-heading" id="login-msg">log in</h2>' +	  
			  '<div class="form-group">' +
			    '<label class="sr-only" for="user">Username</label>' +
			    '<input ' +
			       'type="text" class="form-control" name="user" id="user-login"' +
			       'placeholder="user name" required autofocus>' +
			  '</div>' +
			  
			  '<div class="form-group">' +
			    '<label class="sr-only" for="pass">Password</label>' +
			    '<input ' +
			       'type="password" class="form-control" name="pass" id="pass-login"' +
			       'placeholder="password" required>' +
			  '</div>' +
			  
			  '<button type="submit" class="btn btn-primary btn-lg btn-block" id="btn-login">submit</button>' +
			  '<a href="#" role="button"' +
			     'class="btn btn-success btn-lg btn-block " data-toggle="modal" data-target="#signIn-modal"' +
			     'class="close" data-dismiss="modal">sign in</a>' +
			'</form>' +
		      '</div>     ' +
		    '</div>' +
		  '</div>' +
		'</div>' +

		'<!-- Modal sign-in-->' +
		'<div class="modal fade" id="signIn-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">' +
		  '<div class="modal-dialog" role="document">' +
		    '<div class="modal-content">   ' +
		      
		      '<div class="modal-header">' +
			'<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
			  '<span aria-hidden="true">&times;</span>' +
			'</button>' +
		      '</div>' +
		      '<div class="modal-body">	' +
			
			'<form class="form-login">' +
			  
			 '<img src="res/css/images/logo.jpg" alt="Welcome!"' +
			       'class="img-responsive img-circle">' +
			  			  
			  '<h2 class="form-login-heading" id="signin-msg">sign in</h2>' +
			  '<div class="form-group">' +
			    '<label class="sr-only" for="user">Username</label>' +
			    '<input ' +
			       'type="text" class="form-control" name="user" id="user-signin"' +
			       'placeholder="user name" required autofocus>' +
			  '</div>' +
			  
			  '<div class="form-group">' +
			    '<label class="sr-only" for="pass">Password</label>' +
			    '<input ' +
			       'type="password" class="form-control" name="pass" id="pass-signin"' +
			       'placeholder="password" required>' +
			  '</div>' +
			  
			  '<div class="form-group">' +
			    '<label class="sr-only" for="pass-re">Password</label>' +
			    '<input ' +
			       'type="password" class="form-control" name="pass-re" id="pass-re-signin"' +
			       'placeholder="repeat password" required>' +
			  '</div>' +
			  
			  '<div class="form-group">' +
			    '<label class="sr-only" for="mail">Mail</label>' +
			    '<input type="email"' +
				   'class="form-control" name="mail" id="mail-signin" placeholder="email" required>' +
			  '</div>' +
			  
			  '<button type="submit" class="btn btn-primary btn-lg btn-block" id="btn-signin">submit</button>' +
			  
			'</form>' +
		      	
		      '</div> ' +
		    '</div>' +
		  '</div>' +
		'</div>'; 						
	}
	else {
		html += '<!-- Modal Settings-->' +
		'<div class="modal fade bs-example-modal-lg" id="modal-settings" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">' +
		  '<div class="modal-dialog modal-lg"> ' +
		    '<div class="modal-content">' +
		      '<div class="modal-header"> ' +
			'<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
			  '<span aria-hidden="true">&times;</span>' +
			'</button>' +
		      '</div>' +

		      '<form class="submit-comment comment"> ' +
			'<div class="form-group">' +
			  '<h3>Bio</h3>' +
			  '<label class="sr-only" for="update-bio">Bio</label>' +
			  '<textarea class="form-control" rows="1" name="update-bio"' +
				    'placeholder="Enter a new bio description ..." required maxlength="100"></textarea>' +
			  '<button type="submit" class="btn btn-info btn-block">submit</button>' +
			'</div>' +
		      '</form>' +
		      
		     '<form class="submit-comment comment">' +
			'<div class="form-group">' +
			  '<h3>About</h3>' +
			  '<label class="sr-only" for="update-info">About</label>' +
			  '<textarea class="form-control" rows="2" name="update-about"' +
				    'placeholder="Enter a new about section" required maxlength="300" ></textarea>' +
			  '<button type="submit" class="btn btn-info btn-block">submit</button>' +
			'</div>' +
		      '</form>' +
		      
		      '<form class="submit-comment comment">' +
			'<div class="form-group">' +
			  '<h3>Image</h3>' +
			  '<label class="sr-only" for="update-img">Profile image</label>' +
			  '<textarea class="form-control" rows="2" name="update-bio"' +
				    'placeholder="Enter a new image url ..." required maxlength="300"></textarea>' +
			  '<button type="submit" class="btn btn-info btn-block ">submit</button>' +
			'</div>' +
		      '</form>' +
		      
		    '</div>' +
		  '</div>' +
		'</div>';
	}	
	return html;
}
