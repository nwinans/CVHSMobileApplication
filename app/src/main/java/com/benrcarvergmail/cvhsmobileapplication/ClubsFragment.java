package com.benrcarvergmail.cvhsmobileapplication;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cvillerobotics on 3/14/2016.
 */
public class ClubsFragment extends ListFragment {

    private ArrayList<Club> data;

    private static final String TAG = "ClubsFragment";

    /**
     * Instantiates a new Club fragment.
     */
    public ClubsFragment() {
        // Instantiate the data ArrayList so we may populate it during onCreateView()
        data = new ArrayList<Club>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_clubs, container, false);
        // Create object reference to the RecyclerView created in fragment_clubs.xml
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        // Ensure that its size is fixed (unchanging)
        rv.setHasFixedSize(true);
        // Populate the data ArrayList. We currently do not utilize the boolean return type
        populateData();
        // Create an adapter for the RecyclerView, passing the ArrayList of text we want displayed
        ClubRecyclerViewAdapter adapter = new ClubRecyclerViewAdapter(data);
        // Set the RecyclerView's adapater to the one we just created
        rv.setAdapter(adapter);

        // A LinearLayoutManager is a A RecyclerView.LayoutManager
        // implementation which provides similar functionality to ListView.
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }


    /* This will populate the data ArrayList with the data we want to display. This may
     eventually get more complicated (if we require lots of different data other than
     text to be shown. Additionally, this will eventually grab the information from a server.
     */
    private boolean populateData() {
        /* This text was generated with an Android Studio plugin known as Insert Dummy Text. That
         fact is completely useless but nevertheless, it's a good plugin and I recommend it. I
         add a new line ( + "\n" to each String to ensure it doesn't get cut off. This may mess
         things up of the String is only one line though, so we'll see what happens.
         */

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
        }

        // Add each new club to the ArrayList. We are creating the Club when we pass them.
        data.add(new Club("America's Leaders of Engineering Interest",
                "Club for people interested in engineering" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Amnesty International Club",
                "Support human rights in a peaceful manner" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Animal Welfare Club",
                "All animal lovers can come and participate in helping our furry friends"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Art Honor Society",
                "For students who excel in art and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Best Buddies",
                "A social club for building friendship with students with physical and intellectual impairments" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Business Honor Society",
                "For students who excel in the business field and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Catholic Students Assocation",
                "Examines the Catholic traditions and practices" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Chess Club",
                "Come and enjoy the game of chess" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Class of 2016",
                "For current seniors" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Class of 2017",
                "For current juniors" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Class of 2018",
                "For current sophomores" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Class of 2019",
                "For current freshmeat" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Coalition of Young Conservatives",
                "Examine the Conservative political agenda and help students participate in it " +
                        "more meaningfully" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("College Partnership Program",
                "College and career readiness activities, trips, and workshops for aspiring " +
                        "college students who are eligible for acceptance to the program" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Computer Programming",
                " " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Dance Team",
                "Performs routines and jazz, pop, kick, and hip hop at home football games and " +
                        "competes in regional dance team competitions during fall and winter" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("DECA",
                "Exposed students to business in work situations in marketing " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Discussion",
                " " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Eastern Anime and Culture Club",
                "Watch and complete project games of eastern culture animation" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Edge",
                "A fellowship organization enabling students to discuss questions and challenges" +
                        "of life" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("English Honor Society",
                "For students who excel in english and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Environmental Impact Club",
                "To make students aware of our environmental impact " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("FBLA",
                "To develop business and technology skills for careers"
                        +"in business"
                        +"Meets first Monday of each month RM 113"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Fellowship of Christian Athletes",
                "To share the gospel and grow as followers "
                        +"Students do not have to be athletes to attend"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("First Response Club",
                "Learn about how first response works " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("French Club",
                "To give students an opportunity to learn more about the culture "
                        +"of the French speaking world" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("French Honor Society",
                "For students who excel in french and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Future Medical Leaders of America",
                "Students interested in the medical field " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Gay Straight Alliance",
                "To advocate acceptance and tolerance in the community " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("German Club",
                "Students who are interested in German culture " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("German Honor Society",
                "For students who excel in german and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("G.I.R.L.S.",
                "Club to empower, motivate, and provide opportunities for all students "
                        +"with an emphasis on females"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("G.O.A.T.",
                "Learn the art of music, production, performing, and recording " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Hispanic Leadership Club",
                "Bringing Hispanic students for fellowship, leadership, and scholarship " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Ice Hockey Club",
                "Organizes skating competition and discusses ice skating " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("International Culinary",
                "Introduce and make food from all parts of the world " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("It's Academic",
                "Group of students who participate in trivia challenges in competitive environment " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Key Club",
                "Student organization that focuses on community service" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Korean Club",
                "Student organization that focuses on the Korean culture" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Latin Club",
                "Organization to support Latin students and enrich the classroom experience" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Latin Honor Society",
                "For students who excel in latin and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Locking Arms Group",
                "Students discuss social issues and possible solutions" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Math Honor Society",
                "For students who excel in math and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Mazza Club",
                " " + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Military support Club",
                "Support soldiers and their families" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Modal Judiciary",
                "Prepares students to compete in mock trials and moot courts" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Model UN",
                "Organization that competes in diplomatic debating and compromising" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Muslim Student Association",
                "Support Muslim students and friends with enrichment and fellowship activities" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("National Honor Society",
                "For students who excel in academics"+
                        "To qualify for NHS membership, students must be in grades 11 or 12 " +
                        "and have a cumulative GPA of 3.5 or higher" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Pinterest Club",
                "Students come together to express their creativity " +
                        "through projects found on Pinterest" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Red Cross Club",
                "Volunteer in donation drives and participate in disaster relief activites" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Robotics",
                "STEM development in robotics making and competition" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Rubik's Cube Club",
                "Club dedicated to the Rubik's Cube" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Science Honor Society",
                "For students who excel in science and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Science Olympiad Club",
                "Students participate in competitions testing the knowledge of all sciences" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Soap Making Club",
                "Join us to make nice smelling soap" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Social Studies Honor Society",
                "For students who excel in social studies and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Spanish Honor Society",
                "For students who excel in spanish and take interest in the subject"+ "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Step Team",
                "Join us to make rhythmic dance" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Speech and Debate",
                "For students interested in speech and debate" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Students Helping India",
                "To educate about the societal disaster in India that deals with poverty" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Team Tutors",
                "Students tutor athletes while working around their unique " +
                        "and challenging schedules" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Thespians Society",
                "Theater and honor society support" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Tri-M",
                "Music Honor Society" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("The Sentinel",
                "Centreville High School newspaper" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("UNICEF Club",
                "Helps impoverished women and children around the world" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Warhammer",
                "Helps impoverished women and children around the world" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Wildcat Writing Center",
                "Student run, peer tutoring organization" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("Women in Math Honor Society",
                "For girls who show an interest in math and want to advance in the field" + "\n",
                Integer.MIN_VALUE));
        data.add(new Club("ZOIC",
                "Literary Magazine" + "\n",
                Integer.MIN_VALUE));

        // Because I am too lazy to add this into each constructor...
        data.get(0).setSponsor("Paige Clark");
        data.get(1).setSponsor("Kathryn Mayhew");
        data.get(2).setSponsor("Michelle Neer");
        data.get(3).setSponsor("Katharine Search");
        data.get(4).setSponsor("Tommy Lamb, Kathleen McGuire");
        data.get(5).setSponsor("Leah Stowers");
        data.get(6).setSponsor("David Campbell");
        data.get(7).setSponsor("J.H. Novak");
        data.get(8).setSponsor("Sarah Pevner, Caitlin Rock");
        data.get(9).setSponsor("Kalli Chaney, Ashley Saccamando");
        data.get(10).setSponsor("Amy Balint, Dawn Barham");
        data.get(11).setSponsor("Jennifer Filsinger, Mary Lewis");
        data.get(12).setSponsor("David Campbell");
        data.get(13).setSponsor("Nancy Scheider, David Bausman, Donna Thompson, Sinitra DeHaven");
        data.get(14).setSponsor("Oliver Small");
        data.get(15).setSponsor("Jackie Cipolla");
        data.get(16).setSponsor("Michael Carfang, Miranda Schick");
        data.get(17).setSponsor("Jennie Hwangpo");
        data.get(18).setSponsor("Lauren White");
        data.get(19).setSponsor("Megan Lee");
        data.get(20).setSponsor("Jennifer Filsinger");
        data.get(21).setSponsor("Jean Cole Kleitz");
        data.get(22).setSponsor("Leah Stowers");
        data.get(23).setSponsor("Joshua Culver");
        data.get(24).setSponsor("Sean Scott");
        data.get(25).setSponsor("Sophie Turpin");
        data.get(26).setSponsor("Sophie Turpin");
        data.get(27).setSponsor("Judy Martin");
        data.get(28).setSponsor("Kathleen Willmann");
        data.get(29).setSponsor("Melissa Rife, Katherine Shepard");
        data.get(30).setSponsor("Melissa Rife");
        data.get(31).setSponsor("Sinitra DeHaven");
        data.get(32).setSponsor("Bill Burke");
        data.get(33).setSponsor("John O'Rourke");
        data.get(34).setSponsor("Catherine Ruffing");
        data.get(35).setSponsor("Susan Reese");
        data.get(36).setSponsor("David Campbell");
        data.get(37).setSponsor("Mariana Taboada");
        data.get(38).setSponsor("Jean No");
        data.get(39).setSponsor("Kathryn Mayhew, David Campbell");
        data.get(40).setSponsor("Kathryn Mayhew");
        data.get(41).setSponsor("Gordon Person");
        data.get(42).setSponsor("Kathy Beatty, Susan Rigby");
        data.get(43).setSponsor("Heather Fehr, Claudia DaBose");
        data.get(44).setSponsor("Maria Marris");
        data.get(45).setSponsor("Catherine Ruffing");
        data.get(46).setSponsor("Jackie Golodolinski, Michelle Pillor");
        data.get(47).setSponsor("Maliha Malik, David Bausman");
        data.get(48).setSponsor("Noel Miller, Alex Morrison");
        data.get(49).setSponsor("Jessica Berg");
        data.get(50).setSponsor("Mary Rubin");
        data.get(51).setSponsor("Oliver Small");
        data.get(52).setSponsor("Mary Rubin");
        data.get(53).setSponsor("Heather Fehr");
        data.get(54).setSponsor("Kathleen Waterfall");
        data.get(55).setSponsor("Christina Lee");
        data.get(56).setSponsor("Gary Baird");
        data.get(57).setSponsor("Barbara Haber, Judy Martin");
        data.get(58).setSponsor("Dana Doss");
        data.get(59).setSponsor("Terry Angelotti");
        data.get(60).setSponsor("Michelle Neer");
        data.get(61).setSponsor("Katherin Strobl");
        data.get(62).setSponsor("Mike Hudson");
        data.get(63).setSponsor("Melissa Hall, Lynne Babcock");
        data.get(64).setSponsor("Marissa D'Orazio");
        data.get(65).setSponsor("Caitlin Rock");
        data.get(66).setSponsor("David Bausman");
        data.get(67).setSponsor("Alison Hughes");
        data.get(68).setSponsor("Susan Rigby");
        data.get(69).setSponsor("Bridget Donoghue");

        return true; // May eventually return false if unable to pull data from server
    }


    /**
     * Club class to store all data pertaining to what might be
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
         * for an announcement
         *
         * @param title the announcement's title
         * @param text the body informational text of the announcement
         * @param sponsor the sponsor/poster of the announcement
         * @param iconSource source for the announcement's icon's source in the format R.id.XYZ
         * @param imageSource source for the announcement's image's source in the format R.id.XYZ
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
         * @param title  the announcement's title
         * @param text   the text-based information for the Announcement
         * @param source the source for the (optional) image in the format R.id.XYZ
         */

        public Club(String title, String text, int source) {
            this.title = title;
            this.text = text;
            imageSource = source;
        }

        /**
         * Instantiates a new Announcement with a title, text.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         *
         * @param title  the announcement's title
         * @param text   the text-based information for the Announcement
         */

        public Club(String title, String text) {
            this.title = title;
            this.text = text;
            imageSource = Integer.MIN_VALUE;
        }

        /**
         * Instantiates a new Announcement with text, an image.
         *
         * @param text   the text-based information for the Announcement
         * @param source the source for the (optional) image in the format R.id.XYZ
         */

        public Club(String text, int source) {
            this.text = text;
            imageSource = source;
        }

        /**
         * Instantiates a new Announcement with just the text.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         * @param text the text-based information for the Announcement
         * @param date the date of the announcement
         */
        public Club(String text, Date date) {
            this.text = text;
            imageSource = Integer.MIN_VALUE;
        }

        /**
         * Instantiates a new Announcement with just an image and a date.
         *
         * @param source the source for the (optional) image in the format R.id.XYZ
         * @param date   the date of the announcement
         */
        public Club(int source, Date date) {
            imageSource = source;
        }

        /**
         * Instantiates a new Announcement with just an image.
         *
         * @param source the source for the (optional) image in the format R.id.XYZ
         */
        public Club(int source) {
            imageSource = source;
        }

        /**
         * Instantiates a new Announcement with just text.
         * This also will assign imageSource to Integer.MIN_VALUE so it will
         * be something that we can check for and that won't be used automatically by accident.
         * @param text the text for the Announcement.
         */
        public Club(String text) {
            this.text = text;
            imageSource = Integer.MIN_VALUE;
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
         * Get method for the announcement's sponsor.
         *
         * @return the sponsor of the announcement
         */
        public String getSponsor() {
            return sponsor;
        }

        /**
         * Setter for the announcement's sponsor
         *
         * @param newSponsor new sponsor for announcement
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
         * Converts Announcement to String form in the format
         * Announcement: ANNOUNCEMENT_TITLE, ANNOUNCEMENT_TEXT, ANNOUNCEMENT_IMAGE_SOURCE
         */
        @Override
        public String toString() {
            return "Club: " + title + ", " + text + ", " + ", " + imageSource;
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
            Log.i(TAG, "generateIntro() called!");
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
}
