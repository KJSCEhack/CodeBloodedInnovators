package spit.comps.collegemate.Fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import spit.comps.collegemate.HelperClasses.AppConstants;
import spit.comps.collegemate.HelperClasses.RequestHandler;
import spit.comps.collegemate.Items.ProjectType1Item;
import spit.comps.collegemate.LoginActivity;
import spit.comps.collegemate.R;
import spit.comps.collegemate.RecyclerAdapter.ProjectType1RecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectsFragment extends Fragment {

    String JSON_NEWS_STRING="";
    RecyclerView recyclerView;
    ArrayList<ProjectType1Item> items;
    ProgressDialog progressDialog;
    FloatingActionButton filter_fab;


    public ProjectsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        items=new ArrayList<ProjectType1Item>();

        filter_fab = (FloatingActionButton)view.findViewById(R.id.fragment_project_type1_filter_fab);

        filter_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View bottomSheetView = getLayoutInflater().inflate(R.layout.fragment_filter_bottom_sheet, null);

                final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
                dialog.setContentView(bottomSheetView);
                dialog.show();

                Button filter_button=(Button)bottomSheetView.findViewById(R.id.bottom_sheet_filter_apply_btn);

                filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        recyclerView=(RecyclerView)view.findViewById(R.id.fragment_project_type1_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new GetProjectList().execute();

        return view;
    }

    private class GetProjectList extends AsyncTask<Void,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Fetching data");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            HashMap<String,String> args = new HashMap<>();
            args.put("email","3");
            RequestHandler sh = new RequestHandler();

            String jsonStr = sh.sendPostRequest(AppConstants.get_type1_projects,args);
            return jsonStr;
            }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (progressDialog.isShowing())
            {
                progressDialog.hide();
            }

            JSON_NEWS_STRING = s;

            JSONObject jsonObject = null;
            String jsonstring1="";
            try {

                jsonObject = new JSONObject(JSON_NEWS_STRING);
                jsonstring1 = (String) jsonObject.get("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Toast.makeText(getActivity(), "A:"+jsonstring1, Toast.LENGTH_SHORT).show();

            int status = readNews(jsonstring1);

            if (status==1)
            {

            }
            else
            {

            }

            recyclerView.setAdapter(new ProjectType1RecyclerAdapter(items,getActivity()));
        }
    }

    private int readNews(String JSON_NEWS_STRING){

        try {
            JSONArray result = new JSONArray(JSON_NEWS_STRING);

            //String jsonstring1 = (String) jsonObject.get("data");
            //JSONArray result = new JSONArray(jsonstring1);

            for(int i=0;i<result.length();i++)
            {
                JSONObject js = result.getJSONObject(i);
                String event_id = js.getString("pk");


                JSONObject c = js.getJSONObject("fields");
                String name = c.getString("name");

                String organizer = c.getString("organizer");
                String contact = c.getString("contact");

                String date = c.getString("date");
                String venue = c.getString("venue");

                String description = c.getString("description");
                String time = c.getString("time");

                String regs = c.getString("registration_status");
                String poster = c.getString("poster");

                String event_type = c.getString("event_type");

                ProjectType1Item object = new ProjectType1Item(event_id,
                        name,organizer,contact,date,venue,description,time,
                        regs,poster,event_type);

                items.add(object);
            }
            //Toast.makeText(getActivity(), "No.:"+items.size(), Toast.LENGTH_SHORT).show();
            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
