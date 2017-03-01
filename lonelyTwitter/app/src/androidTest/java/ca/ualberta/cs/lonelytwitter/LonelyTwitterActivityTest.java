package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2<LonelyTwitterActivity> {
    private Solo solo;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

        public void setup() throws Exception{
            solo = new Solo(getInstrumentation(), getActivity());
        }
    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testTweet(){
        solo.assertCurrentActivity("Wrong activity", LonelyTwitterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");

        solo.clickOnButton("Save");

        solo.clearEditText((EditText) solo.getView(R.id.body));

        assertTrue(solo.waitForText("Test Tweet!"));

        solo.clickOnButton("clear");
        assertFalse(solo.searchText("Test Tweet!"));
    }

    public void testClickTweetList(){
        solo.assertCurrentActivity("Wrong activity", LonelyTwitterActivity.class);
        solo.clickOnButton("clear");

        solo.enterText((EditText) solo.getView(R.id.body), "Test Tweet!");
        solo.clickOnButton("Save");
        solo.waitForText("Test Tweet");

        final ListView oldTweetsList = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals("Test Tweet!", tweet.getMessage());

        solo.clickInList(0);
        solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);
        assertTrue(solo.waitForText("TextView"));

        solo.goBack();
        solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
    }

    @Override
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}