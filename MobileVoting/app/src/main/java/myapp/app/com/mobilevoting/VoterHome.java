package myapp.app.com.mobilevoting;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VoterHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database= FirebaseDatabase. getInstance ();;
    DatabaseReference myRef = database.getReference();
    DatabaseReference cons = myRef.child("constituency");
    String passong_consti,name1,vid1,flag,votedto,mob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voter_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Voting App");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //displaySelectedScreen(R.id.nav_detail);
        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.username);
        TextView vid = (TextView)header.findViewById(R.id.vid);
        name1=getIntent().getStringExtra("name");
        vid1=getIntent().getStringExtra("voter");
        flag=getIntent().getStringExtra("flag");
        votedto=getIntent().getStringExtra("voted");
        mob=getIntent().getStringExtra("mob");
        name.setText(name1);
        vid.setText(vid1);
        passong_consti="5";
        DatabaseReference constit=cons.child(getIntent().getStringExtra("const"));
        Toast. makeText (getBaseContext(),flag,Toast. LENGTH_SHORT ).show();
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
        getMenuInflater().inflate(R.menu.voter_home, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



     private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_detail:
                fragment = new FragmentVoteDetail();
                break;
            case R.id.nav_cast:
                fragment = new CastVote();
                break;
            case R.id.nav_poll:
                fragment = new FragmentPollStatus();
                break;
            case R.id.nav_home:
                fragment=new FragmentHome();
                break;
            case R.id.nav_logout:
                Intent in = new Intent(getApplicationContext(),SignIn.class);
                startActivity(in);
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            Bundle b = new Bundle();
            b.putString("cons",passong_consti);
            b.putString("name",name1);
            b.putString("voted to",votedto);
            b.putString("flag",flag);
            b.putString("vid",vid1);
            b.putString("mob",mob);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment.setArguments(b);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        displaySelectedScreen(id);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
