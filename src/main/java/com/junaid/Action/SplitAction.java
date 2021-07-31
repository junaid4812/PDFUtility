package com.junaid.Action;

import com.junaid.util.PDFConstants;
import org.apache.commons.cli.CommandLine;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SplitAction extends Action {
    SplitAction(CommandLine cmd) {
        super(cmd);
    }

    @Override
    public void performAction() throws Exception {
        PDDocument pd = null;
        List<PDDocument> tobeclosed = new ArrayList<>();
        try {
            File sourceFile = performValidation();
            pd = loadPDF(sourceFile);
            int totalPages = pd.getNumberOfPages();
            System.out.println(String.format(PDFConstants.MSG_INFO_TOTAL_PAGES, totalPages, sourceFile.getName()));
            Splitter splitter = new Splitter();
            splitter.setSplitAtPage(userInputs.getPageNumber());

            List<PDDocument> splitDocs = splitter.split(pd);
            String outputFileName = userInputs.getDestDir() +
                    "\\" +
                    userInputs.getFileName().replace(".pdf", "") +
                    "(%d).pdf";
            int i = 1;
            for (PDDocument doc : splitDocs) {
                doc.save(String.format(outputFileName, i++));
                tobeclosed.add(doc);
            }

            System.out.println("[DONE]: Split Completed!! \n OutputLocation:" + userInputs.getDestDir());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (pd != null) {
                pd.close();
            }
            for (PDDocument doc : tobeclosed) {
                doc.close();
            }
        }

    }

    private File performValidation() throws Exception {
        if (userInputs.getPageNumber() < 1) {
            throw new Exception(PDFConstants.ERR_INVALID_PAGENUMBER);
        }
        if (PDFConstants.NOT_PROVIDED.equals(userInputs.getFileName())) {
            throw new Exception(PDFConstants.ERR_FILENAME_REQUIRED);
        }
        String filePath = userInputs.getSourceDir() + "\\" + userInputs.getFileName();
        return new File(filePath);
    }

}
