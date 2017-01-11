package co.droidmesa.jsilval.login_activity.Interfaces;

import android.provider.ContactsContract;

/**
 * Created by jsilval on 16/11/16.
 */

public interface ProfileQuery {
    String[] PROJECTION = {
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
    };

    int ADDRESS = 0;
    int IS_PRIMARY = 1;
}
