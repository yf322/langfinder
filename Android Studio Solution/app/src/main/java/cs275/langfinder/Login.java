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
import android.widget.Toast;

import cs275.langfinder.svcclient.UserController;

public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button btnLogIn = (Button) findViewById(R.id.bLogin);
        Button bSignup = (Button) findViewById(R.id.bSignUp);
        btnLogIn.setOnClickListener(new logInOnClickListener());
        bSignup.setOnClickListener(new signupOnClickListener());

    }

    public class signupOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Login.this, SignUp.class);
            Login.this.startActivity(intent);
            Login.this.finish();
        }
    }

    public class logInOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            checkToLogIn();
        }
    }

    public void checkToLogIn() {
        EditText txtEmail = (EditText) findViewById(R.id.etEmail);
        EditText txtPassword = (EditText) findViewById(R.id.etPassword);

        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    UserController userController = new UserController(AppConfiguration.getApiEndpointRoot());
                    Integer login = userController.login(email, password);
                    return login;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer login) {
                if (login != null) {
                    goToSearchPage(login);
                } else {
                    Toast.makeText(Login.this, "Invalid email or password!", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    public void goToSearchPage(Integer login) {
        Intent intent = new Intent();
        intent.setClass(Login.this, Search.class);
        intent.putExtra("id", login);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
