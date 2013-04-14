package sriram.android.parser;

import java.util.HashMap;
import java.util.Set;

public class Stop implements Document{

	private HashMap<String,String> tagMap = new HashMap<String,String>();

	private String[] fields = {"stop_id", "check", "stop_name", "stop_lat", "stop_lon"};

	public int getKey(){
		return Integer.parseInt(tagMap.get("stop_id"));
	}

	public Stop(){
		for(int i = 0; i < fields.length; i++){
			tagMap.put(fields[i], "");
		}
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
		return "stop_id";
	}

}
