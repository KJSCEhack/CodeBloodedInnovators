package spit.comps.collegemate;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    EditText uid_edittext,password_edittext;
    Button login_button;

    String JSON_NEWS_STRING="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uid_edittext = (EditText)findViewById(R.id.uid_edittext);
        password_edittext = (EditText)findViewById(R.id.password_edittext);
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
                JSON_NEWS_STRING = s;
                readNews(JSON_NEWS_STRING);
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

    private void readNews(String JSON_NEWS_STRING){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_NEWS_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString("name");
                String email = jo.getString("email");
                String branch = jo.getString("branch");
                String year = jo.getString("year");

                Toast.makeText(this,name+" "+email,Toast.LENGTH_SHORT).show();

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
        }

    }

}
