package lotnest.rika.plan;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;

public class PlanMapper {

    @SneakyThrows
    public Plan mapFromFile(@NotNull String planFileName, int semester) {
        Plan result = new Plan(planFileName, semester);

        if (!new File(result.getPlanFileName()).exists()) {
            throw new IOException(String.format("File does not exist: '%s'", result.getPlanFileName()));
        }

        JSONArray root = new JSONArray("VCALENDAR");
        JSONArray vEvents = root.getJSONArray(0);

        for (int i = 0; i < vEvents.length(); i++) {

        }

        return result;
    }
}
