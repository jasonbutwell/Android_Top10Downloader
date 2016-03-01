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

    // Used to store an ArrayList of records using the applications class
    private ArrayList<Application> applications;

    // constructor to set XMLdata and initialise ArrayList
    public ParseApplications(String xmlData) {
        this.xmlData = xmlData;
        applications = new ArrayList<>();
    }

    // return the contents of the ArrayList
    public ArrayList<Application> getApplications() {
        return applications;
    }

    // process() method to sift through the XML to pull the tag data that we want
    public boolean process() {

        boolean status = true;

        // temporary store for the current record we are building
        Application currentRecord = null;

        // Are we looking at a valid XML entry to pull data from?
        boolean inEntry = false;

        // Temp string holder
        String textValue = "";

        try {
            // use XmlPullParserFactory to pull the tags
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            // set name space aware
            factory.setNamespaceAware(true);

            // create a new parser
            XmlPullParser xpp = factory.newPullParser();

            // pass in our xmlData to the parser
            xpp.setInput(new StringReader(this.xmlData));

            // grab the event type
            int eventType = xpp.getEventType();

            // Loop, while we are not looking at the end of the xml document
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();

                // call a switch on the actual event type returned by the parser
                switch(eventType)
                {
                    // if we are looking at a start tag deliminter
                    case XmlPullParser.START_TAG:
                        // send to logCat
//                        Log.d("ParseApplications", "Starting Tag for "+ tagName);

                        // if we are looking at entry, create new record
                        if (tagName.equalsIgnoreCase("entry")) {

                            // we are looking at an entry
                            inEntry = true;

                            // creates new instance of record
                            currentRecord = new Application();
                            //break;
                            // break removed from here. Bug fix #1
                        }
                        break;
                    // break inserted here! Bug fix #1

                    // if we are looking at the actual text within the tag
                    case XmlPullParser.TEXT:

                        // obtain the text from within the tag and store it in textValue
                            textValue = xpp.getText();
                        break;

                    // Implemented functionality for read the data from the tags that we are interested in.
                    case XmlPullParser.END_TAG:
//                        Log.d("ParseApplications", "Ending Tag for "+ tagName);

                        // if we are in an entry
                        if (inEntry) {

                            // add the current record to the array list and build the current record
                            // using the set methods for name, artist and release date

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
                // move to the next xml event
                eventType = xpp.next();
            }

        }catch(Exception e) {

            // exception handling
            // print the stack trace

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

        // if all ok, return true as default
        return true;
    }
}
