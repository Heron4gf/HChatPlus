package it.heron4gf.hchatplus.checks;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class ProfanityCheck extends Check implements CanReplace {

    private boolean shouldReplace = false;

    public ProfanityCheck(boolean enabled, boolean shouldStrike, Component trigger_message, boolean shouldReplace) {
        super(enabled, shouldStrike, trigger_message);
    }

    @Override
    public CheckType getType() {
        return CheckType.PROFANITY;
    }

    @Override
    public boolean doesBypass(Player player) {
        return player.hasPermission("hchatplus.bypass.profanity");
    }

    @Override
    public boolean isTriggered(Player player, String message) {
        return false;
    }

    @Override
    public boolean replacementIsEnabled() {
        return shouldReplace;
    }

    @Override
    public String replace(String message) {
        return null;
    }
}
