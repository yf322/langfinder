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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import cs275.langfinder.data.Language;
import cs275.langfinder.data.LanguageLevel;
import cs275.langfinder.data.User;
import cs275.langfinder.data.UserProfileBundle;
import cs275.langfinder.svcclient.TypeController;
import cs275.langfinder.svcclient.UserController;

public class Search extends Activity {
    NavBar navbar;
    String LINK = AppConfiguration.getApiEndpointRoot();
    Integer languageId = null;
    Integer levelId = null;

    public void updateLanguagesStart() {
        new AsyncTask<Void, Void, Language[]> () {
            @Override
            protected Language[] doInBackground(Void... params) {
                TypeController typeController = new TypeController(LINK);
                Language[] languages = typeController.getLanguages();
                return languages;
            }

            @Override
            protected void onPostExecute(Language[] languages) {
                Spinner spnLanguage = (Spinner) findViewById(R.id.spnLanguage);
                ArrayList<String> language = new ArrayList<>();
                language.add("All");
                ArrayList<String> listId = new ArrayList<>();
                listId.add(null);
                for ( int i = 0; i < languages.length; i++) {
                    String langName = languages[i].getName();
                    String langId = String.valueOf(languages[i].getId());
                    language.add( langName );
                    listId.add( langId );
                }
                String[] langContent = (String[]) language.toArray(new String[0]);
                final String[] langIdContent = (String[]) listId.toArray(new String[0]);
                ArrayAdapter<String> adapter = new ArrayAdapter(Search.this, android.R.layout.simple_spinner_item, langContent);
                spnLanguage.setSelection(0, false);
                spnLanguage.setAdapter(adapter);

                spnLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (langIdContent[position] != null) {
                            languageId = Integer.valueOf(langIdContent[position]);
                        }
                        else {
                            languageId = null;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        }.execute();
    }

    public void updateLanguageLevelStart() {
        new AsyncTask<Void, Void, LanguageLevel[]>() {
            @Override
            protected LanguageLevel[] doInBackground(Void... params) {
                TypeController typeController = new TypeController(LINK);
                LanguageLevel[] languageLevels = typeController.getLanguageLevels();
                return languageLevels;
            }

            @Override
            protected void onPostExecute(LanguageLevel[] languageLevels) {
                Spinner spnLevel = (Spinner) findViewById(R.id.spnLevel);
                ArrayList levels = new ArrayList();
                levels.add("All");
                ArrayList listId = new ArrayList();
                listId.add(null);
                for ( int i = 0; i < languageLevels.length; i++) {
                    String name = languageLevels[i].getName();
                    String lvId = String.valueOf(languageLevels[i].getId());
                    levels.add( name );
                    listId.add( lvId );
                }
                String[] langLevels = (String[])levels.toArray(new String[0]);
                final String[] langLevelId = (String[])listId.toArray(new String[0]);
                ArrayAdapter<String> adapter = new ArrayAdapter(Search.this, android.R.layout.simple_spinner_item, langLevels);
                spnLevel.setAdapter(adapter);

                spnLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (langLevelId[position] != null) {
                            levelId = Integer.valueOf(langLevelId[position]);
                        }
                        else {
                            levelId = null;
                        }
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
        setContentView(R.layout.search);
        navbar = new NavBar(this, "Search");

        updateLanguagesStart();
        updateLanguageLevelStart();

        Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new startOnClickListener());


    }


    public class startOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            new AsyncTask<Void, Void, UserProfileBundle[]>() {
                public String getName() {
                    EditText editName = (EditText) findViewById(R.id.edtName);
                    String name = editName.getText().toString();
                    if(name.trim().length()==0) name = null;
                    return name;
                }

                @Override
                protected UserProfileBundle[] doInBackground(Void... params) {
                    UserController userController = new UserController(LINK);
                    UserProfileBundle[] userProfileBundles = userController.searchUsers( getName(), languageId, levelId);
                    return userProfileBundles;
                }

                @Override
                protected void onPostExecute(UserProfileBundle[] userProfileBundles) {
                    if (userProfileBundles != null) {
                        ListView lsvResult = (ListView) findViewById(R.id.lsvResult);
                        ArrayList idList = new ArrayList();
                        for (int i = 0; i < userProfileBundles.length; i++) {
                            User user = userProfileBundles[i].getUser();
                            String id = String.valueOf(user.getId());
                            idList.add(id);
                        }
                        final String[] idArray = (String[]) idList.toArray(new String[0]);
                        ListAdapter adapter = new SearchAdapter(Search.this, userProfileBundles);
                        lsvResult.setAdapter(adapter);

                        lsvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(Search.this, Profile.class);
                                Integer theId = Integer.parseInt(idArray[position]);
                                intent.putExtra("userId", theId);
                                Search.this.startActivity(intent);
                                Search.this.finish();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(Search.this, "Nothing found!", Toast.LENGTH_LONG).show();
                    }

                }
            }.execute();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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
