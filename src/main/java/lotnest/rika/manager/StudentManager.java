package lotnest.rika.manager;

import lotnest.rika.configuration.IdProperty;
import lotnest.rika.configuration.MessageProperty;
import lotnest.rika.util.CommandUtil;
import lotnest.rika.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
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

public class StudentManager extends ListenerAdapter {

    public static final Pattern STUDENT_PATTERN = Pattern.compile("[Ss](\\d){3,5} [AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]{2,} [AaĄąBbCcĆćDdEeĘęFfGgHhIiJjKkLlŁłMmNnŃńOoÓóPpRrSsŚśTtUuWwYyZzŹźŻż]{2,}");

    @Override
    public void onGuildMemberJoin(final @NotNull GuildMemberJoinEvent event) {
        final Guild guild = event.getGuild();
        final MessageChannel channel = guild.getTextChannelById(IdProperty.JOIN_LEAVE_MESSAGES_CHANNEL);
        if (channel == null) {
            return;
        }

        final Member member = event.getMember();
        final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
        embedBuilder.setTitle(replacePlaceholders(MessageProperty.NEW_STUDENT_TITLE, guild.getMemberCount()));
        embedBuilder.setDescription(MessageProperty.NEW_STUDENT_DESCRIPTION);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public void onGuildMemberRemove(final @NotNull GuildMemberRemoveEvent event) {
        final Guild guild = event.getGuild();
        final MessageChannel channel = guild.getTextChannelById(IdProperty.JOIN_LEAVE_MESSAGES_CHANNEL);
        if (channel == null) {
            return;
        }

        final Member member = event.getMember();
        if (member == null) {
            return;
        }

        final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
        embedBuilder.setTitle(replacePlaceholders(MessageProperty.STUDENT_LEFT_TITLE, guild.getMemberCount()));
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public void onGuildMemberUpdateBoostTime(final @NotNull GuildMemberUpdateBoostTimeEvent event) {
        final Guild guild = event.getGuild();
        final MessageChannel channel = guild.getTextChannelById(IdProperty.BOOST_MESSAGES_CHANNEL);
        if (channel == null) {
            return;
        }

        final Member member = event.getMember();
        final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
        embedBuilder.setTitle(replacePlaceholders(MessageProperty.BOOST_MESSAGE_TITLE, guild.getBoostCount()));
        embedBuilder.setDescription(MessageProperty.BOOST_MESSAGE_DESCRIPTION);
        channel.sendMessageEmbeds(embedBuilder.build()).queue();
    }

    @Override
    public void onGuildMessageReceived(final @NotNull GuildMessageReceivedEvent event) {
        CommandUtil.getCommandChannel(event).ifPresent(map -> map.forEach((channel, member) -> {
            if (!channel.getId().equals(IdProperty.VERIFICATION_CHANNEL)) {
                return;
            }

            if (member.getUser().isBot()) {
                return;
            }

            final Guild guild = event.getGuild();
            final Role studentRole = guild.getRoleById(IdProperty.STUDENT_ROLE);
            final EmbedBuilder embedBuilder = MessageUtil.getDefaultEmbedBuilder(member);
            final List<Role> memberRoles = member.getRoles();
            final Role verificatonFailedRole = guild.getRoleById(IdProperty.VERIFICATION_FAILED_ROLE);
            final Message message = event.getMessage();

            if (memberRoles.contains(verificatonFailedRole)) {
                return;
            }

            if (studentRole == null) {
                embedBuilder.setDescription(MessageProperty.ROLE_NOT_FOUND);
                channel.sendMessageEmbeds(embedBuilder.build()).queue();
                message.delete().queue();
                return;
            }

            if (STUDENT_PATTERN.matcher(message.getContentDisplay()).matches()) {
                if (memberRoles.contains(studentRole)) {
                    embedBuilder.setDescription(MessageProperty.ALREADY_VERIFIED);
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

                        embedBuilder.setDescription(MessageProperty.VERIFIED_SUCCESSFULLY);
                        message.delete().queue();
                    } catch (final IllegalStateException e) {
                        message.delete().queue();
                    }
                }
            } else {
                if (!memberRoles.contains(studentRole)) {
                    embedBuilder.setDescription(MessageProperty.FAILED_TO_VERIFY);

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
