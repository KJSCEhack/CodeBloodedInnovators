package spit.comps.collegemate.RecyclerAdapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import spit.comps.collegemate.AnnouncementItem;
import spit.comps.collegemate.R;

public class AnnouncementRecyclerAdapter extends RecyclerView.Adapter<AnnouncementRecyclerAdapter.ViewHolder> {

    ArrayList<AnnouncementItem> arrayList;
    Context context;

    public AnnouncementRecyclerAdapter(ArrayList<AnnouncementItem> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public AnnouncementRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.announcement_item, parent, false);
        return new AnnouncementRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AnnouncementRecyclerAdapter.ViewHolder holder,final int position) {
        holder.date.setText(arrayList.get(position).date);
        holder.time.setText(arrayList.get(position).time);
        holder.title.setText(arrayList.get(position).title);
        holder.description.setText(arrayList.get(position).description);

        if (arrayList.get(position).importance.equals("1"))
        {
            int theme= Color.parseColor("#FFA500");
            holder.importance.setBackgroundColor(theme);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).link != null){
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayList.get(position).link));
                        context.startActivity(myIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(context, "No application can handle this request."
                                + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(context, "No link available",  Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView date,time,title,description;
        View importance;

        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.announcements_item_cardview);
            date=itemView.findViewById(R.id.announcement_item_date);
            time=itemView.findViewById(R.id.announcement_item_time);
            title=itemView.findViewById(R.id.announcement_item_title);
            description=itemView.findViewById(R.id.announcement_item_desc);
            importance=itemView.findViewById(R.id.announcement_item_category);
        }
    }


}

