package com.github.nekozuki0509;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.channel.concrete.VoiceChannelImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EventListener extends ListenerAdapter {
    private final List<VoiceChannelImpl> voiceChannels = new ArrayList<>();

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        if (event.getChannelJoined() != null && "1536288980067422288".equals(event.getChannelJoined().getId())) {
            event.getGuild().createVoiceChannel("VC-" + event.getMember().getEffectiveName(), event.getChannelJoined().getParentCategory())
                    .queue(newChannel -> {
                        voiceChannels.add((VoiceChannelImpl) newChannel);
                        event.getGuild().moveVoiceMember(event.getMember(), newChannel).queue();
                    });
        } else if (event.getChannelLeft() != null && voiceChannels.stream().map(VoiceChannelImpl::getId).anyMatch(id -> id.equals(event.getChannelLeft().getId())) && event.getChannelLeft().getMembers().isEmpty()) {
            voiceChannels.removeIf(channel -> channel.getId().equals(event.getChannelLeft().getId()));
            event.getChannelLeft().delete().queue();
        }
    }
}
