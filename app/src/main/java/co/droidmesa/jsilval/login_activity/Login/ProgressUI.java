package co.droidmesa.jsilval.login_activity.Login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;

import co.droidmesa.jsilval.login_activity.Views.LoginViews;

/**
 * Created by jsilval on 15/11/16.
 */

public class ProgressUI {
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(final boolean show, Activity activity) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        final LoginViews login = LoginViews.getInstance();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = activity.getResources().getInteger(android.R.integer.config_shortAnimTime);

            login.getmLoginFormView().setVisibility(show ? View.GONE : View.VISIBLE);
            login.getmLoginFormView().animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    login.getmLoginFormView().setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            login.getmProgressView().setVisibility(show ? View.VISIBLE : View.GONE);
            login.getmProgressView().animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    login.getmProgressView().setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login.getmProgressView().setVisibility(show ? View.VISIBLE : View.GONE);
            login.getmLoginFormView().setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
