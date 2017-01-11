package co.droidmesa.jsilval.login_activity.Views;

import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import co.droidmesa.jsilval.login_activity.Login.Login;
import co.droidmesa.jsilval.login_activity.LoginActivity;
import co.droidmesa.jsilval.login_activity.R;

/**
 * Created by jsilval on 15/11/16.
 */

public class LoginViews {
    // UI references.
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private AutoCompleteTextView mEmailView;

    public static LoginViews instance = null;

    private LoginViews() {
        final Activity activity = LoginActivity.getInstance();
        Log.d("bbb", "12345");

        mEmailView       = (AutoCompleteTextView) activity.findViewById(R.id.email);
        mPasswordView    = (EditText) activity.findViewById(R.id.password);
        mLoginFormView   = activity.findViewById(R.id.login_form);
        mProgressView    = activity.findViewById(R.id.login_progress);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
           public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
               Log.d("hola", Integer.toString(R.id.login));

               if (id == R.id.login || id == EditorInfo.IME_NULL) {
                   Log.d("pin", "jijij");
                   Login login = new Login();
                   login.attemptLogin();
                   return true;
               } else
                   Log.d("pinp", textView.getText().toString());

               return false;
           }
        });
    }

    public static LoginViews getInstance() {
        if(instance == null) {
            instance = new LoginViews();
        }
        return instance;
    }

    public AutoCompleteTextView getmEmailView() {
        return mEmailView;
    }

    public EditText getmPasswordView() {
        return mPasswordView;
    }

    public View getmProgressView() {
        return mProgressView;
    }

    public View getmLoginFormView() {
        return mLoginFormView;
    }
}
