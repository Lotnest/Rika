package dev.lotnest.rika.utils;

import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.configuration.MessageConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import static dev.lotnest.rika.Rika.MAIN_COLOR;

public class MessageUtils {

    private MessageUtils() {
    }

    public static @NotNull String replacePlaceholders(@NotNull String message, @NotNull Object... objects) {
        if (objects == null) {
            return message;
        }

        int i = 0;
        for (Object object : objects) {
            message = message.replace("{" + i++ + "}", object.toString());
        }
        return message;
    }

    public static @NotNull EmbedBuilder getDefaultEmbedBuilder(@NotNull Member member) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(MemberUtils.getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setFooter(MessageConstants.FOOTER);
        return embedBuilder;
    }

    public static @NotNull EmbedBuilder getCommandEmbedBuilder(@NotNull CommandInfo commandInfo, @NotNull String commandName) {
        Member member = commandInfo.getExecutor();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle(MessageUtils.replacePlaceholders(MessageConstants.COMMAND_TITLE, commandName));
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(MemberUtils.getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setFooter(MessageConstants.FOOTER);
        return embedBuilder;
    }
}
