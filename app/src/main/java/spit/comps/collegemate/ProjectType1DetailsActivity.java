package spit.comps.collegemate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import spit.comps.collegemate.HelperClasses.AppConstants;
import spit.comps.collegemate.HelperClasses.GoogleCalendar;
import spit.comps.collegemate.HelperClasses.RequestHandler;

public class ProjectType1DetailsActivity extends AppCompatActivity {

    TextView name,description,organizer,time,type;
    Button register,calendar;
    ImageView imageView,callButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_type1_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(AppConstants.LIKE_PREFS, MODE_PRIVATE);
                String event_status= prefs.getString(getIntent().getStringExtra("Event_id"), "0");

                if (event_status.equals("0"))
                {
                    //API
                    addLikes(getIntent().getStringExtra("Email"),getIntent().getStringExtra("Event_id"));

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(getIntent().getStringExtra("Event_id"), "1");
                    editor.commit();
                    try {
                        GoogleCalendar.addEvent();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Toast.makeText(ProjectType1DetailsActivity.this, "Thanks for Like!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ProjectType1DetailsActivity.this, "Already Liked!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        name = (TextView)findViewById(R.id.details_project_type1_name);
        description = (TextView) findViewById(R.id.details_project_type1_description);
        organizer = (TextView) findViewById(R.id.details_project_type1_org);
        time = (TextView) findViewById(R.id.details_project_type1_time);
        register = (Button) findViewById(R.id.details_project_type1_submit_btn);
        calendar = (Button) findViewById(R.id.details_project_type1_calendar_btn);

        callButton = (ImageView) findViewById(R.id.details_project_call);

        imageView = (ImageView)findViewById(R.id.details_project_type1_image);
        Picasso.get().load(getIntent().getStringExtra("Poster")).into(imageView);

        name.setText(getIntent().getStringExtra("Title"));
        description.setText(getIntent().getStringExtra("Description"));
        time.setText(getIntent().getStringExtra("Venue")+"\n"+getIntent().getStringExtra("Time"));
        organizer.setText(getIntent().getStringExtra("Organizer"));

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + getIntent().getStringExtra("Contact")));
                startActivity(callIntent);
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(AppConstants.REGISTER_PREFS, MODE_PRIVATE);
                String event_status= prefs.getString(getIntent().getStringExtra("Event_calendar"), "0");

                if (event_status.equals("0"))
                {
                    //API
                    Uri calendarUri = CalendarContract.CONTENT_URI
                            .buildUpon()
                            .appendPath("time")
                            .build();

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(getIntent().getStringExtra("Event_calendar"), "1");
                    editor.commit();
                    startActivity(new Intent(Intent.ACTION_VIEW, calendarUri));
                }
                else
                {
                    Toast.makeText(ProjectType1DetailsActivity.this, "Already Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(AppConstants.REGISTER_PREFS, MODE_PRIVATE);
                String event_status= prefs.getString(getIntent().getStringExtra("Event_id"), "0");

                if (event_status.equals("0"))
                {
                    //API
                    addRegister(getIntent().getStringExtra("Email"),getIntent().getStringExtra("Event_id"));

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(getIntent().getStringExtra("Event_id"), "1");
                    editor.commit();
                    Toast.makeText(ProjectType1DetailsActivity.this, "Already Registered!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ProjectType1DetailsActivity.this, "Already Liked!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void addLikes(final String email, final String pk) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> args = new HashMap<>();
                args.put("email",email);
                args.put("pk",pk);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(AppConstants.event_like,args);

                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    private void addRegister(final String email, final String pk) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> args = new HashMap<>();
                args.put("email",email);
                args.put("pk",pk);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(AppConstants.event_register,args);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
