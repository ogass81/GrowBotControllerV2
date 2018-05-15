package e.oliver.growbotcontrollerv2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements LogListFragment.OnLogListFragmentInteractionListener, SettingsFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener, SensorListFragment.OnSensorListFragmentInteractionListener, SensorDetailsFragment.OnSensorDetailsFragmentInteractionListener, RuleSetListFragment.OnRuleSetListFragmentInteractionListener, RuleSetDetailsFragment.OnRuleSetDetailsFragmentInteractionListener, TriggerCategoryListFragment.OnTriggerCategoryListFragmentInteractionListener, TriggerListFragment.OnTriggerListFragmentInteractionListener, TriggerDetailsFragment.OnTriggerDetailsFragmentInteractionListener, ActionChainListFragment.OnActionChainListFragmentInteractionListener, ActionChainDetailsFragment.OnActionChainDetailsFragmentInteractionListener, SocketListFragment.OnSocketListFragmentInteractionListener, SocketDetailsFragment.OnSocketDetailsFragmentInteractionListener, ActionListFragment.OnActionListFragmentInteractionListener, ActionDetailsFragment.OnActionDetailsFragmentInteractionListener {

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

        View header = navigationView.inflateHeaderView(R.layout.nav_header_main);

        TextView server_ip = header.findViewById(R.id.value_ip);
        server_ip.setText(Settings.getInstance().getClient_ip());

        TextView server_version = header.findViewById(R.id.value_version);
        server_version.setText(Settings.getInstance().getFirmware_version());

        TextView compile_date = header.findViewById(R.id.value_compiledate);
        compile_date.setText(Settings.getInstance().getFirmware_date());

        TextView compile_time = header.findViewById(R.id.value_compiletime);
        compile_time.setText(Settings.getInstance().getFirmware_time());
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
        Class fragmentClass = null;
        Fragment fragment = null;
        Bundle parameters = new Bundle();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragmentClass = SettingsFragment.class;
            parameters.putString("config", "active");
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragment.setArguments(parameters);
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment).addToBackStack("List").commit();

        // Set action bar title
        setTitle(item.getTitle());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        menu.clear();
        navView.inflateMenu(R.menu.activity_main_drawer);

        Fragment fragment = null;
        Bundle bundle = new Bundle();
        Class fragmentClass = null;

        if (id == R.id.nav_info) {
            menu.add(R.id.nav_info, 100, 0, "Device").setIcon(R.drawable.ic_developer_board_black_24dp);
            menu.add(R.id.nav_info, 101, 0, "System Log").setIcon(R.drawable.ic_event_note_black_24dp);

            fragmentClass = InfoFragment.class;
        }
        //Device Info
        else if (id == 100) {
            fragmentClass = InfoFragment.class;
            drawer.closeDrawer(GravityCompat.START);
        }
        //Log
        else if (id == 101) {
            fragmentClass = LogListFragment.class;
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_sensor) {
            fragmentClass = SensorListFragment.class;
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_actionchain) {
            fragmentClass = ActionChainListFragment.class;
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_trigger) {
            fragmentClass = TriggerCategoryListFragment.class;
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_ruleset) {
            fragmentClass = RuleSetListFragment.class;
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.nav_socket) {
            fragmentClass = SocketListFragment.class;
            drawer.closeDrawer(GravityCompat.START);

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


        return true;
    }

    //Sensor Interactions
    public void onSensorListFragmentInteraction(SensorListItem item) {
        SensorDetailsFragment fragment = null;


        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
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
        bundle.putInt("id", item.getId());

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
        bundle.putInt("cat", item.getId());

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
        bundle.putInt("id", item.getId());
        bundle.putInt("cat", item.getCat());
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
        bundle.putInt("id", item.getId());

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
        bundle.putInt("id", item.getId());

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLogListFragmentInteraction(LogListItem item) {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the custom layout/view
        View customView = inflater.inflate(R.layout.fragment_log_details, null);

                /*
                    public PopupWindow (View contentView, int width, int height)
                        Create a new non focusable popup window which can display the contentView.
                        The dimension of the window must be passed to this constructor.

                        The popup does not provide any background. This should be handled by
                        the content view.

                    Parameters
                        contentView : the popup's content
                        width : the popup's width
                        height : the popup's height
                */


        TextView id = customView.findViewById(R.id.value_id);
        id.setText(item.getId().toString());
        TextView time = customView.findViewById(R.id.value_time);
        time.setText(item.getFormatedTime().toString());
        TextView value_source = customView.findViewById(R.id.value_source);
        value_source.setText(item.getSource());
        TextView value_msg = customView.findViewById(R.id.value_msg);
        value_msg.setText(item.getMessage());
        TextView value_vars = customView.findViewById(R.id.value_variables);
        Set<String> keys = item.getParameters().keySet();
        for (String key : keys) {
            value_vars.append(key + " " + item.getParameters().get(key) + "\n");
        }


        // Initialize a new instance of popup window
        final PopupWindow mPopupWindow = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        mPopupWindow.setElevation(5.0f);

        // Get a reference for the custom view close button
        ImageButton closeButton = customView.findViewById(R.id.ib_close);

        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
            }
        });

        // Finally, show the popup window at the center location of root relative layout
        mPopupWindow.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 0);

    }
}
