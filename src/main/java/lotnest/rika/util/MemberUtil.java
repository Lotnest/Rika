package lotnest.rika.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            member.getGuild().addRoleToMember(member, role).submit();
        }
    }

    public static void addRole(@NotNull final Member member, final long roleId) {
        addRole(member, String.valueOf(roleId));
    }

    public static void removeRole(@NotNull final Member member, @NotNull final String roleId) {
        final Role role = member.getGuild().getRoleById(roleId);
        if (role != null && member.getRoles().contains(role)) {
            member.getGuild().removeRoleFromMember(member, role).submit();
        }
    }

    public static void removeRole(@NotNull final Member member, final long roleId) {
        removeRole(member, String.valueOf(roleId));
    }
}
