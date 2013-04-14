package display;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sriram.android.parser.Document;
import sriram.android.parser.Route;
import sriram.android.parser.SaxFeedParser;
import sriram.android.parser.Document.type;

import com.windrealm.android.R;
import com.windrealm.android.R.id;
import com.windrealm.android.R.layout;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

public class ListRoutes extends ListActivity {

	private static final String TAG_ID = "route_id";
	private static final String TAG_NAME = "route_long_name";




	private HashMap<Integer, ArrayList<Document>> routes;
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
		textView.setText("Please pick a route from the following list");


		// Create and populate the maps of information of planet names.


		Log.d("Hi","gi");
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

		//
		Intent i = new Intent(this, TabbedTrips.class);
		Bundle b = new Bundle();
		b.putInt("selectedRoute", selectedRouteID); //Your id
		i.putExtras(b);
		startActivity(i);


	}







	private ListAdapter loadFeed() {
		try{
			SaxFeedParser parser = new SaxFeedParser();


			//Parse xml documents
			routes = parser.parse("http://homepages.ecs.vuw.ac.nz/~venkatsrir/routes.xml", Document.type.ROUTE,-1);
			


			//Display route list
			List<HashMap<String, String>> titles = new ArrayList<HashMap<String, String>>();

			//Description



			int pos=0;
			for (Integer key : routes.keySet()){
				Log.d("Key", ""+key);
				for(Document doc: routes.get(key)){
					HashMap<String, String> map = new HashMap<String, String>();
					String id="Route ID: " + doc.getKey();
					String name="Name: "+doc.getValue(TAG_NAME);
					map = new HashMap<String, String>();
					map.put(TAG_ID, id);
					map.put(TAG_NAME, name);
					positions.put(pos,doc);
					pos++;

					titles.add(map);
				}

			}

			
		


			// adding HashList to ArrayList


			ListAdapter adapter = new SimpleAdapter(this, titles,R.layout.routesitem,new String[] { TAG_NAME, TAG_ID}, new int[] { R.id.routeName, R.id.routeID});

			return adapter;
		}
		catch (Throwable t){
			Log.e("AndroidNews",t.getMessage(),t);
			return null;
		}

	}
	public void write(Map<Integer,ArrayList<Document>> map){
	
	}

	public void read(){

		

		
	}


}







