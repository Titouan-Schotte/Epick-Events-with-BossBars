package fr.titoune.bossbarevent;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public final class Main extends JavaPlugin implements Listener {
    public static Map<BossBar, Object[]> bossBarIntegerMap = new HashMap<BossBar, Object[]>();
    @Override
    public void onEnable() {
        this.getCommand("epickevent").setExecutor(new CommandBossBarEvent());
        getServer().getPluginManager().registerEvents(new Events(), this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new  Runnable(){
            public void run(){
                for(BossBar bar : bossBarIntegerMap.keySet()){
                    if(((int)bossBarIntegerMap.get(bar)[0])>0){
                        bossBarIntegerMap.get(bar)[0]=((int)bossBarIntegerMap.get(bar)[0])-1;
                        int playerCount = 0;
                        for(Player player:getServer().getOnlinePlayers()){
                            playerCount+=1;
                        }

                        int seconds = (((int)bossBarIntegerMap.get(bar)[0] % 86400) % 3600) % 60,
                        minutes = (((int)bossBarIntegerMap.get(bar)[0] % 86400) % 3600) / 60,
                        hours = ((int)bossBarIntegerMap.get(bar)[0] % 86400) / 3600 ,
                        days = (int)bossBarIntegerMap.get(bar)[0] / 86400;



                        bar.setTitle(((String) bossBarIntegerMap.get(bar)[4]).replaceAll("\\{playersCount\\}", ""+playerCount).replaceAll("\\{playersMaxCount\\}", ""+getServer().getMaxPlayers()).replaceAll("\\{minutes\\}", ""+minutes).replaceAll("\\{seconds\\}", ""+seconds).replaceAll("\\{hours\\}", ""+hours).replaceAll("\\{days\\}", ""+days));
                        bar.setProgress( ((float)((int)bossBarIntegerMap.get(bar)[0]) / (float)((int)bossBarIntegerMap.get(bar)[1])));
                    } else {
                        if(((boolean) bossBarIntegerMap.get(bar)[2])){
                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            Bukkit.dispatchCommand(console, (String) bossBarIntegerMap.get(bar)[3]);
                        }
                        bossBarIntegerMap.remove(bar);
                        bar.setProgress(0);
                        bar.removeAll();
                    }
                }
            }
        }, 20l, 20l);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
