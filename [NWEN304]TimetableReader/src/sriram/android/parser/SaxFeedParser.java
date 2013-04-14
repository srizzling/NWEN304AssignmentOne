package sriram.android.parser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class SaxFeedParser extends Activity{

	public HashMap<Integer, ArrayList<Document>> parse(String url, Document.type type, int key) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			XmlHandler handler=null;

			handler = new XmlHandler(type,key);
			
			String fileName="";
			switch(type){
			case ROUTE:
				fileName="routes.xml";
				break;
			case TRIP:
				fileName="trips.xml";
				break;
			case STOPTIME:
				fileName="stoptimes.xml";
				break;
			case STOP:
				fileName="stops.xml";
				break;
			}
			parser.parse(getInputStream(url,fileName),handler);

			return (HashMap<Integer, ArrayList<Document>>) handler.getItems();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private InputStream getInputStream(String feedUrl, String type) {
		try {
			URL url = new URL(feedUrl);
			//create the new connection
			File file = new File("//sdcard//", type);
			if (file.exists()) {
				FileInputStream in = new FileInputStream("//sdcard//"+type);
				return in;
			}
			else if (isNetworkAvailable()) {
				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream("//sdcard//"+type);

				byte data[] = new byte[1024];
				int count;
				while ((count = input.read(data)) != -1) {
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();

				return url.openConnection().getInputStream();
			}
			else{
				try {
					throw new NoInternetConnectionException();
				} catch (NoInternetConnectionException e) {
					Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
				}
			}
		

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	         = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
}