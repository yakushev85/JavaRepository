package org.oiakushev.favwconverter.model;

import java.io.File;

public class FileToConvert {
    private final File file;
    private ConvertStatus status;

    public FileToConvert(File file) {
        this.file = file;
        this.status = ConvertStatus.READY;
    }

    public File getFile() {
        return file;
    }

    public ConvertStatus getStatus() {
        return status;
    }

    public void setStatus(ConvertStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "(" + status + ") " + file.getName() + " <" + file.getPath() + ">";
    }
}
