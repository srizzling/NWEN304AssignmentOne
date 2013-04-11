package com.windrealm.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxFeedParser{

	public Map<Integer, ArrayList<Document>> parse(String url, Document.type type) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			XmlHandler handler = new XmlHandler(type);
			parser.parse(this.getInputStream(url), handler);
			return handler.getItems();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private InputStream getInputStream(String feedUrl) {
		try {
			URL url = new URL(feedUrl);
			return url.openConnection().getInputStream();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}