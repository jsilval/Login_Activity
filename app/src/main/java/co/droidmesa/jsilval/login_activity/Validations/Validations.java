package co.droidmesa.jsilval.login_activity.Validations;

/**
 * Created by jsilval on 15/11/16.
 *
 */

public class Validations {

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }


    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /*
     * Usernames/userIds are case insensitive. User 'smith' and user 'Smith' should be the same user.
     * User names should be unique.
     * */
    public static boolean isUserValid(String username) {

        return username.length() > 3;
    }

}
