package dev.lotnest.rika.configuration;

import static dev.lotnest.rika.Rika.COMMANDS;

public class CommandConstants {

    public static final String GROUP = COMMANDS.getProperty("group", "grupa");
    public static final String GROUPS = COMMANDS.getProperty("groups", "grupy");
    public static final String WELCOME = COMMANDS.getProperty("welcome", "witamy");
    public static final String PING = COMMANDS.getProperty("ping", "ping");
    public static final String CAT = COMMANDS.getProperty("cat", "kot");
    public static final String DOG = COMMANDS.getProperty("dog", "pies");
    public static final String IT_EMPLOYEE = COMMANDS.getProperty("it-employee", "pracownikit");
    public static final String MUTE = COMMANDS.getProperty("mute", "wycisz");
    public static final String PLAN = COMMANDS.getProperty("plan", "plan");
    public static final String ITN = COMMANDS.getProperty("itn", "itn");
    public static final String GAKKO = COMMANDS.getProperty("gakko", "gakko");

    private CommandConstants() {
    }
}
