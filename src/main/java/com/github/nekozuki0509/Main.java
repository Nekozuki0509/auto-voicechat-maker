package com.github.nekozuki0509;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class Main {
    public static void main(String[] args) {
        JDA jda = JDABuilder.createDefault(Dotenv.load().get("AUTO-VOICECHAT-MAKER-BOTTOKEN"))
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new EventListener())
                .build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            System.err.printf("JDAの初期化に失敗: %s%n", ExceptionUtils.getStackTrace(e));
        }
    }
}