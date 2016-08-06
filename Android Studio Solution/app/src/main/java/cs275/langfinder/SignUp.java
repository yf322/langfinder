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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cs275.langfinder.svcclient.UserController;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        Button bLogin2 = (Button) findViewById(R.id.bLogin2);
        Button bSignUp2 = (Button) findViewById(R.id.bSignUp2);

        bLogin2.setOnClickListener(new bLogin2OnClickListener());
        bSignUp2.setOnClickListener(new bSignUp2OnClickListener());

    }

    public class bLogin2OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SignUp.this, Login.class);
            startActivity(intent);
        }
    }

    public class bSignUp2OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            EditText etFirstName = (EditText) findViewById((R.id.etFirstName));
            EditText etLastName = (EditText) findViewById((R.id.etLastName));
            EditText etEmail2 = (EditText) findViewById((R.id.etEmail2));
            EditText etPassword2 = (EditText) findViewById((R.id.etPassword2));
            EditText etPassword2Confirm = (EditText) findViewById((R.id.etPassword2Confirm));

            final String firstName = etFirstName.getText().toString();
            final String lastName = etLastName.getText().toString();
            final String email = etEmail2.getText().toString();
            final String password = etPassword2.getText().toString();
            String passwordConfirm = etPassword2Confirm.getText().toString();

            if(firstName.length() == 0 || lastName.length() == 0 || email.length() == 0 || password.length() == 0 || passwordConfirm.length() == 0)
            {
                new AlertDialog.Builder(SignUp.this)
                        .setTitle("Oops!")
                        .setMessage("You forgot to fill something out!")
                        .setNeutralButton("Dismiss", new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which){ dialog.cancel(); }})
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return;
            }

            if(!password.equals(passwordConfirm))
            {
                new AlertDialog.Builder(SignUp.this)
                        .setTitle("Oops!")
                        .setMessage("Your passwords do not match.")
                        .setNeutralButton("Dismiss", new DialogInterface.OnClickListener(){ public void onClick(DialogInterface dialog, int which){ dialog.cancel(); }})
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return;
            }

            new AsyncTask<Void, Void, Integer>() {
                @Override
                protected Integer doInBackground(Void... params) {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    try {
                        userController.createUser(email, password, firstName, lastName);
                        userController.login(email, password);
                        return 1;
                    }catch (Exception e) {
                        return 2;
                    }
                }

                @Override
                protected void onPostExecute(Integer n) {
                    if ( n == 1 ) {
                        Intent intent = new Intent(SignUp.this, Search.class);
                        SignUp.this.startActivity(intent);
                        SignUp.this.finish();
                    }
                    else {
                        Toast.makeText(SignUp.this, "Can't sign up!", Toast.LENGTH_LONG).show();
                    }
                }
            }.execute();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
