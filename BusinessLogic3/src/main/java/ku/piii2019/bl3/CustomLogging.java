/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    private static Logger LOGGER_INSTANCE = null;
    private static Level DEFAULT_LOG_LEVEL = Level.WARNING;
    private static String DEFAULT_LOG_FILE = "./log.log";

    public static Logger getInstance() {
        if (LOGGER_INSTANCE == null) {
            LOGGER_INSTANCE = Logger.getLogger(CustomLogging.class.getName());
            LOGGER_INSTANCE.setUseParentHandlers(false);
            LOGGER_INSTANCE.addHandler(getConsoleHandler());
            LOGGER_INSTANCE.setLevel(DEFAULT_LOG_LEVEL);
        }
        return LOGGER_INSTANCE;
    }



    public static void logIt(Exception ex) {
        ex.printStackTrace();
    }

    public static void logIt(String msg, Level level) {
        if (level == null) {
            level = CustomLogging.DEFAULT_LOG_LEVEL;
        }
        CustomLogging.getInstance().log(level, msg);
    }
    public static void printIt(String msg){
        System.out.println(msg);
    }
    public static void printHandlers() {
        Handler[] handlers = getInstance().getHandlers();
        System.out.println(handlers.length);
        Arrays.asList(handlers).stream().forEach(System.out::println);
    }

    public static void setDefaultFilePath(String path) {
        DEFAULT_LOG_FILE = path;
    }

    public static void setDefaultLoggingLevel(Level l) {
        CustomLogging.DEFAULT_LOG_LEVEL = l;
    }

    public static void addHandler(Handler h) {
        CustomLogging.getInstance().addHandler(h);
    }

    public static void removeHandler(Handler h) {
        CustomLogging.getInstance().removeHandler(h);
    }

    public static void setLevel(Level l) {
        getInstance().setLevel(l);
    }

    public static void removeAllHandlers() {
        Handler[] handlers = CustomLogging.getInstance().getHandlers();
        for (Handler h : handlers) {
            CustomLogging.getInstance().removeHandler(h);
        }
    }
    
    public static void removeAllFileHandlers() {
        Handler[] handlers = CustomLogging.getInstance().getHandlers();
        for (Handler h : handlers) {
            if(h instanceof FileHandler){
             CustomLogging.getInstance().removeHandler(h);
            }
        }
    }

    public static Handler getConsoleHandler() {
        ConsoleHandler ch = new ConsoleHandler();
        SimpleFormatter sf = new SimpleFormatter();
        ch.setFormatter(sf);
        return ch;
    }

    public static Handler getFileHandler(String fileName) {
        String pathString = DEFAULT_LOG_FILE;
        SimpleFormatter sf = new SimpleFormatter();
        if (fileName != null) {
            pathString = fileName;
        }
        try {
            Path candidateFilePath = Paths.get(fileName);
            Path candidateFolder = null;
            try{
            candidateFolder = candidateFilePath.getParent().normalize().toAbsolutePath();
            }
            catch(Exception ex){
                
            }
            if(candidateFolder==null){
                candidateFolder = Paths.get("./");
                candidateFilePath = Paths.get(candidateFolder.toString(), candidateFilePath.toString()); 
            }
            if (!Files.isDirectory(candidateFolder)) {
                Files.createDirectories(candidateFolder);
            } 
            if (!Files.exists(candidateFilePath)) {
                Files.createFile(candidateFilePath);
            }

            Handler h = new FileHandler(pathString);
            h.setEncoding("UTF-8");
            h.setFormatter(sf);
            return h;
        } catch (Exception e) {
            System.out.println("There has been an error when trying to create the log file.");
            e.printStackTrace();
        }
        return null;
    }
    public static void printAmountOfHandlers(){
        System.out.println(getInstance().getHandlers().length);
    }
     public static void printAmountOfFileHandlers(){
        int num = (int) Arrays.asList(getInstance().getHandlers()).stream().filter(x->x instanceof FileHandler).count();
        CustomLogging.printIt("The number of file handlers: "+num);
    }
    public static Level getDEFAULT_LOG_LEVEL() {
        return DEFAULT_LOG_LEVEL;
    }
}
