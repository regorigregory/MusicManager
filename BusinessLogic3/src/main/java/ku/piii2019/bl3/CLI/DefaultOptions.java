/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;


/**
 *
 * @author Gergo Endresz <k1721863@kingston.ac.uk>
 */
public class DefaultOptions {

    private static Options defaultOptions = null;
    private static Options defaultCopyOptions = null;
    private static String newLine = "";

    public static Options getDefaultCopyOptions() {
        if (defaultCopyOptions == null) {
            defaultCopyOptions = new Options();
            
            Option o = null;
            
            o = Option.builder("h").hasArg(false).longOpt("help").desc("Usage: CLI -option <args>").build();
            defaultCopyOptions.addOption(o);
//            o = Option.builder("c").hasArg(false).longOpt("copy").desc("command to copy:)").required().build();
//            defaultOptions.addOption(o);
            
            o = Option.builder("s").hasArg(true).argName("Path").longOpt("src").desc("source folder").required().build();
            defaultCopyOptions.addOption(o);
            
            o = Option.builder("d").hasArg(true).argName("Path").longOpt("dsc").desc("destination folder").required().build();
            defaultCopyOptions.addOption(o);
            
            OptionGroup og = new OptionGroup();
            o = Option.builder("ID3EX").hasArg(false).longOpt("exclude-ID3").desc("Excluding duplicates based on ID3 tag").required(false).build();
            og.addOption(o);
            
            o = Option.builder("FEX").hasArg(false).longOpt("exclude-FNAME").desc("Excluding duplicates based on Filename").required(false).build();
            
            og.addOption(o);
            
            o = Option.builder("NOEX").hasArg(false).longOpt("no-exclusion").desc("No exclusion, duplicates will be copied.").required(false).build();
            
            og.addOption(o);
            defaultCopyOptions.addOptionGroup(og);
            
//             o = Option.builder("m").hasArg(false).longOpt("move").desc("Using this flag, it will move instead of copying it.").required(false).build();
//            defaultOptions.addOption(o);
            
 
            
        }
        return defaultCopyOptions;
    }

    public static Options getDefaultRefileOptions() {
        
        throw new UnsupportedOperationException("Has not been implemented.");
    }

    public static Options getDefaultPlaylistCreationOptions() {
        throw new UnsupportedOperationException("Has not been implemented.");
    }

}
