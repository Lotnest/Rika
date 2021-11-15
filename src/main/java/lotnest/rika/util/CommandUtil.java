package lotnest.rika.util;

import lotnest.rika.configuration.IdProperty;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static lotnest.rika.configuration.ConfigProperty.PREFIX;

public class CommandUtil {

    private CommandUtil() {
    }

    public static boolean isValidCommand(final @Nullable String message) {
        return isValidCommand(message, null);
    }

    public static boolean isValidCommand(final @Nullable String message, final @Nullable String expectedCommand) {
        return message != null && message.startsWith(PREFIX + (expectedCommand != null ? expectedCommand : ""));
    }

    public static String @NotNull [] getArguments(final @Nullable String command) {
        if (!isValidCommand(command)) {
            return new String[]{};
        }
        return command.replaceFirst(PREFIX, "").split(" ");
    }

    public static String @NotNull [] getArguments(final @Nullable Message message) {
        if (message == null) {
            return new String[]{};
        }

        final String rawContent = message.getContentRaw();
        if (!isValidCommand(rawContent)) {
            return new String[]{};
        }
        return rawContent.replaceFirst(PREFIX, "").split(" ");
    }

    public static @NotNull Optional<Map<MessageChannel, Member>> getCommandChannel(final @NotNull GuildMessageReceivedEvent event) {
        final MessageChannel channel = event.getChannel();
        if (!channel.getId().equals(IdProperty.COMMANDS_CHANNEL) && !channel.getId().equals(IdProperty.TESTING_CHANNEL) && !channel.getId().equals(IdProperty.VERIFICATION_CHANNEL)) {
            return Optional.empty();
        }

        final Member member = event.getMember();
        if (member == null || member.getUser().isBot()) {
            return Optional.empty();
        }
        return Optional.of(Collections.singletonMap(channel, member));
    }
}
