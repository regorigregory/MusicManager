/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.util.Set;
import ku.piii2019.bl3.CLI.CLICommandProcessor;
import ku.piii2019.bl3.CLI.DefaultOptions;
import ku.piii2019.bl3.CLI.DefaultParserSingleton;
import ku.piii2019.bl3.CustomLogging;
import ku.piii2019.bl3.DuplicateFindFromFilename;
import ku.piii2019.bl3.DuplicateFindFromID3;
import ku.piii2019.bl3.DuplicateFinder;
import ku.piii2019.bl3.MediaFileService;
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

    public static void main(String[] args) {
        CreateM3U.getInstance().processArgs(args);
    }

    private CreateM3U() {
    }

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
            processArgsBody(cmd, opts);
        } catch (ParseException pex) {
            CustomLogging.logIt(pex);

        }

    }

    @Override
    public void processArgsBody(CommandLine cmd, Options opts) {

        if (cmd.hasOption("h")) {
            String usage = "-s <source_folder>  -f <output_filename> [-d <destination_folder>][-ID3EX | -FEX | -NOEX] [-g <genre_filter1>, <genre_filter2>, <genre_filtern>"
                    + " | -a <artist_filter1>, <artist_filter2>, <artist_filtern>]";
            CLIHelpFormatter.printHelp(opts, usage);
        } else if (cmd.hasOption("s") && cmd.hasOption("f")) {

            String fileNameToSave = cmd.getOptionValue('f');
            if (!fileNameToSave.endsWith(".m3u")) {
                fileNameToSave += ".m3u";
            }
            String srcFolder = cmd.getOptionValue('s');
            String destinationFolder = cmd.hasOption("d") ? cmd.getOptionValue("d") : srcFolder;
            String maximumLength = cmd.hasOption("ml") ? cmd.getOptionValue("ml") : null;
            Double parsedMaximumLength = null;
            if (maximumLength != null) {
                parsedMaximumLength = Double.parseDouble(maximumLength);
                parsedMaximumLength = parsedMaximumLength * 60;
            }
            DuplicateFinder df = null;
            if (cmd.hasOption("ID3EX")) {
                df = new DuplicateFindFromID3();
            } else if (cmd.hasOption("FEX")) {
                df = new DuplicateFindFromFilename();
            }

            FilterType type = null;

            Set<MediaItem> foundItems = MediaFileService.getInstance().getAllID3MediaItems(srcFolder, df)[0];
            String[] searchTerms = null;
            if (cmd.hasOption("a")) {
                searchTerms = cmd.getOptionValues("a");
                type = FilterType.ARTIST;
            } else if (cmd.hasOption("g")) {
                searchTerms = cmd.getOptionValues("g");
                type = FilterType.GENRE;

            }

            SearchService searchService = new SimpleSearch();
            Set<MediaItem> filteredItems = searchService.filterBy(searchTerms, foundItems, type);
            String header = M3U.getHeader();

            MediaFileService.getInstance().writeLineToFile(fileNameToSave, destinationFolder, header);
            Integer currentLength = 0;
            for (MediaItem mi : filteredItems) {
                String line = M3U.getMediaItemInf(mi, false);
                if (parsedMaximumLength != 0) {
                    int lengthWhatIF = currentLength + mi.getLengthInSeconds();
                    if (lengthWhatIF > parsedMaximumLength) {
                        continue;

                    }
                    currentLength += mi.getLengthInSeconds();
                }
                MediaFileService.getInstance().writeLineToFile(fileNameToSave, destinationFolder, line);
            }
            System.out.println("The following playlist has been created:");
            System.out.println(fileNameToSave + " in the folder: " + destinationFolder);
        }

    }

}