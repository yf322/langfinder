package cs275.langfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;

import cs275.langfinder.data.UserProfileBundle;
import cs275.langfinder.svcclient.UserController;

public class EditProfile extends Activity {
    NavBar navbar;
    UserProfileBundle userProfileBundle;
    String aboutText;
    String lookingForText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        navbar = new NavBar(this, "Profile");

        fillProfileData();
    }

    public void fillProfileData() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    int myUserId = userController.getCurrentUserId();
                    userProfileBundle = userController.getUserWithProfile(myUserId);
                    aboutText = userProfileBundle.getUserProfile().getAboutText();
                    lookingForText = userProfileBundle.getUserProfile().getLookingForText();

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

                EditText etAboutText = (EditText) findViewById((R.id.etAboutText));
                EditText etLookingForText = (EditText) findViewById((R.id.etLookingForText));

                etAboutText.setText(aboutText);
                etLookingForText.setText(lookingForText);
            }
        }.execute();
    }

    public void bSaveProfile(View v) {
        EditText etAboutText = (EditText) findViewById((R.id.etAboutText));
        EditText etLookingForText = (EditText) findViewById((R.id.etLookingForText));

        aboutText = etAboutText.getText().toString();
        lookingForText = etLookingForText.getText().toString();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    int myUserId = userController.getCurrentUserId();

                    userController.updateProfile(myUserId, aboutText, lookingForText);

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error saving profile.";
                }
            }

            @Override
            protected void onPostExecute(String status) {
                if (!status.equals("Success!")) {
                    toasty(status);
                    return;
                }

                finish();
            }
        }.execute();
    }

    public void bCancelActivity(View v) {
        finish();
    }

    public void bEditLanguages(View v) {
        Intent intent = new Intent(this, EditLanguages.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
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
