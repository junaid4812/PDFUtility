package com.junaid.util;

public interface PDFConstants {

    String ACTION_MERGE ="MERGE";
    String ACTION_SPLIT = "SPLIT";
    String ACTION_EXTRACT = "EXTRACT";

    String PARAM_ACTION = "action";
    String PARAM_PASSWORD= "password";
    String PARAM_SOURCEDIR= "source";
    String PARAM_DESTDIR= "destination";
    String PARAM_FILENAME= "filename";
    String PARAM_SPLITNUMBER= "pagenumber";

    String NOT_PROVIDED = "NOTPROVIDED";
    String OUTPUT_FILENAME = "Output%s.pdf";

    String MSG_INFO_TOTAL_PAGES ="[INFO]: Total %d pages in %s";
    String MSG_INFO_USER_INPUTS = "[INFO]: Inputs provided are: \n Action: %s " +
            "\n SourceDir: %s" +
            "\n DestDir: %s" +
            "\n FileName: %s" +
            "\n PageNumber: %s";
            //"\n Password: %s";



    String ERR_INVALID_ACTION ="[ERROR]: Invalid Action '%s'. Only possible actions are %s, %s, %s";
    String ERR_INVALID_PAGENUMBER ="[ERROR]: Page number is mandatory for the split action and its value should be greater than 1";
    String ERR_FILENAME_REQUIRED ="[ERROR]: For Split action source file name is required";

}
