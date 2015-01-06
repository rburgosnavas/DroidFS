package com.rburgosnavas.droidfs.clients;

import com.rburgosnavas.droidfs.constants.Constants;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class HttpAuthClient {

    private static final String CLIENT_ID_TAG = "client_id";
    private static final String CLIENT_SECRET_TAG = "client_secret";
    private static final String GRANT_TYPE_TAG = "grant_type";
    private static final String CODE_TAG = "code";
    private static final String REFRESH_TOKEN_TAG = "refresh_token";
    private static final String AUTH_CODE_TAG = "authorization_code";

    private static FormEncodingBuilder makeBasicForm() {
        return new FormEncodingBuilder()
                .add(CLIENT_ID_TAG, Constants.CLIENT_ID)
                .add(CLIENT_SECRET_TAG, Constants.API_KEY);
    }

    private static Request makeRequest(RequestBody requestBody) {
        return new Request.Builder()
                .url(Constants.FS_APIV2 + "/oauth2/access_token/")
                .post(requestBody)
                .build();
    }

    private static Response makeResponse(Request request) throws IOException {
        OkHttpClient client = new OkHttpClient();
        return client.newCall(request).execute();
    }

    public static Response getAccessToken(String authCode) throws IOException {
        RequestBody requestBody = makeBasicForm()
                .add(GRANT_TYPE_TAG, AUTH_CODE_TAG)
                .add(CODE_TAG, authCode)
                .build();
        Request request = makeRequest(requestBody);

        return makeResponse(request);
    }

    public static Response getRefreshToken(String refreshToken) throws IOException {
        RequestBody requestBody = makeBasicForm()
                .add(GRANT_TYPE_TAG, REFRESH_TOKEN_TAG)
                .add(REFRESH_TOKEN_TAG, refreshToken)
                .build();
        Request request = makeRequest(requestBody);

        return makeResponse(request);
    }
}
