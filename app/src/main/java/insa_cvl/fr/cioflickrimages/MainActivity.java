package insa_cvl.fr.cioflickrimages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncFlickrJSONData task = new AsyncFlickrJSONData(MainActivity.this);
        task.execute("http://www.flickr.com/services/feeds/photos_public.gne?"
                + "tags=luke_skywalker"
                + "&format=json");
    }
}
