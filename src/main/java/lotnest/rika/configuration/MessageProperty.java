package lotnest.rika.configuration;

import lombok.Getter;

import static lotnest.rika.Rika.MESSAGES;

@Getter
public class MessageProperty {

    public static final String ACTIVITY = MESSAGES.getProperty("activity");
    public static final String FOOTER = MESSAGES.getProperty("footer");
    public static final String NEW_STUDENT_TITLE = MESSAGES.getProperty("new-student-title");
    public static final String NEW_STUDENT_DESCRIPTION = MESSAGES.getProperty("new-student-description");
    public static final String STUDENT_LEFT_TITLE = MESSAGES.getProperty("student-left-title");
    public static final String BOOST_MESSAGE_TITLE = MESSAGES.getProperty("boost-message-title");
    public static final String BOOST_MESSAGE_DESCRIPTION = MESSAGES.getProperty("boost-message-description");
    public static final String COMMAND_TITLE = MESSAGES.getProperty("command-title");
    public static final String GROUP_EXERCISE_REGEX = MESSAGES.getProperty("group-exercise-regex");
    public static final String GROUP_LECTURE_REGEX = MESSAGES.getProperty("group-lecture-regex");
    public static final String GROUP_COMMAND_ALREADY_ASSIGNED = MESSAGES.getProperty("group-command-already-assigned");
    public static final String GROUP_COMMAND_ADDED_EXERCISE = MESSAGES.getProperty("group-command-added-exercise");
    public static final String GROUP_COMMAND_CHANGED_EXERCISE = MESSAGES.getProperty("group-command-changed-exercise");
    public static final String GROUP_COMMAND_ADDED_LECTURE = MESSAGES.getProperty("group-command-added-lecture");
    public static final String GROUP_COMMAND_CHANGED_LECTURE = MESSAGES.getProperty("group-command-changed-lecture");
    public static final String GROUP_COMMAND_INVALID_TYPE = MESSAGES.getProperty("group-command-invalid-type");
    public static final String GROUP_COMMAND_EXERCISE_NOT_FOUND = MESSAGES.getProperty("group-command-exercise-not-found");
    public static final String GROUP_COMMAND_LECTURE_NOT_FOUND = MESSAGES.getProperty("group-command-lecture-not-found");
    public static final String ALREADY_VERIFIED = MESSAGES.getProperty("already-verified");
    public static final String FAILED_TO_VERIFY = MESSAGES.getProperty("failed-to-verify");
    public static final String VERIFIED_SUCCESSFULLY = MESSAGES.getProperty("verified-successfully");
    public static final String WELCOME_TITLE = MESSAGES.getProperty("welcome-title");
    public static final String WELCOME_DESCRIPTION = MESSAGES.getProperty("welcome-description");
    public static final String MOST_POPULAR_EXERCISE_GROUPS = MESSAGES.getProperty("most-popular-exercise-groups");
    public static final String MOST_POPULAR_LECTURE_GROUPS = MESSAGES.getProperty("most-popular-lecture-groups");
    public static final String PING_DESCRIPTION = MESSAGES.getProperty("ping-description");
    public static final String SERVICE_ERROR_OCCURRED = MESSAGES.getProperty("service-error-occurred");
    public static final String IT_EMPLOYEE_DESCRIPTION = MESSAGES.getProperty("it-employee-description");
    public static final String USER_MUTED = MESSAGES.getProperty("user-muted");
    public static final String USER_ALREADY_MUTED = MESSAGES.getProperty("user-already-muted");
    public static final String ROLE_NOT_FOUND = MESSAGES.getProperty("role-not-found");
    public static final String NO_USER_TO_MUTE = MESSAGES.getProperty("no-user-to-mute");
    public static final String COMMAND_NO_PERMISSION = MESSAGES.getProperty("command-no-permission");
    public static final String MUTED_ROLE_NAME = MESSAGES.getProperty("muted-role-name");

    private MessageProperty() {
    }
}
