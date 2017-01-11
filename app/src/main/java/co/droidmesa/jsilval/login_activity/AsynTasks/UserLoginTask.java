package co.droidmesa.jsilval.login_activity.AsynTasks;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import co.droidmesa.jsilval.login_activity.Login.Login;
import co.droidmesa.jsilval.login_activity.Login.ProgressUI;
import co.droidmesa.jsilval.login_activity.LoginActivity;
import co.droidmesa.jsilval.login_activity.R;
import co.droidmesa.jsilval.login_activity.UserActivity;
import co.droidmesa.jsilval.login_activity.Utils.SessionManager;
import co.droidmesa.jsilval.login_activity.Views.LoginViews;

/**
 * Created by jsilval on 15/11/16.
 *
 * Represents an asynchronous loginRef/registration task used to authenticate
 * the user.
 */

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

    private String mEmail;
    private String mPassword;
    private Activity activity;
    private LoginViews loginRef;

    public UserLoginTask(String email, String password) {
        mEmail = email;
        mPassword = password;
        activity = LoginActivity.getInstance();
        loginRef = LoginViews.getInstance();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        try {
            // Simulate network access.
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            return false;
        }

        for (String credential : Login.DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(mEmail)) {
                // Account exists, return true if the password matches.
                return pieces[1].equals(mPassword);
            }
        }

        // TODO: register the new account here.
        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        Login.mAuthTask = null;
        ProgressUI.showProgress(false, activity);

        if (success) {
            Intent i = new Intent(activity, UserActivity.class);
            activity.finish();
            SessionManager sessionManager = new SessionManager(activity);
            sessionManager.createLoginSession(mEmail, "my_token_session", "local");
            activity.startActivity(i);

        } else {
            loginRef.getmPasswordView().setError(activity.getString(R.string.error_incorrect_password));
            loginRef.getmPasswordView().requestFocus();
        }
    }

    @Override
    protected void onCancelled() {
        Login.mAuthTask = null;
        ProgressUI.showProgress(false, activity);
    }
}

