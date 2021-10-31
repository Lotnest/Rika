package lotnest.rika.reaction;

import org.jetbrains.annotations.NotNull;

public abstract class Reaction {

    public abstract String getMessageId();

    public abstract void handleReactionAdd(final @NotNull ReactionInfo reactionInfo);

    public abstract void handleReactionRemove(final @NotNull ReactionInfo reactionInfo);
}
