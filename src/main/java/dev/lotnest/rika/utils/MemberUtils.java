package dev.lotnest.rika.utils;

import dev.lotnest.rika.command.student.GroupCommand;
import dev.lotnest.rika.configuration.IdConstants;
import dev.lotnest.rika.configuration.MessageConstants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static dev.lotnest.rika.utils.MessageUtils.replacePlaceholders;

public class MemberUtils {

    private MemberUtils() {
    }

    public static @NotNull String getNameAndTag(@Nullable Member member) {
        if (member == null) {
            return "";
        }
        return member.getEffectiveName() + "#" + member.getUser().getDiscriminator();
    }

    public static void addRole(@NotNull Member member, @NotNull String roleId) {
        Role role = member.getGuild().getRoleById(roleId);
        if (role != null) {
            member.getGuild().addRoleToMember(member, role).queue();
        }
    }

    public static void addRole(@NotNull Member member, long roleId) {
        addRole(member, String.valueOf(roleId));
    }

    public static void addRole(@NotNull Member member, @NotNull Role role) {
        member.getGuild().addRoleToMember(member, role).queue();
    }

    public static void removeRole(@NotNull Member member, @NotNull String roleId) {
        Role role = member.getGuild().getRoleById(roleId);
        if (role != null) {
            member.getGuild().removeRoleFromMember(member, role).queue();
        }
    }

    public static void removeRole(@NotNull Member member, long roleId) {
        removeRole(member, String.valueOf(roleId));
    }

    public static void removeRole(@NotNull Member member, @NotNull Role role) {
        member.getGuild().removeRoleFromMember(member, role).queue();
    }

    public static @NotNull Optional<Role> findRole(@NotNull Member member, long roleId) {
        return member.getGuild().getRoles().stream()
                .filter(role -> role.getIdLong() == roleId)
                .findFirst();
    }

    public static @NotNull Optional<Role> findRole(@NotNull Member member, @NotNull String roleName) {
        return member.getGuild().getRoles().stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst();
    }

    public static void findRoleAndAddToMember(@NotNull String roleName, @NotNull Member member, @NotNull String roleAddedMessage, @NotNull String roleNotFoundMessage, @NotNull String roleAlreadyAssignedMessage, @NotNull EmbedBuilder embedBuilder, @NotNull MessageChannel channel) {
        if (roleName.isEmpty()) {
            return;
        }

        Optional<Role> optionalRole = findRole(member, roleName);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            List<Role> memberRoles = member.getRoles();

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

    public static void findGroupRoleAndAddToMember(@NotNull String group, @NotNull Member member, @NotNull EmbedBuilder embedBuilder, @NotNull MessageChannel channel) {
        Pattern pattern = GroupCommand.EXERCISE_PATTERN.matcher(group).matches() ? GroupCommand.EXERCISE_PATTERN : GroupCommand.LANGUAGE_PATTERN.matcher(group).matches() ? GroupCommand.LANGUAGE_PATTERN : null;
        if (pattern == null) {
            embedBuilder.setDescription(MessageConstants.GROUP_COMMAND_INVALID);
            channel.sendMessageEmbeds(embedBuilder.build()).queue();
            return;
        }

        findRoleAndAddToMember(
                pattern,
                group,
                member,
                MessageConstants.GROUP_COMMAND_ADDED,
                MessageConstants.GROUP_COMMAND_NOT_FOUND,
                MessageConstants.GROUP_COMMAND_ALREADY_ASSIGNED,
                MessageConstants.GROUP_COMMAND_CHANGED,
                embedBuilder,
                channel
        );
    }

    public static void findRoleAndAddToMember(@NotNull Pattern pattern, @NotNull String roleName, @NotNull Member member, @NotNull String roleAddedMessage, @NotNull String roleNotFoundMessage, @NotNull String roleAlreadyAssignedMessage, @NotNull String roleChangedMessage, @NotNull EmbedBuilder embedBuilder, @NotNull MessageChannel channel) {
        Optional<Role> optionalRole = findRole(member, roleName);
        if (optionalRole.isPresent()) {
            Role role = optionalRole.get();
            List<Role> memberRoles = member.getRoles();

            if (memberRoles.contains(role)) {
                embedBuilder.setDescription(replacePlaceholders(roleAlreadyAssignedMessage, role.getName()));
            } else {
                Optional<Role> optionalOtherRole = memberRoles.stream()
                        .filter(otherRole -> pattern.matcher(otherRole.getName()).matches())
                        .findFirst();
                if (optionalOtherRole.isPresent()) {
                    Role otherRole = optionalOtherRole.get();
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

    public static boolean hasRole(@NotNull Member member, @NotNull Role role) {
        return hasRole(member, role.getName());
    }

    public static boolean hasRole(@NotNull Member member, long roleId) {
        return findRole(member, roleId).isPresent();
    }

    public static boolean hasRole(@NotNull Member member, @NotNull String roleName) {
        return findRole(member, roleName).isPresent();
    }

    public static boolean hasRole(@NotNull Member member, @Nullable MessageChannel messageChannelIfNoRole, @NotNull String roleIdMessage) {
        boolean hasRole = hasRole(member, Long.parseLong(roleIdMessage));
        if (!hasRole && messageChannelIfNoRole != null) {
            EmbedBuilder embedBuilder = MessageUtils.getDefaultEmbedBuilder(member);
            messageChannelIfNoRole.sendMessageEmbeds(embedBuilder.build()).queue();
        }
        return hasRole;
    }

    public static boolean isStudent(@NotNull Member member) {
        return hasRole(member, null, IdConstants.STUDENT_ROLE);
    }

    public static boolean isModerator(@NotNull Member member, @Nullable MessageChannel messageChannelIfNoRole) {
        return hasRole(member, messageChannelIfNoRole, IdConstants.MODERATOR_ROLE);
    }

    public static boolean isAdmin(@NotNull Member member, @Nullable MessageChannel messageChannelIfNoRole) {
        return hasRole(member, messageChannelIfNoRole, IdConstants.ADMINISTRATOR_ROLE);
    }

    public static boolean isExclusivePermissionUser(@NotNull String memberId) {
        return memberId.equals("209254420192428032");
    }

    public static boolean isValidStudent(@NotNull String input) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(IService.API_URL + "student/validate/" + input.replaceAll(" ", "%20")))
                .build();
        try {
            return Boolean.parseBoolean(IService.HTTP_CLIENT.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .get(10L, TimeUnit.SECONDS));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
