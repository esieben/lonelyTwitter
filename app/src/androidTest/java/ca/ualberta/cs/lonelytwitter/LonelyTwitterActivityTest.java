package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2<LonelyTwitterActivity> {
    private Solo solo;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    /**
     *
     */
    public void testTweet(){
        solo.assertCurrentActivity("Wrong activity", LonelyTwitterActivity.class);
        //tests to see if LonelyTwitterActivity is current activity
        //prints string "Wrong activity" if assert fails

        solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");
        //enters text "Test Tweet!" into EditText field from R.id.body

        solo.clickOnButton("Save");
        //clicks on button having text "Save"

        solo.clearEditText((EditText) solo.getView(R.id.body));
        //removes text in EditText field in R.id.body

        assertTrue(solo.waitForText("Test Tweet!"));
        //checks to ensure that the String "Test Tweet!" is entered by solo

        solo.clickOnButton("Clear");

        assertFalse(solo.searchText("Test Tweet!"));
        //checks to ensure String "Test Tweet!" has been entered, doesn't wait as long as waitForText
    }

    public void testClickTweetList(){
        LonelyTwitterActivity activity = (LonelyTwitterActivity) solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong activity", LonelyTwitterActivity.class);
        solo.clickOnButton("Clear");

        solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");
        solo.clickOnButton("Save");
        solo.waitForText("Test Tweet!");

        final ListView oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        String tweetData = tweet.toString();
        assertEquals("Test Tweet!", tweet.getMessage());

        solo.clickInList(0);
        //clicks on first item on available list view

        solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);
        assertTrue(solo.waitForText(tweet.toString()));

        solo.goBack();
        //same as using the back button on the android UI

        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);

    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}