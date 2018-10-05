package spit.comps.collegemate.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import spit.comps.collegemate.Items.ProjectType1Item;
import spit.comps.collegemate.ProjectType1DetailsActivity;
import spit.comps.collegemate.R;

public class ProjectType1RecyclerAdapter extends RecyclerView.Adapter<ProjectType1RecyclerAdapter.ViewHolder> {


    ArrayList<ProjectType1Item> arrayList;
    Context context;

    public ProjectType1RecyclerAdapter(ArrayList<ProjectType1Item> arrayList, Context context)
    {
        this.arrayList=arrayList;
        this.context=context;
    }

    @Override
    public ProjectType1RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_type1_item, parent, false);
        return new ProjectType1RecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectType1RecyclerAdapter.ViewHolder holder, final int position) {

        holder.department_textview.setText(arrayList.get(position).description);
        holder.title_textview.setText(arrayList.get(position).name);
        Picasso.get().load(arrayList.get(position).poster).into(holder.project_imageview);

        holder.projectid_textview.setText("Event ID: "+arrayList.get(position).id);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, ProjectType1DetailsActivity.class);
                intent.putExtra("Event_id",arrayList.get(position).id);
                intent.putExtra("Title",arrayList.get(position).name);
                intent.putExtra("Description",arrayList.get(position).description);
                intent.putExtra("Time",arrayList.get(position).time);
                intent.putExtra("Venue",arrayList.get(position).venue);
                intent.putExtra("Poster",arrayList.get(position).poster);
                intent.putExtra("Organizer",arrayList.get(position).organizer);
                intent.putExtra("Contact",arrayList.get(position).contact);
                intent.putExtra("Event_type",arrayList.get(position).event_type);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView department_textview,title_textview;
        ImageView project_imageview;
        CardView cardView;
        TextView projectid_textview;

        public ViewHolder(View itemView) {
            super(itemView);

            department_textview=itemView.findViewById(R.id.project_type1_item_departmenttext);
            projectid_textview=itemView.findViewById(R.id.project_type1_item_id_text);
            title_textview=itemView.findViewById(R.id.project_type1_item_titletext);
            project_imageview=itemView.findViewById(R.id.project_type1_item_image);
            cardView=itemView.findViewById(R.id.project_type1_item_cardview);
        }
    }

}
