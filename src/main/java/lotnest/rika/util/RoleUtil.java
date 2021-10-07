package lotnest.rika.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RoleUtil {

    private RoleUtil() {
    }

    public static @NotNull List<Role> findRoles(@NotNull final String regex, @NotNull final Guild guild) {
        return findRoles(Pattern.compile(regex), guild);
    }

    public static @NotNull List<Role> findRoles(@NotNull final Pattern pattern, @NotNull final Guild guild) {
        final List<Role> roles = new ArrayList<>();
        for (final Role role : guild.getRoles()) {
            if (pattern.matcher(role.getName()).matches()) {
                roles.add(role);
            }
        }
        return roles;
    }

    public static @NotNull Map<Role, Integer> findRolesWithMemberCount(@NotNull final String regex, @NotNull final Guild guild) {
        return findRolesWithMemberCount(Pattern.compile(regex), guild);
    }

    public static @NotNull Map<Role, Integer> findRolesWithMemberCount(@NotNull final Pattern pattern, @NotNull final Guild guild) {
        final Map<Role, Integer> rolesWithMemberCount = new HashMap<>();
        findRoles(pattern, guild).forEach(role -> rolesWithMemberCount.put(role, guild.getMembersWithRoles(role).size()));
        return rolesWithMemberCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
