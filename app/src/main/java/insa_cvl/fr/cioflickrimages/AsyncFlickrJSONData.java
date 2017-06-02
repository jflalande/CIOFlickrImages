package insa_cvl.fr.cioflickrimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jf on 03/05/17.
 */
public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {

    private AppCompatActivity myActivity;

    public AsyncFlickrJSONData(AppCompatActivity mainActivity) {
        myActivity = mainActivity;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL url = null;
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

            result = readStream(in); // Read stream
        }
        catch (MalformedURLException e) { e.printStackTrace(); }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return json; // returns the result
    }

    @Override
    protected void onPostExecute(JSONObject s) {

        ListView list = (ListView)myActivity.findViewById(R.id.listview);
        BitmapAdapter tableau = new BitmapAdapter(list.getContext());
        list.setAdapter(tableau);

        // For testing purpose
        //Bitmap largeIcon = BitmapFactory.decodeResource(myActivity.getResources(), R.drawable.test);
        //tableau.add(largeIcon);

        try {
            JSONArray items = s.getJSONArray("items");
            for (int i = 0; i<items.length(); i++)
            {
                JSONObject flickr_entry = items.getJSONObject(i);
                String urlmedia = flickr_entry.getJSONObject("media").getString("m");
                Log.i("CIO", "URL media: " + urlmedia);

                // Downloading image
                AsyncBitmapDownloader abd = new AsyncBitmapDownloader(tableau);
                abd.execute(urlmedia);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();

        // Extracting the JSON object from the String
        String jsonextracted = sb.substring("jsonFlickrFeed(".length(), sb.length() - 1);
        //Log.i("CIO", jsonextracted);
        return jsonextracted;
    }


}
