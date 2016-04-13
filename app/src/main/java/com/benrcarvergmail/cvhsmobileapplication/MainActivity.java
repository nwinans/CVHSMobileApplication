package com.benrcarvergmail.cvhsmobileapplication;

<<<<<<< HEAD

=======
import android.app.Dialog;
>>>>>>> 30fb635d83327726485c350e4d44403658bf5cea
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.AlertDialog;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Object to represent the toolbar. The toolbar is the element
    // labeled "CVHS Mobile Application" with the back arrow
    private Toolbar mToolbar;

    // Object to represent the tabLayout. TabLayout is an XML layout used for creating tabbed
    // interfaces. It is best to use this methodology for our current target API and supported APIs
    private TabLayout mTabLayout;

    // Object for the ViewPager. ViewPagers are layout managers that allow the user to flip left
    // and right through pages of data. This is what allows us to swipe left and right to go through
    // tabs in addition to tapping the specific tab that we want.
    private ViewPager mViewPager;

    // If true, tabs will be given an icon AND text. If false, tabs will only be given an icon.
    private boolean mBoth = false;

    // Hard-coded icons for the tabs
    private int[] tabIcons = {
            R.drawable.tab1, // Newspaper. I am setting the drawable to an XML document that
            // determines what icon to display based on whether or not the tab is active or not.
            R.drawable.tab2, // Academics. I am setting the drawable to an XML document that
            // determines what icon to display based on whether or not the tab is active or not.
            R.drawable.tab3, // Box with cross. I am setting the drawable to an XML document that
            // determines what icon to display based on whether or not the tab is active or not.
            R.drawable.tab4, // Two silhouettes. I am setting the drawable to an XML document that
            // determines what icon to display based on whether or not the tab is active or not.
            R.drawable.tab5 // Gear. I am setting the drawable to an XML document that
            // determines what icon to display based on whether or not the tab is active or not.
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Always call the super onCreate method, passing savedInstanceState
        super.onCreate(savedInstanceState);
        // Set the content view (XML file to render what the user sees) to activity_main.xml
        setContentView(R.layout.activity_main);
        // Instantiated the toolbar object to the one defined in the XML
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        // Assign the support action bar (the toolbar) to the object we just instantiated
        setSupportActionBar(mToolbar);
        // Setting this to true makes it such that if selecting whatever we determine a "home" button to be
        // will make the UI go up ONE level as opposed to going all the way to the front page.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Disables the label defined in AndroidManifest.xml from being displayed on the toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Instantiate the ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        mViewPager.setAdapter(adapter);
        setupViewPager(mViewPager);
        // Instantiate the TabLayout object
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        // Assign the ViewPager object to the TabLayout object so our tabs are able to be navigated
        // by swiping left and right (which is what we want)
        mTabLayout.setupWithViewPager(mViewPager);
        // Prevents the keyboard from opening right when the app opens
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        // Crisis Button
        ImageButton imageButtonCrisis = (ImageButton) findViewById(R.id.crisisButton);
        imageButtonCrisis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog  = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("CRISIS");
                alertDialog.setMessage("CrisisLink Regional Hotlink \n " + "(703) 527-4077\n\n"+
                        "Crisis Texting \n" + "\b\bText NEEDHELP to 85511\n\n"+
                        "Dominion Hospital Emergency Room \b" + "\b\b(703) 536-200\n\n"+
                        "Inova Emergency Services \n" + "\b\b(703) 289-7560\n\n"+
                        "Mobile Crisis Unit \n" + "\b\b1-844-627-4747\n\n"+
                        "National Suicide Prevention Hotline \n" + "\b\b1-800-273-TALK and \n\b\b1-800-SUICIDE\n\n"+
                        "Merrifield Center Emergency Services \n" + "\b\b(703) 573-5769\n\n"+
                        "Fairfax County Police Department \n" + "\b\b(703) 691-2131\n\n"+
                        "Fairfax County Sheriff Department \n" + "\b\b(703) 360-8404\n\n"+
                        "TTY Dial: 711 \n" +
                        "Life-Threatening Emergencies: 911");
                alertDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            }
        });

        // Assign the icons to the tabs
        setupTabIcons();
    }

    // Assigns the tabs the correct icon from the tabIcons array
    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
        mTabLayout.getTabAt(3).setIcon(tabIcons[3]);
        mTabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    // This method creates Fragments for each tab we want.
    // We want five tabs, so we add five Fragments.
    // A Fragment is a piece of an application's user interface
    // or behavior that can be placed in an Activity
    private void setupViewPager(ViewPager viewPager) {
        // Does not pass any text to the addFrag method, so the tabs do not have any text titles
        ViewPagerAdapter vpa = (ViewPagerAdapter) viewPager.getAdapter();
        vpa.addFrag(new AnnouncementsFragment(), ""); // Announcements
<<<<<<< HEAD
        vpa.addFrag(new CalendarFragment(), ""); // Placeholder
=======
>>>>>>> origin/master
        vpa.addFrag(new BasicFragment(), ""); // Placeholder
        vpa.addFrag(new BasicFragment(), ""); // Placeholder
<<<<<<< HEAD
        vpa.addFrag(new InformationFragment(), ""); // Placeholder
=======
        vpa.addFrag(new BasicFragment(), ""); // Placeholder
        vpa.addFrag(new ClubsFragment(), ""); // Placeholder
>>>>>>> 30fb635d83327726485c350e4d44403658bf5cea
    }

    // A FragmentPagerAdapter is an implementation of PagerAdapter that
    // represents each page as a Fragment that is persistently kept in
    // the fragment manager as long as the user can return to the page.
    class ViewPagerAdapter extends FragmentPagerAdapter {
        // List of all the Fragments pertaining to our tabs
        private final List<Fragment> mFragmentList = new ArrayList<>();

        // List of each title of each Fragment. The titles would be the String we pass in setupViewPager()
        private final List<String> mFragmentTitleList = new ArrayList<>();

        Context context;

        // Constructor. Calls the super constructor passing our FragmentManager object
        public ViewPagerAdapter(FragmentManager manager, Context context) {
            super(manager);
            this.context = context;
        }

        // Returns the position of a given fragment in the fragmentList (would
        // effectively return which tab was currently the active tab)
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        // Returns the number of Fragments (equivalent to the number of tabs)
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        // Adds a Fragment (effectively a tab). Takes in a Fragment and a title.
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            notifyDataSetChanged();
            mFragmentTitleList.add(title);
        }

        public void addFrag(ListFragment fragment, String title){
            mFragmentList.add((Fragment)fragment);
            notifyDataSetChanged();
            mFragmentTitleList.add(title);
        }

        // Returns the title of the current Fragment.
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }


}
