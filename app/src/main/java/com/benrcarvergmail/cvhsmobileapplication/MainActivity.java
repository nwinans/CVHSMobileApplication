package com.benrcarvergmail.cvhsmobileapplication;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Object to represent the toolbar. The toolbar is the element
    // labeled "CVHS Mobile Application" with the back arrow
    private Toolbar toolbar;

    // Object to represent the tabLayout. TabLayout is an XML layout used for creating tabbed
    // interfaces. It is best to use this methodology for our current target API and supported APIs
    private TabLayout tabLayout;

    // Object for the ViewPager. ViewPagers are layout managers that allow the user to flip left
    // and right through pages of data. This is what allows us to swipe left and right to go through
    // tabs in addition to tapping the specific tab that we want.
    private ViewPager viewPager;

    // If true, tabs will be given an icon AND text. If false, tabs will only be given an icon.
    private boolean both = false;

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Assign the support action bar (the toolbar) to the object we just instantiated
        setSupportActionBar(toolbar);
        // Setting this to true makes it such that if selecting whatever we determine a "home" button to be
        // will make the UI go up ONE level as opposed to going all the way to the front page.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Disables the label defined in AndroidManifest.xml from being displayed on the toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Instantiate the ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        viewPager.setAdapter(adapter);
        setupViewPagerIconsOnly(viewPager);
        // Instantiate the TabLayout object
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        // Assign the ViewPager object to the TabLayout object so our tabs are able to be navigated
        // by swiping left and right (which is what we want)
        tabLayout.setupWithViewPager(viewPager);

        // Assign the icons to the tabs
        setupTabIcons();
    }

    // Assigns the tabs the correct icon from the tabIcons array
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // This method creates Fragments for each tab we want.
    // We want five tabs, so we add five Fragments.
    // A Fragment is a piece of an application's user interface
    // or behavior that can be placed in an Activity
    // This method only gives tabs icons. It does not give them text.
    private void setupViewPagerIconsOnly(ViewPager viewPager) {
        // Does not pass any text to the addFrag method, so the icons do not have any text
        ViewPagerAdapter vpa = (ViewPagerAdapter) viewPager.getAdapter();
        vpa.addFrag(new AnnouncementsFragment(), "");
        vpa.addFrag(new AnnouncementsFragment(), "");
        vpa.addFrag(new AnnouncementsFragment(), "");
        vpa.addFrag(new AnnouncementsFragment(), "");
        vpa.addFrag(new AnnouncementsFragment(), "");
        // vpa.addFrag(new OneFragment(), ""); // Announcements
        // vpa.addFrag(new TwoFragment(), ""); // Academics
        // vpa.addFrag(new ThreeFragment(), ""); // Wellness
        // vpa.addFrag(new FourFragment(), ""); // Extracurricular
        // vpa.addFrag(new FiveFragment(), ""); // Settings

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

        @Override
        // Returns the position of a given fragment in the fragmentList (would
        // effectively return which tab was currently the active tab)
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        // Returns the number of Fragments (equivalent to the number of tabs)
        public int getCount() {
            return mFragmentList.size();
        }

        // Adds a Fragment (effectively a tab). Takes in a Fragment and a title.
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            notifyDataSetChanged();
            mFragmentTitleList.add(title);
        }

        @Override
        // Returns the title of the current Fragment.
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
