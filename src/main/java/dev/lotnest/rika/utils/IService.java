package dev.lotnest.rika.utils;

import dev.lotnest.rika.configuration.MessageConstants;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface IService {

    HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    @NotNull String getServiceUrl();

    default String getJsonValue(@NotNull String jsonKey) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getServiceUrl()))
                .build();
        try {
            return HTTP_CLIENT.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(responseBody -> getJsonValue(responseBody, jsonKey))
                    .get(10L, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return MessageConstants.SERVICE_ERROR_OCCURRED;
        }
    }

    default @NotNull String getJsonValue(@NotNull String responseBody, @NotNull String jsonKey) {
        JSONArray apiKeysJsonArray = new JSONArray(responseBody);
        JSONObject apiKeysJsonObject = apiKeysJsonArray.getJSONObject(0);
        return apiKeysJsonObject.getString(jsonKey);
    }

    default boolean isUp() {
        try {
            URL url = new URL(getServiceUrl());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            return httpURLConnection.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
