package com.jasonbutwell.top10downloader2;

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
        Application currentRecord = null;
        boolean inEntry = false;
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
//                        Log.d("ParseApplications", "Starting Tag for "+ tagName);

                        if (tagName.equalsIgnoreCase("entry")) {
                            inEntry = true;
                            currentRecord = new Application();
                            //break;
                            // break removed from here. Bug fix #1
                        }
                        break;
                    // break inserted here! Bug fix #1

                    case XmlPullParser.TEXT:
                            textValue = xpp.getText();
                        break;

                    // Implemented functionality for read the data from the tags that we are interested in.
                    case XmlPullParser.END_TAG:
//                        Log.d("ParseApplications", "Ending Tag for "+ tagName);
                        if (inEntry) {
                            if (tagName.equalsIgnoreCase("entry")) {
                                applications.add(currentRecord);
                                inEntry = false;
                            } else if (tagName.equalsIgnoreCase("name")) {
                                currentRecord.setName(textValue);
                            } else if (tagName.equalsIgnoreCase("artist")) {
                                currentRecord.setArtist(textValue);
                            } else if (tagName.equalsIgnoreCase("releaseDate")) {
                                currentRecord.setReleaseDate(textValue);
                            }
                        }
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

        // loop through array list as app for reference
        // output the values of what we pull from the XML tags we are interested in.
        // Use tag 'ParseApplications' in logCat filter to see the results.

//        for (Application app : applications )
//        {
//            Log.d("ParseApplications", "************");
//            Log.d("ParseApplications", "Name: "+ app.getName());
//            Log.d("ParseApplications", "Artist: "+ app.getArtist());
//            Log.d("ParseApplications", "Release Date: "+ app.getReleaseDate());
//        }

        return true;
    }
}
