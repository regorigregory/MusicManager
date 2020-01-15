/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.util.Set;
import ku.piii2019.bl3.CustomLogging;
import ku.piii2019.bl3.DuplicateFindFromFilename;
import ku.piii2019.bl3.DuplicateFindFromID3;
import ku.piii2019.bl3.DuplicateFinder;
import ku.piii2019.bl3.MediaFileService;
import ku.piii2019.bl3.MediaItem;
import ku.piii2019.bl3.SearchService;
import ku.piii2019.bl3.SimpleSearch;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author regor
 */
public class DoRefile implements CLICommandProcessor {

    public static CLICommandProcessor instance = null;

    public static void main(String[] args) {
        DoRefile.getInstance().processArgs(args);
    }

    private DoRefile() {
    }

    public static CLICommandProcessor getInstance() {
        if (instance == null) {
            instance = new DoRefile();
        }
        return instance;
    }

    @Override
    public void processArgs(String... args) {
        CommandLineParser clp = DefaultParserSingleton.getInstance();
        Options opts = getDefaultOptions();

        try {

            CommandLine cmd = clp.parse(opts, args);
            processArgsBody(cmd, opts);
        } catch (ParseException pex) {
            CustomLogging.logIt(pex);

        }

    }

    @Override
    public void processArgsBody(CommandLine cmd, Options opts) {
        if (cmd.hasOption("h")) {
            String usage = "-s <source_folder> -d <destination_folder> [-ID3EX | -FEX | -NOEX] [-g <genre_filter> | -a <artist_filter>]";
            CLIHelpFormatter.printHelp(opts, usage);
        } else if (cmd.hasOption("s")) {

            String srcFolder = cmd.getOptionValue("s");
            String dstFolder = cmd.hasOption("d") ? cmd.getOptionValue("d") : srcFolder;

            DuplicateFinder df = null;
            if (cmd.hasOption("ID3EX")) {
                df = new DuplicateFindFromID3();
            } else if (cmd.hasOption("FEX")) {
                df = new DuplicateFindFromFilename();
            }

            SearchService.FilterType type = null;

            Set<MediaItem> foundItems = MediaFileService.getInstance().getAllID3MediaItems(srcFolder, df)[0];
            String searchTerms[] = null;
            try {
                if (cmd.hasOption("a")) {
                    searchTerms = cmd.getOptionValues("a");
                    type = SearchService.FilterType.ARTIST;
                } else if (cmd.hasOption("g")) {
                    searchTerms = cmd.getOptionValues("g");
                    type = SearchService.FilterType.GENRE;

                }
            } catch (Exception ex) {
                String message = "No filter argument (genre or artist) has been specified.";
                CustomLogging.logIt(message, null);
                CustomLogging.logIt(ex);
            }
            SearchService searchService = new SimpleSearch();
            Set<MediaItem> filteredItems = searchService.filterBy(searchTerms, foundItems, type);

            for (MediaItem m : filteredItems) {
                MediaFileService.getInstance().refileAndCopyMediaItem(dstFolder, m);
            }
        }

    }
    
    public Options getDefaultOptions(){
         
            Options defaultRefileOptions = new Options();
            
            Option o = null;
            
            o = Option.builder("h").hasArg(false).longOpt("help").desc("This screen.").build();
            defaultRefileOptions.addOption(o);

            o = Option.builder("s").hasArg(true).argName("source-folder").longOpt("src").desc("Source folder to scan. If no artist or genre filter is given, then every single file in the folder will be processed.").required(false).build();
            defaultRefileOptions.addOption(o);
            
            o = Option.builder("d").hasArg(true).argName("destination-folder").longOpt("dsc").desc("Optional destination folder to save the m3u file.").required(false).build();
            defaultRefileOptions.addOption(o);
            
            //Filters group xtra mile
            OptionGroup og = new OptionGroup();
            o = Option.builder("a").hasArgs().valueSeparator(',').longOpt("artist").desc("Filtering playlist by a single or multiple artists. If multiple, separete with \",\" (commas).").build();
            og.addOption(o);
            
            o = Option.builder("g").hasArgs().valueSeparator(',').longOpt("genre").desc("Filtering playlist by a single or multiple genres.  If multiple, separete with \",\" (commas).").build();
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
     
        
        return defaultRefileOptions;
    }
}
