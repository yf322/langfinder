package cs275.langfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cs275.langfinder.data.User;
import cs275.langfinder.data.UserConnectionBundle;
import cs275.langfinder.svcclient.UserController;

public class FriendsList extends Activity {
    NavBar navbar;

    public void updateUserStart() {
        new AsyncTask<Void, Void, UserConnectionBundle[]>() {
            @Override
            protected UserConnectionBundle[] doInBackground(Void... params) {
                UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                int currentId = userController.getCurrentUserId();
                UserConnectionBundle[] userConnectionBundle = userController.getUserConnectionProfiles(currentId);
                return userConnectionBundle;
            }

            @Override
            protected void onPostExecute(UserConnectionBundle[] userConnectionBundle) {
                if (userConnectionBundle != null) {
                    ListView lsvContact = (ListView) findViewById(R.id.lsvContact);
                    ArrayList list = new ArrayList();
                    for (int i = 0; i < userConnectionBundle.length; i++) {
                        User user = userConnectionBundle[i].getUser();
                        String id = user.getId().toString();
                        list.add(id);
                    }
                    final String[] contactList = (String[]) list.toArray(new String[0]);
                    final ListAdapter contactAdapter = new FriendsListAdapter(FriendsList.this, userConnectionBundle);
                    lsvContact.setAdapter(contactAdapter);

                    lsvContact.setOnItemClickListener(
                            new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String str = contactList[position];
                                    int userId = Integer.parseInt(str);
                                    Intent intent = new Intent(FriendsList.this, Profile.class);
                                    intent.putExtra("userId", userId);
                                    FriendsList.this.startActivity(intent);
                                    FriendsList.this.finish();
                                }
                            }
                    );
                }
                else {
                    Toast.makeText(FriendsList.this, "Haha! You don't have any friends, loser!!", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public void getInviteStart() {
        new AsyncTask<Void, Void, UserConnectionBundle[]>() {
            @Override
            protected UserConnectionBundle[] doInBackground(Void... params) {
                UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                Integer userId = userController.getCurrentUserId();
                UserConnectionBundle[] userConnectionBundles = userController.getUserInviteProfiles(userId);
                return userConnectionBundles;
            }

            @Override
            protected void onPostExecute(UserConnectionBundle[] userConnectionBundles) {
                if (userConnectionBundles.length != 0) {
                    ListAdapter inviteAdapter = new InviteListAdapter(FriendsList.this, userConnectionBundles);
                    ListView inviteList = (ListView) findViewById(R.id.lsvInvite);
                    inviteList.setAdapter(inviteAdapter);
                }
                else {
                    ListView inviteList = (ListView) findViewById(R.id.lsvInvite);
                    inviteList.setVisibility(ListView.GONE);
                }
            }
        }.execute();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);
        navbar = new NavBar(this, "Friends");

        updateUserStart();
        getInviteStart();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends_list, menu);
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
