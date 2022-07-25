package fr.titoune.bossbarevent;

import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static fr.titoune.bossbarevent.Main.bossBarIntegerMap;

public final class Events implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent evt){
        for(BossBar bossBar : bossBarIntegerMap.keySet()){
            if(!bossBar.getPlayers().contains(evt.getPlayer())){
                bossBar.addPlayer(evt.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent evt){
        for(BossBar bossBar : bossBarIntegerMap.keySet()){
            if(bossBar.getPlayers().contains(evt.getPlayer())){
                bossBar.removePlayer(evt.getPlayer());
            }
        }
    }
}