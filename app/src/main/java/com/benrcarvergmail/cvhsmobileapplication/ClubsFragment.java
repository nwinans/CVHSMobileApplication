package com.benrcarvergmail.cvhsmobileapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// ToDo: Thoroughly comment all of this code.

/**
 * Started by Mr. Small's Advanced Computer Science AB/Data Structures class on 3/14/2016.
 */
public class ClubsFragment extends Fragment {

    private ArrayList<Club> mClubData;

    private List<String> mGroupList;
    private List<String> mChildList;
    private Map<String, List<String>> mClubCollection;

    private ClubsExpandableListAdapter mExpandableListAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private final String mDefaultSpreadsheetURL = "https://spreadsheets.google.com/tq?key=1Ujv343n_71WhNtozzc88SIm18ahy-Wfrh5lMmMFEsVs";

    private static final String TAG = "ClubsFragment";

    /**
     * Instantiates a new Club fragment.
     */
    public ClubsFragment() {
        // Instantiate the mClubData ArrayList so we may populate it during onCreateView()
        mClubData = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_clubs, container, false);

        // Set up the List Adapter prior to calling populateData()
        mGroupList = new ArrayList<>();
        mClubCollection = new LinkedHashMap<>();
        mExpandableListAdapter = new ClubsExpandableListAdapter(getActivity(), mGroupList, mClubCollection);

        // Create object reference to the RecyclerView created in fragment_clubs.xml
        // Populate the mClubData ArrayList. We currently do not utilize the boolean return type
        populateData();

        ExpandableListView mExpandableListView = (ExpandableListView) rootView.findViewById(R.id.listview);


