package sriram.android.parser;


import java.util.Set;


public interface Document {


	public int getKey();
	public String getValue(String key);
	public void setTag(String tagName, String value);
	public Set<String> getTags();
	public enum type {ROUTE,TRIP,STOPTIME,STOP};
	public String getKeyName();
}
