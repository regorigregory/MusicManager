/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.io.PrintWriter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 *
 * @author regor
 */
public class CLIHelpFormatter {
    private static HelpFormatter helpFormatter =null;

    public CLIHelpFormatter() {
    }
    private static HelpFormatter getHelpFormatter(){
        if(helpFormatter == null){
            helpFormatter = new HelpFormatter();
        }
        return helpFormatter;
    }
    public static void printHelp(Options opts, String usage){
        PrintWriter pw =new PrintWriter(System.out, true);
        getHelpFormatter().printHelp(pw, 100, usage, "\r\n", opts, 1, 10, "\r\n");

    }
}
