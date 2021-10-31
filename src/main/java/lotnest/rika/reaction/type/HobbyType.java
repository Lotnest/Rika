package lotnest.rika.reaction.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lotnest.rika.util.MemberUtil;
import net.dv8tion.jda.api.entities.Member;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static lotnest.rika.Rika.IDS;

@AllArgsConstructor
@Getter
public enum HobbyType {

    PROGRAMMING("⌨️", IDS.getProperty("hobby-programming-role")),
    GAMING("\uD83C\uDFAE", IDS.getProperty("hobby-gaming-role")),
    ANIMALS("\uD83D\uDC31", IDS.getProperty("hobby-animals-role")),
    LEARNING("\uD83D\uDD22", IDS.getProperty("hobby-learning-role")),
    READING("\uD83D\uDCDA", IDS.getProperty("hobby-reading-role")),
    MUSIC("\uD83C\uDFB5", IDS.getProperty("hobby-music-role")),
    SINGING("\uD83C\uDFA4", IDS.getProperty("hobby-singing-role")),
    INSTRUMENTS("\uD83C\uDFB8", IDS.getProperty("hobby-instruments-role")),
    SPORT("\uD83C\uDFC5", IDS.getProperty("hobby-sport-role")),
    DYI("\uD83D\uDD28", IDS.getProperty("hobby-dyi-role")),
    DRAWING("\uD83C\uDFA8", IDS.getProperty("hobby-drawing-role")),
    ASTRONOMY("⭐", IDS.getProperty("hobby-astronomy-role")),
    ANIME("\uD83C\uDDEF\uD83C\uDDF5", IDS.getProperty("hobby-anime-role")),
    DANCE("\uD83D\uDC83", IDS.getProperty("hobby-dance-role"));

    private final String unicode;
    private final String roleId;

    @NotNull
    public static Optional<HobbyType> fromEmoji(@NotNull final String emoji) {
        for (final HobbyType hobbyType : values()) {
            if (hobbyType.getUnicode().equals(emoji)) {
                return Optional.of(hobbyType);
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
