package it.heron4gf.hchatplus;

import it.heron4gf.hchatplus.checks.Check;
import it.heron4gf.hchatplus.eventhandler.AsyncChatListener;
import it.heron4gf.hchatplus.placeholderexpansion.ChatPlusExpansion;
import it.heron4gf.hchatplus.redispubsub.ChatPubSub;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class HChatPlus extends JavaPlugin {

    @Getter
    private static HChatPlus instance;

    @Getter
    private String format;

    @Getter
    private JedisPool jedisPool;

    private ChatPubSub chatPubSub;

    @Getter
    private Set<Check> enabled_checks = new HashSet<>();

    @Getter
    private HashMap<UUID, Integer> chat_strikes = new HashMap<>();

    public static final float CHAT_DELAY = 3f;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        if(getConfig().getBoolean("redis.enabled")) {
            jedisPool = new JedisPool(getConfig().getString("redis.host"), getConfig().getInt("redis.port"));
            subscribeRedis();
        }

        Bukkit.getPluginManager().registerEvents(new AsyncChatListener(),this);

        new ChatPlusExpansion().register();
    }

    public void subscribeRedis() {
        chatPubSub = new ChatPubSub();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            //jedisPool.getResource().psubscribe(new ChatPubSub(), ChatPubSub.CHANNEL_NAME);
            jedisPool.getResource().subscribe(chatPubSub, ChatPubSub.CHANNEL_NAME);
        });
    }

    public void reloadChecks() {
        enabled_checks.clear();
    }

    public void unsubscribeRedis() {
        chatPubSub.unsubscribe(ChatPubSub.CHANNEL_NAME);
    }

    public void reloadConfig() {
        super.reloadConfig();
        format = getConfig().getString("chat.format");
    }
    @Override
    public void onDisable() {
        unsubscribeRedis();
    }
}
