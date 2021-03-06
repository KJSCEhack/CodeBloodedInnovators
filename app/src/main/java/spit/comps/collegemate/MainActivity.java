package spit.comps.collegemate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import spit.comps.collegemate.Fragments.Home2Fragment;
import spit.comps.collegemate.Fragments.ProjectsFragment;
import spit.comps.collegemate.Fragments.SpeechToText;
import spit.comps.collegemate.Fragments.TimeTableFragment;
import spit.comps.collegemate.HelperClasses.AppConstants;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, BeaconConsumer, MonitorNotifier {

    FragmentManager fm;
    FrameLayout HomeScreen_FragmentContainer;
    BeaconManager mBeaconManager;
    String backStageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Login Logic

        try
        {
            SharedPreferences prefs = getSharedPreferences(AppConstants.LOGIN_PREFS, MODE_PRIVATE);
            String login_status= prefs.getString("login_status", "0");

            if (!login_status.equals("1"))
            {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }

        }catch (Exception e)
        {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }

        //End

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        HomeScreen_FragmentContainer=(FrameLayout)findViewById(R.id.HomeScreen_FragmentContainer);
        fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.HomeScreen_FragmentContainer,new Home2Fragment()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_name);
        TextView navEmail = (TextView) headerView.findViewById(R.id.nav_header_email);

        SharedPreferences prefs = getSharedPreferences(AppConstants.LOGIN_PREFS, MODE_PRIVATE);
        navUsername.setText(prefs.getString("name", "0"));
        navEmail.setText(prefs.getString("email", "0"));

        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public void onResume() {
        super.onResume();
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the main Eddystone-UID frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout(BeaconParser.EDDYSTONE_UID_LAYOUT));
        mBeaconManager.bind(this);

    }

    @Override
    public void onBeaconServiceConnect() {
        // Set the two identifiers below to null to detect any beacon regardless of identifiers
        //Identifier myBeaconNamespaceId = Identifier.parse("e9885047b7feedcf9865");
        Identifier myBeaconNamespaceId = Identifier.parse("0195d091a1fa7be139dc");
        Identifier myBeaconInstanceId = Identifier.parse("000000000000");
        Region region = new Region("my-beacon-region", myBeaconNamespaceId, myBeaconInstanceId, null);
        mBeaconManager.addMonitorNotifier(this);
        try {
            mBeaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didEnterRegion(Region region) {
        Toast.makeText(this, "Attendance marked for "+ region.getId1(), Toast.LENGTH_LONG).show();
        Log.d("entrl", "I detected a beacon in the region with namespace id " + region.getId1() +
                " and instance id: " + region.getId2());
    }

    public void didExitRegion(Region region) {
    }

    public void didDetermineStateForRegion(int state, Region region) {
    }

    @Override
    public void onPause() {
        super.onPause();
        mBeaconManager.unbind(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences prefs = getSharedPreferences(AppConstants.LOGIN_PREFS, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("login_status", "0");
            editor.commit();
            startActivity(new Intent(this,LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //final FragmentTransaction fragmentTransaction = fm.beginTransaction();

        final FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (id==R.id.nav_events)
        {
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.HomeScreen_FragmentContainer, new ProjectsFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (id == R.id.nav_home) {
            boolean isFragmentinStack = fm.popBackStackImmediate(backStageName, 0);
            if(!isFragmentinStack){
                Home2Fragment fragment = Home2Fragment.newInstance();
                fragmentTransaction.replace(R.id.HomeScreen_FragmentContainer, fragment);
                backStageName = fragment.getClass().getName();
                fragmentTransaction.addToBackStack(backStageName).commit();
            }

        }
        else if (id == R.id.nav_announcements) {
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.HomeScreen_FragmentContainer, new AnnouncementFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }

        else if (id == R.id.nav_timetable) {
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.HomeScreen_FragmentContainer, new TimeTableFragment());
            fragmentTransaction.addToBackStack(null).commit();
        }
        else if (id == R.id.nav_notesresources) {
            getSupportFragmentManager().popBackStackImmediate();
            fragmentTransaction.replace(R.id.HomeScreen_FragmentContainer, new SpeechToText());
            fragmentTransaction.addToBackStack(null).commit();
        }
        /*
        else if (id == R.id.nav_announcements) {

        }
        else if (id == R.id.nav_exams) {

        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
