package com.rburgosnavas.droidfs.api.constants;

/**
 * A set of constants used for persisting and retrieving OAuth data from
 * {@link android.content.SharedPreferences}.
 */
public class Auth {
    /**
     * The {@link android.content.SharedPreferences} file name for persisting OAuth data.
     */
    public static final String OAUTH_PREFERENCES = "oauth_preferences";
    /**
     * The access_token label.
     */
    public static final String ACCESS_TOKEN = "access_token";
    /**
     * The scope label.
     */
    public static final String SCOPE = "scope";
    /**
     * The expires_in label.
     */
    public static final String EXPIRES_IN = "expires_in";
    /**
     * The refresh_token label.
     */
    public static final String REFRESH_TOKEN = "refresh_token";
    /**
     * The access_timestamp label.
     */
    public static final String ACCESS_TIMESTAMP = "access_timestamp";
    /**
     * The expiration_timestamp label.
     */
    public static final String EXPIRATION_TIMESTAMP = "expiration_timestamp";

    private Auth() {}
}
