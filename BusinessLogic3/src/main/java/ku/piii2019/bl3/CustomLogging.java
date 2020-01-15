/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
/**
 *
 * @author regor
 */
public class CustomLogging {
    private static Logger LOGGER_INSTANCE=null;
    private static Level DEFAULT_LOG_LEVEL = Level.WARNING;
    private static String DEFAULT_LOG_FILE = "./log.log";
  
    public static Logger getInstance(){
        if(LOGGER_INSTANCE == null){
            LOGGER_INSTANCE = Logger.getLogger(CustomLogging.class.getName());
        }
        return LOGGER_INSTANCE;
    }
    public static void defaultSetup(){
        CustomLogging.getInstance().setLevel(DEFAULT_LOG_LEVEL);
        CustomLogging.getInstance().addHandler(getConsoleHandler());
     

    }
    
    
    public static void logIt(Exception ex) {
        ex.printStackTrace();
    }

    public static void logIt(String msg, Level level) {
        if(level==null){
            level = CustomLogging.DEFAULT_LOG_LEVEL;
        }
        CustomLogging.getInstance().log(level, msg);
    }
    
      public static void printHandlers(){
        Handler[] handlers =getInstance().getHandlers();
        System.out.println(handlers.length);
        Arrays.asList(handlers).stream().forEach(System.out::println);
     
    }
    
    public static void setDefaultFilePath(String path){
        DEFAULT_LOG_FILE = path;
    }
    
    public static void setDefaultLoggingLevel(Level l){
        CustomLogging.DEFAULT_LOG_LEVEL = l;
    }
    public static void addHandler(Handler h){
        CustomLogging.getInstance().addHandler(h);
    }
    
    public static void removeHandler(Handler h){
        CustomLogging.getInstance().removeHandler(h);
    }
    
    public static void setLevel(Level l){
        getInstance().setLevel(l);
    }
    
    public static void removeAllHandlers(){
        Handler[] handlers = CustomLogging.getInstance().getHandlers();
        for(Handler h : handlers){
            CustomLogging.getInstance().removeHandler(h);
        }
    }
   
    public static Handler getConsoleHandler(){
        ConsoleHandler ch = new ConsoleHandler();
        SimpleFormatter sf = new SimpleFormatter();
        ch.setFormatter(sf);
        return ch;
    }
    
    public static Handler getFileHandler(String fileName){
        String pathString = DEFAULT_LOG_FILE;
        SimpleFormatter sf = new SimpleFormatter();
        if(fileName!=null){
            pathString = fileName;
        }
        try{
            Handler h =new FileHandler(pathString);
            h.setEncoding("UTF-8");
            h.setFormatter(sf);
        return h;
        }catch (Exception e){
            System.out.println("There has been an error when trying to create the log file.");
            e.printStackTrace();
        }
        return null;
    }

    public static Level getDEFAULT_LOG_LEVEL() {
        return DEFAULT_LOG_LEVEL;
    }
    
    
}
