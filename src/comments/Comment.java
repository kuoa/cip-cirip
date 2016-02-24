package comments;
import java.util.Date;
import java.util.List;

import org.bson.Document;

public class Comment {
	
	private Document document; 				/** comment document, inserted in the mongo database */
	private final int authorId;				/** author id */
	private final String authorLogin;		/** author login */
	private final Date date;				/** comment date */	
	private final String comment;			/** comment string */
	private int replyToId;					/** reply user id, if exists */
	
	private List<String> hashtags;			/** hashtags,if exist [#hashtag] */
	private String imageUrl;				/** image url, if exists[#img URL]*/
	private String videoUrl;				/** video url, if exists[#video URL] */	
	
	
	public Comment (int authorId, String authorLogin, String comment){
		
		document = new Document();
		
		this.authorId = authorId;
		this.authorLogin = authorLogin;
		date = new Date();
		this.comment = comment;		
		
		this.replyToId = parseReplyToId();		
		this.hashtags = parseHashtags();
		this.imageUrl = parseImageUrl();
		this.videoUrl = parseVideoUrl();
		
	}
	
	public Document getDocument(){
								
		document.append("author_id", authorId)
				.append("author_login", authorLogin)
				.append("date", date)
				.append("comment", comment)
				.append("reply_to_id", replyToId)
				.append("likes", "null")
				.append("hashtags", hashtags)
				.append("image", imageUrl)
				.append("video", videoUrl);				
		
		return document;
	}


	private String parseImageUrl() {
		// TODO Auto-generated method stub
		return null;
	}


	private String parseVideoUrl() {
		// TODO Auto-generated method stub
		return null;
	}
	

	private List<String> parseHashtags() {
		// TODO Auto-generated method stub
		return null;
	}	

	private int parseReplyToId() {
		// TODO Auto-generated method stub
		return 0;
	}		
}
