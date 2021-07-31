package com.junaid.model;

import com.junaid.util.PDFConstants;
import com.junaid.util.PDFUtil;

public class UserInputs {
    private String sourceDir;
    private String destDir;
    private String fileName;
    private String password;
    private int pageNumber;

    public UserInputs(String sourceDir, String destDir, String fileName, String password, int pageNumber) {
        this.setDestDir(destDir);
        this.setSourceDir(sourceDir);
        this.setFileName(fileName);
        this.setPassword(password);
        this.setPageNumber(pageNumber);
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    public String getDestDir() {
        return destDir;
    }

    public void setDestDir(String destDir) {
        if (PDFConstants.NOT_PROVIDED.equals(destDir)) {
            this.destDir = PDFUtil.getCurrentDirectory();
        } else {
            this.destDir = destDir;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

}
