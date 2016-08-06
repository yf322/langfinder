package cs275.langfinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cs275.langfinder.data.User;
import cs275.langfinder.data.UserProfileBundle;
import cs275.langfinder.svcclient.MessageController;
import cs275.langfinder.svcclient.UserController;

public class CreateInvite extends Activity {

    int toUserId;
    NavBar navbar;
    int myUserId;
    String firstName;
    String lastName;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_invite);
        navbar = new NavBar(this, "Profile");

        Intent intent = getIntent();
        toUserId = intent.getIntExtra("toUserId", -1);

        getRecipient();
    }

    public void getRecipient() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    myUserId = userController.getCurrentUserId();
                    UserProfileBundle user = userController.getUserWithProfile(toUserId);
                    firstName = user.getUser().getFirstName();
                    lastName = user.getUser().getLastName();

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error finding recipient.";
                }
            }

            @Override
            protected void onPostExecute(String status) {
                if (!status.equals("Success!")) {
                    toasty(status);
                    return;
                }

                TextView tvInviteRecipient = (TextView) findViewById((R.id.tvInviteRecipient));
                tvInviteRecipient.setText(firstName + " " + lastName);
            }
        }.execute();
    }

    public void bSendInvite(View v) {
        EditText etInviteMessageBody = (EditText) findViewById((R.id.etInviteMessageBody));
        message = etInviteMessageBody.getText().toString();

        if (message.length() == 0) {
            toasty("You forgot to fill something out!");
            return;
        }

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    int myUserId = userController.getCurrentUserId();
                    userController.inviteUser(myUserId, toUserId, message);

                    MessageController messageController = new MessageController(AppConfiguration.getApiEndpointRoot());
                    messageController.sendMessage(myUserId, toUserId, "You have a new invite!", message);

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error sending invite.";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_invite, menu);
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