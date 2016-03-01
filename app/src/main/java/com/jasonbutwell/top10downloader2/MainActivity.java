package com.jasonbutwell.top10downloader2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    // stores for button, listview and XML string buffer contents

    private Button btnParse;

    private ListView listApps;

    private String mFileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the ids of the XML parse button and the listview

        btnParse = (Button)findViewById(R.id.btnParse);
        listApps = (ListView)findViewById(R.id.xmlListView);

        // create a click listener for the button

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create new instance of the ParseApplications class passing in the XML we read in
                ParseApplications parseApplications = new ParseApplications(mFileContents);
                // call the process() method to process the XML and pull the tags
                parseApplications.process();

                // setup array list adapter to show array contents within our listview

                // We need an ArrayAdapter to show the contents of the array
                ArrayAdapter<Application> arrayAdapter = new ArrayAdapter<Application>(MainActivity.this, R.layout.list_item, parseApplications.getApplications());
                listApps.setAdapter(arrayAdapter);
            }
        });

        // Example URL to use for XML file source
        String parseURL = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml";

        // create new instance of DownloadData class
        DownloadData downloadData = new DownloadData(this);

        // call it's execute method to parse the URL of the XML
        downloadData.execute(parseURL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // We use an AsyncTask to do the reading of the XML to prevent blocking
    // and also in case we want to cancel the task
    class DownloadData extends AsyncTask<String, Void, String> {

        // stores the context of the main activity
        private MainActivity mainActivity;

        public DownloadData(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("DownloadData", "Result was: " + result);
        }

        // String... = Variable number of arguments (works like an array of arguments)
        @Override
        protected String doInBackground(String... params) {
            // call to download the XML using the first parameter as the filename from the argument array
            mFileContents = downloadXMLFile(params[0]);
            // check if we actually downloaded something
            if (mFileContents == null) {
                Log.d("DownloadData", "Error Downloading");
            }

            // return back what we downloaded
            return mFileContents;
        }

        // the method that does the actual reading of the XML from the URL
        private String downloadXMLFile(String urlPath) {
            // temporary store as a StringBuilder
            StringBuilder tempBuffer = new StringBuilder();
            // try - catch to handle possible IOException
            try {
                // format as an actual URL we can use
                URL url = new URL(urlPath);
                // create connection to web resource
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // response holds the response code
                int response = connection.getResponseCode();
                // output the response code to LogCat so we can check everything went ok.
                Log.d("DownloadData", "The response code was " + response);

                // Input stream reader initialisation
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                // char buffer
                int charRead;
                // buffer will read data in 500 byte chunks
                char[] inputBuffer = new char[500];

                // loop and read the characters while there are characters present
                while (true) {
                    charRead = isr.read(inputBuffer);

                    // if the character read is less than or equal to 0 then we break the read loop
                    if (charRead <= 0) {
                        break;
                    }
                    // if not 0 then append the input buffer of 500 chars onto the stringbuilder
                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charRead));
                }

                // return the whole buffer (converted to string)
                return tempBuffer.toString();

            } catch (IOException e) {
                Log.d("DownloadData", "IO Exception reading data: " + e.getMessage());
            }

            // if there was a problem then we return null
            return null;
        }
    }

}




