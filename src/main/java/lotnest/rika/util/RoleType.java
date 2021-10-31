package lotnest.rika.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lotnest.rika.configuration.IdProperty;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum RoleType {

    STUDENT(IdProperty.STUDENT_ROLE),
    MODERATOR(IdProperty.MODERATOR_ROLE),
    ADMINISTRATOR(IdProperty.ADMINISTRATOR_ROLE);

    private final String roleId;

    public static @NotNull Optional<RoleType> fromId(final @NotNull String roleId) {
        return Arrays.stream(values())
                .filter(roleType -> roleType.getRoleId().equals(roleId))
                .findFirst();
    }

    public boolean hasMember(final @NotNull Member member) {
        return member.getRoles().stream()
                .anyMatch(role -> role.getId().equals(roleId));
    }
}
