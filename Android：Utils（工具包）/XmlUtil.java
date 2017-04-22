package com.alex.chinacity;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlUtil 
{
	private static List<String> listCountyNames;
	public static List<String> getCountyList(String xml)
	{
		try 
		{
			XmlPullParserFactory facyory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = facyory.newPullParser();
			parser.setInput(new StringReader(xml));
			for(int eventType = parser.getEventType();eventType!=XmlPullParser.END_DOCUMENT;eventType=parser.next())
			{
				if(eventType==XmlPullParser.START_DOCUMENT)
					listCountyNames = new ArrayList<String> ();
				if(eventType==XmlPullParser.START_TAG)
				{
					if("d".equalsIgnoreCase(parser.getName()))
						listCountyNames.add(parser.nextText());
				}
			}
			return listCountyNames;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
