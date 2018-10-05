package spit.comps.collegemate;

import android.app.DownloadManager;
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
import org.mortbay.util.ajax.JSON;

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
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                JSON_NEWS_STRING = s;

                JSONObject jsonObject = null;
                String jsonstring1="";
                try {
                    jsonObject = new JSONObject(JSON_NEWS_STRING);
                    jsonstring1 = (String) jsonObject.get("data");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int status = readNews(jsonstring1);

                if (status==1)
                {
                    finish();
                }
                else
                {
                    //Toa
                }
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> args = new HashMap<>();
                args.put("uid",uid);
                args.put("password",password);

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
            JSONArray result = new JSONArray(JSON_NEWS_STRING);
            JSONObject object = result.getJSONObject(0);
            JSONObject jo = object.getJSONObject("fields");

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
    }

}
