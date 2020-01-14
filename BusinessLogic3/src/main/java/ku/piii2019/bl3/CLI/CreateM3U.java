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
import ku.piii2019.bl3.FileServiceImpl;
import ku.piii2019.bl3.M3U;
import ku.piii2019.bl3.MediaItem;
import ku.piii2019.bl3.SearchService;
import ku.piii2019.bl3.SearchService.FilterType;
import ku.piii2019.bl3.SimpleSearch;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author regor
 */
public class CreateM3U implements CLICommandProcessor {

    public static CLICommandProcessor instance = null;

    private CreateM3U() {
    }

    ;
    public static CLICommandProcessor getInstance() {
        if (instance == null) {
            instance = new CreateM3U();
        }
        return instance;
    }

    @Override
    public void processArgs(String... args) {
        CommandLineParser clp = DefaultParserSingleton.getInstance();
        Options opts = DefaultOptions.getDefaultM3UOptions();

        try {

            CommandLine cmd = clp.parse(opts, args);
            processArgsBody(cmd);
        } catch (ParseException pex) {
            CustomLogging.logIt(pex);

        }

    }

    @Override
    public void processArgsBody(CommandLine cmd) {
        String fileNameToSave = cmd.getOptionValue('f');
        String srcFolder = cmd.getOptionValue('s');
        String destinationFolder = cmd.hasOption("d") ? cmd.getOptionValue("d") : srcFolder;
        String maximumLength = cmd.hasOption("ml") ? cmd.getOptionValue("ml") : null;

        DuplicateFinder df = null;
        if (cmd.hasOption("ID3EX")) {
            df = new DuplicateFindFromID3();
        } else if (cmd.hasOption("FEX")) {
            df = new DuplicateFindFromFilename();
        }
        
        FilterType type = null;

        Set<MediaItem> foundItems = FileServiceImpl.getInstance().getAllID3MediaItems(srcFolder, df);
        String[] searchTerms = null;
        if (cmd.hasOption("a")) {
                searchTerms = cmd.getOptionValue("a").split(" ");
                type = FilterType.ARTIST;
        } else if (cmd.hasOption("g")) {
                searchTerms = cmd.getOptionValue("a").split(" ");
                type = FilterType.GENRE;

        } 
        
         SearchService searchService = new SimpleSearch();
        Set<MediaItem> filteredItems = searchService.filterBy(searchTerms, foundItems, type);
        String header = M3U.getHeader();

       

        FileServiceImpl.getInstance().writeLineToFile(fileNameToSave, destinationFolder, header);

        
        for(MediaItem mi : filteredItems){
            String line = M3U.getMediaItemInf(mi, false);
            FileServiceImpl.getInstance().writeLineToFile(fileNameToSave, destinationFolder, line);
        }
        
        
        
    }

}
