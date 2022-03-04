package dev.lotnest.rika.command.fun;

import dev.lotnest.rika.command.AbstractCommand;
import dev.lotnest.rika.command.CommandInfo;
import dev.lotnest.rika.command.CommandType;
import dev.lotnest.rika.configuration.CommandConstants;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ITNCommand extends AbstractCommand {

    @Override
    public @NotNull String getName() {
        return CommandConstants.ITN;
    }

    @Override
    public @NotNull CommandType getCommandType() {
        return CommandType.FUN;
    }

    @SneakyThrows
    @Override
    public void execute(@NotNull CommandInfo commandInfo) {
        commandInfo.getChannel()
                .sendFile(new File("src/main/resources/images/ITN.png"))
                .queue();
    }
}
