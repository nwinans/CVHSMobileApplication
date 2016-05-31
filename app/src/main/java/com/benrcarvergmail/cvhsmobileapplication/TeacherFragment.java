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
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 3Robotics on 5/20/2016.
 */
public class TeacherFragment extends Fragment {

    private ArrayList<Teacher> myData;
    private List<String> mGroupList;
    private List<String> mChildList;
    private Map<String, List<Teacher>> mTeacherCollection;
    TeacherExpandableListAdapter mExpandableListAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final String defaultSpreadsheetUrl = "https://spreadsheets.google.com/tq?key=1ck-Qzizhg5VxNNP0gPwU193yE1QwE7oQD-SMSV4kX1k";

    private static final String TAG = "TeachersFragment";

    /**
     * Instantiates a new Club fragment.
     */
    public TeacherFragment() {
        // Instantiate the mData ArrayList so we may populate it during onCreateView()
        myData = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstantState){
        super.onCreate(savedInstantState);
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_clubs, container, false);

        // Set up the List Adapter prior to calling populateData()
        mGroupList = new ArrayList<>();
        mTeacherCollection = new LinkedHashMap<>();
        mExpandableListAdapter = new TeacherExpandableListAdapter(getActivity(), mGroupList, mTeacherCollection);

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

    private void refreshContent() {
        populateData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void createGroupList() {
        mGroupList = new ArrayList<>();
        for (int i = 0; i < myData.size(); i++) {
            // Log.i(TAG,(i + ": " + mData.get(i).getTitle()));
            mGroupList.add(myData.get(i).getName());
        }
    }

    private void createCollection() {
        // Preparing clubs collection (the children, I guess)
        String[] randomData = {"Dank Memes", "Click for more memes", "Club memes"};
        String[] defaultData = {"Mr. Small", "The robotics club is a club about robotics", "Memes are Great"};
        mTeacherCollection = new LinkedHashMap<>();
        ArrayList<Teacher> mathTeachers = new ArrayList<Teacher>();
        ArrayList<Teacher> scienceTeachers = new ArrayList<Teacher>();
        ArrayList<Teacher> socialStudiesTeachers = new ArrayList<Teacher>();
        ArrayList<Teacher> englishTeachers = new ArrayList<Teacher>();
        ArrayList<Teacher> physicalEducationTeachers = new ArrayList<Teacher>();
        ArrayList<Teacher> musicTeachers = new ArrayList<Teacher>();
        ArrayList<Teacher> cteTeachers = new ArrayList<Teacher>();
        ArrayList<Teacher> artTeachers = new ArrayList<Teacher>();
        mGroupList.add("Mathematics");
        mGroupList.add("Science");
        mGroupList.add("Social Studies");
        mGroupList.add("English");
        mGroupList.add("Physical Education");
        mGroupList.add("Music");
        mGroupList.add("Career Technical Education");
        mGroupList.add("Art");

        for(int i = 0; i < myData.size() ; i++){
            Teacher temp = myData.get(i);
            if(temp.getDepartment().equals("Mathematics")){
                mathTeachers.add(temp);
            }
            if(temp.getDepartment().equals("Science")){
                scienceTeachers.add(temp);
            }
            if(temp.getDepartment().equals("Social Studies")){
                socialStudiesTeachers.add(temp);
            }
            if(temp.getDepartment().equals("English")){
                englishTeachers.add(temp);
            }
            if(temp.getDepartment().equals("Physical Education")){
                physicalEducationTeachers.add(temp);
            }
            if(temp.getDepartment().equals("Music")){
                musicTeachers.add(temp);
            }
            if(temp.getDepartment().equals("CTE")){
                cteTeachers.add(temp);
            }
            if(temp.getDepartment().equals("Art")){
                artTeachers.add(temp);
            }

        }
            mTeacherCollection.put("Mathematics", mathTeachers);
            mTeacherCollection.put("Science", scienceTeachers);
            mTeacherCollection.put("Social Studies", socialStudiesTeachers);
            mTeacherCollection.put("English", englishTeachers);
            mTeacherCollection.put("Physical Education", physicalEducationTeachers);
            mTeacherCollection.put("Music", musicTeachers);
            mTeacherCollection.put("Career Technical Education", cteTeachers);
            mTeacherCollection.put("Art", artTeachers);

    }

       /* for (String club : mGroupList) {
            Log.i(TAG, "Club: " + club);
            if (club.equals("Robotics")) {
                loadChild(defaultData);
            } else {
                loadChild(randomData);
            }

        }
    }
    mTeacherCollection.put(club, mChildList);   */

    private void loadChild(String[] TeachersData) {
        mChildList = new ArrayList<>();
        mChildList.addAll(Arrays.asList(TeachersData));
    }

    /* This will populate the mData ArrayList with the mData we want to display. This may
     eventually get more complicated (if we require lots of different mData other than
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
         a lot. That means that every single time populateData is called, all of this the mData below
         is re-added to the mData ArrayList. If I neglect to clear the ArrayList (I ensure that it isn't
         null to avoid a NullPointerException), there will be duplicated mData in the ArrayList. Android
         obviously doesn't know any better than to create extra Cards out of this duplicated mData, resulting
         in lots and lots of cards with the exact same mData. Clearing the ArrayList each time the populateData()
         method is called ensures that there aren't any duplicates. Whether or not there's a better way to do this
         is beyond me at the moment, but this works currently and I'm fine with that.
         */
        if (myData != null) {
            myData.clear();
        }
        new DownloadTeachers().execute(defaultSpreadsheetUrl);
    }






     class DownloadTeachers extends AsyncTask<String, Void, String> {

        public DownloadTeachers() {

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
            mExpandableListAdapter.updateData(mGroupList, mTeacherCollection);
            mExpandableListAdapter.notifyDataSetChanged();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mExpandableListAdapter.notifyDataSetChanged();                // Notify the adapter that the data changed

                    Toast.makeText(getActivity(), "Teachers loaded successfully (probably)", Toast.LENGTH_SHORT).show();
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
                    spreadsheet is organized by column. At the time that Justin is writing this comment,
                    the columns are name, courses, email in that
                    order. Therefore, we can just grab the data we want by going to the column its
                    stored in, since we know the columns. We are going through each row of the spreadsheet
                    with the for-loop above. Each iteration of the for-loop goes to the next row, ensuring we
                    get all of the data stored in the spreadsheet.
                     */
                    String TeacherName = columns.getJSONObject(0).getString("v");
                    String email = columns.getJSONObject(2).getString("v");
                    String department = columns.getJSONObject(1).getString("v");

                    // Parameters for constructor are:
                    // String title, String text, String author, int iconSource, int imageSource, Date announcementDate
                    Teacher teach = new Teacher(TeacherName, email, department);

                    // Log.i(TAG, "Teacher created: " + teach.toString());

                    // If we want to show this announcement, add it to the data ArrayList.
                    if (1 == 1) {
                        myData.add(teach);
                    }
                }

            } catch (JSONException e) {
                Log.e(TAG, "JSONException caught whilst parsing", e);
            }
        }
    }
}

