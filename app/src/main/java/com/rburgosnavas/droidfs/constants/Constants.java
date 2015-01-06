package com.rburgosnavas.droidfs.constants;

public class Constants {
    public static final String CLIENT_ID = "942069879bd8da300e19";
    public static final String API_KEY = "af214976f826ceec7a7f706c31541e0334227884";

    public static final String FS_URL = "https://www.freesound.org";
    public static final String REDIR = "/home/app_permissions/permission_granted/";
    public static final String FS_APIV2 = FS_URL + "/apiv2";
    public static final String FS_OAUTH_AUTH = FS_APIV2 + "/oauth2/authorize/";
    public static final String FS_OAUTH_AUTH_QUERY = FS_OAUTH_AUTH + "?client_id=" + CLIENT_ID +
            "&response_type=code&state=droidfs";
}
