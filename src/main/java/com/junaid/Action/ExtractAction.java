package com.junaid.Action;

import com.junaid.util.PDFConstants;
import org.apache.commons.cli.CommandLine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ExtractAction extends Action {
    ExtractAction(CommandLine cmd) {
        super(cmd);
    }

    @Override
    public void performAction() throws Exception {
        List<PDDocument> tobeclosed = new ArrayList<>();
        try {
            List<File> files;
            if (PDFConstants.NOT_PROVIDED.equals(userInputs.getFileName())) {
                files = Files.list(Paths.get(userInputs.getSourceDir()))
                        .filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".pdf"))
                        .map(Path::toFile)
                        .sorted(Comparator.comparing(File::getName))
                        .collect(Collectors.toList());
            } else {
                String filePath = userInputs.getSourceDir() + "\\" + userInputs.getFileName();
                File sourceFile = new File(filePath);
                files = new ArrayList<>();
                files.add(sourceFile);
            }
            for (File file : files) {
                PDDocument pd = loadPDF(file);
                extractAttachment(pd, file.getName());
                tobeclosed.add(pd);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            for (PDDocument doc : tobeclosed) {
                doc.close();
            }
        }
    }

    private void extractAttachment(PDDocument pd, String fileName) throws IOException {

        pd.setAllSecurityToBeRemoved(true);
        /*
         * Attachments are stored as part of the "names" dictionary in the
         * document catalog
         */
        final PDDocumentNameDictionary names = new PDDocumentNameDictionary(pd.getDocumentCatalog());
        final PDEmbeddedFilesNameTreeNode efTree = names.getEmbeddedFiles();
        if (!Objects.isNull(efTree)) {
            System.out.println("Attachment Extraction for : "+fileName);
            for (Map.Entry<String, PDComplexFileSpecification> entry : efTree.getNames().entrySet()) {
                PDComplexFileSpecification fileSpec = entry.getValue();
                PDEmbeddedFile embeddedFile = getEmbeddedFile(fileSpec);
                if (embeddedFile != null) {
                    String embeddedFilename = userInputs.getDestDir()
                            + "\\Attachments_"
                            + fileName.replace(".pdf", "")
                            + "\\"
                            + fileSpec.getFilename();
                    File file = new File(embeddedFilename);
                    File parentDir = file.getParentFile();
                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(embeddedFile.toByteArray());
                    }
                    System.out.println("Extracted - "+ fileSpec.getFilename());
                }
            }
        } else {
            System.out.println("No Attachments available in the file: " + fileName);
        }
    }

    private PDEmbeddedFile getEmbeddedFile(PDComplexFileSpecification fileSpec) {
        // search for the first available alternative of the embedded file
        PDEmbeddedFile embeddedFile = null;
        if (fileSpec != null) {
            embeddedFile = fileSpec.getEmbeddedFileUnicode();
            if (embeddedFile == null) {
                embeddedFile = fileSpec.getEmbeddedFileDos();
            }
            if (embeddedFile == null) {
                embeddedFile = fileSpec.getEmbeddedFileMac();
            }
            if (embeddedFile == null) {
                embeddedFile = fileSpec.getEmbeddedFileUnix();
            }
            if (embeddedFile == null) {
                embeddedFile = fileSpec.getEmbeddedFile();
            }
        }
        return embeddedFile;
    }
}
