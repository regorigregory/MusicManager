/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

/**
 *
 * @author regor
 */
public class DefaultParserSingleton {
    
    private static CommandLineParser parserInstance=null;
    public static CommandLineParser getInstance(){
        if(parserInstance==null){
            parserInstance = new DefaultParser();
        }
    return parserInstance;
    }
    

}
