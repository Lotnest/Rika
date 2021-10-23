package lotnest.rika.util;

import lombok.SneakyThrows;
import lotnest.rika.configuration.Message;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

public class ServiceUtil {

    private ServiceUtil() {
    }

    @SneakyThrows
    public static String fetchStringFromUrl(@NotNull final String url, @Nullable final String jsonKey) {
        final OkHttpClient httpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Response response = httpClient.newCall(request).execute();
        final ResponseBody responseBody = response.body();

        if (responseBody != null) {
            final JSONObject jsonResponse = new JSONObject(responseBody.string());
            return jsonResponse.get(jsonKey).toString();
        }

        return Message.SERVICE_ERROR_OCCURRED;
    }
}
