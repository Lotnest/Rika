package lotnest.rika;

import lombok.SneakyThrows;
import lotnest.rika.configuration.Config;
import lotnest.rika.configuration.Message;
import lotnest.rika.listener.command.group.GroupCommandListener;
import lotnest.rika.listener.command.group.GroupsCommandListener;
import lotnest.rika.listener.command.student.WelcomeCommandListener;
import lotnest.rika.listener.reaction.HobbyReactionListener;
import lotnest.rika.listener.reaction.SpecializationReactionListener;
import lotnest.rika.listener.student.StudentListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

public class Rika {

    public static final Properties CONFIG = new Properties();
    public static final Properties IDS = new Properties();
    public static final Properties MESSAGES = new Properties();
    public static final Properties COMMANDS = new Properties();

    public static JDA JDA;
    public static String PREFIX;
    public static final Color MAIN_COLOR = new Color(0, 184, 238);

    static {
        try {
            final ClassLoader classLoader = Rika.class.getClassLoader();
            CONFIG.loadFromXML(classLoader.getResourceAsStream("config.xml"));
            IDS.loadFromXML(classLoader.getResourceAsStream("ids.xml"));
            MESSAGES.loadFromXML(classLoader.getResourceAsStream("messages.xml"));
            COMMANDS.loadFromXML(classLoader.getResourceAsStream("commands.xml"));

            PREFIX = CONFIG.getProperty("prefix");
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        final JDABuilder jdaBuilder = JDABuilder.create(System.getenv("TOKEN"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGE_REACTIONS);
        jdaBuilder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS);
        jdaBuilder.setBulkDeleteSplittingEnabled(false);
        jdaBuilder.setActivity(Activity.of(Config.ACTIVITY_TYPE, Message.ACTIVITY));
        addEventListeners(jdaBuilder);
    }

    @SneakyThrows
    private static void addEventListeners(@NotNull final JDABuilder jdaBuilder) {
        JDA = jdaBuilder.addEventListeners(
                new StudentListener(),
                new SpecializationReactionListener(),
                new HobbyReactionListener(),
                new GroupCommandListener(),
                new GroupsCommandListener(),
                new WelcomeCommandListener()
        ).build();
    }
}
