package lotnest.rika.utils;

import lotnest.rika.command.CommandInfo;
import lotnest.rika.configuration.MessageProperty;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import static lotnest.rika.Rika.MAIN_COLOR;
import static lotnest.rika.configuration.MessageProperty.FOOTER;
import static lotnest.rika.utils.MemberUtils.getNameAndTag;

public class MessageUtils {

    private MessageUtils() {
    }

    public static @NotNull String replacePlaceholders(@NotNull String message, @NotNull Object... objects) {
        if (objects == null) {
            return message;
        }

        int i = 0;
        for (Object object : objects) {
            message = message.replace("{" + i + "}", objects[i++].toString());
        }
        return message;
    }

    public static @NotNull EmbedBuilder getDefaultEmbedBuilder(@NotNull Member member) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setFooter(FOOTER);
        return embedBuilder;
    }

    public static @NotNull EmbedBuilder getCommandEmbedBuilder(@NotNull CommandInfo commandInfo, @NotNull String commandName) {
        Member member = commandInfo.getExecutor();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(MessageUtils.replacePlaceholders(MessageProperty.COMMAND_TITLE, commandName));
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setFooter(FOOTER);
        return embedBuilder;
    }
}
