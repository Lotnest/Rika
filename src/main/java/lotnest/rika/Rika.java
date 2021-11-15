package lotnest.rika;

import lombok.SneakyThrows;
import lotnest.rika.configuration.ConfigProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.manager.CommandManager;
import lotnest.rika.manager.ReactionManager;
import lotnest.rika.manager.StudentManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.awt.*;
import java.util.Properties;

public class Rika {

    public static final Properties CONFIG = new Properties();
    public static final Properties IDS = new Properties();
    public static final Properties MESSAGES = new Properties();
    public static final Properties COMMANDS = new Properties();
    public static final Color MAIN_COLOR = new Color(0, 184, 238);
    private static JDA jda;

    @SneakyThrows
    public static void main(final String[] args) {
        final long now = System.currentTimeMillis();

        final ClassLoader classLoader = Rika.class.getClassLoader();
        CONFIG.loadFromXML(classLoader.getResourceAsStream("config.xml"));
        IDS.loadFromXML(classLoader.getResourceAsStream("ids.xml"));
        MESSAGES.loadFromXML(classLoader.getResourceAsStream("messages.xml"));
        COMMANDS.loadFromXML(classLoader.getResourceAsStream("commands.xml"));

        final JDABuilder jdaBuilder = JDABuilder.create(System.getenv("TOKEN"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGE_REACTIONS);
        jdaBuilder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS);
        jdaBuilder.setBulkDeleteSplittingEnabled(false);
        jdaBuilder.setActivity(Activity.of(ConfigProperty.ACTIVITY_TYPE, MessageProperty.ACTIVITY));
        jda = jdaBuilder.addEventListeners(
                new StudentManager(),
                new CommandManager(),
                new ReactionManager()
        ).build();
        jda.awaitReady();

        System.out.println("Rika bot loaded in " + (System.currentTimeMillis() - now) + " ms");
    }

    public static JDA getJDA() {
        return jda;
    }
}
