package com.webkonsept.minecraft.lagmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class LagMeterLogger {
	LagMeter plugin;
	
	private String error = "*shrug* Dunno.";
	private boolean logMemory = true;
	private boolean logTPS = true;
	protected static boolean enabled = false;
	private String timeFormat = "MM-dd-yyyy HH:mm:ss";
	
	Player[] playersOnline = Bukkit.getServer().getOnlinePlayers();

	String datafolder = "LagMeter";
	File logfile;
	PrintWriter log;
	
	LagMeterLogger (LagMeter instance,boolean enable){
		plugin = instance;
		if (enable){
			this.enable();
		}
	}
	LagMeterLogger (LagMeter instance){
		plugin = instance;
	}
	
	
	// Getters, setters, blah blah boring.
	public boolean enable(File logTo){
		logfile = logTo;
		return beginLogging();
	}
	public boolean enable(){
		return this.enable(new File(plugin.getDataFolder(),"lag.log"));
	}
	public boolean enabled(){
		return enabled;
	}
	public void disable() throws IOException, FileNotFoundException, Exception {
//		LagMeter.loadConfig();
		if(LagMeter.enableLogging = true) {
				closeLog();
		}
	}
	public void logMemory(boolean set){
		logMemory = set;
		if (logMemory == false && logTPS == false){
			try {
				this.disable();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.error("Both log outputs disabled:  Logging disabled.");
		}
	}
	public boolean logMemory(){
		return logMemory;
	}
	public void logTPS(boolean set){
		logTPS = set;
		if (logMemory == false && logTPS == false){
			try {
				this.disable();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.error("Both log outputs disabled:  Logging disabled.");
		}
	}
	public boolean logTPS(){
		return logTPS;
	}
	public String getError(){
		return this.error;
	}
	private void error(String errorMessage){
		this.error = errorMessage;
	}
	public String getTimeFormat(){
		return timeFormat;
	}
	public void setTimeFormat(String newFormat){
		timeFormat = newFormat;
	}
	public String getFilename(){
		if (logfile != null){
			return logfile.getAbsolutePath();
		}
		else {
			return "!! UNKNOWN !!";
		}
	}
	// Where real stuff happens!
	
	private boolean beginLogging(){
		boolean ret = true;
		if (logfile == null){
			error("Logfile is null");
			ret = false;
		}
		else if (logMemory == false && logTPS == false){
			error("Both logMemory and logTPS are disabled.  Nothing to log!");
			ret = false;
		}
		else {
			try {
				if (! logfile.exists()){
					logfile.createNewFile();
				}
				log = new PrintWriter(new FileWriter(logfile,true));
				log("Logging enabled.");
			}
			catch( IOException e){
				e.printStackTrace();
				error("IOException opening logfile");
				ret = false;
			}
		}
		enabled = true;
		return ret;
	}
	private void closeLog() throws
			IOException,
			Exception,
			FileNotFoundException
	{
		if(enabled != false){
			log.flush();
			log.close();
			log = null;
			enabled = false;
		}
	}
	protected void log(String message){
		if (enabled && LagMeter.playerLoggingEnabled != false){
				message = "["+now()+"] "+message;
				log.println(message);
				log.println("Players online: " + playersOnline.length);
				log.flush();
		}
		else if(enabled && LagMeter.playerLoggingEnabled != true){
		  message = "["+now()+"] "+message;
		  log.println(message);
		  log.flush();
		  }
		  
	}
	public String now() {
		// http://www.rgagnon.com/javadetails/java-0106.html
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
		return sdf.format(cal.getTime());
	}
}
