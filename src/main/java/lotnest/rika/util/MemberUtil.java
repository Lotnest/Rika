package lotnest.rika.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class MemberUtil {

    public static @NotNull String getNameAndTag(@Nullable final Member member) {
        if (member == null) {
            return "";
        }
        return member.getEffectiveName() + "#" + member.getUser().getDiscriminator();
    }

    public static void addRole(@NotNull final Member member, @NotNull final String roleId) {
        final Role role = member.getGuild().getRoleById(roleId);
        if (role != null) {
            member.getGuild().addRoleToMember(member, role).queue();
        }
    }

    public static void addRole(@NotNull final Member member, final long roleId) {
        addRole(member, String.valueOf(roleId));
    }

    public static void removeRole(@NotNull final Member member, @NotNull final String roleId) {
        final Role role = member.getGuild().getRoleById(roleId);
        if (role != null && member.getRoles().contains(role)) {
            member.getGuild().removeRoleFromMember(member, role).queue();
        }
    }

    public static void removeRole(@NotNull final Member member, final long roleId) {
        removeRole(member, String.valueOf(roleId));
    }

    public static @NotNull Optional<Role> findRole(@NotNull final Member member, @NotNull final String roleName) {
        return member.getGuild().getRoles().stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst();
    }

    public static void findRoleAndAddToMember(@NotNull final List<Pattern> patterns, @NotNull final String roleName, @NotNull final Member member, @NotNull final String roleAddedMessage, @NotNull final String roleNotFoundMessage, @NotNull final String invalidRoleTypeMessage, @NotNull final EmbedBuilder embedBuilder, @NotNull final MessageChannel channel) {
        for (final Pattern pattern : patterns) {
            if (pattern.matcher(roleName).matches()) {
                final Optional<Role> optionalExerciseRole = findRole(member, roleName);
                if (optionalExerciseRole.isPresent()) {
                    final Role role = optionalExerciseRole.get();
                    member.getGuild().addRoleToMember(member, role).queue();
                    embedBuilder.setDescription(replacePlaceholders(roleAddedMessage, role.getName()));
                } else {
                    embedBuilder.setDescription(replacePlaceholders(roleNotFoundMessage, roleName));
                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                }
                return;
            }
        }

    }
}
