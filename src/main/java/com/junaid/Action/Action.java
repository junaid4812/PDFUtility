package com.junaid.Action;

import com.junaid.model.UserInputs;
import com.junaid.util.PDFConstants;
import org.apache.commons.cli.CommandLine;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public abstract class Action {
    protected UserInputs userInputs;

    Action(CommandLine cmd) {
        printInputs(cmd);
    }

    abstract public void performAction() throws Exception;

    protected PDDocument loadPDF(File file) throws Exception {
        PDDocument pd = null;
        try {
            if (!PDFConstants.NOT_PROVIDED.equals(userInputs.getPassword())) {
                try {
                    //Check if its able to access with the password provided
                    pd = PDDocument.load(file, userInputs.getPassword());
                } catch (IOException e) {
                    //Check if its able to access without password
                    pd = PDDocument.load(file);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new Exception("[ERROR]: Failed to load pdf " + file.getName());
        }
        return pd;
    }

    private void printInputs(CommandLine cmd) {
        String action = cmd.getOptionValue(PDFConstants.PARAM_ACTION);
        String sourceDir = cmd.getOptionValue(PDFConstants.PARAM_SOURCEDIR);
        String destDir = getOptionalParameterValue(PDFConstants.PARAM_DESTDIR, cmd);
        String password = getOptionalParameterValue(PDFConstants.PARAM_PASSWORD, cmd);
        String fileName = getOptionalParameterValue(PDFConstants.PARAM_FILENAME, cmd);
        String pageNumber = getOptionalParameterValue(PDFConstants.PARAM_SPLITNUMBER, cmd);
        System.out.println(String.format(PDFConstants.MSG_INFO_USER_INPUTS,
                action, sourceDir, destDir, fileName, pageNumber));

        int number = PDFConstants.NOT_PROVIDED.equals(pageNumber) ? 0 : Integer.parseInt(pageNumber);
        this.userInputs = new UserInputs(sourceDir, destDir, fileName, password, number);
    }

    private String getOptionalParameterValue(String parameterName, CommandLine cmd) {
        return cmd.hasOption(parameterName) ? cmd.getOptionValue(parameterName) : PDFConstants.NOT_PROVIDED;
    }
}
