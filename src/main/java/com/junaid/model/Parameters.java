package com.junaid.model;

import com.junaid.util.PDFConstants;
import org.apache.commons.cli.*;

public class Parameters {
    public Options options;
    public CommandLine parameters;

    public Parameters() {
        if (options == null) {
            options = new Options();

            Option action = new Option("a", PDFConstants.PARAM_ACTION, true,
                    "PDF Action e.g. Merge, Split, Extract");
            action.setRequired(true);
            options.addOption(action);

            Option destination = new Option("d", PDFConstants.PARAM_DESTDIR, true,
                    "Destination directory, default destination is the output directory at tool location");
            destination.setRequired(false);
            options.addOption(destination);

            Option fileName = new Option("f", PDFConstants.PARAM_FILENAME, true,
                    "Filename - Acts as input for Split & Extract action and as output name for merge action");
            fileName.setRequired(false);
            options.addOption(fileName);


            Option number = new Option("n", PDFConstants.PARAM_SPLITNUMBER, true,
                    "Page number to split the file");
            number.setRequired(false);
            options.addOption(number);

            Option password = new Option("p", PDFConstants.PARAM_PASSWORD, true,
                    "Password for encrypted pdf");
            password.setRequired(false);
            options.addOption(password);


            Option source = new Option("s", PDFConstants.PARAM_SOURCEDIR, true,
                    "Source Directory");
            source.setRequired(true);
            options.addOption(source);
        }
    }

    public CommandLine readInputParameters(String[] args) throws ParseException {
        if(parameters == null) {
            HelpFormatter formatter = new HelpFormatter();
            CommandLineParser parser = new DefaultParser();
            try {
                parameters = parser.parse(options, args);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                formatter.printHelp("PDF Utility Info", options);
                throw e;
            }
        }
        return parameters;
    }
}
