package com.rburgosnavas.droidfs.clients;

import com.rburgosnavas.droidfs.constants.Constants;
import com.rburgosnavas.droidfs.httpservices.FreesoundApiService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class FreesoundRestClient {
    private final RestAdapter adapter;

    public FreesoundRestClient(final String accessToken) {
        adapter = new RestAdapter.Builder()
                .setEndpoint(Constants.FS_APIV2)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestInterceptor.RequestFacade request) {
                        request.addHeader("Authorization", "Bearer " + accessToken);
                    }
                })
                .build();
    }

    public FreesoundApiService makeService() {
        return adapter.create(FreesoundApiService.class);
    }
}
