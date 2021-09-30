package lotnest.rika.listener.student;

import lotnest.rika.configuration.Id;
import lotnest.rika.configuration.Message;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import static lotnest.rika.Rika.MAIN_COLOR;
import static lotnest.rika.util.MemberUtil.getNameAndTag;
import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class StudentListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull final GuildMemberJoinEvent event) {
        final Guild guild = event.getGuild();
        final MessageChannel channel = guild.getTextChannelById(Id.JOIN_LEAVE_MESSAGES_CHANNEL);
        if (channel == null) {
            return;
        }

        final Member member = event.getMember();
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setTitle(replacePlaceholders(Message.NEW_STUDENT_TITLE, guild.getMemberCount()));
        embedBuilder.setDescription(Message.NEW_STUDENT_DESCRIPTION);
        embedBuilder.setFooter(Message.FOOTER);

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public void onGuildMemberRemove(@NotNull final GuildMemberRemoveEvent event) {
        final Guild guild = event.getGuild();
        final MessageChannel channel = guild.getTextChannelById(Id.JOIN_LEAVE_MESSAGES_CHANNEL);
        if (channel == null) {
            return;
        }

        final Member member = event.getMember();
        if (member == null) {
            return;
        }

        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setTitle(replacePlaceholders(Message.STUDENT_LEFT_TITLE, guild.getMemberCount()));
        embedBuilder.setFooter(Message.FOOTER);

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public void onGuildMemberUpdateBoostTime(@NotNull final GuildMemberUpdateBoostTimeEvent event) {
        final Guild guild = event.getGuild();
        final MessageChannel channel = guild.getTextChannelById(Id.BOOST_MESSAGES_CHANNEL);
        if (channel == null) {
            return;
        }

        final Member member = event.getMember();
        final EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(MAIN_COLOR);
        embedBuilder.setAuthor(getNameAndTag(member), null, member.getUser().getAvatarUrl());
        embedBuilder.setTitle(replacePlaceholders(Message.BOOST_MESSAGE_TITLE, guild.getBoostCount()));
        embedBuilder.setDescription(Message.BOOST_MESSAGE_DESCRIPTION);
        embedBuilder.setFooter(Message.FOOTER);

        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }
}
