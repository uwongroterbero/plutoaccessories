package com.example.plutoaccessories.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "pluto_session";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_ID = "id_user";
    private static final String KEY_NAME = "nama";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context){
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLoginSession(int idUser, String nama, String email, String role){
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_ID, idUser);
        editor.putString(KEY_NAME, nama);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ROLE, role);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public String getRole(){
        return prefs.getString(KEY_ROLE, "user");
    }

    public int getUserId(){
        return prefs.getInt(KEY_ID, -1);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }
}
