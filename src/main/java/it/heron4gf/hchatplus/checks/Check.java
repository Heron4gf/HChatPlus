package it.heron4gf.hchatplus.checks;

import it.heron4gf.hchatplus.ChatPlusAPI;
import lombok.Data;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public abstract class Check {

    @Getter
    private boolean enabled;
    private boolean shouldStrike;
    @Getter
    private Component trigger_message;

    public Check(boolean enabled, boolean shouldStrike, Component trigger_message) {
        this.enabled = enabled;
        this.shouldStrike = shouldStrike;
        this.trigger_message = trigger_message;
    }

    public abstract CheckType getType();

    public abstract boolean doesBypass(Player player);
    public boolean shouldStrike() {
        return shouldStrike;
    }
    public void strike(Player player) {
        ChatPlusAPI.strike(player);
    }

    public boolean checkAndCancel(Player player, String message) {
        if(isTriggered(player, message)) {
            if(shouldStrike()) strike(player);
            player.sendMessage(getTrigger_message());
            return true;
        }
        return false;
    }

    public abstract boolean isTriggered(Player player, String message);

}
