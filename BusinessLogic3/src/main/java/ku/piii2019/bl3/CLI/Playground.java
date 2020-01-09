/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;
import java.util.LinkedList;
import java.util.Scanner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
/**
 *
 * @author regor
 */
public class Playground {
    public static void main(String... args){
        CommandLineParser dps = DefaultParserSingleton.getInstance();
        Options ops = new Options();
        ops.addOption("h", "help", false, "This is just a test help message");
        try{
        CommandLine cl = dps.parse(ops, args);
        if(cl.hasOption("h")){
            System.out.println("You need some help buddy");
            System.out.println(cl.getOptionValue("h"));
        }
        }catch(ParseException pex){
            pex.printStackTrace();
        }
    }
    
}
