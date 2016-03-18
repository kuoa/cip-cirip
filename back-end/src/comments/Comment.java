package comments;

import java.util.Date;
import java.util.List;

import org.bson.Document;

import auth.RegexUtils;

public class Comment {

	private Document document;
	/** comment document, inserted in the mongo database */

	private final int authorId;
	/** author id */
	private final String authorLogin;
	/** author login */
	private final Date date;
	/** comment date */
	private final String comment;
	/** comment string */
	private int replyToId;
	/** reply user id, if exists */

	private List<String> hashtags;
	/** hashtags,if exist [#hashtag] */
	private String imageUrl;
	/** image url, if exists[#img URL] */
	private String videoUrl; /** video url, if exists[#video URL] */

	/**
	 * Create a new comment.
	 * 
	 * @param authorId
	 *            authorId
	 * @param authorLogin
	 *            authorLogin
	 * @param comment
	 *            comment
	 */
	public Comment(int authorId, String authorLogin, String comment) {

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

	/**
	 * Return a comment Document ready to be inserted into the data base.
	 * The comment is parsed for hashtags, images, videos.
	 * @return a comment Document
	 */
	
	public Document getDocument(){
		
		Document author = new Document()
				.append("id", authorId)
				.append("login", authorLogin);		
		
		Document comm = new Document()
				.append("date", date)
				.append("text", comment)
				.append("reply_to_id", replyToId)
				.append("likes", "null")
				.append("hashtags", hashtags)
				.append("image", imageUrl)
				.append("video", videoUrl);
		
		document.append("author", author).append("comment", comm);				
		
		return document;						
	}

	private String parseImageUrl() {		
		return RegexUtils.parseImageUrl(comment);
	}

	private String parseVideoUrl() {
		return RegexUtils.parseVideoUrl(comment);
	}

	private List<String> parseHashtags() {
		return RegexUtils.parseHashTags(comment);
	}

	private int parseReplyToId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
