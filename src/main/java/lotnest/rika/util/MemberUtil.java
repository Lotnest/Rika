package lotnest.rika.util;

import lotnest.rika.command.student.GroupCommand;
import lotnest.rika.configuration.IdProperty;
import lotnest.rika.configuration.MessageProperty;
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

    public static void addRole(final @NotNull Member member, final @NotNull String roleId) {
        final Role role = member.getGuild().getRoleById(roleId);
        if (role != null) {
            member.getGuild().addRoleToMember(member, role).queue();
        }
    }

    public static void addRole(final @NotNull Member member, final long roleId) {
        addRole(member, String.valueOf(roleId));
    }

    public static void addRole(final @NotNull Member member, final @NotNull Role role) {
        member.getGuild().addRoleToMember(member, role).queue();
    }

    public static void removeRole(final @NotNull Member member, final @NotNull String roleId) {
        final Role role = member.getGuild().getRoleById(roleId);
        if (role != null) {
            member.getGuild().removeRoleFromMember(member, role).queue();
        }
    }

    public static void removeRole(final @NotNull Member member, final long roleId) {
        removeRole(member, String.valueOf(roleId));
    }

    public static void removeRole(final @NotNull Member member, final @NotNull Role role) {
        member.getGuild().removeRoleFromMember(member, role).queue();
    }

    public static @NotNull Optional<Role> findRole(final @NotNull Member member, final long roleId) {
        return member.getGuild().getRoles().stream()
                .filter(role -> role.getIdLong() == roleId)
                .findFirst();
    }

    public static @NotNull Optional<Role> findRole(final @NotNull Member member, final @NotNull String roleName) {
        return member.getGuild().getRoles().stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst();
    }

    public static void findRoleAndAddToMember(final @NotNull String roleName, final @NotNull Member member, final @NotNull String roleAddedMessage, final @NotNull String roleNotFoundMessage, final @NotNull String roleAlreadyAssignedMessage, final @NotNull EmbedBuilder embedBuilder, final @NotNull MessageChannel channel) {
        if (roleName.isEmpty()) {
            return;
        }

        final Optional<Role> optionalRole = findRole(member, roleName);
        if (optionalRole.isPresent()) {
            final Role role = optionalRole.get();
            final List<Role> memberRoles = member.getRoles();

            if (memberRoles.contains(role)) {
                embedBuilder.setDescription(replacePlaceholders(roleAlreadyAssignedMessage, member.getAsMention()));
            } else {
                addRole(member, role);
                embedBuilder.setDescription(replacePlaceholders(roleAddedMessage, member.getAsMention()));
            }
        } else {
            embedBuilder.setDescription(replacePlaceholders(roleNotFoundMessage, roleName));
        }
    }

    public static void findGroupRoleAndAddToMember(final @NotNull String group, final @NotNull Member member, final @NotNull EmbedBuilder embedBuilder, final @NotNull MessageChannel channel) {
        final Pattern pattern = GroupCommand.EXERCISE_PATTERN.matcher(group).matches() ? GroupCommand.EXERCISE_PATTERN : GroupCommand.LECTURE_PATTERN.matcher(group).matches() ? GroupCommand.EXERCISE_PATTERN : null;
        if (pattern == null) {
            embedBuilder.setDescription(MessageProperty.GROUP_COMMAND_INVALID_TYPE);
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        findRoleAndAddToMember(
                pattern,
                group,
                member,
                MessageProperty.GROUP_COMMAND_ADDED_EXERCISE,
                MessageProperty.GROUP_COMMAND_EXERCISE_NOT_FOUND,
                MessageProperty.GROUP_COMMAND_ALREADY_ASSIGNED,
                MessageProperty.GROUP_COMMAND_CHANGED_EXERCISE,
                embedBuilder,
                channel
        );
    }

    public static void findRoleAndAddToMember(final @NotNull Pattern pattern, final @NotNull String roleName, final @NotNull Member member, final @NotNull String roleAddedMessage, final @NotNull String roleNotFoundMessage, final @NotNull String roleAlreadyAssignedMessage, final @NotNull String roleChangedMessage, final @NotNull EmbedBuilder embedBuilder, final @NotNull MessageChannel channel) {
        final Optional<Role> optionalRole = findRole(member, roleName);
        if (optionalRole.isPresent()) {
            final Role role = optionalRole.get();
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

    public static boolean hasRole(final @NotNull Member member, final @NotNull Role role) {
        return hasRole(member, role.getName());
    }

    public static boolean hasRole(final @NotNull Member member, final long roleId) {
        return findRole(member, roleId).isPresent();
    }

    public static boolean hasRole(final @NotNull Member member, final @NotNull String roleName) {
        return findRole(member, roleName).isPresent();
    }

    public static boolean hasRole(final @NotNull Member member, final @Nullable MessageChannel messageChannelIfNoRole, final @NotNull String roleIdMessage) {
        final boolean hasRole = hasRole(member, Long.parseLong(roleIdMessage));
        if (!hasRole && messageChannelIfNoRole != null) {
            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            messageChannelIfNoRole.sendMessageEmbeds(embedBuilder.build()).queue();
        }
        return hasRole;
    }

    public static boolean isStudent(final @NotNull Member member, final @Nullable MessageChannel messageChannelIfNoRole) {
        return hasRole(member, messageChannelIfNoRole, IdProperty.STUDENT_ROLE);
    }

    public static boolean isModerator(final @NotNull Member member, final @Nullable MessageChannel messageChannelIfNoRole) {
        return hasRole(member, messageChannelIfNoRole, IdProperty.MODERATOR_ROLE);
    }

    public static boolean isAdmin(final @NotNull Member member, final @Nullable MessageChannel messageChannelIfNoRole) {
        return hasRole(member, messageChannelIfNoRole, IdProperty.ADMINISTRATOR_ROLE);
    }

    public static boolean isLotnest(final @NotNull String memberId) {
        return memberId.equals("209254420192428032");
    }
}
