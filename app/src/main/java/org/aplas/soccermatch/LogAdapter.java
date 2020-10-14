package org.aplas.soccermatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {

    //All methods in this adapter are required for a bare minimum recyclerview adapter
    private int listItemLayout;
    private ArrayList<LogItem> itemList;
    // Constructor of the class
    public LogAdapter(int layoutId, ArrayList<LogItem> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    // specify the row layout file and click for each row
    @Override
    public LogAdapter.LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        LogViewHolder holder = new LogViewHolder(view);
        return holder;
    }



    // load data in each row element
    @Override
    public void onBindViewHolder(final LogViewHolder holder, final int listPosition) {
        holder.time.setText(itemList.get(listPosition).getTime());
        holder.player.setText(itemList.get(listPosition).getPlayer());
        String name = itemList.get(listPosition).getName();
        holder.name.setText(name);
        if (name.startsWith("Goal")) {
            holder.img.setImageResource(R.drawable.icon_goal);
        } else if (name.startsWith("Yellow")) {
            holder.img.setImageResource(R.drawable.icon_yellow_card);
        } else {
            holder.img.setImageResource(R.drawable.icon_red_card);
        }
    }

    // Static inner class to initialize the views of rows
    public static class LogViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView time;
        public TextView player;
        public ImageView img;
        public LogViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.txtName);
            time = (TextView) itemView.findViewById(R.id.txtTime);
            player = (TextView) itemView.findViewById(R.id.txtPlayer);
            img = (ImageView) itemView.findViewById(R.id.eventIcon);
        }
    }
}