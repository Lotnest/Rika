package lotnest.rika.utils;

import lombok.SneakyThrows;
import lotnest.rika.configuration.MessageProperty;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public interface Service {

    String getServiceUrl();

    @SneakyThrows
    default String getJsonValue(@NotNull String jsonKey) {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getServiceUrl())
                .build();
        Response response = httpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();

        if (responseBody != null) {
            JSONObject jsonResponse = new JSONObject(responseBody.string());
            return jsonResponse.get(jsonKey).toString();
        }

        return MessageProperty.SERVICE_ERROR_OCCURRED;
    }
}
