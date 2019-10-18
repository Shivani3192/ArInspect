package com.example.arinspect.network;

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author spatel
 * @since 16-10-2019
 */
public class ApiClient {

    private static ApiClient apiClient = null;
    private Retrofit retrofit;

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (apiClient == null) {
            synchronized (ApiClient.class) {
                if (apiClient == null)
                    apiClient = new ApiClient();
            }
        }

        return apiClient;
    }

    public void create() {
        if (retrofit == null) {
            retrofit = getBuilder().build();
        }
    }

    private Retrofit.Builder getBuilder() {
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    @NonNull
    public <K> K createService(Class<K> serviceApi) {
        return retrofit.create(serviceApi);
    }
}