        mExpandableListView.setAdapter(mExpandableListAdapter);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String selected = (String) mExpandableListAdapter.getChild(groupPosition, childPosition);
                Toast.makeText(getContext(), selected, Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout)
                rootView.findViewById(R.id.swipe_refresh_layout_clubs);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        return rootView;
    }

    // Refreshes the Clubs by reconnecting to the server and pulling new data
    private void refreshContent() {
        populateData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void createGroupList() {
        mGroupList = new ArrayList<>();
        Log.i(TAG, "OUTSIDE For-loop of createGroupList()");
        for (int i = 0; i < mClubData.size(); i++) {
            Log.i(TAG, "in For-loop of createGroupList()");
            Log.i(TAG,(i + ": " + mClubData.get(i).getTitle()));
            mGroupList.add(mClubData.get(i).getTitle());
        }
    }

    private void createCollection() {
        // Preparing clubs collection (the children, I guess)
        mClubCollection = new LinkedHashMap<>();

        for (Club c: mClubData) {
            String[] s = new String[2];
            s[0] = "Sponsor(s): " + c.getSponsor();
            s[1] = "Description: " + c.getText();
            loadChild(s);

            mClubCollection.put(c.getTitle(), mChildList);
        }
    }

    private void loadChild(String[] clubsData) {
        mChildList = new ArrayList<>();
        mChildList.addAll(Arrays.asList(clubsData));
    }

    /* This will populate the mClubData ArrayList with the mClubData we want to display. This may
     eventually get more complicated (if we require lots of different mClubData other than
     text to be shown. Additionally, this will eventually grab the information from a server.
     */
    private void populateData() {
        /* This text was generated with an Android Studio plugin known as Insert Dummy Text. That
         fact is completely useless but nevertheless, it's a good plugin and I recommend it. I
         add a new line ( + "\n" to each String to ensure it doesn't get cut off. This may mess
         things up of the String is only one line though, so we'll see what happens.
         */

        /* populateData() is called every time onCreateView() is called by an clubFragment.
         This happens fairly often. Effectively, with the way RecyclerView works and all, it happens
         a lot. That means that every single time populateData is called, all of this the mClubData below
         is re-added to the mClubData ArrayList. If I neglect to clear the ArrayList (I ensure that it isn't
         null to avoid a NullPointerException), there will be duplicated mClubData in the ArrayList. Android
         obviously doesn't know any better than to create extra Cards out of this duplicated mClubData, resulting
         in lots and lots of cards with the exact same mClubData. Clearing the ArrayList each time the populateData()
         method is called ensures that there aren't any duplicates. Whether or not there's a better way to do this
         is beyond me at the moment, but this works currently and I'm fine with that.
         */
        if (mClubData != null) {
            mClubData.clear();
        }

        new DownloadClubs().execute(mDefaultSpreadsheetURL);
    }


    /**
     * Club class to store all mClubData pertaining to what might be
     * displayed or associated with any given Club. This implementation
     * is subject to change at any point, as a better methodology may be discovered.
     */
    class Club {

        private String title;           // The club's name
        private String text;            // The club's textual information
        private String sponsor;          // The club's sponsor
        private int iconSource;         // The source for the club's icon in format R.id.XYZ
        private int imageSource;        // In the format R.id.XYZ

        /**
         * Full (or almost full if more fields are added and I forget to change this comment) constructor
         * for an club
         *
         * @param title the club's title
         * @param text the body informational text of the club
         * @param sponsor the sponsor/poster of the club
         * @param iconSource source for the club's icon's source in the format R.id.XYZ
         * @param imageSource source for the club's image's source in the format R.id.XYZ
         */
        public Club(String title, String text, String sponsor, int iconSource, int imageSource) {
            this.title = title;
            this.text = text;
            this.sponsor = sponsor;
            this.iconSource = iconSource;
            this.imageSource = imageSource;
        }

        /**
         * Instantiates a new club with a title, text, and image.
         *
         * @param title  the club's title
         * @param text   the text-based information for the club
         * @param source the source for the (optional) image in the format R.id.XYZ
         */

        public Club(String title, String text, int source) {
            this.title = title;
            this.text = text;
            imageSource = source;
        }

        /**
         * Instantiates a new club with a title, text.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         *
         * @param title  the club's title
         * @param text   the text-based information for the club
         */

        public Club(String title, String text) {
            this.title = title;
            this.text = text;
            imageSource = Integer.MIN_VALUE;
        }

        /**
         * Instantiates a new club with text, an image.
         *
         * @param text   the text-based information for the club
         * @param source the source for the (optional) image in the format R.id.XYZ
         */

        public Club(String text, int source) {
            this.text = text;
            imageSource = source;
        }

        /**
         * Instantiates a new club with just the text.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         * @param text the text-based information for the club
         * @param date the date of the club
         */
        public Club(String text, Date date) {
            this.text = text;
            imageSource = Integer.MIN_VALUE;
        }

        /**
         * Instantiates a new club with just an image and a date.
         *
         * @param source the source for the (optional) image in the format R.id.XYZ
         * @param date   the date of the club
         */
        public Club(int source, Date date) {
            imageSource = source;
        }

        /**
         * Instantiates a new club with just an image.
         *
         * @param source the source for the (optional) image in the format R.id.XYZ
         */
        public Club(int source) {
            imageSource = source;
        }

        /**
         * Instantiates a new club with just text.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         * @param text the text for the club.
         */
        public Club(String text) {
            this.text = text;
            imageSource = Integer.MIN_VALUE;
        }

        /**
         * Gets image source.
         *
         * @return the club's image source
         */
        public int getImageSource() {
            return imageSource;
        }

        /**
         * Sets image source.
         *
         * @param imageSource the new image source in the form R.id.XYZ
         */
        public void setImageSource(int imageSource) {
            this.imageSource = imageSource;
        }

        /**
         * Gets text.
         *
         * @return the club's text
         */
        public String getText() {
            return text;
        }

        /**
         * Sets text.
         *
         * @param text the new text value
         */
        public void setText(String text) {
            this.text = text;
        }

        /**
         * Gets title.
         *
         * @return the club's title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets title.
         *
         * @param title the new club title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Get method for the club's sponsor.
         *
         * @return the sponsor of the club
         */
        public String getSponsor() {
            return sponsor;
        }

        /**
         * Setter for the club's sponsor
         *
         * @param newSponsor new sponsor for club
         */
        public void setSponsor(String newSponsor) {
            sponsor = newSponsor;
        }

        @Override
        public boolean equals(Object obj) {
            // If obj is null, return false
            if (obj == null) {
                return false;
            }

            // clazz.isAssignableFrom(Foo.class) returns true if the
            // clazz object is a superclass or superinterface of Foo
            if (!Club.class.isAssignableFrom(obj.getClass())) {
                return false;
            }

            // Check to see if all necessary variables are equal or not
            final Club objPerson = (Club) obj;
            if (this.imageSource != objPerson.imageSource) {
                return false;
            }
            if ((this.text == null) ? (objPerson.text != null) : !this.text.equals(objPerson.text)) {
                return false;
            }
            if ((this.title == null) ? (objPerson.title != null) : !this.title.equals(objPerson.title)) {
                return false;
            }
            return true;
        }

        /**
         * Converts club to String form in the format
         * club: club_TITLE, club_TEXT, club_IMAGE_SOURCE
         */
        @Override
        public String toString() {
            return "Club: " + title + ", " + text + ", " + ", " + imageSource;
        }
    }

    /**
     * Created by Benjamin on 5/22/2016.
     */
    public class DownloadClubs extends AsyncTask<String, Void, String> {

        public DownloadClubs() {

        }

        @Override
        protected String doInBackground(String... params) {
            // params[0] is the url
            try {
                return downloadContent(params[0]);
            } catch (IOException e) {
                return "Unable to download the requested page...";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            /*
            Remove the unneeded parts of the download and construct a JSON object. If we don't do this
            part, we would have a bunch of unnecessary data around the data we want. We have to cut
            that out so we can actually parse everything.
             */
            Log.d(TAG, result);
            int start = result.indexOf("{", result.indexOf("{") + 1);
            int end = result.lastIndexOf("}");

            String resultFromSub = result.substring(start, end);

            try {
                JSONObject table = new JSONObject(resultFromSub);
                parseDownload(table);
            } catch (JSONException e) {
                Log.e(TAG, "JSONException caught onPostExecute", e);
            }

            createGroupList();
            createCollection();
            mExpandableListAdapter.updateData(mGroupList, mClubCollection);
            mExpandableListAdapter.notifyDataSetChanged();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mExpandableListAdapter.notifyDataSetChanged();                // Notify the adapter that the data changed

                    Toast.makeText(getActivity(), "Clubs loaded successfully (probably)", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private String downloadContent(String urlStr) throws IOException {
            InputStream inputStream = null;

            try {
                // Create a URL object with the URL of the spreadsheet as a parameter passed to this method.
                URL url = new URL(urlStr);
                // Create a new HTTPUrlConnection with the URL.
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // The following two methods' parameters are in milliseconds. The first method defines
                // how long we shall try to read the data before timing out. The second is how long we should
                // try to actually connect before timing out.
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);

                /*
                We are going to use an HTTP GET request. GET requests data from a specified resource.
                You may have heard of "POST". POST submits data to be processed to a specified resource,
                 which obviously isn't really what we're wanting to do here.
                */
                connection.setRequestMethod("GET");
                // This sets the value of the doInputField for the URLConnection to what we specify.
                // A URL can be used for input or output. We're using it for input.
                connection.setDoInput(true);

                // Connect! Hooray!
                connection.connect();
                // Grab the response code.
                int responseCode = connection.getResponseCode();
                // Assign our InputStream object to the connect as an input stream so we can process the data.
                inputStream = connection.getInputStream();

                return convertStreamToString(inputStream);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }

        private String convertStreamToString(InputStream is) {
            // Convert the input to a String with a buffered reader.
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            // Go line-by-line until we've been through the whole thing.
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }

        private void parseDownload(JSONObject results) {
            try {
                JSONArray rows = results.getJSONArray("rows");

                for (int i = 0; i < rows.length(); i++) {
                    JSONObject row = rows.getJSONObject(i);
                    JSONArray columns = row.getJSONArray("c");

                    /*
                    Parse the JSONArray. Basically, we have rows and columns. The data in the
                    spreadsheet is organized by column. At the time that Ben is writing this comment,
                    the columns are position, author, date, show_flag, title, and contents in that
                    order. Therefore, we can just grab the data we want by going to the column its
                    stored in, since we know the columns. We are going through each row of the spreadsheet
                    with the for-loop above. Each iteration of the for-loop goes to the next row, ensuring we
                    get all of the data stored in the spreadsheet.
                     */
                    String sponsors = columns.getJSONObject(0).getString("v");
                    String clubName = columns.getJSONObject(1).getString("v");
                    String clubDesc = columns.getJSONObject(2).getString("v");
                    int show_flag = columns.getJSONObject(3).getInt("v");

                    // Parameters for constructor are:
                    // String title, String text, String author, int iconSource, int imageSource, Date announcementDate
                    Club club = new Club(clubName, clubDesc, sponsors, -1, -1);

                    // Log.i(TAG, "Club created: " + club.toString());

                    // If we want to show this announcement, add it to the data ArrayList.
                    if (1 == 1) {
                        mClubData.add(club);
                    }
                }

            } catch (JSONException e) {
                Log.e(TAG, "JSONException caught whilst parsing", e);
            }
        }
    }
}
