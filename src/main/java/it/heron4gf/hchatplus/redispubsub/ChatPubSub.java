package it.heron4gf.hchatplus.redispubsub;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;

public class ChatPubSub extends JedisPubSub {
    public static final String CHANNEL_NAME = "velocity:chat";

    @Override
    public void onMessage(String channel, String message) {
        String player_uuid = "";
        for(int i = 0; i < message.length(); i++) {
            if(message.charAt(i) == ':') break;
            player_uuid += message.charAt(i);
        }
        message = message.replaceFirst(player_uuid+ ":", "");

        UUID uuid = UUID.fromString(player_uuid);
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getUniqueId().equals(uuid)) {
                // this is the sender server
                return;
            }
        }

        for(Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {

    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {

    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {

    }

    @Override
    public void onPong(String message) {

    }
}
