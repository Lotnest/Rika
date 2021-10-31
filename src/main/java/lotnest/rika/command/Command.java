package lotnest.rika.command;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public abstract class Command {

    public abstract String getName();

    public abstract CommandType getCommandType();

    public abstract void execute(@NotNull final CommandInfo commandInfo);
}
