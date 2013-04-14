package sriram.android.parser;

import java.util.HashMap;
import java.util.Set;

public class Trip implements Document{

	private HashMap<String,String> tagMap = new HashMap<String,String>();

	private String[] fields = {"route_id", "service_id", "trip_id", "trip_headsign", "direction_id", "block_id", "shape_id"};

	public int getKey(){
		return Integer.parseInt(tagMap.get("route_id"));
	}

	public Trip(){
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
		return "route_id";
	}

}
