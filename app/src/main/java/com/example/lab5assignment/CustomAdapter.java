
package com.example.lab5assignment;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Context mContext;
    Controllerdb controldb;
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Score = new ArrayList<String>();

    public CustomAdapter(Context context, ArrayList<String> Id, ArrayList<String> Name, ArrayList<String> Score) {
        this.mContext = context;
        this.Id = Id;
        this.Name = Name;
        this.Score = Score;

    }

    @Override
    public int getCount() {
        return Id.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder holder;
        controldb = new Controllerdb(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.layout, null);
            holder = new viewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.tvid);
            holder.name = (TextView) convertView.findViewById(R.id.tvname);
            holder.score = (TextView) convertView.findViewById(R.id.tvscore);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();

        }
        holder.id.setText(Id.get(position));
        holder.name.setText(Name.get(position));
        holder.score.setText(Score.get(position));
        return convertView;
    }

    public class viewHolder {
        TextView id;
        TextView name;
        TextView score;

    }
}

