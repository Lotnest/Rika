package lotnest.rika.util;

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
    default String getJsonValue(final @NotNull String jsonKey) {
        final OkHttpClient httpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(getServiceUrl())
                .build();
        final Response response = httpClient.newCall(request).execute();
        final ResponseBody responseBody = response.body();

        if (responseBody != null) {
            final JSONObject jsonResponse = new JSONObject(responseBody.string());
            return jsonResponse.get(jsonKey).toString();
        }

        return MessageProperty.SERVICE_ERROR_OCCURRED;
    }
}
