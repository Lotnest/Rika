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

    private MemberUtil() {
    }

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

    public static void addRole(@NotNull final Member member, @NotNull final Role role) {
        member.getGuild().addRoleToMember(member, role).queue();
    }

    public static void removeRole(@NotNull final Member member, @NotNull final String roleId) {
        final Role role = member.getGuild().getRoleById(roleId);
        if (role != null) {
            member.getGuild().removeRoleFromMember(member, role).queue();
        }
    }

    public static void removeRole(@NotNull final Member member, final long roleId) {
        removeRole(member, String.valueOf(roleId));
    }

    public static void removeRole(@NotNull final Member member, @NotNull final Role role) {
        member.getGuild().removeRoleFromMember(member, role).queue();
    }

    public static @NotNull Optional<Role> findRole(@NotNull final Member member, @NotNull final String roleName) {
        return member.getGuild().getRoles().stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst();
    }

    public static void findRoleAndAddToMember(@NotNull final Pattern pattern, @NotNull final String roleName, @NotNull final Member member, @NotNull final String roleAddedMessage, @NotNull final String roleNotFoundMessage, @NotNull final String roleAlreadyAssignedMessage, @NotNull final String roleChangedMessage, @NotNull final EmbedBuilder embedBuilder, @NotNull final MessageChannel channel) {
        final Optional<Role> optionalExerciseRole = findRole(member, roleName);
        if (optionalExerciseRole.isPresent()) {
            final Role role = optionalExerciseRole.get();
            final List<Role> memberRoles = member.getRoles();

            if (memberRoles.contains(role)) {
                embedBuilder.setDescription(replacePlaceholders(roleAlreadyAssignedMessage, role.getName()));
            } else {
                final Optional<Role> optionalOtherRole = memberRoles.stream()
                        .filter(otherRole -> pattern.matcher(otherRole.getName()).matches())
                        .findFirst();
                if (optionalOtherRole.isPresent()) {
                    final Role otherRole = optionalOtherRole.get();
                    removeRole(member, otherRole);
                    addRole(member, role);
                    embedBuilder.setDescription(replacePlaceholders(roleChangedMessage, otherRole.getName(), role.getName()));
                } else {
                    addRole(member, role);
                    embedBuilder.setDescription(replacePlaceholders(roleAddedMessage, role.getName()));
                }
            }
        } else {
            embedBuilder.setDescription(replacePlaceholders(roleNotFoundMessage, roleName));
        }

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
