package spit.comps.collegemate;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import spit.comps.collegemate.HelperClasses.AppConstants;
import spit.comps.collegemate.HelperClasses.RequestHandler;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText uid_edittext,password_edittext;
    Button login_button;

    String JSON_NEWS_STRING="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uid_edittext = (TextInputEditText)findViewById(R.id.uid_edittext);
        password_edittext = (TextInputEditText)findViewById(R.id.password_edittext);
        login_button = (Button)findViewById(R.id.login_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getProjectNews(String.valueOf(uid_edittext.getText()),String.valueOf(password_edittext.getText()));
            }
        });
    }

    private void getProjectNews(final String uid, final String password) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(LoginActivity.this, "He"+s, Toast.LENGTH_SHORT).show();
                JSON_NEWS_STRING = s;
                int status = readNews(JSON_NEWS_STRING);

                if (status==1)
                {
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "I", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> args = new HashMap<>();
                args.put("uid","2016");
                args.put("password","12345");

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(AppConstants.validate_user,args);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private int readNews(String JSON_NEWS_STRING){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_NEWS_STRING);
            JSONArray result = jsonObject.getJSONArray("user");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString("name");
                String email = jo.getString("email");
                String branch = jo.getString("branch");
                String year = jo.getString("year");


                SharedPreferences prefs = getSharedPreferences(AppConstants.LOGIN_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("login_status", "1");
                editor.putString("name",name);
                editor.putString("email",email);
                editor.putString("branch",branch);
                editor.putString("year",year);
                editor.commit();

                return 1;
                //newsItemArrayList.add(new ProjectType1NewsItem(date,time,headline,description));
            }

            /*
            if (newsItemArrayList.size()==0)
            {
                newCard.setVisibility(View.GONE);
            }else {
                ProjectType1NewsListAdapter adapter=new ProjectType1NewsListAdapter(this,newsItemArrayList);
                news_listView.setAdapter(adapter);
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

}
