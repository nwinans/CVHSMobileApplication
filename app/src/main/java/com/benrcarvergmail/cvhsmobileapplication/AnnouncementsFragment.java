package com.benrcarvergmail.cvhsmobileapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * The type Announcements fragment.
 */
public class AnnouncementsFragment extends Fragment {

    private ArrayList<Announcement> data;                       // List of announcements to be displayed

    private EditText mEditIPAddress, mEditPort;                 // References to the IP Address EditText field and the Port EditText field

    private final String defaultServerAddress = "clogicd.com/announcements.txt";  // Default IP address for the server/database
    private final String defaultServerPort = "";            // Default port for the server/database
    private final String defaultSpreadsheetUrl = "https://spreadsheets.google.com/tq?key=15norrPRiVtM1vVSmCtSTzDQhxrSpSX6QrruIvNOCeS0";

    private AnnouncementsRecyclerViewAdapter mAdapter;          // Reference to the RecyclerViewAdapter
    private AnimatedRecyclerView mAnimatedRecyclerView;         // Reference to Ben's extension of RecyclerView

    private SwipeRefreshLayout mSwipeRefreshLayout;              // Swipe Refresh Layout for Announcements

    private static final String TAG = "AnnouncementsFragment";  // TAG

    /**
     * Instantiates a new Announcements fragment.
     */
    public AnnouncementsFragment() {
        // Instantiate the data ArrayList so we may populate it during onCreateView()
        data = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView() for AnnouncementsFragment called.");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_announcements, container, false);
        // Create object reference to the RecyclerView created in fragment_announcements.xml
        mAnimatedRecyclerView = (AnimatedRecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        // Ensure that its size is fixed (unchanging)
        mAnimatedRecyclerView.setHasFixedSize(true);
        // Create an adapter for the RecyclerView, passing the ArrayList of text we want displayed
        mAdapter = new AnnouncementsRecyclerViewAdapter(data);
        // Populate the data ArrayList. We currently do not utilize the boolean return type
        populateData(false, defaultServerAddress, defaultServerPort);
        // Set the RecyclerView's adapter to the one we just created
        mAnimatedRecyclerView.setAdapter(mAdapter);
        // Instantiate the references to the IP Address and Port EditText fields
        // Notice we are calling rootView.findViewById(), not rv.findViewById().
        // This is because the EditText fields are NOT a part of the recycler view (rv). They are
        // a part of the actual announcements fragment, so we use the rootView object.
        mEditIPAddress = (EditText) rootView.findViewById(R.id.ip_address_field);
        mEditPort = (EditText) rootView.findViewById(R.id.port_field);
        // Instantiate a reference to the button that refreshes the announcements and add an
        // onClick listener to that button. Eventually, we will refresh by scrolling or some
        // other nicer implementation but this works for now. Notice we are calling rootView.findViewById(),
        // not rv.findViewById(). This is because the button is NOT a part of the recycler view (rv). It is
        // a part of the actual announcements fragment, so we use the rootView object.
        final Button refreshAnnouncementsButton = (Button) rootView.findViewById(R.id.button_refresh_connection);
        refreshAnnouncementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the IP and the port entered in the text fields and pass
                // them to the populateData() method. Notice we are calling toString() after
                // we call getText(). This is because getText() in this case returns an Editable,
                // as we are calling getText() on EditText fields. In order to convert the Editable
                // to a String, we must call toString() after we call getText().
                String IPAddress = mEditIPAddress.getText().toString();
                String port = mEditPort.getText().toString();

                // Currently, Ben has it set up such that if NO IP address or port is specified, the
                // app will assume the default addresses are to be used.
                if (IPAddress.equals("") && port.equals("")) {
                    populateData(true, defaultServerAddress, defaultServerPort);
                } else if (IPAddress.equals("")) {
                    Toast.makeText(getActivity(), "Please specify an IP address", Toast.LENGTH_SHORT).show();
                }  else {
                    populateData(true, IPAddress, port);
                }
            }
        });
        // A LinearLayoutManager is a A RecyclerView.LayoutManager
        // implementation which provides similar functionality to ListView.
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mAnimatedRecyclerView.setLayoutManager(llm);

        mSwipeRefreshLayout = (SwipeRefreshLayout)
                rootView.findViewById(R.id.swipe_refresh_layout_announcements);

        // The refresh loading icon will cycle thru these colors
        // mSwipeRefreshLayout.setColorSchemeColors(R.color.refreshBlue, R.color.refreshYellow, R.color.refreshRed);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
            }
        });

        return rootView;
    }

    // Refreshes the Announcements by reconnecting to the server and pulling new data
    private void refreshContent() {
        populateData(true, defaultServerAddress, defaultServerPort);
        // ToDo: Potentially figure out a way to make this last a little longer because the refresh animation stops too soon so it appears something isn't working for ~1 second.
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /* This will populate the data ArrayList with the data we want to display. This may
     eventually get more complicated (if we require lots of different data other than
     text to be shown. Additionally, this will eventually grab the information from a server.

     This method is kind of a redundant middle-man between the AsyncTask and the rest of the program.
     It may eventually be removed due to said redundancy but for now, I'm leaving it.
     */
    private void populateData(boolean doAnimate, String... args) {
        /* populateData() is called every time onCreateView() is called by an AnnouncementFragment.
         This happens fairly often. Effectively, with the way RecyclerView works and all, it happens
         a lot. That means that every single time populateData is called, all of this the data below
         is re-added to the data ArrayList. If I neglect to clear the ArrayList (I ensure that it isn't
         null to avoid a NullPointerException), there will be duplicated data in the ArrayList. Android
         obviously doesn't know any better than to create extra Cards out of this duplicated data, resulting
         in lots and lots of cards with the exact same data. Clearing the ArrayList each time the populateData()
         method is called ensures that there aren't any duplicates. Whether or not there's a better way to do this
         is beyond me at the moment, but this works currently and I'm fine with that.
         */
        if (data != null) {
            data.clear();
            mAdapter.notifyDataSetChanged();
        }

        // Offload the network connection to the AsyncTask
        // new RetrieveAnnouncementsTask().execute(args);
        new DownloadAnnouncements().execute(defaultSpreadsheetUrl);

        // Only animate if we actually want to animate
        if (doAnimate) {
            mAnimatedRecyclerView.setDoAnimate(true);      // Notify the AnimatedRecyclerView that it is okay to animate
        } else {
            mAnimatedRecyclerView.setDoAnimate(false);      // Notify the AnimatedRecyclerView that it is okay to animate
        }
    }


    /**
     * Announcement class to store all data pertaining to what might be
     * displayed or associated with any given announcement. This implementation
     * is subject to change at any point, as a better methodology may be discovered.
     *
     * There are a lot of possible future features to announcements. The possibilities
     * include: customisable icon, Announcement type (club, sports, general, weather, etc.),
     * Announcement caster (dedicated field pertaining to whom the announcement is from), etc.
     */
    class Announcement {

        private String title;           // The announcement's title
        private String text;            // The announcement's textual information
        private String author;          // The announcement's author/poster
        private int iconSource;         // The source for the announcement's icon in format R.id.XYZ
        private int imageSource;        // In the format R.id.XYZ
        private Date announcementDate;  // The date the announcement was posted.

        /**
         * Full (or almost full if more fields are added and I forget to change this comment) constructor
         * for an announcement
         *
         * @param title the announcement's title
         * @param text the body informational text of the announcement
         * @param author the author/poster of the announcement
         * @param iconSource source for the announcement's icon's source in the format R.id.XYZ
         * @param imageSource source for the announcement's image's source in the format R.id.XYZ
         * @param announcementDate date for the announcement in the form of a import java.util.Date object
         */
        public Announcement(String title, String text, String author, int iconSource, int imageSource, Date announcementDate) {
            this.title = title;
            this.text = text;
            this.author = author;
            this.iconSource = iconSource;
            this.imageSource = imageSource;
            this.announcementDate = announcementDate;
        }

        /**
         * Instantiates a new Announcement with a title, text, an image, and a date.
         *
         * @param title  the announcement's title
         * @param text   the text-based information for the Announcement
         * @param source the source for the (optional) image in the format R.id.XYZ
         * @param date   the date of the announcement
         */

        public Announcement(String title, String text, int source, Date date) {
            this.title = title;
            this.text = text;
            imageSource = source;
            announcementDate = date;
        }

        /**
         * Instantiates a new Announcement with a title, text, and a date.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         *
         * @param title  the announcement's title
         * @param text   the text-based information for the Announcement
         * @param date   the date of the announcement
         */

        public Announcement(String title, String text, Date date) {
            this.title = title;
            this.text = text;
            imageSource = Integer.MIN_VALUE;
            announcementDate = date;
        }

        /**
         * Instantiates a new Announcement with text, an image, and a date.
         *
         * @param text   the text-based information for the Announcement
         * @param source the source for the (optional) image in the format R.id.XYZ
         * @param date   the date of the announcement
         */

        public Announcement(String text, int source, Date date) {
            this.text = text;
            imageSource = source;
            announcementDate = date;
        }

        /**
         * Instantiates a new Announcement with text, an image, and a date.
         *
         * @param text   the text-based information for the Announcement
         * @param title  the title of the announcement
         * @param author the author of the announcement
         * @param date   the date of the announcement
         */

        public Announcement(String title, String author, String text, Date date) {
            this.text = text;
            this.title = title;
            this.author = author;
            announcementDate = date;
        }

        /**
         * Instantiates a new Announcement with just the text and a date.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         * @param text the text-based information for the Announcement
         * @param date the date of the announcement
         */
        public Announcement(String text, Date date) {
            this.text = text;
            announcementDate = date;
            imageSource = Integer.MIN_VALUE;
        }

        /**
         * Instantiates a new Announcement with just an image and a date.
         *
         * @param source the source for the (optional) image in the format R.id.XYZ
         * @param date   the date of the announcement
         */
        public Announcement(int source, Date date) {
            imageSource = source;
            announcementDate = date;
        }

        /**
         * Instantiates a new Announcement with just an image. The date is
         * automatically assigned to the date and time of the method call.
         *
         * @param source the source for the (optional) image in the format R.id.XYZ
         */
        public Announcement(int source) {
            imageSource = source;
            announcementDate = new Date();
        }

        /**
         * Instantiates a new Announcement with just text. The date is
         * automatically assigned to the date and time of the method call.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         * @param text the text for the Announcement.
         */
        public Announcement(String text) {
            this.text = text;
            imageSource = Integer.MIN_VALUE;
            announcementDate = new Date();
        }

        /**
         * Gets announcement date.
         *
         * @return the announcement date
         */
        public Date getAnnouncementDate() {
            return announcementDate;
        }

        /**
         * Sets announcement date.
         *
         * @param announcementDate the new announcement date
         */
        public void setAnnouncementDate(Date announcementDate) {
            this.announcementDate = announcementDate;
        }

        /**
         * Gets image source.
         *
         * @return the Announcement's image source
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
         * @return the Announcement's text
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
         * @return the Announcement's title
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets title.
         *
         * @param title the new Announcement title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * Get method for the announcement's author.
         *
         * @return the author of the announcement
         */
        public String getAuthor() {
            return author;
        }

        /**
         * Setter for the announcement's author
         *
         * @param newAuthor new author for announcement
         */
        public void setAuthor(String newAuthor) {
            author = newAuthor;
        }

        @Override
        public boolean equals(Object obj) {
            // If obj is null, return false
            if (obj == null) {
                return false;
            }

            // clazz.isAssignableFrom(Foo.class) returns true if the
            // clazz object is a superclass or superinterface of Foo
            if (!Announcement.class.isAssignableFrom(obj.getClass())) {
                return false;
            }

            // Check to see if all necessary variables are equal or not
            final Announcement objPerson = (Announcement) obj;
            if ((this.announcementDate == null) ? (objPerson.announcementDate != null) : !this.announcementDate.equals(objPerson.announcementDate)) {
                return false;
            }
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
         * Converts Announcement to String form in the format
         * Announcement: ANNOUNCEMENT_TITLE, ANNOUNCEMENT_TEXT, ANNOUNCEMENT_DATE, ANNOUNCEMENT_IMAGE_SOURCE
         */
        @Override
        public String toString() {
            return "Announcement: " + title + ", " + text + ", " + announcementDate.toString() + ", " + imageSource;
        }

        /**
         * This method creates a substring from the announcement's text to be used as a intro of
         * sorts. Basically, this generated String can be used to display on each CardView when
         * the CardView isn't expanded. Upon expansion, the CardView will display the full text
         * of the announcement.
         *
         * @return a substring of the announcement
         */
        public String generateIntro() {
            // Log.i(TAG, "generateIntro() called!");
            if (text.length() == 0) {
                return "...";
            } else {
                // Ensure that the text is long to generate an 80-character substring
                if (text.length() >= 80) {
                    return text.substring(0, 80) + "...";
                } else {
                    return text; // The text is already short enough.
                }
            }
        }
    }

    // This probably won't be used much anymore, if ever.
    private class RetrieveAnnouncementsTask extends AsyncTask<String, Void, Void> {

        /**
         *
         * @param URLInformation An Array of information to build the URL. URLInformation[0]
         *                       is the IP Address while URLInformation[1] is the port to use.
         *
         */
        @Override
        protected Void doInBackground(String... URLInformation) {
            try {
                URL url;
                // If the user did not specify a port, connect directly to whatever they specified. Otherwise, build the URL with their components.
                if(URLInformation[1].equals("")) {
                    url = new URL("http://" + URLInformation[0]);
                } else {
                    url = new URL("http://" + URLInformation[0] + ":" + URLInformation[1] + "/announcements.txt");
                }
                Log.i(TAG, "URL: " + url.toString());
                // Read all the text returned by the Server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;               // Currently in the form: title*author*message*date
                                           // The date is in the format MMDDYYYY
                String delim = "[*]+";     // Delimiter for parsing the String

                // Assign line to the next line from the file and check if it's null. If it isn't,
                // we will do something with it. If it is, that's the end of all the announcements.
                while((line = in.readLine()) != null) {
                    // Split up the line into sections and store each section in the tokens Array
                    String[] tokens = line.split(delim);
                    int day = Integer.parseInt(tokens[3].substring(0, 2));      // Grab the day portion of the date
                    int month = Integer.parseInt(tokens[3].substring(2,4));     // Grab the month portion of the date
                    int year = Integer.parseInt(tokens[3].substring(4));        // Grab the year portion of the date
                    // Create a GregorianCalendar object for the date. We create a GregorianCalender instead of a Date because
                    // much of the date code is depricated. We can, however, call .getTime() on the GregorianCalender object, which
                    // will return a date. We can pass the returned date an announcement, which knows how to properly handle and display dates.
                    GregorianCalendar gregorianDate = new GregorianCalendar(year, month, day);
                    Announcement a = new Announcement(tokens[0], tokens[1], tokens[2], gregorianDate.getTime());
                    Log.i(TAG, a.toString());
                    data.add(a);
                }

                in.close();

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException", e);
                /*
                Because we are not in the main UI thread but instead are in an AsyncTask, we cannot
                call Toast.makeText() like normal. Instead, if we have to get the current activity
                and tell Android to run this on the UI thread. We do this by calling getActivity() to
                get the activity and then runOnUiThread(), passing a new Runnable() to the runOnUiThread()
                method. We define the Runnable's run method, telling it to create a Toast message for us.
                 */
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Invalid IP address", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                Log.e(TAG, "IOException", e);
                /*
                Because we are not in the main UI thread but instead are in an AsyncTask, we cannot
                call Toast.makeText() like normal. Instead, if we have to get the current activity
                and tell Android to run this on the UI thread. We do this by calling getActivity() to
                get the activity and then runOnUiThread(), passing a new Runnable() to the runOnUiThread()
                method. We define the Runnable's run method, telling it to create a Toast message for us.
                 */
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Error reading data from the server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }

        // Called after the AsyncTask finishes executing the doInBackground() method
        @Override
        protected void onPostExecute(Void v) {
            /*
            Similar to the above methods in which we were creating Toast messages, we have to go back to
            the UI thread for this. Whenever we modify the data set the RecyclerView is using, we have to
            notify its adapater that the data has changed. We do this by calling mAdapater.notifyDataSetChanged().
            In order for this to take effect, however, it has to be done from the main UI thread, so we get the activity,
            tell Android to run this on the UI thread, create a new Runnable, and define the run method to do as we'd like.

            We also create a Toast message stating that the new data has been pulled (refreshed).
             */
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();                // Notify the adapter that the data changed

                    Toast.makeText(getActivity(), "Refresh complete", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Created by Benjamin on 5/22/2016.
     */
    public class DownloadAnnouncements extends AsyncTask<String, Void, String> {

        public DownloadAnnouncements() {

        }

        @Override
        protected String doInBackground(String... params) {
            // params[0] is the url
            try {
                return downloadContent(params[0]);
            } catch (IOException e) {
                Log.e(TAG, "IOException while doInBackground", e);
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
                e.printStackTrace();
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.notifyDataSetChanged();                // Notify the adapter that the data changed

                    Toast.makeText(getActivity(), "Refresh complete", Toast.LENGTH_SHORT).show();
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
                    int position = columns.getJSONObject(0).getInt("v");
                    String author = columns.getJSONObject(1).getString("v");
                    String date = columns.getJSONObject(2).getString("v");
                    int show_flag = columns.getJSONObject(3).getInt("v");
                    String title = columns.getJSONObject(4).getString("v");
                    String content = columns.getJSONObject(5).getString("v");

                    /*
                     We construct a date object utilizing a SimpleDateFormat object. The date is
                     stored in the spreadsheet in the form of MonthMonth/DayDay/YearYearYearYear,
                     such as 04/20/2015. Knowing this, we create a date object with the format
                     MM/dd/YYYY and use that to parse the dates we pulled from the spreadsheet.
                     We have to parse the dates because they're stored as Strings, basically.
                     */

                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    Date parsedDate = format.parse(date);

                    // Parameters for constructor are:
                    // String title, String text, String author, int iconSource, int imageSource, Date announcementDate
                    Announcement announcement = new Announcement(title, content, author, -1, -1, parsedDate);

                    // If we want to show this announcement, add it to the data ArrayList.
                    if (show_flag == 1) {
                        data.add(announcement);
                    }
                }

            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
