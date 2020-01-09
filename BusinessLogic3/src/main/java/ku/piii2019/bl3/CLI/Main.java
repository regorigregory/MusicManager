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
import org.apache.commons.cli.OptionGroup;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
/**
 *
 * @author regor
 */
public class Main {
    public static void main(String... args){
        Options ops = new Options();
        System.out.println("Hello");
        CommandLineParser dps = DefaultParserSingleton.getInstance();
        OptionGroup og = new OptionGroup();
        Option o = Option.builder("i").desc("Test1").longOpt("hello").required().build();
        og.addOption(o);
        o=null;
        o = Option.builder("h").desc("Test2").longOpt("hello-bello").required().build();

        og.addOption(o);
        og.setRequired(true);
        ops.addOptionGroup(og);
        try{
            
           dps.parse(ops, args);

       
        }catch(ParseException pex){
            pex.printStackTrace();
        }
    }
    
}
