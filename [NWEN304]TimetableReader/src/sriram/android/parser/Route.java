package sriram.android.parser;

import java.util.HashMap;
import java.util.Set;

public class Route implements Document{

	private HashMap<String,String> tagMap = new HashMap<String,String>();

	private String[] fields = {"route_short_name", "route_id", "agency_id", "route_long_name", "route_desc", "route_type"};

	public int getKey(){
		return Integer.parseInt(tagMap.get("route_short_name"));
	}

	public Route(){
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
		return "route_short_name";
	}

}
