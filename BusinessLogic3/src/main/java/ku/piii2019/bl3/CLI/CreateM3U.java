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
import ku.piii2019.bl3.SearchService.FilterType;
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
        Options opts =getDefaultOptions();

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
                parsedMaximumLength = parsedMaximumLength*60;
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
    
    public Options getDefaultOptions(){
       
            Options defaultM3UOptions = new Options();
            
            Option o = null;
            
            o = Option.builder("h").hasArg(false).longOpt("help").desc("This screen").build();
            defaultM3UOptions.addOption(o);

            o = Option.builder("f").hasArg(true).argName("output-file").longOpt("filename").desc("Filename to be used to save the generated list.").required(false).build();
            defaultM3UOptions.addOption(o);
            
            o = Option.builder("s").hasArg(true).argName("source-folder").longOpt("src").desc("Source folder to scan. If no artist or genre filter is given, then every single file in the folder will be processed.").required(false).build();
            defaultM3UOptions.addOption(o);
            
            o = Option.builder("d").hasArg(true).argName("destination-folder").longOpt("dsc").desc("Optional destination folder to save the m3u file. If not specified, it will be saved in the src folder.").required(false).build();
            defaultM3UOptions.addOption(o);
            
            //Filters group
            OptionGroup og = new OptionGroup();
            o = Option.builder("a").hasArgs().valueSeparator(',').longOpt("artist").desc("Filtering playlist by a single or multiple artists.").build();
            og.addOption(o);
            
            o = Option.builder("g").hasArgs().valueSeparator(',').longOpt("genre").desc("Filtering playlist by a single or multiple genres.").build();
            og.addOption(o);
              og.addOption(o);
            og.setRequired(false);
             defaultM3UOptions.addOptionGroup(og);
          
            //optional length specification
            o = Option.builder("ml").hasArg(true).longOpt("max-length").desc("The maximum length of the playlist in minutes. Fractions are welcome.:) So 1.1 will result in a 1 minute 6 second playlist.").required(false).build();
            
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
     
        
        return defaultM3UOptions;
    }

}
