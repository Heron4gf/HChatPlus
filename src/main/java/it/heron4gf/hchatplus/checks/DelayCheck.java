package it.heron4gf.hchatplus.checks;

import it.heron4gf.hchatplus.cooldown.Cooldown;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class DelayCheck extends Check {

    private float delay;
    public DelayCheck(boolean enabled, boolean shouldStrike, Component trigger_message, float delay) {
        super(enabled, shouldStrike, trigger_message);

        this.delay = delay;
    }

    @Override
    public CheckType getType() {
        return CheckType.DELAY;
    }

    @Override
    public boolean doesBypass(Player player) {
        return player.hasPermission("hchatplus.bypass.delay");
    }

    @Override
    public boolean checkAndCancel(Player player, String message) {
        if(isTriggered(player, message)) {
            if(shouldStrike()) strike(player);
            player.sendMessage(getTrigger_message());
            return true;
        }
        Cooldown.setCooldown(player, delay);
        return false;
    }

    @Override
    public boolean isTriggered(Player player, String message) {
        return Cooldown.isInCooldown(player);
    }
}
