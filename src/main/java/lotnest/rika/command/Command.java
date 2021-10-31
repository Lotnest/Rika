package lotnest.rika.command;

import org.jetbrains.annotations.NotNull;

public abstract class Command {

    public abstract String getName();

    public abstract CommandType getCommandType();

    public abstract void execute(@NotNull final CommandInfo commandInfo);
}
