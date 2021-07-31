package com.junaid.Action;

import com.junaid.util.PDFConstants;
import com.junaid.util.PDFUtil;
import org.apache.commons.cli.CommandLine;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MergeAction extends Action {

    MergeAction(CommandLine cmd) {
        super(cmd);
    }

    @Override
    public void performAction() throws Exception {
        List<PDDocument> tobeclosed = new ArrayList<>();
        PDDocument mergedPDF = null;
        try {
            List<File> files = Files.list(Paths.get(userInputs.getSourceDir()))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".pdf"))
                    .map(Path::toFile)
                    .sorted(Comparator.comparing(File::getName))
                    .collect(Collectors.toList());


            String destFileName = getDestinationFileName();
            mergedPDF = new PDDocument();
            PDFMergerUtility ut = new PDFMergerUtility();

            for (File file : files) {
                PDDocument pd = loadPDF(file);
                tobeclosed.add(pd);
                ut.appendDocument(mergedPDF, pd);
            }

            mergedPDF.save(destFileName);
            System.out.println("[DONE]: Merge Completed!! \n OutputFile:" + destFileName);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (mergedPDF != null) {
                mergedPDF.close();
            }
            for (PDDocument doc : tobeclosed) {
                doc.close();
            }
        }
        /*
        * Without Encryption
            PDFMergerUtility ut = new PDFMergerUtility();
            ut.setDestinationFileName(destFileName);
            for (File file : files) {
              ut.addSource(file);
            }
            ut.mergeDocuments(null);
            System.out.println("Merge Completed!! \n OutputFile:" + ut.getDestinationFileName());
        * */
    }

    private String getDestinationFileName() {
        String fileName = PDFConstants.NOT_PROVIDED.equals(userInputs.getFileName()) ?
                String.format(PDFConstants.OUTPUT_FILENAME, PDFUtil.current()) : userInputs.getFileName();
        return userInputs.getDestDir() + "\\" + fileName;
    }
}
