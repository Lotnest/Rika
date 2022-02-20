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
import java.util.Properties;
import java.util.logging.Logger;

public class Rika {

    public static final Properties CONFIG = new Properties();
    public static final Properties IDS = new Properties();
    public static final Properties MESSAGES = new Properties();
    public static final Properties COMMANDS = new Properties();
    public static final Color MAIN_COLOR = new Color(0, 184, 238);
    public static final Logger LOGGER = Logger.getLogger("Rika");
    public static final SemesterEnum CURRENT_SEMESTER = SemesterEnum.FOUR;

    private static PlanManager PLAN_MANAGER;
    private static JDA JDA;

    @SneakyThrows
    public static void main(final String[] args) {
        long now = System.currentTimeMillis();

        loadPropertiesFromXML();
        loadCurrentSemesterPlan();
        buildJDA();

        awaitReady(now);
    }

    public static JDA getJDA() {
        return JDA;
    }

    @SneakyThrows
    public static void buildJDA() {
        JDABuilder jdaBuilder = JDABuilder.create(System.getenv("TOKEN"), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_EMOJIS, GatewayIntent.GUILD_MESSAGE_REACTIONS);
        jdaBuilder.disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS);
        jdaBuilder.setActivity(Activity.of(ConfigConstants.ACTIVITY_TYPE, MessageConstants.ACTIVITY));
        JDA = jdaBuilder.addEventListeners(new StudentListener(), new CommandListener(), new ReactionListener())
                .build();
    }

    public static PlanManager getPlanManager() {
        if (PLAN_MANAGER == null) {
            PLAN_MANAGER = new PlanManager();
        }
        return PLAN_MANAGER;
    }

    public static void loadCurrentSemesterPlan() {
        PlanMapper planMapper = new PlanMapper();
        Plan currentSemesterPlan = planMapper.mapFromSemester(CURRENT_SEMESTER);
        getPlanManager().addPlan(currentSemesterPlan);
    }

    @SneakyThrows
    private static void loadPropertiesFromXML() {
        ClassLoader classLoader = Rika.class.getClassLoader();
        CONFIG.loadFromXML(classLoader.getResourceAsStream("config.xml"));
        IDS.loadFromXML(classLoader.getResourceAsStream("ids.xml"));
        MESSAGES.loadFromXML(classLoader.getResourceAsStream("messages.xml"));
        COMMANDS.loadFromXML(classLoader.getResourceAsStream("commands.xml"));
    }

    @SneakyThrows
    private static void awaitReady(long millisStarted) {
        JDA.awaitReady();
        LOGGER.info(String.format("\nRika Discord Bot started in %d ms", System.currentTimeMillis() - millisStarted));
    }
}
