package it.heron4gf.hchatplus.eventhandler;

import it.heron4gf.hchatplus.HChatPlus;
import it.heron4gf.hchatplus.Utils;
import it.heron4gf.hchatplus.cooldown.Cooldown;
import it.heron4gf.hchatplus.redispubsub.ChatPubSub;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import redis.clients.jedis.Jedis;

public class AsyncChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    void chatDelay(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) return;
        Player player = event.getPlayer();
        if(player.hasPermission("hchatplus.bypass.cooldown")) return;
        if(Cooldown.isInCooldown(player)) {
            event.setCancelled(true);
            player.sendMessage(Component.text("You have to wait "+HChatPlus.CHAT_DELAY+" seconds between messages!").color(TextColor.color(0xFF0A2A)));
        } else {
            Cooldown.setCooldown(player, HChatPlus.CHAT_DELAY);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    void chatUnicode(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) return;
        Player player = event.getPlayer();
        if(player.hasPermission("hchatplus.bypass.unicode")) return;
        if(Utils.containsUnicodeChars(event.getMessage())) {
            event.setCancelled(true);
            player.sendMessage(Component.text("You can't use unicode characters!").color(TextColor.color(0xFF0A2A)));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    void chatIps(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) return;
        Player player = event.getPlayer();
        if(player.hasPermission("hchatplus.bypass.ips")) return;
        if(Utils.containsServerIps(event.getMessage())) {
            event.setCancelled(true);
            player.sendMessage(Component.text("You can't send server ips!").color(TextColor.color(0xFF0A2A)));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void formatListener(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) return;
        Player player = event.getPlayer();

        if(player.hasPermission("hchatplus.bypass.caps")) return;
        // if caps is more than 70% of the message and message length is > 5
        if(Utils.countCaps(event.getMessage()) > 70 && event.getMessage().length() > 5) {
            event.setMessage(event.getMessage().toLowerCase());
            player.sendMessage(Component.text("Your message was set in lower case to make it more readable!").color(TextColor.color(0xFF0A2A)));
        }

        if(player.hasPermission("hchatplus.bypass.repetition")) return;
        // if a message contains more than 3 times the same consecutive letter, remove the extra letters
        String newMessage = Utils.removeUnnecessaryLetters(event.getMessage());
        if(!newMessage.equals(event.getMessage())) {
            event.setMessage(newMessage);
            player.sendMessage(Component.text("Your message was modified to make it more readable!").color(TextColor.color(0xFF0A2A)));
        }

        String format = Utils.formatChat(player)+event.getMessage();
        event.setFormat(format);

        try(Jedis jedis = HChatPlus.getInstance().getJedisPool().getResource()) {
            jedis.publish(ChatPubSub.CHANNEL_NAME, player.getUniqueId()+":"+format);
        }
    }

}
