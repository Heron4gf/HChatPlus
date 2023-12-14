package it.heron4gf.hchatplus;

import org.bukkit.entity.Player;

public class ChatPlusAPI {

    public static int strikes(Player player) {
        if(!HChatPlus.getInstance().getChat_strikes().containsKey(player.getUniqueId())) return 0;
        return HChatPlus.getInstance().getChat_strikes().get(player.getUniqueId());
    }

    public static void strike(Player player) {
        HChatPlus.getInstance().getChat_strikes().put(player.getUniqueId(),strikes(player)+1);
    }

    public static void resetStrikes(Player player) {
        HChatPlus.getInstance().getChat_strikes().remove(player.getUniqueId());
    }

}
