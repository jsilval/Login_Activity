package co.droidmesa.jsilval.login_activity.Views;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import co.droidmesa.jsilval.login_activity.Login.Login;
import co.droidmesa.jsilval.login_activity.R;

/**
 * Created by jsilval on 15/11/16.
 */

public class LoginReferences {
    private Activity activity;
    // UI references.
    public static AutoCompleteTextView mEmailView;
    public static EditText mPasswordView;
    public static View mProgressView;
    public static View mLoginFormView;

   public LoginReferences(final Activity activity) {
        this.activity = activity;
        mEmailView = (AutoCompleteTextView) activity.findViewById(R.id.email);
        mPasswordView = (EditText) activity.findViewById(R.id.password);
        mLoginFormView = activity.findViewById(R.id.login_form);
        mProgressView = activity.findViewById(R.id.login_progress);

       mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
               if (id == R.id.login || id == EditorInfo.IME_NULL) {
                   Login login = new Login();
                   login.attemptLogin();
                   return true;
               }
               return false;
           }
       });
    }
}
