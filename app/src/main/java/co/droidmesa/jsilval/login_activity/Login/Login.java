package co.droidmesa.jsilval.login_activity.Login;

import android.app.Activity;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import co.droidmesa.jsilval.login_activity.AsynTasks.UserLoginTask;
import co.droidmesa.jsilval.login_activity.Interfaces.ProfileQuery;
import co.droidmesa.jsilval.login_activity.LoginActivity;
import co.droidmesa.jsilval.login_activity.R;
import co.droidmesa.jsilval.login_activity.Validations.Validations;
import co.droidmesa.jsilval.login_activity.Views.LoginViews;

/**
 * Created by jsilval on 17/10/16.
 */

public class Login implements LoaderCallbacks<Cursor> {
    private final String TAG = getClass().getSimpleName();
    private Activity activity;
    private LoginViews login;// = new LoginViews(activity);

    // Show primary email addresses first. Note that there won't be
    // a primary email address if the user hasn't specified one.
    private static final String SORT_ORDER =  ContactsContract.Contacts.Data.IS_PRIMARY + " DESC";
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    public static UserLoginTask mAuthTask = null;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    public static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    public Login() {
        this.activity = LoginActivity.getInstance();
        this.login = LoginViews.getInstance();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, (Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                ContactsContract.Contacts.Data.CONTENT_DIRECTORY)).toString());
        return new CursorLoader(activity.getApplication(),
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},


                SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        Log.d(TAG, Integer.toString(emails.size()));
        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        login.getmEmailView().setAdapter(adapter);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        //  LoginViews.
        login.getmEmailView().setError(null);
        login.getmPasswordView().setError(null);

        // Store values at the time of the login attempt.
        String email = login.getmEmailView().getText().toString();
        String password = login.getmPasswordView().getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !Validations.isPasswordValid(password)) {
            login.getmPasswordView().setError(activity.getString(R.string.error_invalid_password));
            focusView = login.getmPasswordView();
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            login.getmEmailView().setError(activity.getString(R.string.error_field_required));
            focusView = login.getmEmailView();
            cancel = true;
        } else if (!Validations.isEmailValid(email)) {
            login.getmEmailView().setError(activity.getString(R.string.error_invalid_email));
            focusView = login.getmEmailView();
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            ProgressUI.showProgress(true, activity);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }
}
