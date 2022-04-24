package dev.lotnest.rika.command.student;

import dev.lotnest.rika.command.AbstractCommand;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.utils.IService;
import dev.lotnest.rika.utils.MessageUtils;
import dev.lotnest.rika.utils.TimeUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class PlanCommand extends AbstractCommand implements IService {

    @Override
    public @NotNull String getName() {
        return CommandConstants.PLAN;
    }

    @Override
    public @NotNull CommandType getCommandType() {
        return CommandType.STUDENT;
    }

    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        EmbedBuilder commandEmbedBuilder = MessageUtils.getCommandEmbedBuilder(commandInfo, getName());

        commandInfo.getMessage().delete().queue();

        try {
            JSONTokener tokener = new JSONTokener(getJsonBody().get(10L, TimeUnit.SECONDS));
            JSONObject lessonJson = new JSONObject(tokener);

            commandEmbedBuilder.addField("Następna lekcja", "\n" + getLessonString(lessonJson), true);
        } catch (Exception e) {
            e.printStackTrace();
            commandEmbedBuilder.setDescription(MessageConstants.SERVICE_ERROR_OCCURRED);
        }

        commandInfo.getChannel()
                .sendMessageEmbeds(commandEmbedBuilder.build())
                .queue();
    }

    @Override
    public @NotNull String getServiceUrl() {
        return API_URL + "lesson/next";
    }

    private @NotNull String getLocalDateTimeString(@NotNull String jsonValue) {
        return LocalDateTime.parse(jsonValue).format(TimeUtils.DEFAULT_DATE_TIME_FORMATTER);
    }

    private @NotNull String getLessonString(@NotNull JSONObject lessonJson) {
        return MessageConstants.LESSON_SEMESTER + " " + lessonJson.getInt("semesterNumber") + "\n"
                + MessageConstants.LESSON_SUBJECT + " " + lessonJson.getString("code") + "\n"
                + MessageConstants.LESSON_TYPE + " " + lessonJson.getString("typeName") + "\n"
                + MessageConstants.LESSON_START_TIME + " " + getLocalDateTimeString(lessonJson.getString("startTime")) + "\n"
                + MessageConstants.LESSON_END_TIME + " " + getLocalDateTimeString(lessonJson.getString("endTime")) + "\n"
                + MessageConstants.LESSON_ROOM + " " + lessonJson.getString("room") + "\n"
                + MessageConstants.LESSON_TIME_LEFT + " " + lessonJson.getString("timeLeft");
    }
}
