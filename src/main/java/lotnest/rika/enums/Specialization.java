package lotnest.rika.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lotnest.rika.util.MemberUtil;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static lotnest.rika.Rika.IDS;

@AllArgsConstructor
@Getter
public enum Specialization {

    DATABASES("\uD83C\uDD94", IDS.getProperty("specialization-databases-role")),
    SOFTWARE_ENGINEERING_AND_DATABASES("⌨️", IDS.getProperty("specialization-software-engineering-and-databases-role")),
    SYSTEM_AND_NETWORK_PROGRAMMING("\uD83D\uDDA5️", IDS.getProperty("specialization-system-and-network-programming-role")),
    INTELLIGENT_DATA_PROCESSING_SYSTEMS("\uD83D\uDCCA", IDS.getProperty("specialization-intelligent-data-processing-systems-role")),
    MULTIMEDIA("\uD83C\uDFB5", IDS.getProperty("specialization-multimedia-role")),
    MULTIMEDIA_3D_ANIMATION("\uD83C\uDF9E️", IDS.getProperty("specialization-multimedia-3d-animation-role")),
    MULTIMEDIA_GAME_PROGRAMMING("\uD83D\uDC7E", IDS.getProperty("specialization-multimedia-game-programming-role")),
    ROBOTICS("\uD83E\uDD16", IDS.getProperty("specialization-robotics-role")),
    BUSINESS_APPLICATIONS_PROGRAMMING("\uD83D\uDCBC", IDS.getProperty("specialization-business-applications-programming-role")),
    MOBILE_DEVICES_NETWORKS("\uD83D\uDCF1", IDS.getProperty("specialization-mobile-devices-networks-role"));

    private final String unicode;
    private final String roleId;

    @NotNull
    public static Optional<Specialization> fromEmoji(@NotNull final String emoji) {
        for (final Specialization specialization : values()) {
            if (specialization.getUnicode().equals(emoji)) {
                return Optional.of(specialization);
            }
        }
        return Optional.empty();
    }

    public void addRole(@NotNull final Member member) {
        MemberUtil.addRole(member, roleId);
    }

    public void removeRole(@NotNull final Member member) {
        MemberUtil.removeRole(member, roleId);
    }
}
