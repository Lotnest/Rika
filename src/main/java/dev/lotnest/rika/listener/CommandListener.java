package dev.lotnest.rika.listener;

import dev.lotnest.rika.command.Command;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.command.fun.CatCommand;
import dev.lotnest.rika.command.fun.DogCommand;
import dev.lotnest.rika.command.fun.ITEmployeeCommand;
import dev.lotnest.rika.command.fun.ITNCommand;
import dev.lotnest.rika.command.student.GroupCommand;
import dev.lotnest.rika.command.student.GroupsCommand;
import dev.lotnest.rika.command.student.PlanCommand;
import dev.lotnest.rika.command.student.WelcomeCommand;
import dev.lotnest.rika.command.utils.MuteCommand;
import dev.lotnest.rika.command.utils.PingCommand;
import dev.lotnest.rika.configuration.ConfigConstants;
import dev.lotnest.rika.configuration.IdConstants;
import dev.lotnest.rika.utils.CommandUtils;
import lombok.Getter;
import lombok.SneakyThrows;
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
public class CommandListener extends ListenerAdapter {

    private final Set<String> commandChannelIds = new HashSet<>();
    private final Set<Command> commands = new HashSet<>();

    @SneakyThrows
    public CommandListener() {
        commandChannelIds.add(IdConstants.COMMANDS_CHANNEL);
        commandChannelIds.add(IdConstants.TESTING_CHANNEL);
        commandChannelIds.add(IdConstants.VERIFICATION_CHANNEL);

        // Student
        commands.add(new WelcomeCommand());
        commands.add(new GroupCommand());
        commands.add(new GroupsCommand());
        commands.add(new PlanCommand());

        // Fun
        commands.add(new CatCommand());
        commands.add(new DogCommand());
        commands.add(new ITEmployeeCommand());
        commands.add(new ITNCommand());

        // Utils
        commands.add(new PingCommand());
        commands.add(new MuteCommand());
    }

    public CommandInfo getCommandInfo(@NotNull GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        String[] commandArguments = CommandUtils.getArguments(message);

        return new CommandInfo(
                event.getGuild(),
                event.getMember(),
                message,
                event.getChannel(),
                commandArguments
        );
    }

    public boolean isCommandChannel(@NotNull MessageChannel channel) {
        return commandChannelIds.contains(channel.getId());
    }

    public Set<Command> getCommandsByType(@NotNull CommandType commandType) {
        return commands.stream()
                .filter(command -> command.getCommandType().equals(commandType))
                .collect(Collectors.toSet());
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        Member member = event.getMember();

        if (member == null || member.getUser().isBot()) {
            return;
        }

        if (message.startsWith(ConfigConstants.PREFIX)) {
            CommandInfo commandInfo = getCommandInfo(event);
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
