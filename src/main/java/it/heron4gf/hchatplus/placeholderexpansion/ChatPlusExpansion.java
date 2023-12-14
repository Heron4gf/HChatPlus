package it.heron4gf.hchatplus.placeholderexpansion;

import it.heron4gf.hchatplus.ChatPlusAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChatPlusExpansion extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "hchatplus";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Heron4gf";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("strikes")) {
            if(player.isOnline()) {
                return String.valueOf(ChatPlusAPI.strikes(player.getPlayer()));
            } else {
                return "0";
            }
        }
        return "Invalid placeholder";
    }
}
