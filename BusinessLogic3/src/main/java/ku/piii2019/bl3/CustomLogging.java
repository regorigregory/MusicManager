/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3;
import java.util.logging.Logger;
/**
 *
 * @author regor
 */
public class CustomLogging {
    private static Logger LOGGER_INSTANCE=null;
    
    public static Logger getLoggerInstance(){
        if(LOGGER_INSTANCE == null){
            LOGGER_INSTANCE = Logger.getLogger(CustomLogging.class.getName());
        }
        return LOGGER_INSTANCE;
    }
    public static void logIt(Exception ex) {
        ex.printStackTrace();
    }

    public static void logIt(String msg) {
        System.out.println(msg);
    }
}
