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

    private static Options defaultRefileOptions = null;
    private static Options defaultCopyOptions = null;
    private static Options defaultM3UOptions = null;

    private static String newLine = "";

    public static Options getDefaultCopyOptions() {
        if (defaultCopyOptions == null) {
            defaultCopyOptions = new Options();
            
            
            
            
            Option o = null;
            
            o = Option.builder("h").hasArg(false).longOpt("help").desc("This screen.").build();
            defaultCopyOptions.addOption(o);
//            o = Option.builder("c").hasArg(false).longOpt("copy").desc("command to copy:)").required().build();
//            defaultOptions.addOption(o);
            
            o = Option.builder("s").hasArg(true).argName("Path").longOpt("src").desc("Source folder path.").required(false).build();
            defaultCopyOptions.addOption(o);
            
            o = Option.builder("d").hasArg(true).argName("Path").longOpt("dsc").desc("Destination folder path.").required(false).build();
            defaultCopyOptions.addOption(o);
            
            OptionGroup og = new OptionGroup();
            o = Option.builder("ID3EX").hasArg(false).longOpt("exclude-ID3").desc("Excluding duplicates based on ID3 tag.").required(false).build();
            og.addOption(o);
            
            o = Option.builder("FEX").hasArg(false).longOpt("exclude-FNAME").desc("Excluding duplicates based on Filename.").required(false).build();
            
            og.addOption(o);
            
            o = Option.builder("NOEX").hasArg(false).longOpt("no-exclusion").desc("No exclusion, duplicates will be copied.").required(false).build();
            
            og.addOption(o);
            defaultCopyOptions.addOptionGroup(og);
            
//             o = Option.builder("m").hasArg(false).longOpt("move").desc("Using this flag, it will move instead of copying it.").required(false).build();
//            defaultOptions.addOption(o);
            
 
            
        }
        return defaultCopyOptions;
    }
    public static Options getDefaultM3UOptions() {
        if (defaultM3UOptions == null) {
            defaultM3UOptions = new Options();
            
            Option o = null;
            
            o = Option.builder("h").hasArg(false).longOpt("help").desc("This screen").build();
            defaultM3UOptions.addOption(o);

            o = Option.builder("f").hasArg(true).argName("output-file").longOpt("filename").desc("Filename to be used to save the generated list.").required(true).build();
            defaultM3UOptions.addOption(o);
            
            o = Option.builder("s").hasArg(true).argName("source-folder").longOpt("src").desc("Source folder to scan. If no artist or genre filter is given, then every single file in the folder will be processed.").required(true).build();
            defaultM3UOptions.addOption(o);
            
            o = Option.builder("d").hasArg(true).argName("destination-folder").longOpt("dsc").desc("Optional destination folder to save the m3u file.").required(false).build();
            defaultM3UOptions.addOption(o);
            
            //Filters group
            OptionGroup og = new OptionGroup();
            o = Option.builder("a").hasArgs().longOpt("artist").desc("Filtering playlist by a single or multiple artists.").build();
            og.addOption(o);
            
            o = Option.builder("g").hasArgs().longOpt("genre").desc("Filtering playlist by a single or multiple genres.").build();
            og.addOption(o);
              og.addOption(o);
            og.setRequired(false);
             defaultM3UOptions.addOptionGroup(og);
          
            //optional length specification
            o = Option.builder("ml").hasArg(true).longOpt("max-length").desc("The maximum length of the playlist in seconds.").required(false).build();
            
            defaultM3UOptions.addOption(o);
           //Optional duplicate filter enabling
           
            og = new OptionGroup();
            o = Option.builder("ID3EX").hasArg(false).longOpt("exclude-ID3").desc("Excluding duplicates based on ID3 tag.").required(false).build();
            og.addOption(o);
            
            o = Option.builder("FEX").hasArg(false).longOpt("exclude-FNAME").desc("Excluding duplicates based on filename.").required(false).build();
            
            og.addOption(o);
            
            o = Option.builder("NOEX").hasArg(false).longOpt("no-exclusion").desc("No exclusion, duplicates will be copied.").required(false).build();
            
            og.addOption(o);
            defaultM3UOptions.addOptionGroup(og);
     
        }
        return defaultM3UOptions;
    }
    public static Options getDefaultRefileOptions() {
         if (defaultRefileOptions == null) {
            defaultRefileOptions = new Options();
            
            Option o = null;
            
            o = Option.builder("h").hasArg(false).longOpt("help").desc("This screen.").build();
            defaultRefileOptions.addOption(o);

            o = Option.builder("s").hasArg(true).argName("source-folder").longOpt("src").desc("Source folder to scan. If no artist or genre filter is given, then every single file in the folder will be processed.").required(false).build();
            defaultRefileOptions.addOption(o);
            
            o = Option.builder("d").hasArg(true).argName("destination-folder").longOpt("dsc").desc("Optional destination folder to save the m3u file.").required(false).build();
            defaultRefileOptions.addOption(o);
            
            //Filters group xtra mile
            OptionGroup og = new OptionGroup();
            o = Option.builder("a").hasArgs().longOpt("artist").desc("Filtering playlist by a single or multiple artists.").build();
            og.addOption(o);
            
            o = Option.builder("g").hasArgs().longOpt("genre").desc("Filtering playlist by a single or multiple genres.").build();
            og.addOption(o);
            og.setRequired(false);
            defaultRefileOptions.addOptionGroup(og);
          
           //Optional duplicate filter enabling xtra mile
           
            og = new OptionGroup();
            o = Option.builder("ID3EX").hasArg(false).longOpt("exclude-ID3").desc("Excluding duplicates based on ID3 tag.").required(false).build();
            og.addOption(o);
            
            o = Option.builder("FEX").hasArg(false).longOpt("exclude-FNAME").desc("Excluding duplicates based on filename.").required(false).build();
            
            og.addOption(o);
            
            o = Option.builder("NOEX").hasArg(false).longOpt("no-exclusion").desc("No exclusion, duplicates will be copied.").required(false).build();
            
            og.addOption(o);
            defaultRefileOptions.addOptionGroup(og);
     
        }
        return defaultRefileOptions;
    }

    public static Options getDefaultPlaylistCreationOptions() {
        throw new UnsupportedOperationException("Has not been implemented.");
    }

}
