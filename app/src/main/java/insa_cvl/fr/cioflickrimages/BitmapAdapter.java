package insa_cvl.fr.cioflickrimages;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by jf on 02/06/17.
 */

class BitmapAdapter extends BaseAdapter {

    private Context context_ = null;

    public BitmapAdapter(Context context) {
        context_ = context;
    }

    ArrayList<Bitmap> myList = new ArrayList<Bitmap>();
    Context context;

    // retourne le nombre d'objet présent dans notre liste
    @Override
    public int getCount() {
        return myList.size();
    }

    // retourne un élément de notre liste en fonction de sa position
    @Override
    public Bitmap getItem(int position) {
        return myList.get(position);
    }

    // retourne l'id d'un élément de notre liste en fonction de sa position
    @Override
    public long getItemId(int position) {
        return myList.indexOf(getItem(position));
    }

    public void add(Bitmap image) {
        myList.add(image);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Bitmap image = (Bitmap)getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context_)
                    .inflate(R.layout.uneimage, parent, false);
        }

        ImageView iv = (ImageView)convertView.findViewById(R.id.imageView);
        iv.setImageBitmap(image);

        return convertView;
    }

}
