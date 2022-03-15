package dev.lotnest.rika.utils;

import dev.lotnest.rika.configuration.MessageConstants;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public interface IService {

    String API_URL = "https://rika-bot-api.herokuapp.com/rika/";
    HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    @NotNull String getServiceUrl();

    default @NotNull String getJsonValue(@NotNull String jsonKey) {
        try {
            return getJsonBody().thenApply(responseBody -> getJsonValue(responseBody, jsonKey))
                    .get(10L, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return MessageConstants.SERVICE_ERROR_OCCURRED;
        }
    }

    default @NotNull String getJsonValue(@NotNull String responseBody, @NotNull String jsonKey) {
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getString(jsonKey);
    }

    default @NotNull CompletableFuture<String> getJsonBody() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(getServiceUrl()))
                .build();
        return HTTP_CLIENT.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
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
