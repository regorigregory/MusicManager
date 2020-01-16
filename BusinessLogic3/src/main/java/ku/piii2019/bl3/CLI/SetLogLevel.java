/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl3.CLI;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import ku.piii2019.bl3.CustomLogging;
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
public class SetLogLevel implements CLICommandProcessor {

    private static SetLogLevel instance = null;
    private static String[] severityLevels = new String[]{
        "SEVERE",
        "WARNING",
        "INFO",
        "CONFIG",
        "FINE",
        "FINER",
        "FINEST",
        "OFF",
        "ALL"};

    private SetLogLevel() {
    }

    public static void main(String[] args) {
        SetLogLevel.getInstance().processArgs(args);
    }

    public static CLICommandProcessor getInstance() {
        if (instance == null) {
            instance = new SetLogLevel();
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
            String usage = "[-f <filename>] [-l <SEVERE | WARNING | INFO | CONFIG | FINE | FINER | FINEST | ALL | OFF>]\r\n"
                    + "Note: by default, every time \"f\" options are used, it adds a new handler.\r\n"
                    + " In order to remove the already added ones, use the -r option.\r\n"
                    + "If not removed, it can result in multiple outputs.\r\n"
                    + "If \"l\" option is not specified, than the default logging level is: Warning.\r\n"
                    //+ "Please also note, that the default file log is set to the relative path \"./log.log\""
                    + "Additionally: console handler cannot be added. It has been disabled.";
            CLIHelpFormatter.printHelp(opts, usage);
            return;
        }
           if (cmd.hasOption("hs")) {
            CustomLogging.printAmountOfFileHandlers();
            return;
        }
        if (cmd.hasOption("r")) {
            CustomLogging.removeAllFileHandlers();
            CustomLogging.printIt("File handlers have been successfully removed.");
        }
        if (cmd.hasOption("f")) {

            CustomLogging.addHandler(CustomLogging.getFileHandler(cmd.getOptionValue("f")));
           CustomLogging.printIt("File handler have been successfully added.");
        }
//        if (cmd.hasOption("c")) {
//            CustomLogging.addHandler(CustomLogging.getConsoleHandler());
//        }
        
        if (cmd.hasOption("l")) {
            String message = "You have succesfully set the logging level to: "+cmd.getOptionValue("l");
            switch (cmd.getOptionValue("l")) {
                case "SEVERE":
                    CustomLogging.setLevel(Level.SEVERE);
                    break;
                case "WARNING":
                    CustomLogging.setLevel(Level.WARNING);
                    break;
                case "INFO":
                    CustomLogging.setLevel(Level.INFO);
                    break;
                case "CONFIG":
                    CustomLogging.setLevel(Level.CONFIG);
                    break;
                case "FINE":
                    CustomLogging.setLevel(Level.FINE);
                    break;
                case "FINER":
                    CustomLogging.setLevel(Level.FINER);
                    break;
                case "FINEST":
                    CustomLogging.setLevel(Level.FINEST);
                    break;
                case "OFF":
                    CustomLogging.setLevel(Level.OFF);
                    break;
                case "ALL":
                    CustomLogging.setLevel(Level.ALL);
                    break;
                default:
                    CustomLogging.getInstance().warning("The provided argument is invalid. Use \"-h\" to learn about allowed values.");
                    message="Operation unsuccessful";
                    break;
              
            }
              CustomLogging.printIt(message);
        }

    }

    @Override
    public Options getDefaultOptions() {

        Options defaultLoggingOptions = new Options();

        Option o = null;

        o = Option.builder("h").hasArg(false).longOpt("help").desc("This screen.").build();
        defaultLoggingOptions.addOption(o);
//            o = Option.builder("c").hasArg(false).longOpt("copy").desc("command to copy:)").required().build();
//            defaultOptions.addOption(o);
        o = Option.builder("hs").hasArg(false).longOpt("handlers").desc("Displays the amount of file handlers  having been added to this logger.").build();
        defaultLoggingOptions.addOption(o);
        o = Option.builder("f").longOpt("LOG_INTO_FILE").hasArg(true).argName("path_to_logfile_name").desc("Adding a file log handler to this logger.").required(false).build();
        defaultLoggingOptions.addOption(o);

//        o = Option.builder("c").longOpt("LOG_INTO_CONSOLE").hasArg(false).longOpt("src").desc("Adding a console output to the Logger.").required(false).build();
//        defaultLoggingOptions.addOption(o);

        o = Option.builder("r").longOpt("remove_handlers").hasArg(false).desc("Removing the file handlers having been added to this logger.").required(false).build();
        defaultLoggingOptions.addOption(o);

        o = Option.builder("l").longOpt("LEVEL").hasArg(true).argName("LEVEL").desc("Set the logging level to this value.").required(false).build();
        defaultLoggingOptions.addOption(o);

//             o = Option.builder("m").hasArg(false).longOpt("move").desc("Using this flag, it will move instead of copying it.").required(false).build();
//            defaultOptions.addOption(o);
        return defaultLoggingOptions;
    }
}
