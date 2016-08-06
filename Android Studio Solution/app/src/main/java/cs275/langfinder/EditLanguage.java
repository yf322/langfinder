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
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs275.langfinder.data.Language;
import cs275.langfinder.data.LanguageLevel;
import cs275.langfinder.data.UserLanguageBundle;
import cs275.langfinder.svcclient.TypeController;
import cs275.langfinder.svcclient.UserController;

public class EditLanguage extends Activity {
    Integer languageId;
    CheckBox cbIsNative;
    CheckBox cbWantsToLearn;
    Spinner sEditLanguage;
    Spinner sEditLanguageLevel;
    NavBar navbar;
    TextView tvLevelInfo;

    ArrayAdapter<String> languagesAdapter;
    ArrayAdapter<String> languageLevelsAdapter;
    ArrayList<Integer> availableLanguageIds = new ArrayList<>();

    Integer newLanguageId;
    Integer languageLevel;
    String languageLevelName;
    ArrayList<String> languageLevelInfo = new ArrayList<>();
    Boolean isNative;
    Boolean wantsToLearn;
    ArrayList<Language> languages = new ArrayList<>();
    ArrayList<LanguageLevel> languageLevels = new ArrayList<>();

    ArrayList<String> languageStrings = new ArrayList<>();
    ArrayList<String> languageLevelStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_language);
        navbar = new NavBar(this, "Profile");

        Intent intent = getIntent();
        languageId = intent.getIntExtra("languageId", -1);

        cbIsNative = (CheckBox) findViewById((R.id.cbIsNative));
        cbWantsToLearn = (CheckBox) findViewById((R.id.cbWantsToLearn));
        sEditLanguage = (Spinner) findViewById((R.id.sEditLanguage));
        sEditLanguageLevel = (Spinner) findViewById((R.id.sEditLanguageLevel));
        tvLevelInfo = (TextView) findViewById(R.id.tvLevelInfo);

        Button bCancelLanguage = (Button) findViewById(R.id.bCancelLanguage);
        Button bDeleteLanguage = (Button) findViewById(R.id.bDeleteLanguage);

        if (languageId.equals(-1)) {
            bCancelLanguage.setVisibility(View.VISIBLE);
        } else {
            bDeleteLanguage.setVisibility(View.VISIBLE);
        }

        fillSpinners();
    }

    public void bCancelActivity(View v) {
        finish();
    }

    public void fillSpinners() {
        if (!languageId.equals(-1)) {
            sEditLanguage.setEnabled(false);
            sEditLanguage.setClickable(false);
        }

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    TypeController typeController = new TypeController(AppConfiguration.getApiEndpointRoot());
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());


                    Language[] languages2 = typeController.getLanguages();
                    LanguageLevel[] languageLevels2 = typeController.getLanguageLevels();

                    Integer myUserId = userController.getCurrentUserId();

                    if(languageId.equals(-1)) {
                        UserLanguageBundle[] userLanguageBundles = userController.getUserLanguages(myUserId.intValue());

                        ArrayList<Integer> myLanguages = new ArrayList<>();

                        for (int i = 0; i < userLanguageBundles.length; i++) {
                            myLanguages.add(userLanguageBundles[i].getLanguage().getId());
                        }

                        for (int i = 0; i < languages2.length; i++) {
                            if(!myLanguages.contains(languages2[i].getId())) {
                                languages.add(languages2[i]);
                                availableLanguageIds.add(languages2[i].getId());
                                languageStrings.add(languages2[i].getName());
                            }
                        }
                    }
                    else {
                        UserLanguageBundle editing = userController.getUserLanguage(myUserId.intValue(), languageId.intValue());
                        languages.add(editing.getLanguage());
                        languageStrings.add(editing.getLanguage().getName());
                    }

                    for (int i = 0; i < languageLevels2.length; i++) {
                        languageLevels.add(languageLevels2[i]);
                        languageLevelStrings.add(languageLevels2[i].getName());
                        languageLevelInfo.add(languageLevels2[i].getDescription());
                    }

                    languagesAdapter = new ArrayAdapter<>(EditLanguage.this, android.R.layout.simple_spinner_item, languageStrings);
                    languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    languageLevelsAdapter = new ArrayAdapter<>(EditLanguage.this, android.R.layout.simple_spinner_item, languageLevelStrings);
                    languageLevelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error getting languages.";
                }
            }

            @Override
            protected void onPostExecute(String status) {
                if (!status.equals("Success!")) {
                    toasty(status);
                    return;
                }

                sEditLanguage.setAdapter(languagesAdapter);
                sEditLanguageLevel.setAdapter(languageLevelsAdapter);

                sEditLanguageLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tvLevelInfo.setText(languageLevelInfo.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                if (!languageId.equals(-1)) {
                    fillLanguageData();
                }
                else
                {
                    tvLevelInfo.setText(languageLevelInfo.get(0));
                }
            }
        }.execute();
    }

    public void fillLanguageData() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());

                    Integer myUserId = userController.getCurrentUserId();
                    UserLanguageBundle userLanguageBundle = userController.getUserLanguage(myUserId.intValue(), languageId.intValue());

                    languageLevelName = userLanguageBundle.getLanguageLevel().getName();
                    languageLevel = userLanguageBundle.getLanguageLevel().getId();
                    isNative = userLanguageBundle.getUserLanguage().getIsNative();
                    wantsToLearn = userLanguageBundle.getUserLanguage().getWantsToLearn();

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error getting user language information.";
                }
            }

            @Override
            protected void onPostExecute(String status) {
                if (!status.equals("Success!")) {
                    toasty(status);
                    return;
                }

                tvLevelInfo.setText(languageLevelInfo.get(languageLevel));

                sEditLanguageLevel.setSelection(languageLevel-1);
                sEditLanguage.setSelection(0);

                cbIsNative.setChecked(isNative);
                cbWantsToLearn.setChecked(wantsToLearn);
            }
        }.execute();
    }

    public void bDeleteLanguage(View v) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    Integer myUserId = userController.getCurrentUserId();
                    userController.deleteUserLanguage(myUserId.intValue(), languageId.intValue());

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error deleting language.";
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

    public void bSaveLanguage(View v) {
        if(languageId.equals(-1) & availableLanguageIds.size() == 0) {

            toasty("You already know all the languages!");
            return;
        }

//        newLanguageId = availableLanguageIds.get(sEditLanguage.getSelectedItemPosition());
        newLanguageId = languages.get(sEditLanguage.getSelectedItemPosition()).getId();
        languageLevel = languageLevels.get(sEditLanguageLevel.getSelectedItemPosition()).getId();

        wantsToLearn = cbWantsToLearn.isChecked();
        isNative = cbIsNative.isChecked();

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());

                    Integer myUserId = userController.getCurrentUserId();

                    if (!languageId.equals(-1)) {
                        userController.updateUserLanguage(myUserId.intValue(), languageId.intValue(), wantsToLearn, isNative, languageLevel.intValue());
                    } else {
                        userController.createUserLanguage(myUserId.intValue(), newLanguageId.intValue(), wantsToLearn, isNative, languageLevel.intValue());
                    }

                    return "Success!";

                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error saving language.";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_language, menu);
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
