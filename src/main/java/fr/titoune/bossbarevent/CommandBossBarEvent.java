package fr.titoune.bossbarevent;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static fr.titoune.bossbarevent.Main.bossBarIntegerMap;

public class CommandBossBarEvent implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(args.length==1 && args[0].equals("clear")){
            for(BossBar bar : bossBarIntegerMap.keySet()){
                bar.removeAll();
            }
            bossBarIntegerMap=new HashMap<BossBar, Object[]>();
            return true;
        }
        if(args.length>=5 && args[4].contains("\"")){
            String title = "";
            String commandInTheEnd = "";
            int breakTitleCommand = 0;
            for(int i=4;i<args.length;i++){
                title+=args[i]+" ";
                if(args[i].contains("\"") && i!=4){
                    breakTitleCommand=i+1;
                    break;
                }
            }
            if(breakTitleCommand!=0){
                for(int i=breakTitleCommand;i<args.length;i++){
                    commandInTheEnd+=args[i]+" ";
                }
            }

            title = title.replaceAll("\"", "");
            commandInTheEnd = commandInTheEnd.replaceAll("\"", "");
            BossBar bar = Bukkit.getServer().createBossBar(title, BarColor.valueOf(args[2]), BarStyle.valueOf(args[3]));
            bar.setVisible(true);
            bar.setProgress(1);
            for(Player player : sender.getServer().getOnlinePlayers()){
                bar.addPlayer(player);
            }
            int chrono = Integer.parseInt(args[0])*60+Integer.parseInt(args[1]);
            if(commandInTheEnd.equals("")){
                bossBarIntegerMap.put(bar, new Object[]{chrono, chrono,false});
            } else {
                bossBarIntegerMap.put(bar, new Object[]{chrono, chrono,true,commandInTheEnd});
            }
            sender.sendMessage("§2Success !");
            return true;
        }
        sender.sendMessage("\n§n§lColors§r :\n\n\n - §9BLUE§r,\n - §dPINK§r,\n - §2GREEN§r,\n - §eYELLOW§r,\n - §fWHITE§r,\n - §5PURPLE§r,\n - §cRED§r");
        sender.sendMessage("\n\n§n§lStyles§r :\n\n\n - §6SOLID§r,\n - §6SEGMENTED_6§r,\n - §6SEGMENTED_10§r,\n - §6SEGMENTED_12§r,\n - §6SEGMENTED_20§r");
        sender.sendMessage("§4WRONG USAGE ! \n§oepickevent <minutes> <seconds> <color> <style> \"<title>\" \"<commandAtTheEnd>\"=>OPTIONAL");
        return true;
    }


}