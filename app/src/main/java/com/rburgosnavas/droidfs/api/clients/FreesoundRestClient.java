package com.rburgosnavas.droidfs.api.clients;

import com.rburgosnavas.droidfs.api.constants.Api;
import com.rburgosnavas.droidfs.api.services.FreesoundApiService;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FreesoundRestClient {
    private final FreesoundApiService service;
    private static FreesoundRestClient freesoundRestClient;

    private FreesoundRestClient(String accessToken) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .header("Authorization", "Bearer " + accessToken)
                            .build();

                    return chain.proceed(request);
                })
                .build();

        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(Api.FS_APIV2)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        service = adapter.create(FreesoundApiService.class);
    }

    public FreesoundApiService makeService() {
        return service;
    }

    public static FreesoundRestClient newInstance(String accessToken) {
        if (freesoundRestClient == null) {
            freesoundRestClient = new FreesoundRestClient(accessToken);
        }

        return freesoundRestClient;
    }
}
