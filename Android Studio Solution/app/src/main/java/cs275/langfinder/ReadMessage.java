package cs275.langfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs275.langfinder.data.UserMessage;
import cs275.langfinder.data.User;
import cs275.langfinder.svcclient.MessageController;
import cs275.langfinder.svcclient.UserController;

public class ReadMessage extends Activity {
    NavBar navbar;
    String LINK = AppConfiguration.getApiEndpointRoot();
    Integer messageId = null;
    Integer toUserId = null;
    Button reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_message);
        navbar = new NavBar(this, "Messages");

        reply = (Button) findViewById(R.id.bReply);
        Button delete = (Button) findViewById(R.id.bDeleteMessage);

        getMessageStart();

        reply.setOnClickListener(new sendMailOnClickListener());
        delete.setOnClickListener(new deleteMailOnClickListener());
    }

    public void getMessageStart() {
        new AsyncTask<Void, Void, UserMessage>() {
            @Override
            protected UserMessage doInBackground(Void... params) {
                messageId = Integer.parseInt(getIntent().getExtras().get("id").toString());
                MessageController messageController = new MessageController(LINK);
                UserMessage message = messageController.getMessage(messageId);
                return message;
            }

            @Override
            protected void onPostExecute(UserMessage message) {
                final TextView txtname = (TextView) findViewById(R.id.tvFromTo);
                TextView txtdate = (TextView) findViewById(R.id.tvDateSent);
                TextView txtSubject = (TextView) findViewById(R.id.tvSubject);
                TextView txtContent = (TextView) findViewById(R.id.tvMessageBody);

                toUserId = message.getSenderUserId();
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        UserController userController = new UserController(LINK);
                        User user = userController.getUser(toUserId);
                        String name = user.getFirstName() + " " + user.getLastName();
                        return name;
                    }

                    @Override
                    protected void onPostExecute(String name) {
                        txtname.setText(name);
                    }
                }.execute();
                String date = message.getDateSentUTC().toString();
                String subject = message.getSubject();
                String content = message.getMessage();

                txtdate.setText(date);
                txtSubject.setText(subject);
                txtContent.setText(content);
            }
        }.execute();
    }

    public class sendMailOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ReadMessage.this, CreateMessage.class);
            intent.putExtra("toUserId", toUserId);
            ReadMessage.this.startActivity(intent);
            ReadMessage.this.finish();

        }
    }

    public class deleteMailOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            deleteMessage();
            Intent intent = new Intent(ReadMessage.this, MessageList.class);
            ReadMessage.this.startActivity(intent);
            ReadMessage.this.finish();
        }
    }

    public void deleteMessage() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                String idStr = getIntent().getExtras().get("id").toString();
                int id = Integer.parseInt(idStr);
                MessageController messageController = new MessageController(LINK);
                messageController.deleteMessage(id);
                return null;
            }
        }.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read_message, menu);
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
