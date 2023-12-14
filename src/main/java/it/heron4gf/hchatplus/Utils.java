package it.heron4gf.hchatplus;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    public static int countCaps(String message) {
        // return percentage of caps
        int caps = 0;
        for(int i = 0; i < message.length(); i++) {
            if(Character.isUpperCase(message.charAt(i))) caps++;
        }
        return (int) ((caps*100)/message.length());
    }

    public static String removeUnnecessaryLetters(String message) {
        // if a message contains more than 3 times the same consecutive letter, remove the extra letters
        String newMessage = "";
        for(int i = 0; i < message.length(); i++) {
            if(i > 2 && message.charAt(i) == message.charAt(i-1) && message.charAt(i) == message.charAt(i-2) && message.charAt(i) == message.charAt(i-3)) continue;
            newMessage += message.charAt(i);
        }
        return newMessage;
    }

    public static boolean containsServerIps(String message) {
        // use regex to determine if message contains server ips, don't be fooled by formats like mc,server,it or mc.server.it
        String dontFoolMe = message.replace(",",".").replace(" ",".").replace(":",".").replace("-",".").replace("_",".");
        if(dontFoolMe.contains("mc.") || dontFoolMe.contains("play.")) return true;
        return message.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
    }

    public static boolean containsUnicodeChars(String message) {
        for(int i = 0; i < message.length(); i++) {
            if(message.charAt(i) > 127 && message.charAt(i) != 224 && message.charAt(i) != 232 && message.charAt(i) != 236 && message.charAt(i) != 242 && message.charAt(i) != 249 && message.charAt(i) != 'ç') return true;
        }
        return false;
    }

    public static String formatChat(Player player) {
        String format = HChatPlus.getInstance().getFormat();
        format = PlaceholderAPI.setPlaceholders(player, format);
        return ChatColor.translateAlternateColorCodes('&',format);
    }
}
