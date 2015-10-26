package com.hcse.file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.util.List;

public class ListFile {
    private List<String> fileNameList;

    public ListFile(String name) throws IOException {
        fileNameList = java.nio.file.Files.readAllLines(FileSystems.getDefault().getPath(name), Charset.forName("GBK"));
    }

    public List<String> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }
}
