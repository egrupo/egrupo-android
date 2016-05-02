package pt.egrupo.app.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pt.egrupo.app.App;
import pt.egrupo.app.R;
import pt.egrupo.app.models.User;
import pt.egrupo.app.network.HTTPStatus;
import pt.egrupo.app.network.SimpleTask;
import pt.egrupo.app.utils.ELog;
import pt.egrupo.app.utils.Globals;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    @Bind(R.id.username)EditText mUsernameView;
    @Bind(R.id.password)EditText mPasswordView;
    @Bind(R.id.login_progress)View mProgressView;
    @Bind(R.id.login_form)View mLoginFormView;

    boolean isLogging;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(App.isLogged()){
            goToHome();
        }

        setContentView(R.layout.activity_login);
        // Set up the login form.
        ButterKnife.bind(this);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if(isLogging)
            return;

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isEmailValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            login();
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
//        return email.contains("@");
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void login(){

        HashMap<String,String> mParams = new HashMap<>();
        mParams.put("username",mUsernameView.getText().toString());
        mParams.put("password",mPasswordView.getText().toString());
        mParams.put("grant_type","password");
        mParams.put("client_id",Globals.CLIENT_ID);
        mParams.put("client_secret",Globals.CLIENT_SECRET);

        new SimpleTask((App) getApplication(), new SimpleTask.SimpleTaskHelper() {
            @Override
            public void onPreExecute() {
                isLogging = true;
                showProgress(true);
            }

            @Override
            public void backgroundPreExecute() {

            }

            @Override
            public void backgroundPostExecute(int code, String result) {

                if(code == HTTPStatus.OK){
                    try {
                        JSONObject response = new JSONObject(result);
                        App.saveAccessToken(response.getString("access_token"));
                        App.saveRefreshToken(response.getString("refresh_token"));
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void mainPostExecute(int code, String result) {
                ELog.d("LoginAct", "Result(" + code + "): " + result);
                if(code == HTTPStatus.OK) {
                    getMe();
                } else {
                    isLogging = false;
                    showProgress(false);
                    if(code == HTTPStatus.UNAUTHORIZED){
                        //invalid credentials
                    }
                }
            }
        }, Globals.OAUTH,SimpleTask.TYPE_POST,mParams).execute();
    }

    public void getMe(){

        new SimpleTask((App) getApplication(), new SimpleTask.SimpleTaskHelper() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void backgroundPreExecute() {

            }

            @Override
            public void backgroundPostExecute(int code, String result) {
                ELog.d("LoginAct", "Result(" + code + "): " + result);
                if(code == HTTPStatus.OK){

                    User me = new Gson().fromJson(result,User.class);
                    App.saveUser(me);
                    App.saveBaseEndpoint(me.getOrganization_slug());
                    App.saveDivisao(me.getDivisao());
                    ((App)getApplication()).initApi();
                } else {

                }

            }

            @Override
            public void mainPostExecute(int code, String result) {
                isLogging = false;
                showProgress(false);
                if(code == HTTPStatus.OK){
                    goToHome();
                }

            }
        },Globals.ME,SimpleTask.TYPE_GET).execute();
    }

    public void goToHome(){
        Intent i = new Intent(this,HomeActivity.class);
        startActivity(i);
        this.finish();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}

