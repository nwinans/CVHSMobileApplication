package com.benrcarvergmail.cvhsmobileapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;

import java.util.ArrayList;
import java.util.Date;

/**
 * The type Announcements fragment.
 */
public class AnnouncementsFragment extends Fragment {

    private ArrayList<Announcement> data;

    /**
     * Instantiates a new Announcements fragment.
     */
    public AnnouncementsFragment() {
        // Instantiate the data ArrayList so we may populate it during onCreateView()
        data = new ArrayList<Announcement>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_announcements, container, false);
        // Create object reference to the RecyclerView created in fragment_announcements.xml
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        // Ensure that its size is fixed (unchanging)
        rv.setHasFixedSize(true);
        // Populate the data ArrayList. We currently do not utilize the boolean return type
        populateData();
        // Create an adapter for the RecyclerView, passing the ArrayList of text we want displayed
        MyRecyclerViewAdapater adapter = new MyRecyclerViewAdapater(data);
        // Set the RecyclerView's adapater to the one we just created
        rv.setAdapter(adapter);

        // A LinearLayoutManager is a A RecyclerView.LayoutManager
        // implementation which provides similar functionality to ListView.
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    // This will populate the data ArrayList with the data we want to display. This may
    // eventually get more complicated (if we require lots of different data other than
    // text to be shown. Additionally, this will eventually grab the information from a server.
    private boolean populateData() {
        // This text was generated with an Android Studio plugin known as Insert Dummy Text. That
        // fact is completely useless but nevertheless, it's a good plugin and I recommend it. I
        // add a new line ( + "\n" to each String to ensure it doesn't get cut off. This may mess
        // things up of the String is only one line though, so we'll see what happens.

        // Add each new announcement to the ArrayList. We are creating the Announcements when we pass them.
        data.add(new Announcement("Test Announcement #1",
                "Chilled celery can be made melted by seasoning with white wine. " +
                    "Turkey mousse has to have a delicious, sour pickles component." + "\n",
                        Integer.MIN_VALUE,
                            new Date()));
        data.add(new Announcement("Test Announcement #2",
                "Cook iced lettuces in a bottle with soy sauce for about an hour to increase their viscosity." +
                    "Remember: scraped melon tastes best when peeled in a frying pan varnished with dill." + "\n",
                        Integer.MIN_VALUE,
                            new Date()));
        data.add(new Announcement("Test Announcement #3",
                "After warming the chickpeas, enamel avocado, rhubarb and maple syrup " +
                "with it in a plastic bag. Toast two chocolates, rice, and marmalade in a large " +
                "frying pan over medium heat, cook for a dozen minutes and soak with some " +
                "zucchini."+ "\n",
                    Integer.MIN_VALUE,
                        new Date()));
        data.add(new Announcement("Test Announcement #4",
               "All children like pressed raspberries in peanut sauce and woodruff." +
                    "Try draining paste rinseed with gold tequila, enameled with corn syrup."+ "\n",
                        Integer.MIN_VALUE,
                            new Date()));
        data.add(new Announcement("Test Announcement #5",
               "Mash peanut butter quickly, then mix with whiskey and serve thoroughly in pan." +
                    "Mash margarine smoothly, then mix with kefir and serve fairly in bottle." + "\n",
                        Integer.MIN_VALUE,
                            new Date()));

        return true; // May eventually return false if unable to pull data from server
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
        private int imageSource;        // In the format R.id.XYZ
        private Date announcementDate;  // The date the announcement was posted.

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
    }
}
