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
public enum HobbyType {

    PROGRAMMING("⌨️", Rika.IDS.getProperty("hobby-programming-role")),
    GAMING("\uD83C\uDFAE", Rika.IDS.getProperty("hobby-gaming-role")),
    ANIMALS("\uD83D\uDC31", Rika.IDS.getProperty("hobby-animals-role")),
    LEARNING("\uD83D\uDD22", Rika.IDS.getProperty("hobby-learning-role")),
    READING("\uD83D\uDCDA", Rika.IDS.getProperty("hobby-reading-role")),
    MUSIC("\uD83C\uDFB5", Rika.IDS.getProperty("hobby-music-role")),
    SINGING("\uD83C\uDFA4", Rika.IDS.getProperty("hobby-singing-role")),
    INSTRUMENTS("\uD83C\uDFB8", Rika.IDS.getProperty("hobby-instruments-role")),
    SPORT("\uD83C\uDFC5", Rika.IDS.getProperty("hobby-sport-role")),
    DYI("\uD83D\uDD28", Rika.IDS.getProperty("hobby-dyi-role")),
    DRAWING("\uD83C\uDFA8", Rika.IDS.getProperty("hobby-drawing-role")),
    ASTRONOMY("⭐", Rika.IDS.getProperty("hobby-astronomy-role")),
    ANIME("\uD83C\uDDEF\uD83C\uDDF5", Rika.IDS.getProperty("hobby-anime-role")),
    DANCE("\uD83D\uDC83", Rika.IDS.getProperty("hobby-dance-role")),
    COOKING("\uD83E\uDDD1\u200D\uD83C\uDF73", Rika.IDS.getProperty("hobby-cooking-role"));

    private final String unicode;
    private final String roleId;

    @NotNull
    public static Optional<HobbyType> fromEmoji(@NotNull String emoji) {
        for (HobbyType hobbyType : values()) {
            if (hobbyType.getUnicode().equals(emoji)) {
                return Optional.of(hobbyType);
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
