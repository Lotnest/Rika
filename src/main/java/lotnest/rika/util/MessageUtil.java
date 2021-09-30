package lotnest.rika.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static lotnest.rika.Rika.PREFIX;

public class MessageUtil {

    public static boolean isValidCommand(@Nullable final String message) {
        return message != null && message.startsWith(PREFIX);
    }

    public static @NotNull List<String> getArguments(@Nullable final String command) {
        if (!isValidCommand(command)) {
            return new ArrayList<>();
        }

        final List<String> args = new ArrayList<>(List.of(command.split("!")));
        args.remove(0);
        return args;
    }

    public static String replacePlaceholders(@NotNull String message, final Object @NotNull ... objects) {
        int i = 0;
        for (final Object object : objects) {
            message = message.replace("{" + i + "}", objects[i++].toString());
        }
        return message;
    }
}
