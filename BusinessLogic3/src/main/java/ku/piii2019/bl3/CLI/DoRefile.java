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
import ku.piii2019.bl3.M3U;
import ku.piii2019.bl3.MediaItem;
import ku.piii2019.bl3.SearchService;
import ku.piii2019.bl3.SimpleSearch;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author regor
 */
public class DoRefile implements CLICommandProcessor{
      public static CLICommandProcessor instance = null;

    private DoRefile() {
    }

    ;
    public static CLICommandProcessor getInstance() {
        if (instance == null) {
            instance = new DoRefile();
        }
        return instance;
    }

    
    @Override
    public void processArgs(String... args) {
        CommandLineParser clp = DefaultParserSingleton.getInstance();
        Options opts = DefaultOptions.getDefaultRefileOptions();

        try {

            CommandLine cmd = clp.parse(opts, args);
            processArgsBody(cmd);
        } catch (ParseException pex) {
            CustomLogging.logIt(pex);

        }

    }

    @Override
    public void processArgsBody(CommandLine cmd) {
        String srcFolder = cmd.getOptionValue('s');
        String destinationFolder = cmd.hasOption("d") ? cmd.getOptionValue("d") : srcFolder;

        DuplicateFinder df = null;
        if (cmd.hasOption("ID3EX")) {
            df = new DuplicateFindFromID3();
        } else if (cmd.hasOption("FEX")) {
            df = new DuplicateFindFromFilename();
        }
        
        SearchService.FilterType type = null;

        Set<MediaItem> foundItems = MediaFileService.getInstance().getAllID3MediaItems(srcFolder, df);
        String[] searchTerms = null;
        if (cmd.hasOption("a")) {
                searchTerms = cmd.getOptionValue("a").split(" ");
                type = SearchService.FilterType.ARTIST;
        } else if (cmd.hasOption("g")) {
                searchTerms = cmd.getOptionValue("a").split(" ");
                type = SearchService.FilterType.GENRE;

        } 
        
        

        SearchService searchService = new SimpleSearch(); 
        Set<MediaItem> filteredItems = searchService.filterBy(searchTerms, foundItems, type);
        
        for(MediaItem m : filteredItems){
            MediaFileService.getInstance().refileAndCopyMediaItem(srcFolder, m);
        }
        
        
        
    }
}
