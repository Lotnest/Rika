package dev.lotnest.rika.reaction.type;

import dev.lotnest.rika.Rika;
import dev.lotnest.rika.utils.MemberUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum SpecializationType {

    DATABASES("\uD83C\uDD94", Rika.IDS.getProperty("specialization-databases-role")),
    SOFTWARE_ENGINEERING_AND_DATABASES("⌨️", Rika.IDS.getProperty("specialization-software-engineering-and-databases-role")),
    SYSTEM_AND_NETWORK_PROGRAMMING("\uD83D\uDDA5️", Rika.IDS.getProperty("specialization-system-and-network-programming-role")),
    INTELLIGENT_DATA_PROCESSING_SYSTEMS("\uD83D\uDCCA", Rika.IDS.getProperty("specialization-intelligent-data-processing-systems-role")),
    MULTIMEDIA("\uD83C\uDFB5", Rika.IDS.getProperty("specialization-multimedia-role")),
    MULTIMEDIA_3D_ANIMATION("\uD83C\uDF9E️", Rika.IDS.getProperty("specialization-multimedia-3d-animation-role")),
    MULTIMEDIA_GAME_PROGRAMMING("\uD83D\uDC7E", Rika.IDS.getProperty("specialization-multimedia-game-programming-role")),
    ROBOTICS("\uD83E\uDD16", Rika.IDS.getProperty("specialization-robotics-role")),
    BUSINESS_APPLICATIONS_PROGRAMMING("\uD83D\uDCBC", Rika.IDS.getProperty("specialization-business-applications-programming-role")),
    MOBILE_DEVICES_NETWORKS("\uD83D\uDCF1", Rika.IDS.getProperty("specialization-mobile-devices-networks-role"));

    private final String unicode;
    private final String roleId;

    @NotNull
    public static Optional<SpecializationType> fromEmoji(@NotNull String emoji) {
        for (SpecializationType specializationType : values()) {
            if (specializationType.getUnicode().equals(emoji)) {
                return Optional.of(specializationType);
            }
        }
        return Optional.empty();
    }

    public void addRole(@NotNull Member member) {
        MemberUtils.addRole(member, roleId);
    }

    public void removeRole(@NotNull Member member) {
        MemberUtils.removeRole(member, roleId);
    }
}
