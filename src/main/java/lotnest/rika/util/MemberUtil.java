package lotnest.rika.util;

import lotnest.rika.configuration.Id;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
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

    public static @NotNull Optional<Role> findRole(@NotNull final Member member, final long roleId) {
        return member.getGuild().getRoles().stream()
                .filter(role -> role.getIdLong() == roleId)
                .findFirst();
    }

    public static @NotNull Optional<Role> findRole(@NotNull final Member member, @NotNull final String roleName) {
        return member.getGuild().getRoles().stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst();
    }

    public static void findRoleAndAddToMember(@NotNull final String roleName, @NotNull final Member member, @NotNull final String roleAddedMessage, @NotNull final String roleNotFoundMessage, @NotNull final String roleAlreadyAssignedMessage, @NotNull final EmbedBuilder embedBuilder, @NotNull final MessageChannel channel) {
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

    public static void findRoleAndAddToMember(@NotNull final Pattern pattern, @NotNull final String roleName, @NotNull final Member member, @NotNull final String roleAddedMessage, @NotNull final String roleNotFoundMessage, @NotNull final String roleAlreadyAssignedMessage, @NotNull final String roleChangedMessage, @NotNull final EmbedBuilder embedBuilder, @NotNull final MessageChannel channel) {
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

    public static boolean hasRole(@NotNull final Member member, @NotNull final Role role) {
        return hasRole(member, role.getName());
    }

    public static boolean hasRole(@NotNull final Member member, final long roleId) {
        return findRole(member, roleId).isPresent();
    }

    public static boolean hasRole(@NotNull final Member member, @NotNull final String roleName) {
        return findRole(member, roleName).isPresent();
    }

    public static boolean isRole(@NotNull final GuildMessageReceivedEvent event, final boolean messageIfNoRole, @NotNull final String roleIdMessage) {
        final Member member = event.getMember();
        if (member == null) {
            return false;
        }

        final boolean hasRole = hasRole(member, Long.parseLong(roleIdMessage));

        if (messageIfNoRole && !hasRole) {
            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
        }
        return hasRole;
    }

    public static boolean isStudent(@NotNull final GuildMessageReceivedEvent event, final boolean messageIfNoRole) {
        return isRole(event, messageIfNoRole, Id.STUDENT_ROLE);
    }

    public static boolean isModerator(@NotNull final GuildMessageReceivedEvent event, final boolean messageIfNoRole) {
        return isRole(event, messageIfNoRole, Id.MODERATOR_ROLE);
    }

    public static boolean isAdmin(@NotNull final GuildMessageReceivedEvent event, final boolean messageIfNoRole) {
        return isRole(event, messageIfNoRole, Id.ADMIN_ROLE);
    }
}
