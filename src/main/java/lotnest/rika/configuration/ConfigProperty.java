package lotnest.rika.configuration;

import net.dv8tion.jda.api.entities.Activity;

import static lotnest.rika.Rika.CONFIG;

public class ConfigProperty {

    public static final String PREFIX = CONFIG.getProperty("prefix", "!");
    public static final Activity.ActivityType ACTIVITY_TYPE = Activity.ActivityType.valueOf(CONFIG.getProperty("activity-type", Activity.ActivityType.LISTENING.toString()));
}
