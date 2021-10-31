package lotnest.rika.util;

import lotnest.rika.configuration.Id;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static lotnest.rika.configuration.Config.PREFIX;

public class CommandUtil {

    private CommandUtil() {
    }

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

    public static @NotNull Optional<Map<MessageChannel, Member>> getCommandChannel(@NotNull final GuildMessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        if (!channel.getId().equals(Id.COMMANDS_CHANNEL) && !channel.getId().equals(Id.TESTING_CHANNEL) && !channel.getId().equals(Id.VERIFICATION_CHANNEL)) {
            return Optional.empty();
        }

        final Member member = event.getMember();
        if (member == null || member.getUser().isBot()) {
            return Optional.empty();
        }
        return Optional.of(Collections.singletonMap(channel, member));
    }
}
