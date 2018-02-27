package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SensorListFragment.OnSensorListFragmentInteractionListener, SensorDetailsFragment.OnSensorDetailsFragmentInteractionListener, RuleSetListFragment.OnRuleSetListFragmentInteractionListener, RuleSetDetailsFragment.OnRuleSetDetailsFragmentInteractionListener, TriggerCategoryListFragment.OnTriggerCategoryListFragmentInteractionListener, TriggerListFragment.OnTriggerListFragmentInteractionListener, TriggerDetailsFragment.OnTriggerDetailsFragmentInteractionListener, ActionChainListFragment.OnActionChainListFragmentInteractionListener, ActionChainDetailsFragment.OnActionChainDetailsFragmentInteractionListener, SocketListFragment.OnSocketListFragmentInteractionListener, SocketDetailsFragment.OnSocketDetailsFragmentInteractionListener, ActionListFragment.OnActionListFragmentInteractionListener, ActionDetailsFragment.OnActionDetailsFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //OG: UI Interactions
    /*
    public void onFragmentInteraction(Uri uri) {
        //you can leave it empty
    }
*/
    //OG: Navigation Drawer Event
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Integer id = item.getItemId();
        NavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        menu.clear();
        navView.inflateMenu(R.menu.activity_main_drawer);

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        Class fragmentClass = null;
        System.out.println("MainActivity->NavID: " + id);

        if (id == R.id.nav_info) {
            fragmentClass = InfoFragment.class;
        } else if (id == R.id.nav_sensor) {
            fragmentClass = SensorListFragment.class;
        } else if (id == R.id.nav_actionchain) {
            fragmentClass = ActionChainListFragment.class;
        } else if (id == R.id.nav_trigger) {
            fragmentClass = TriggerCategoryListFragment.class;
        } else if (id == R.id.nav_ruleset) {
            fragmentClass = RuleSetListFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack("List").commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Sensor Interactions
    public void onSensorListFragmentInteraction(SensorListItem item) {
        SensorDetailsFragment fragment = null;


        Bundle bundle = new Bundle();
        bundle.putString("id", item.getId());
        bundle.putString("range", "avg");

        //set Fragmentclass Arguments
        fragment = new SensorDetailsFragment();
        fragment.setArguments(bundle);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack("Detail").commit();
    }

    public void onSensorDetailsFragmentInteraction(Button button) {

    }

    //Ruleset
    public void onRuleSetListFragmentInteraction(RuleSetListItem item) {
        RuleSetDetailsFragment fragment = null;


        Bundle bundle = new Bundle();
        bundle.putString("id", item.getId());

        //set Fragmentclass Arguments
        fragment = new RuleSetDetailsFragment();
        fragment.setArguments(bundle);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack("Detail").commit();
    }

    public void onRuleSetDetailsFragmentInteraction(Button button) {

    }

    //TriggerCategory
    public void onTriggerCategoryListFragmentInteraction(TriggerCategoryListItem item) {
        TriggerListFragment fragment = null;


        Bundle bundle = new Bundle();
        bundle.putString("cat", item.getId());

        //set Fragmentclass Arguments
        fragment = new TriggerListFragment();
        fragment.setArguments(bundle);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack("Detail").commit();
    }


    //Trigger
    public void onTriggerListFragmentInteraction(TriggerListItem item) {
        TriggerDetailsFragment fragment = null;


        Bundle bundle = new Bundle();
        bundle.putString("id", item.getId());
        bundle.putString("cat", item.getCat());
        bundle.putInt("type", item.getType());

        //set Fragmentclass Arguments
        fragment = new TriggerDetailsFragment();
        fragment.setArguments(bundle);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack("Detail").commit();
    }

    public void onTriggerDetailsFragmentInteraction(Button button) {

    }

    //ActionChain
    public void onActionChainListFragmentInteraction(ActionChainListItem item) {
        ActionChainDetailsFragment fragment = null;


        Bundle bundle = new Bundle();
        bundle.putString("id", item.getId().toString());

        //set Fragmentclass Arguments
        fragment = new ActionChainDetailsFragment();
        fragment.setArguments(bundle);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack("Detail").commit();
    }

    public void onActionChainDetailsFragmentInteraction(Button button) {

    }

    //Socket
    public void onSocketListFragmentInteraction(SocketListItem item) {
        SocketDetailsFragment fragment = null;


        Bundle bundle = new Bundle();
        bundle.putString("id", item.getId());

        //set Fragmentclass Arguments
        fragment = new SocketDetailsFragment();
        fragment.setArguments(bundle);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack("Detail").commit();
    }

    public void onSocketDetailsFragmentInteraction(Button button) {

    }

    //Action
    public void onActionListFragmentInteraction(ActionListItem item) {

    }

    public void onActionDetailsFragmentInteraction(Button button) {

    }
}
