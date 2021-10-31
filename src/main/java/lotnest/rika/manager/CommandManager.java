package lotnest.rika.manager;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import lombok.Getter;
import lombok.SneakyThrows;
import lotnest.rika.command.Command;
import lotnest.rika.command.CommandInfo;
import lotnest.rika.command.CommandType;
import lotnest.rika.configuration.ConfigProperty;
import lotnest.rika.configuration.IdProperty;
import lotnest.rika.util.CommandUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class CommandManager extends ListenerAdapter {

    private final Set<String> commandChannelIds = new HashSet<>();
    private final Set<Command> commands = new HashSet<>();

    @SneakyThrows
    public CommandManager() {
        commandChannelIds.add(IdProperty.COMMANDS_CHANNEL);
        commandChannelIds.add(IdProperty.TESTING_CHANNEL);
        commandChannelIds.add(IdProperty.VERIFICATION_CHANNEL);

        for (final PojoClass pojoClass : PojoClassFactory.enumerateClassesByExtendingType("lotnest.rika.command", Command.class, null)) {
            commands.add((Command) pojoClass.getClazz().newInstance());
        }
    }

    public CommandInfo getCommandInfo(final @NotNull GuildMessageReceivedEvent event) {
        final Message message = event.getMessage();
        final String[] commandArguments = CommandUtil.getArguments(message);

        return new CommandInfo(
                event.getGuild(),
                event.getMember(),
                message,
                event.getChannel(),
                commandArguments
        );
    }

    public boolean isCommandChannel(final @NotNull MessageChannel channel) {
        return commandChannelIds.contains(channel.getId());
    }

    public Set<Command> getCommandsByType(final @NotNull CommandType commandType) {
        return commands.stream()
                .filter(command -> command.getCommandType().equals(commandType))
                .collect(Collectors.toSet());
    }

    @Override
    public void onGuildMessageReceived(final @NotNull GuildMessageReceivedEvent event) {
        final String message = event.getMessage().getContentRaw();
        final Member member = event.getMember();

        if (member == null || member.getUser().isBot()) {
            return;
        }

        if (message.startsWith(ConfigProperty.PREFIX)) {
            final CommandInfo commandInfo = getCommandInfo(event);
            if (commandInfo.getExecutor() == null) {
                return;
            }

            if (!isCommandChannel(commandInfo.getChannel())) {
                return;
            }

            commands.stream()
                    .filter(command -> command.getName().equalsIgnoreCase(commandInfo.getArgs()[0]))
                    .findFirst()
                    .ifPresent(command -> command.execute(commandInfo));
        }
    }

}
