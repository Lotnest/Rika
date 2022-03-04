package dev.lotnest.rika.command;

import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand {

    public abstract @NotNull String getName();

    public abstract @NotNull CommandType getCommandType();

    public abstract void execute(final @NotNull CommandInfo commandInfo);
}
