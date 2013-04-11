package display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.Page;
import com.restfb.types.Post;
import com.restfb.types.User;
import com.windrealm.android.Document;
import com.windrealm.android.R;
import com.windrealm.android.Route;
import com.windrealm.android.SaxFeedParser;
import com.windrealm.android.Document.type;
import com.windrealm.android.R.id;
import com.windrealm.android.R.layout;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListRoutes extends ListActivity {

	//private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;


	private Map<Integer, ArrayList<Document>> routes;
	private Map<Integer, ArrayList<Document>> trips;
	private Map<Integer, ArrayList<Document>> stopTimes;
	private Map<Integer, ArrayList<Document>> stops;

	private Map<Integer, Document> positions;




	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);

		//Initialize Positions
		positions= new HashMap<Integer, Document>();



		// Create and populate the maps of information of planet names.
		listAdapter=loadFeed();


		// Set the ArrayAdapter as the ListView's adapter.
		setListAdapter(listAdapter);

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







	private ArrayAdapter<String> loadFeed() {
		try{
			SaxFeedParser parser = new SaxFeedParser();


			//Parse xml documents
			routes = parser.parse("http://homepages.ecs.vuw.ac.nz/~wijosknich/routes.xml", Document.type.ROUTE);
			//trips = parser.parse("http://homepages.ecs.vuw.ac.nz/~wijosknich/trips.xml", Document.type.TRIP);
			//stops = parser.parse("http://homepages.ecs.vuw.ac.nz/~wijosknich/stops.xml", Document.type.STOP);
			//stopTimes = parser.parse("http://homepages.ecs.vuw.ac.nz/~wijosknich/stop_times.xml", Document.type.STOPTIME);



			//Display route list
			List<String> titles = new ArrayList<String>();
			int pos=0;
			for (Integer key : routes.keySet()){
				Log.d("Key", ""+key);
				for(Document doc: routes.get(key)){
					titles.add(doc.getKey() + ": " + doc.getValue("route_long_name"));
					positions.put(pos,doc);
					pos++;
				}

			}

			return new ArrayAdapter<String>(this, R.layout.simplerow,titles);


		} catch (Throwable t){
			Log.e("AndroidNews",t.getMessage(),t);
			return null;
		}

	}








}