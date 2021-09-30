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

    private Message() {
    }
}
