package it.heron4gf.hchatplus.checks;

import it.heron4gf.hchatplus.ChatPlusAPI;
import org.bukkit.entity.Player;

public abstract class Check {



    public boolean doesBypass(Player player) {

    };
    boolean isEnabled();
    boolean shouldStrike();
    default void strike(Player player) {
        ChatPlusAPI.strike(player);
    }

}
