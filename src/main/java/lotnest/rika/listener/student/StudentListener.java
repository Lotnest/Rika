package lotnest.rika.listener.student;

import lotnest.rika.configuration.Id;
import lotnest.rika.configuration.Message;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateBoostTimeEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;

import static lotnest.rika.util.MessageUtil.replacePlaceholders;

public class StudentListener extends ListenerAdapter {

    public static final Pattern STUDENT_PATTERN = Pattern.compile("[Ss](\\d){3,5} [AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]{2,} [AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]{2,}");

    @Override
    public void onGuildMemberJoin(@NotNull final GuildMemberJoinEvent event) {
        final Guild guild = event.getGuild();
        final MessageChannel channel = guild.getTextChannelById(Id.JOIN_LEAVE_MESSAGES_CHANNEL);
        if (channel == null) {
            return;
        }

        final Member member = event.getMember();
        final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
        embedBuilder.setTitle(replacePlaceholders(Message.NEW_STUDENT_TITLE, guild.getMemberCount()));
        embedBuilder.setDescription(Message.NEW_STUDENT_DESCRIPTION);
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

        final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
        embedBuilder.setTitle(replacePlaceholders(Message.STUDENT_LEFT_TITLE, guild.getMemberCount()));
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
        final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
        embedBuilder.setTitle(replacePlaceholders(Message.BOOST_MESSAGE_TITLE, guild.getBoostCount()));
        embedBuilder.setDescription(Message.BOOST_MESSAGE_DESCRIPTION);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public void onGuildMessageReceived(@NotNull final GuildMessageReceivedEvent event) {
        MessageUtil.getCommandChannel(event).ifPresent(map -> map.forEach((channel, member) -> {
            if (!channel.getId().equals(Id.VERIFICATION_CHANNEL)) {
                return;
            }

            final Guild guild = event.getGuild();
            final Role studentRole = guild.getRoleById(Id.STUDENT_ROLE);
            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            final List<Role> memberRoles = member.getRoles();
            final Role verificatonFailedRole = guild.getRoleById(Id.VERIFICATION_FAILED_ROLE);
            final net.dv8tion.jda.api.entities.Message message = event.getMessage();

            if (memberRoles.contains(verificatonFailedRole)) {
                return;
            }

            if (studentRole == null) {
                embedBuilder.setDescription(Message.STUDENT_ROLE_NOT_FOUND);
                channel.sendMessageEmbeds(embedBuilder.build()).queue();
                message.delete().queue();
                return;
            }

            if (STUDENT_PATTERN.matcher(message.getContentDisplay()).matches()) {
                if (memberRoles.contains(studentRole)) {
                    embedBuilder.setDescription(Message.ALREADY_VERIFIED);
                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    message.delete().queue();
                } else {
                    guild.addRoleToMember(member, studentRole).queue();
                    try {
                        member.getUser().openPrivateChannel().submit()
                                .whenComplete((privateChannel, error) -> {
                                    privateChannel.sendMessageEmbeds(embedBuilder.build()).queue();
                                    if (error != null) {
                                        error.addSuppressed(new IllegalStateException("User has disabled PMs"));
                                    }
                                });

                        embedBuilder.setDescription(Message.VERIFIED_SUCCESSFULLY);
                        message.delete().queue();
                    } catch (final IllegalStateException e) {
                        message.delete().queue();
                    }
                }
            } else {
                if (!memberRoles.contains(studentRole)) {
                    embedBuilder.setDescription(Message.FAILED_TO_VERIFY);

                    if (verificatonFailedRole != null) {
                        guild.addRoleToMember(member, verificatonFailedRole).queue();
                    }

                    channel.sendMessageEmbeds(embedBuilder.build()).queue();
                    message.delete().queue();
                }
            }
        }));
    }
}
