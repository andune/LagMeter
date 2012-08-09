package com.webkonsept.minecraft.lagmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LagMeterLogger {
	private static final String fileSeparator = System.getProperty("file.separator");

	static LagMeter plugin;
	
	private String error = "*shrug* Dunno.";
	private boolean logMemory = true;
	private boolean logTPS = true;
	protected static boolean enabled = false;
	private String timeFormat = "MM-dd-yyyy HH:mm:ss";
	
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
		if(!LagMeter.useLogsFolder){
			System.out.println("[LagMeter] Not using logs folder.");
			return this.enable(new File(plugin.getDataFolder(),"lag.log"));
		}else{
			System.out.println("[LagMeter] Using logs folder. This will create a new log for each day (it might log data from tomorrow in today's file if you leave the server running without reloading/restarting).");
			return this.enable(new File(plugin.getDataFolder()+fileSeparator+"logs", "lag"+today()+".log"));
		}
	}
	public boolean enabled(){
		return enabled;
	}
	public void disable() throws IOException, FileNotFoundException, Exception {
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
				// TODO Log exception
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Log exception
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Log exception
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
				// TODO Log exception
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Log exception
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Log exception
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
				if( !logfile.exists() ) {
					File path = new File(logfile.getParent());
					if( !path.exists() )
						path.mkdirs();
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
		if(enabled = true){
			log.flush();
			log.close();
			log = null;
			enabled = false;
		}
	}
	protected void log(String message){
		if (enabled && LagMeter.playerLoggingEnabled){
				message = "["+now()+"] "+message;
				log.println(message);
				log.println("Players online: " +(plugin.getServer().getOnlinePlayers().length));
				log.flush();
		}
		else if(enabled && !LagMeter.playerLoggingEnabled){
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
	
	public String today(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		return sdf.format(calendar.getTime());
	}
}
