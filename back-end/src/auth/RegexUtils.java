package auth;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	/** Letters, digits and _, 3 <= size <= 10 */

	public static final Pattern VALID_USER_REGEX = Pattern.compile("^[a-zA-Z0-9_]{3,10}$");

	/** At least: 1 digit, 1 letter, 1 symbol, size >= 6 */

	public static final Pattern VALID_PASS_REGEX = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])(?!\\w*$).{6,}");

	/** Classic email pattern */

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static final Pattern IMAGE_URL_REGEX = Pattern.compile("#image ([^\\s]+(\\.(?i)(jpg|png|gif|bmp)))");

	public static final Pattern VIDEO_URL_REGEX = Pattern
			.compile("#video (https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*)", Pattern.CASE_INSENSITIVE);

	public static final Pattern HASHTAG_REGEX = Pattern.compile("#[a-zA-Z0-9]*"); 
	
	/* check-up boolean functions */

	public static boolean validArg(String arg) {

		return (arg != null && !arg.isEmpty());
	}

	public static boolean validUser(String user) {

		Matcher matcher = VALID_USER_REGEX.matcher(user);
		return matcher.find();
	}

	public static boolean validMail(String mail) {

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
		return matcher.find();
	}

	public static boolean validPassword(String pass) {

		Matcher matcher = VALID_PASS_REGEX.matcher(pass);
		return matcher.find();

	}

	public static String parseImageUrl(String comment) {

		Matcher matcher = IMAGE_URL_REGEX.matcher(comment);
		if (matcher.find()) {
			return matcher.group(1);
		}

		return "";
	}
	
	public static String parseVideoUrl(String comment) {

		Matcher matcher = VIDEO_URL_REGEX.matcher(comment);
		if (matcher.find()) {
			return matcher.group(1);
		}

		return "";
	}
	
	public static List<String> parseHashTags(String comment){
		
		List<String> hastags = new ArrayList<String>();
		Matcher matcher = HASHTAG_REGEX.matcher(comment);
		
		while(matcher.find()){
			hastags.add(matcher.group());
		}
		
		return hastags;		
	}
}
