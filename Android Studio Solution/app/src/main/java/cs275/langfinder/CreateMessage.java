package cs275.langfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cs275.langfinder.data.User;
import cs275.langfinder.data.UserProfileBundle;
import cs275.langfinder.svcclient.MessageController;
import cs275.langfinder.svcclient.UserController;

public class CreateMessage extends Activity {
    int toUserId;
    int myUserId;

    NavBar navbar;
    UserProfileBundle toUser;
    String subject;
    String message;
    String LINK = AppConfiguration.getApiEndpointRoot();

    public void getMessageStart() {
        new AsyncTask<Void, Void, UserController>() {
            @Override
            protected UserController doInBackground(Void... params) {
                UserController userController = new UserController(LINK);
                toUser = userController.getUserWithProfile(toUserId);
                myUserId = userController.getCurrentUserId();
                return userController;
            }

            @Override
            protected void onPostExecute(UserController userController) {
                TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
                String email = toUser.getUser().getEmail();
                txtEmail.setText(email);
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_message);
        navbar = new NavBar(this, "Messages");
        toUserId = Integer.parseInt(getIntent().getExtras().get("toUserId").toString());
        getMessageStart();
        Button btnSend = (Button) findViewById(R.id.btnsend);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        btnSend.setOnClickListener(new sendOnClickListener());
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public class sendOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText edtSubject = (EditText) findViewById(R.id.edtSubject);
            EditText edtText = (EditText) findViewById(R.id.edtText);

            subject = edtSubject.getText().toString();
            if (subject == null) {
                Toast.makeText(CreateMessage.this, "Please Enter Subject!", Toast.LENGTH_LONG).show();
            } else {
                message = edtText.getText().toString();
                try {
                    sendMessage();
                }catch (Exception e) {
                    Toast.makeText(CreateMessage.this, "Can't send!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void sendMessage() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                MessageController messageController = new MessageController(LINK);
                messageController.sendMessage(myUserId, toUserId, subject, message);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                finish();
            }
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_message, menu);
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
}