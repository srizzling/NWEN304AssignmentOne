package display;

import java.util.Map;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.windrealm.android.Document;
import com.windrealm.android.R;
import com.windrealm.android.SaxFeedParser;
import com.windrealm.android.Trip;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class ListInbound extends ListActivity {

	//private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;


	private Map<Integer, ArrayList<Document>> trips;
	

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


		Toast.makeText(this, "Selected Trip ID "+selectedID, Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, ListInbound.class);
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
				if(Integer.parseInt(doc.getValue("direction_id"))==1){
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
