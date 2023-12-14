package it.heron4gf.hchatplus.cooldown;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {

    private static HashMap<UUID,Long> cooldown = new HashMap<>();

    public static void setCooldown(Player player, float seconds) {
        cooldown.put(player.getUniqueId(),System.currentTimeMillis()+(long)seconds*1000);
    }

    public static boolean isInCooldown(Player player) {
        return cooldown.containsKey(player.getUniqueId()) && cooldown.get(player.getUniqueId()) > System.currentTimeMillis();
    }

}
