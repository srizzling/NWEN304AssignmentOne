package display;

import com.windrealm.android.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;


public class TabbedTrips extends TabActivity {
	  // TabSpec Names
    private static final String INBOUND = "Inbound";
    private static final String OUTBOUND = "Outbound";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);

        TabHost tabHost = getTabHost();

        // Inbound Tab
        TabSpec inboundSpec = tabHost.newTabSpec(INBOUND);
        // Tab Icon
        inboundSpec.setIndicator("Inbound");
        Intent inboxIntent = new Intent(this, ListInbound.class);
		Bundle b = new Bundle();
		b=getIntent().getExtras();
		inboxIntent.putExtras(b);
		inboundSpec.setContent(inboxIntent);



	     // Inbound Tab
        TabSpec outboundSpec = tabHost.newTabSpec(OUTBOUND);
        // Tab Icon
        outboundSpec.setIndicator("Outbound");
        Intent outIntent = new Intent(this, ListOutbound.class);
		Bundle b2 = new Bundle();
		b2=getIntent().getExtras();
		outIntent.putExtras(b2);
		outboundSpec.setContent(outIntent);



        // Adding all TabSpec to TabHost
        tabHost.addTab(inboundSpec); // Adding Inbox tab
        tabHost.addTab(outboundSpec); // Adding Outbox tab

    }

}