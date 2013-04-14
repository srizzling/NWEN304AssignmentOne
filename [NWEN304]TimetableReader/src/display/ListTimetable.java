package display;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sriram.android.parser.DestionationComparator;
import sriram.android.parser.Document;
import sriram.android.parser.Route;
import sriram.android.parser.SaxFeedParser;
import sriram.android.parser.Document.type;

import com.windrealm.android.R;
import com.windrealm.android.R.id;
import com.windrealm.android.R.layout;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListTimetable extends ListActivity {

	private static final String TAG_ID = "departure_time";
	private static final String TAG_NAME = "stop_id";




	private Map<Integer, ArrayList<Document>> routes;
	private Map<Integer, ArrayList<Document>> trips;
	private Map<Integer, ArrayList<Document>> stopTimes;
	private Map<Integer, ArrayList<Document>> stops;

	private Map<Integer, Document> positions;




	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//Initialize Positions
		positions= new HashMap<Integer, Document>();

		TextView textView =(TextView) findViewById(R.id.description);
		textView.setText("These are the departure times and stop names:");




		// Set the ArrayAdapter as the ListView's adapter.
		setListAdapter(loadFeed());

	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//Selected Document
		Document selectedDoc=positions.get(position);

		//Cast Document to Route
		Route selectedRoute = (Route) selectedDoc;

		//Route ID of selectedRoute
		int selectedRouteID = selectedRoute.getKey();

		Toast.makeText(this, "Selected Route "+selectedRouteID, Toast.LENGTH_LONG).show();


	}







	private ListAdapter loadFeed() {
		try{
			SaxFeedParser parser = new SaxFeedParser();

			Bundle b = getIntent().getExtras();
			int key=b.getInt("selectedTrip");
			
			long start=System.currentTimeMillis();
			
			stopTimes = parser.parse("http://homepages.ecs.vuw.ac.nz/~venkatsrir/stop_times.xml", Document.type.STOPTIME,key);
			
			stops = parser.parse("http://homepages.ecs.vuw.ac.nz/~venkatsrir/stops.xml", Document.type.STOP,-1);
			
			long end=System.currentTimeMillis();
			
			long duration=end-start;
			Log.d("duration",""+duration);


			

			//Display route list
			List<HashMap<String, String>> titles = new ArrayList<HashMap<String, String>>();





			int pos=0;
			//arrival time and stop id using trip id in stop times



			for(Document doc: stopTimes.get(key)){
				HashMap<String, String> map = new HashMap<String, String>();
				String id="Departure Time: " + doc.getValue("departure_time");
				String stop_id=doc.getValue("stop_id");
				int stopid=Integer.parseInt(stop_id);
				Document doc2=stops.get(stopid).get(0);
				String name= "Stop Name: "+ doc2.getValue("stop_name");
				map = new HashMap<String, String>();
				map.put(TAG_ID, id);
				map.put(TAG_NAME, name);
				positions.put(pos,doc);
				pos++;
				titles.add(map);
			}




			Collections.sort(titles, new DestionationComparator());
			// adding HashList to ArrayList


			ListAdapter adapter = new SimpleAdapter(this, titles,R.layout.routesitem,new String[] { TAG_NAME, TAG_ID}, new int[] { R.id.routeName, R.id.routeID});

			return adapter;
		}
		catch (Throwable t){
			Log.e("AndroidNews",t.getMessage(),t);
			return null;
		}

	}

}
