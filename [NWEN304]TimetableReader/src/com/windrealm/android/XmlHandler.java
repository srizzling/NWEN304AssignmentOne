package com.windrealm.android;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class XmlHandler extends DefaultHandler{
	private Map<Integer,ArrayList<Document>> items;
	private Document currentItem;
	private StringBuilder builder;

	private Document.type type;

	public Map<Integer,ArrayList<Document>> getItems(){
		return this.items;
	}

	public XmlHandler(Document.type docType){
		super();
		this.type = docType;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);
		if (this.currentItem != null){
			for(String tag: currentItem.getTags()){
				if(localName.trim().equals(tag)){
					currentItem.setTag(localName, builder.toString().trim());
				}
			}
			if (localName.equalsIgnoreCase("record")){
				addDocument();
			}
			builder.setLength(0);
		}
	}

	private void addDocument(){
		int key = currentItem.getKey();
		ArrayList<Document> temp = new ArrayList<Document>();

		//if map contains key add to existing list
		if(items.containsKey(key)){
			temp = items.get(key);
		}

		temp.add(currentItem);
		items.put(key, temp);
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		items = new HashMap<Integer,ArrayList<Document>>();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);

		if (localName.startsWith("record")){

			switch(type){
			case ROUTE:
				currentItem = new Route();
				break;
			case TRIP:
				currentItem = new Trip();
				break;
			case STOPTIME:
				currentItem = new StopTime();
				break;
			case STOP:
				currentItem = new Stop();
				break;
			}

		}
	}
}