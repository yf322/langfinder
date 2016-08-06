package cs275.langfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cs275.langfinder.data.UserLanguageBundle;
import cs275.langfinder.data.UserProfileBundle;
import cs275.langfinder.svcclient.UserController;

public class Profile extends Activity {
    private Handler mHandler = new Handler();

    LanguagesAdapter adapter;
    ListView listView;
    NavBar navbar;
    Integer userId;
    Button bInvite;
    Button bRemove;
    Integer myUserId = -1;
    String firstName = "Error";
    String lastName = "Error";
    String aboutText = "Error";
    String lookingForText = "Error";
    UserLanguageBundle[] userLanguageBundles;
    Boolean areFriends = false;
    Boolean currentlyEditing = false;
    Date lastActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        navbar = new NavBar(this, "Profile");

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);

        setUpProfile();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(currentlyEditing) {
            currentlyEditing = false;

            Intent intent = new Intent(Profile.this, Search.class);
            startActivity(intent);
//            setUpProfile();
        }
    }

    public void setUpProfile() {
        TextView tvName = (TextView) findViewById(R.id.tvNameProfile);
        TextView tvAboutText = (TextView) findViewById(R.id.tvAboutText);
        TextView tvLookingForText = (TextView) findViewById(R.id.tvLookingForText);
        tvName.setText("Loading...");
        tvAboutText.setText("Loading...");
        tvLookingForText.setText("Loading...");
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    myUserId = userController.getCurrentUserId();

                    if (userId == -1 || userId == null) {
                        userId = myUserId;
                    }

                    if (!userId.equals(myUserId)) {
                        areFriends = userController.getUsersAreFriends(myUserId, userId);
                    }

                    UserProfileBundle userProfileBundle = userController.getUserWithProfile(userId);
                    userLanguageBundles = userController.getUserLanguages(userId);

                    firstName = userProfileBundle.getUser().getFirstName();
                    lastName = userProfileBundle.getUser().getLastName();
                    aboutText = userProfileBundle.getUserProfile().getAboutText();
                    lookingForText = userProfileBundle.getUserProfile().getLookingForText();
                    lastActive = userProfileBundle.getUser().getLastActiveUTC();

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error getting profile information.";
                }
            }

            @Override
            protected void onPostExecute(String status) {
                if (!status.equals("Success!")) {
                    toasty(status);
                    return;
                }

                /*Calendar cal = Calendar.getInstance();
                TimeZone timezone = cal.getTimeZone();
                DateFormat df = new SimpleDateFormat("MM/dd");
                df.setTimeZone(timezone);
                
                String lastActiveS = df.format(lastActive);*/

                TextView tvName = (TextView) findViewById(R.id.tvNameProfile);
                TextView tvAboutText = (TextView) findViewById(R.id.tvAboutText);
                TextView tvLookingForText = (TextView) findViewById(R.id.tvLookingForText);
                /*TextView tvLastActive = (TextView) findViewById(R.id.tvLastActive);*/

                tvName.setText(firstName + " " + lastName);
                tvAboutText.setText(aboutText);
                tvLookingForText.setText(lookingForText);
                /*tvLastActive.setText("Last active on " + lastActiveS);*/

                adapter = new LanguagesAdapter(Profile.this, userLanguageBundles);
                listView = (ListView) findViewById(R.id.lvProfileLanguages);
                listView.setAdapter(adapter);

                Button bEditProfile = (Button) findViewById(R.id.bEditProfile);
                Button bMessage = (Button) findViewById(R.id.bMessage);

                bInvite = (Button) findViewById(R.id.bInvite);
                bRemove = (Button) findViewById(R.id.bRemove);

                if (userId == myUserId) {
                    bEditProfile.setVisibility(View.VISIBLE);
                }

                if (userId != myUserId && areFriends) {
                    bRemove.setVisibility(View.VISIBLE);
                    bMessage.setVisibility(View.VISIBLE);
                }
                if (userId != myUserId && !areFriends) {
                    bInvite.setVisibility(View.VISIBLE);
                }
            }
        }.execute();
    }

    public void bEditProfile(View v) {
        currentlyEditing = true;
        Intent intent = new Intent(Profile.this, EditProfile.class);
        startActivity(intent);
    }

    public void bMessage(View v) {
        Intent intent = new Intent(Profile.this, CreateMessage.class);
        intent.putExtra("toUserId", userId);
        startActivity(intent);
    }

    public void bInviteProfile(View v) {
        Intent intent = new Intent(Profile.this, CreateInvite.class);
        intent.putExtra("toUserId", userId);
        startActivity(intent);
    }

    public void bRemove(View v) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    myUserId = userController.getCurrentUserId();
                    userController.removeFriend(myUserId, userId);

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error getting profile information.";
                }
            }

            @Override
            protected void onPostExecute(String status) {
                if (!status.equals("Success!")) {
                    toasty(status);
                    return;
                }

                bInvite = (Button) findViewById(R.id.bInvite);
                bRemove = (Button) findViewById(R.id.bRemove);

                bRemove.setVisibility(View.GONE);
                bInvite.setVisibility(View.VISIBLE);
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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

    public void toasty(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
