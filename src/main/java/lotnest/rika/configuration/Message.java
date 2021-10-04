package lotnest.rika.configuration;

import lombok.Getter;

import static lotnest.rika.Rika.MESSAGES;

@Getter
public class Message {

    public static final String ACTIVITY = MESSAGES.getProperty("activity");
    public static final String FOOTER = MESSAGES.getProperty("footer");
    public static final String NEW_STUDENT_TITLE = MESSAGES.getProperty("new-student-title");
    public static final String NEW_STUDENT_DESCRIPTION = MESSAGES.getProperty("new-student-description");
    public static final String STUDENT_LEFT_TITLE = MESSAGES.getProperty("student-left-title");
    public static final String BOOST_MESSAGE_TITLE = MESSAGES.getProperty("boost-message-title");
    public static final String BOOST_MESSAGE_DESCRIPTION = MESSAGES.getProperty("boost-message-description");
    public static final String COMMAND_TITLE = MESSAGES.getProperty("command-title");
    public static final String GROUP_COMMAND = MESSAGES.getProperty("group-command");
    public static final String GROUP_COMMAND_ADDED_EXERCISE = MESSAGES.getProperty("group-command-added-exercise");
    public static final String GROUP_COMMAND_ADDED_LECTURE = MESSAGES.getProperty("group-command-added-lecture");
    public static final String GROUP_COMMAND_INVALID_TYPE = MESSAGES.getProperty("group-command-invalid-type");
    public static final String GROUP_COMMAND_EXERCISE_NOT_FOUND = MESSAGES.getProperty("group-command-exercise-not-found");
    public static final String GROUP_COMMAND_LECTURE_NOT_FOUND = MESSAGES.getProperty("group-command-lecture-not-found");

    private Message() {
    }
}
