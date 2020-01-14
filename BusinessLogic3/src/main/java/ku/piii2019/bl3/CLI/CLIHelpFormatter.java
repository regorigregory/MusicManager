/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 *
 * @author regor
 */
public class CLIHelpFormatter {
    private static HelpFormatter helpFormatter =null;
    private static HelpFormatter getHelpFormatter(){
        if(helpFormatter == null){
            helpFormatter = new HelpFormatter();
        }
        return helpFormatter;
    }
    public static void printHelp(Options opts, String usage){
        getHelpFormatter().printHelp(usage, opts);
    }
}
