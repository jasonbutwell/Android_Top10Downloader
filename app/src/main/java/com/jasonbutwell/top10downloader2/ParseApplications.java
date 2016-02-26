package com.jasonbutwell.top10downloader2;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by jason on 26/02/16.
 */
public class ParseApplications {
    private String xmlData;
    private ArrayList<Application> applications;

    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<>();
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public boolean process() {
        boolean status = true;
        Application currentRecord;
        boolean isEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                switch(eventType)
                {
                    case XmlPullParser.START_TAG:
                        Log.d("ParseApplications", "Starting Tag for "+ tagName);

                        if (tagName.equalsIgnoreCase("entry")) {
                            isEntry = true;
                            currentRecord = new Application();
                            break;
                        }
                    case XmlPullParser.END_TAG:
                        Log.d("ParseApplications", "Ending Tag for "+ tagName);
                        break;

                    default:
                        // Nothing else to do
                }
                eventType = xpp.next();
            }

        }catch(Exception e) {
            status = false;
            e.printStackTrace();
        }

        return true;
    }
}
