/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.nio.file.Paths;
import ku.piii2019.bl3.CustomLogging;
import ku.piii2019.bl3.DuplicateFindFromFilename;
import ku.piii2019.bl3.DuplicateFindFromID3;
import ku.piii2019.bl3.DuplicateFinder;
import ku.piii2019.bl3.MediaFileService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
//import org.apache.commons.cli.ParseException;

/**
 *
 * @author regor
 */
public class DoCopy implements CLICommandProcessor {

    public static CLICommandProcessor instance = null;

    public static void main(String[] args) {
        DoCopy.getInstance().processArgs(args);
    }

    private DoCopy() {
    }

    public static CLICommandProcessor getInstance() {
        if (instance == null) {
            instance = new DoCopy();
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
        } catch (Exception pex) {
            CustomLogging.logIt(pex);

        }

    }

    @Override
    public void processArgsBody(CommandLine cmd, Options opts) {

        if (cmd.hasOption("h")) {
            String usage = "-s <source_folder> -d <destination_folder> [-ID3EX | -FEX | -NOEX]";
            CLIHelpFormatter.printHelp(opts, usage);
        } else if (cmd.hasOption("s") && cmd.hasOption("d")) {
            String srcFolder = cmd.getOptionValue('s');
            String dstFolder = cmd.getOptionValue('d');
            DuplicateFinder df = null;

            if (cmd.hasOption("ID3EX")) {
                df = new DuplicateFindFromID3();
            } else if (cmd.hasOption("FEX")) {
                df = new DuplicateFindFromFilename();
            }

            MediaFileService fsi = MediaFileService.getInstance();
            fsi.copyMediaFiles(srcFolder, dstFolder, df);
        } else {
            System.out.println("The provided arguments are not valid. Use \"-h\" to view usage");
        }
        return;

    }

    public Options getDefaultOptions() {

        Options defaultCopyOptions = new Options();

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
        return defaultCopyOptions;
    }

}
