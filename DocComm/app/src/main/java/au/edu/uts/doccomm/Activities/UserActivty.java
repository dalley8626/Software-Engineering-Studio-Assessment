package au.edu.uts.doccomm.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import au.edu.uts.doccomm.Fragments.MapFragment;
import au.edu.uts.doccomm.Fragments.PatientInformationFragment;
import au.edu.uts.doccomm.R;

public class UserActivty extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogout;
    private FirebaseAuth mAuth;
    private TextView tvUsername;

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private enum MenuStates {
        PATIENT_INFO, DATA_PACKET, HEARTRATE, RECORD_VIDEO, SEND_FILE, NAVIGATION_MAP
    }
    private MenuStates currentState;


    public void addData(View view) {
        Intent intent = new Intent(getApplicationContext(), PatientInformationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activty);

        mAuth = FirebaseAuth.getInstance();

        //If there is no use logged in, redirect them to the login page
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        FirebaseUser user = mAuth.getCurrentUser();

        //TODO: Change to Username
        //tvUsername = (TextView) findViewById(R.id.etEmail);
        //tvUsername.setText("Welcome" + user.getEmail());

        // the default fragment on display is the patient information
        currentState = MenuStates.PATIENT_INFO;

        // go look for the main drawer layout
        mDrawerLayout = findViewById(R.id.main_drawer_layout);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Set up the menu button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // Setup the navigation drawer, most of this code was taken from:
        // https://developer.android.com/training/implementing-navigation/nav-drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Using a switch to see which item on the menu was clicked
                        switch (menuItem.getItemId()) {
                            // You can find these id's at: res -> menu -> drawer_view.xml
                            case R.id.nav_patient_info:
                                // If the user clicked on a different item than the current item
                                if (currentState != MenuStates.PATIENT_INFO) {
                                    // change the fragment to the new fragment
                                    ChangeFragment(new PatientInformationFragment());
                                    currentState = MenuStates.PATIENT_INFO;
                                }
                                break;
//                            case R.id.nav_data_packet:
//                                if (currentState != MenuStates.DATA_PACKET) {
//                                    ChangeFragment(new DataPacketFragment());
//                                    currentState = MenuStates.DATA_PACKET;
//                                }
//                                break;
//                            case R.id.nav_heartrate:
//                                if (currentState != MenuStates.HEARTRATE) {
//                                    ChangeFragment(new HeartRateFragment());
//                                    currentState = MenuStates.HEARTRATE;
//                                }
//                                break;
//                            case R.id.nav_recordvideo:
//                                if (currentState != MenuStates.RECORD_VIDEO) {
//                                    ChangeFragment(new RecordVideoFragment());
//                                    currentState = MenuStates.RECORD_VIDEO;
//                                }
//                                break;
//                            case R.id.nav_sendfile:
//                                if (currentState != MenuStates.SEND_FILE) {
//                                    ChangeFragment(new SendFileFragment());
//                                    currentState = MenuStates.SEND_FILE;
//                                }
//                                break;
                            case R.id.nav_map:
                                if (currentState != MenuStates.NAVIGATION_MAP) {
                                    ChangeFragment(new MapFragment());
                                    currentState = MenuStates.NAVIGATION_MAP;
                                }
                                break;
                        }

                        return true;
                    }
                });

        // If you need to listen to specific events from the drawer layout.
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );


        // More on this code, check the tutorial at http://www.vogella.com/tutorials/AndroidFragments/article.html
        fragmentManager = getFragmentManager();

        // Add the default Fragment once the user logged in
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment_container, new PatientInformationFragment());
        ft.commit();



        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        PatientInformationActivity.patientInformation.add("Enter medical information.\n");
        PatientInformationActivity.patientInformation.add("Enter medical information 2.\n");

    }

    @Override
    public void onClick(View view) {
        if (view == btnLogout) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }


    /**
     * Called when one of the items in the toolbar was clicked, in this case, the menu button.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This function changes the title of the fragment.
     *
     * @param newTitle The new title to write in the
     */
    public void ChangeTitle(String newTitle) {
        toolbar.setTitle(newTitle);
    }


    /**
     * This function allows to change the content of the Fragment holder
     * @param fragment The fragment to be displayed
     */
    private void ChangeFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
