/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author regor
 */
public enum Messages {
    HELP(Messages.getHelpMessage());
    
    
    private final String message;
   
    private Messages(String msg){
        this.message = msg;
    }
    public String toString(){
        return this.message;
    }
    private static String getHelpMessage(){
        StringBuilder sb = new StringBuilder();
        String newLine = "\r\n";
        sb.append("Usage: CLI -option <args>"+newLine);
        sb.append("-help                                print this message"+newLine);
        sb.append("-src <path>                          source folder"+newLine);
        sb.append("-dst <path>                          destination folder"+newLine);
        sb.append("-copy                                copy contents of src to dst"+newLine);
        sb.append("-ex <ID3 | fname | none >            exclusion mode, can be based on ID3, filename or none"+newLine);
        return sb.toString();
    }
}
