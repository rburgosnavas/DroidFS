package com.rburgosnavas.droidfs.api.clients;

import com.rburgosnavas.droidfs.api.constants.Api;
import com.rburgosnavas.droidfs.api.services.FreesoundApiService;
import com.rburgosnavas.droidfs.api.models.OAuthToken;
import com.rburgosnavas.droidfs.api.models.TokenType;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class HttpAuthClient {
    private static final String CLIENT_ID_TAG = "client_id";
    private static final String CLIENT_SECRET_TAG = "client_secret";
    private static final String GRANT_TYPE_TAG = "grant_type";
    private static final String CODE_TAG = "code";
    private static final String REFRESH_TOKEN_TAG = "refresh_token";
    private static final String AUTH_CODE_TAG = "authorization_code";
    private static final String AUTHORIZATION_CODE = "authorization_code";

    public static Observable<OAuthToken> getAccessToken(String code, TokenType tokenType) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder().build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.FS_APIV2)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        FreesoundApiService service = retrofit.create(FreesoundApiService.class);

        Observable<OAuthToken> tokenObservable = null;

        switch(tokenType) {
            case ACCESS_TOKEN:
                tokenObservable = service.getAccessToken(Api.CLIENT_ID, Api.API_KEY,
                        AUTHORIZATION_CODE, code);
                break;
            case REFRESH_TOKEN:
                tokenObservable = service.getRefreshToken(Api.CLIENT_ID, Api.API_KEY,
                        REFRESH_TOKEN_TAG, code);
                break;
        }

        return tokenObservable;
    }
}
