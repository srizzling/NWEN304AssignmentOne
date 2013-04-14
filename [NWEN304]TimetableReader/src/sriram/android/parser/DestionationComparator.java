package sriram.android.parser;

import java.util.Comparator;
import java.util.HashMap;

public class DestionationComparator implements Comparator<HashMap<String, String>> {
    public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
    	String TAG_ID = "departure_time";    
    	
    	return o1.get(TAG_ID).compareTo(o2.get(TAG_ID));
    }
}