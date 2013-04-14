package sriram.android.parser;

import java.util.HashMap;
import java.util.Set;


public class StopTime implements Document {

	private HashMap<String,String> tagMap = new HashMap<String,String>();

	private String[] fields= {"trip_id","departure_time","stop_id"};

	public StopTime(){
		for(int i = 0; i < fields.length; i++){
			tagMap.put(fields[i], "");
		}
	}


	public int getKey() {
		return Integer.parseInt(tagMap.get("trip_id"));
	}

	public void setTag(String tagName, String value) {
		if(tagMap.containsKey(tagName))
			tagMap.put(tagName, value);
	}

	public Set<String> getTags() {
		return tagMap.keySet();
	}

	public String getValue(String key) {
		return tagMap.get(key);
	}
	
	public String getKeyName(){
		return "trip_id";
	}
}
