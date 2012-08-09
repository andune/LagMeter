package com.webkonsept.minecraft.lagmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
 
public class LagMeterConfig {
    // Variables
    private static YamlConfiguration configuration;
    private static File configFile;
    private static boolean loaded = false;
    
    public static YamlConfiguration getConfig() {
        if (!loaded) {
            loadConfig();
        }
        return configuration;
    }
    
    public static File getConfigFile() {
        return configFile;
    }
    
    
    public static void loadConfig() {
        configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("LagMeter").getDataFolder(), "settings.yml");
        if (configFile.exists()) {
            configuration = new YamlConfiguration();
            try {
                configuration.load(configFile);
                LagMeter.useAverage 			= configuration.getBoolean	("useAverage",				true);
                LagMeter.averageLength 			= configuration.getInt		("averageLength", 			10);
                LagMeter.interval 				= configuration.getInt		("interval", 				40);
                LagMeter.logInterval			= configuration.getInt		("log.interval", 			150);
                LagMeter.enableLogging			= configuration.getBoolean	("log.enable", 				true);
                LagMeter.useLogsFolder			= configuration.getBoolean	("log.useLogsFolder",		true);
                LagMeter.playerLoggingEnabled 	= configuration.getBoolean	("log.logPlayersOnline",	false);
            } catch (FileNotFoundException ex) {
                // TODO Log exception
            } catch (IOException ex) {
                // TODO Log exception
            } catch (InvalidConfigurationException ex) {
                // TODO Log exception
            }
            loaded = true;
        } else {
            try {
                Bukkit.getServer().getPluginManager().getPlugin("LagMeter").getDataFolder().mkdir();
                InputStream jarURL = LagMeterConfig.class.getResourceAsStream("/settings.yml");
                copyFile(jarURL, configFile);
                configuration = new YamlConfiguration();
                configuration.load(configFile);
                try {
                    configuration.load(configFile);
                    
                } catch (FileNotFoundException ex) {
                    // TODO: Log
                } catch (IOException ex) {
                    // TODO: Log
                } catch (InvalidConfigurationException ex) {
                    // TODO: Log
                }
                loaded = true;
                // TODO Log
            } catch (Exception e) {
                // TODO Log exception
            }
        }
    }
 
    static private void copyFile(InputStream in, File out) throws Exception {
        InputStream fis = in;
        FileOutputStream fos = new FileOutputStream(out);
        try {
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
 
    private LagMeterConfig() {
    }
}