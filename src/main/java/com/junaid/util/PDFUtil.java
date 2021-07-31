package com.junaid.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class PDFUtil {
    public static void clearConsole() {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (Exception e) {
                System.out.println("Unable to Clear the Screen!!");
            }
        }
    }

    public static void welcomeMessage() {
        System.out.println("Welcome to the PDFUtility, developed by Mohammed Junaid Khan(junaid4812@gmail.com)");
    }

    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }

    public static String current() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return Instant.now().atZone(ZoneOffset.UTC).format(formatter);
    }

//    public static void isValidPDF(File file, String password) throws Exception {
//        try{
//            PreflightParser parser = new PreflightParser(file);
//            parser.parse();
//            try (PreflightDocument document = parser.getPreflightDocument()) {
//                document.validate();
//                ValidationResult result = document.getResult();
//            }
//        }catch (IOException e){
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            throw new Exception("[ERROR]: Failed to validate " + file.getName());
//        }
//
//    }
//
//    public static void isValidPDF1(File file, String password) throws Exception {
//        RandomAccessFile accessFile;
//        PDFParser parser;
//        try {
//            accessFile = new RandomAccessFile(file, "r");
//            if (!PDFConstants.NOT_PROVIDED.equals(password)) {
//                try {
//                    //Check if its able to access with the password provided
//                    parser = new PDFParser(accessFile, password);
//                   //parser.setLenient(false);
//                    parser.parse();
//                } catch (IOException e) {
//                    //Check if its able to access without password
//                    parser = new PDFParser(accessFile, password);
//                    parser.setLenient(false);
//                    parser.parse();
//                }
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//            throw new Exception("[ERROR]: Failed to validate " + file.getName());
//        }
//    }
}
