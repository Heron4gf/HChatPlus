package it.heron4gf.hchatplus.checks;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;

public class UnicodeCheck extends Check implements CanReplace, HasBlWhLists {
    private List<String> blacklist;
    private List<String> whitelist;
    public UnicodeCheck(boolean enabled, boolean shouldStrike, Component trigger_message, List<String> blacklist, List<String> whitelist) {
        super(enabled, shouldStrike, trigger_message);
        this.whitelist = whitelist;
        this.blacklist = blacklist;
    }

    @Override
    public CheckType getType() {
        return CheckType.UNICODE;
    }

    @Override
    public boolean doesBypass(Player player) {
        return player.hasPermission("hchatplus.bypass.unicode");
    }

    @Override
    public boolean isTriggered(Player player, String message) {
        return false;
    }

    @Override
    public boolean replacementIsEnabled() {
        return false;
    }

    @Override
    public String replace(String message) {
        return null;
    }

    @Override
    public List<String> blacklist() {
        return blacklist;
    }

    @Override
    public List<String> whitelist() {
        return whitelist;
    }
}
