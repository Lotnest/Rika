package dev.lotnest.rika.configuration;

import static dev.lotnest.rika.Rika.IDS;

public class IdConstants {

    public static final String JOIN_LEAVE_MESSAGES_CHANNEL = IDS.getProperty("welcome-leave-messages-channel");
    public static final String BOOST_MESSAGES_CHANNEL = IDS.getProperty("boost-messages-channel");
    public static final String COMMANDS_CHANNEL = IDS.getProperty("commands-channel");
    public static final String TESTING_CHANNEL = IDS.getProperty("testing-channel");
    public static final String VERIFICATION_CHANNEL = IDS.getProperty("verification-channel");
    public static final String SPECIALIZATION_MESSAGE = IDS.getProperty("specialization-message");
    public static final String HOBBY_MESSAGE = IDS.getProperty("hobby-message");
    public static final String STUDENT_ROLE = IDS.getProperty("student-role");
    public static final String VERIFICATION_FAILED_ROLE = IDS.getProperty("verification-failed-role");
    public static final String MUTED_ROLE = IDS.getProperty("muted-role");
    public static final String MODERATOR_ROLE = IDS.getProperty("moderator-role");
    public static final String ADMINISTRATOR_ROLE = IDS.getProperty("administrator-role");

    private IdConstants() {
    }
}
