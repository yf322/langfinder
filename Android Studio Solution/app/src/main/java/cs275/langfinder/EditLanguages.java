package cs275.langfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import cs275.langfinder.data.UserLanguageBundle;
import cs275.langfinder.svcclient.UserController;

public class EditLanguages extends Activity {

    LanguagesAdapter adapter;
    ListView listView;
    UserLanguageBundle[] userLanguageBundles;
    NavBar navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_languages);
        navbar = new NavBar(this, "Profile");

        getEditLanguages();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getEditLanguages();
    }

    public void getEditLanguages() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    int myUserId = userController.getCurrentUserId();
                    userLanguageBundles = userController.getUserLanguages(myUserId);

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error getting user languages.";
                }
            }

            @Override
            protected void onPostExecute(String status) {
                if (!status.equals("Success!")) {
                    toasty(status);
                    return;
                }

                adapter = new LanguagesAdapter(EditLanguages.this, userLanguageBundles);
                listView = (ListView) findViewById(R.id.lvEditLanguages);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(onItemClickListener);
            }
        }.execute();
    }

    public void bAddLanguage(View v) {
        Intent intent = new Intent(EditLanguages.this, EditLanguage.class);
        intent.putExtra("languageId", -1);
        startActivity(intent);
    }

    public void bCancelActivity(View v) {
        finish();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int languageId = userLanguageBundles[position].getLanguage().getId();

            Intent intent = new Intent(EditLanguages.this, EditLanguage.class);
            intent.putExtra("languageId", languageId);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_languages, menu);
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
