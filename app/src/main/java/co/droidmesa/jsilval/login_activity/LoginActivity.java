package co.droidmesa.jsilval.login_activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import co.droidmesa.jsilval.login_activity.Login.Login;
import co.droidmesa.jsilval.login_activity.Views.LoginViews;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 99;
    private Login login;

    // keep reference to this activity
    private static LoginActivity instance;
    // get this activity instance
    public static LoginActivity getInstance() { return instance; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;
        LoginViews.instance = null;
        LoginViews.getInstance();
        login = new Login();
        checkPermission();
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login.attemptLogin();
            }
        });

        Log.d(TAG, "asdasdasda");
    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);

        if (permissionCheck !=  PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                Log.d(TAG, Integer.toString(permissionCheck)+" "+"blablabla");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // perission granted!
                    getLoaderManager().initLoader(0, null, login);
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

