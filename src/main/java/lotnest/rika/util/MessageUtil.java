package lotnest.rika.util;

import lotnest.rika.command.CommandInfo;
import lotnest.rika.configuration.MessageProperty;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import static lotnest.rika.Rika.MAIN_COLOR;
import static lotnest.rika.configuration.MessageProperty.FOOTER;
import static lotnest.rika.util.MemberUtil.getNameAndTag;

public class MessageUtil {

    private MessageUtil() {
    }

    public static @NotNull String replacePlaceholders(@NotNull String message, final @NotNull Object... objects) {
        if (objects == null) {
            return message;
        }

        int i = 0;
        for (final Object object : objects) {
            message = message.replace("{" + i + "}", objects[i++].toString());
        }
        return message;
    }

    public static @NotNull EmbedBuilder getDefaultEmbedBuilder(final @NotNull Member member) {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setFooter(FOOTER);
        return embedBuilder;
    }

    public static @NotNull EmbedBuilder getCommandEmbedBuilder(final @NotNull CommandInfo commandInfo, final @NotNull String commandName) {
        final Member member = commandInfo.getExecutor();

        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(MessageUtil.replacePlaceholders(MessageProperty.COMMAND_TITLE, commandName));
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setFooter(FOOTER);
        return embedBuilder;
    }
}
