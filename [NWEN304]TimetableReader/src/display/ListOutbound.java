package display;

import java.util.Map;

import android.app.Activity;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.windrealm.android.Document;
import com.windrealm.android.R;
import com.windrealm.android.Route;
import com.windrealm.android.SaxFeedParser;
import com.windrealm.android.Document.type;
import com.windrealm.android.R.id;
import com.windrealm.android.R.layout;
import com.windrealm.android.Trip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOutbound extends ListActivity {

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
		Trip selectedTrip = (Trip) selectedDoc;

		//Trip ID of trip
		int selectedID = Integer.parseInt(selectedTrip.getValue("trip_id"));


		Log.d("SelectedTrip",""+selectedID);

		Intent i = new Intent(this, ListOutbound.class);
		Bundle b = new Bundle();
		b.putInt("selectedTrip", selectedID); //Your id

	}







	private ArrayAdapter<String> loadFeed() {
		try{
			SaxFeedParser parser = new SaxFeedParser();


			//Parse xml documents
			//routes = parser.parse("http://homepages.ecs.vuw.ac.nz/~wijosknich/routes.xml", Document.type.ROUTE);
			trips = parser.parse("http://homepages.ecs.vuw.ac.nz/~wijosknich/trips.xml", Document.type.TRIP);
			//stops = parser.parse("http://homepages.ecs.vuw.ac.nz/~wijosknich/stops.xml", Document.type.STOP);
			//stopTimes = parser.parse("http://homepages.ecs.vuw.ac.nz/~wijosknich/stop_times.xml", Document.type.STOPTIME);



			//Display route list
			List<String> titles = new ArrayList<String>();
			int pos=0;

			//Key
			Bundle b = getIntent().getExtras();
			int key=b.getInt("selectedRoute");


			for(Document doc: trips.get(key)){
				if(Integer.parseInt(doc.getValue("direction_id"))==0){
					titles.add(doc.getKey() + ": " + doc.getValue("trip_id"));
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
