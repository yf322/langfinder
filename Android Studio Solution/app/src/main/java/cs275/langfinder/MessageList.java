package cs275.langfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import cs275.langfinder.data.UserMessage;
import cs275.langfinder.data.UserMessageFolder;
import cs275.langfinder.svcclient.MessageController;
import cs275.langfinder.svcclient.UserController;

public class MessageList extends Activity {
    NavBar navbar;
    String LINK = AppConfiguration.getApiEndpointRoot();
    int defaultId;

    public void getMailWithId(final int foldertypeId) {
        new AsyncTask<Void, Void, UserMessage[]>() {

            @Override
            protected UserMessage[] doInBackground(Void... params) {
                MessageController messageController = new MessageController(LINK);
                UserController userController = new UserController(LINK);
                Integer currentId = userController.getCurrentUserId();
                UserMessageFolder[] messageFolderType = messageController.getMessageFolderTypes();
                //int folderId = messageFolderType[foldertypeId].getId();
                UserMessage[] messages = messageController.getMessagesForUser(currentId, foldertypeId);
                return messages;
            }

            @Override
            protected void onPostExecute(final UserMessage[] messages) {
                ListView mailContent = (ListView) findViewById(R.id.mailContent);
                ListAdapter mailAdapter = new MessageListAdapter(MessageList.this, messages);
                mailContent.setAdapter(mailAdapter);

                mailContent.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent();
                                intent.setClass(MessageList.this, ReadMessage.class);
                                UserMessage message = messages[position];
                                int posId = message.getId();
                                intent.putExtra("id", posId);
                                MessageList.this.startActivity(intent);
                                MessageList.this.finish();
                            }
                        }
                );
            }
        }.execute();
    }

    public void getFolderTypeStart() {
        new AsyncTask<Void, Void, UserMessageFolder[]>() {
            @Override
            protected UserMessageFolder[] doInBackground(Void... params) {
                MessageController messageController = new MessageController(LINK);
                UserMessageFolder[] messageFolderType = messageController.getMessageFolderTypes();

                return messageFolderType;
            }

            @Override
            protected void onPostExecute(UserMessageFolder[] messageFolderType) {
                defaultId = messageFolderType[0].getId();
                ArrayList list = new ArrayList();
                ArrayList idList = new ArrayList();
                for (int i = 0; i < messageFolderType.length; i++) {
                    String typeName = messageFolderType[i].getName();
                    String id = String.valueOf(messageFolderType[i].getId());
                    list.add(typeName);
                    idList.add(id);
                }
                final String[] typeList = (String[]) list.toArray(new String[0]);
                final String[] listId = (String[]) idList.toArray(new String[0]);
                Spinner spnFolderType = (Spinner) findViewById(R.id.spnFolderType);
                ArrayAdapter<String> adapter = new ArrayAdapter(MessageList.this, android.R.layout.simple_spinner_item, typeList);
                spnFolderType.setAdapter(adapter);

                spnFolderType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        int typeId = Integer.parseInt(listId[position]);
                        getMailWithId(typeId);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_list);
        navbar = new NavBar(this, "Messages");

        getMailWithId(defaultId);
        getFolderTypeStart();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message_list, menu);
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
