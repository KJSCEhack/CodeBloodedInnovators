package spit.comps.collegemate;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import spit.comps.collegemate.HelperClasses.AppConstants;
import spit.comps.collegemate.HelperClasses.HttpHandler;
import spit.comps.collegemate.RecyclerAdapter.AnnouncementRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnnouncementFragment extends Fragment {

    public AnnouncementFragment() {
        // Required empty public constructor
    }

    ArrayList<AnnouncementItem> items;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_announcement, container, false);

        items=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.fragment_announcement_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        new getAnnouncementList().execute();

        return view;
    }

    private class getAnnouncementList extends AsyncTask<Void,Void,Void>
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
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();

            String jsonStr = sh.makeServiceCall("");

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray feeds = jsonObj.getJSONArray("result");

                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String alert_id = c.getString("alert_id");
                        String date = c.getString("date");
                        String time = c.getString("time");
                        String title = c.getString("title");
                        String importance = c.getString("importance");
                        String description = c.getString("description");
                        String link = c.getString("link");

                        AnnouncementItem object = new AnnouncementItem(alert_id,date,time,title,importance,description,link);
                        items.add(object);
                    }

                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),"Couldn't get data from Server. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
            {
                progressDialog.hide();
            }
            recyclerView.setAdapter(new AnnouncementRecyclerAdapter(items,getActivity()));
        }
    }

}
