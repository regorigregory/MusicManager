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
            String usage = "[-f <filename> | -c] [-l <SEVERE | WARNING | INFO | CONFIG | FINE | FINER | FINEST | ALL | OFF>]";
            CLIHelpFormatter.printHelp(opts, usage);
            return;
        }
        if (cmd.hasOption("f")) {
            CustomLogging.removeAllHandlers();
            CustomLogging.addHandler(CustomLogging.getFileHandler(cmd.getOptionValue("f")));
        } else if (cmd.hasOption("c")) {
            CustomLogging.removeAllHandlers();
            CustomLogging.addHandler(CustomLogging.getConsoleHandler());
        }
        
        if(cmd.hasOption("l")){
            switch(cmd.getOptionValue("l")){
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
                    break;
            }
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
        OptionGroup fileOrConsoleGroup = new OptionGroup();
        o = Option.builder("f").longOpt("LOG_INTO_FILE").hasArg(true).argName("<path_to_logfile_name>").desc("The filename and its path.").required(false).build();
        fileOrConsoleGroup.addOption(o);

        o = Option.builder("c").longOpt("LOG_INTO_CONSOLE").hasArg(false).longOpt("src").desc("Setting the logging exclusively to the console output.").required(false).build();
        fileOrConsoleGroup.addOption(o);

        defaultLoggingOptions.addOptionGroup(fileOrConsoleGroup);

        o = Option.builder("-s").longOpt("LEVEL").hasArg(true).argName("<LEVEL>").desc("Set the logging level to this value.").required(false).build();
        defaultLoggingOptions.addOption(o);

//             o = Option.builder("m").hasArg(false).longOpt("move").desc("Using this flag, it will move instead of copying it.").required(false).build();
//            defaultOptions.addOption(o);
        return defaultLoggingOptions;
    }
}
