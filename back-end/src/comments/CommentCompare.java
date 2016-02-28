package comments;

import java.util.Comparator;

import org.json.JSONException;
import org.json.JSONObject;

public class CommentCompare implements Comparator<JSONObject> {
	
	/** A custom comparator for sorting comments from recent to less recent */
	
	public int compare (JSONObject left, JSONObject right){
		
		try {
			
			int dateLeft = left.getJSONObject("date").getInt("$date");
			int dateRight = right.getJSONObject("date").getInt("$date");
			
			if (dateLeft < dateRight){
				return 1;
			}
			else if (dateLeft > dateRight){
				return -1;
			}
									
		} catch (JSONException e) {			
			e.printStackTrace();
		}		
		return 0;
	}
}
