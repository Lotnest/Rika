package dev.lotnest.rika;

import dev.lotnest.rika.configuration.ConfigConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import dev.lotnest.rika.listener.CommandListener;
import dev.lotnest.rika.listener.ReactionListener;
import dev.lotnest.rika.listener.StudentListener;
import dev.lotnest.rika.plan.Plan;
import dev.lotnest.rika.plan.PlanManager;
import dev.lotnest.rika.plan.PlanMapper;
import dev.lotnest.rika.plan.SemesterEnum;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Logger;

public class Rika {

    public static final Properties CONFIG = new Properties();
    public static final Properties IDS = new Properties();
    public static final Properties MESSAGES = new Properties();
    public static final Properties COMMANDS = new Properties();
    public static final Color MAIN_COLOR = new Color(0, 184, 238);
    public static final Logger LOGGER = Logger.getLogger("Rika");
    public static final SemesterEnum CURRENT_SEMESTER = SemesterEnum.THREE;
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static final DateTimeFormatter PLAN_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
    public static PlanManager PLAN_MANAGER;

    private static JDA jda;

    @SneakyThrows
    public static void main(final String[] args) {
        long now = System.currentTimeMillis();

        ClassLoader classLoader = Rika.class.getClassLoader();
        CONFIG.loadFromXML(classLoader.getResourceAsStream("config.xml"));
        IDS.loadFromXML(classLoader.getResourceAsStream("ids.xml"));
        MESSAGES.loadFromXML(classLoader.getResourceAsStream("messages.xml"));
        COMMANDS.loadFromXML(classLoader.getResourceAsStream("commands.xml"));

        PLAN_MANAGER = new PlanManager();

        PlanMapper planMapper = new PlanMapper();
        Plan currentSemesterPlan = planMapper.mapFromSemester(CURRENT_SEMESTER);
        PLAN_MANAGER.addPlan(currentSemesterPlan);

        JDABuilder jdaBuilder = JDABuilder.create(System.getenv("TOKEN"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGE_REACTIONS);
        jdaBuilder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS);
        jdaBuilder.setBulkDeleteSplittingEnabled(false);
        jdaBuilder.setActivity(Activity.of(ConfigConstants.ACTIVITY_TYPE, MessageConstants.ACTIVITY));
        jda = jdaBuilder.addEventListeners(
                        new StudentListener(),
                        new CommandListener(),
                        new ReactionListener())
                .build();
        jda.awaitReady();

        LOGGER.info("\nRika bot loaded in " + (System.currentTimeMillis() - now) + " ms");
    }

    public static JDA getJDA() {
        return jda;
    }
}
