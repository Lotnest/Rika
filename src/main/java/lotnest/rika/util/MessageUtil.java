package lotnest.rika.util;

import lotnest.rika.configuration.Id;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static lotnest.rika.Rika.MAIN_COLOR;
import static lotnest.rika.Rika.PREFIX;
import static lotnest.rika.configuration.Message.FOOTER;
import static lotnest.rika.util.MemberUtil.getNameAndTag;

public class MessageUtil {

    public static boolean isValidCommand(@Nullable final String message) {
        return isValidCommand(message, null);
    }

    public static boolean isValidCommand(@Nullable final String message, final @Nullable String expectedCommand) {
        return message != null && message.startsWith(PREFIX + (expectedCommand != null ? expectedCommand : ""));
    }

    public static String @NotNull [] getArguments(@Nullable final String command) {
        if (!isValidCommand(command)) {
            return new String[]{};
        }
        return command.replaceFirst(PREFIX, "").split(" ");
    }

    public static String replacePlaceholders(@NotNull String message, final Object @Nullable ... objects) {
        if (objects == null) {
            return message;
        }

        int i = 0;
        for (final Object object : objects) {
            message = message.replace("{" + i + "}", objects[i++].toString());
        }
        return message;
    }

    public static @NotNull EmbedBuilder getDefaultEmbedBuilder(@NotNull final Member member) {
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setFooter(FOOTER);
        return embedBuilder;
    }

    public static @NotNull Optional<Map<MessageChannel, Member>> getCommandChannel(final @NotNull GuildMessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        if (!channel.getId().equals(Id.COMMANDS_CHANNEL) && !channel.getId().equals(Id.TESTING_CHANNEL)) {
            return Optional.empty();
        }

        final Member member = event.getMember();
        if (member == null || member.getUser().isBot()) {
            return Optional.empty();
        }
        return Optional.of(Collections.singletonMap(channel, member));
    }
}
