package org.oiakushev.favwconverter.model;

import java.io.File;

public enum ConvertingType {
    MKV(0, "ffmpeg -nostats -loglevel 0 -i \"%s\" \"%s\"", "mkv"),
    AVI(1, "ffmpeg -nostats -loglevel 0 -i \"%s\" \"%s\"", "avi"),
    MP4(2, "ffmpeg -nostats -loglevel 0 -i \"%s\" \"%s\"", "mp4"),

    MP3(3, "ffmpeg -nostats -loglevel 0 -i \"%s\" -acodec libmp3lame -vn \"%s\"", "mp3"),
    OGG(4, "ffmpeg -nostats -loglevel 0 -i \"%s\" -acodec libvorbis -vn \"%s\"", "ogg"),
    AAC(5, "ffmpeg -nostats -loglevel 0 -i \"%s\" -acodec libfaac -vn \"%s\"", "aac");

    private final int index;
    private final String cmd;
    private final String ext;

    ConvertingType(int index, String cmd, String ext) {
        this.index = index;
        this.cmd = cmd;
        this.ext = ext;
    }

    public int getIndex() {
        return index;
    }

    public String getCmd() {
        return cmd;
    }

    public String getExt() {
        return ext;
    }

    public String generateCmd(String inFullFilename, String inFilename, String pathTo) {
        int indexExt = inFilename.lastIndexOf(".");
        String filenameWithoutExt = (indexExt < 0) ? inFilename : inFilename.substring(0, indexExt);
        String separatorValue = (pathTo.endsWith(File.separator)) ? "" : File.separator;
        String outputFileName = pathTo + separatorValue + filenameWithoutExt + "." + ext;

        File outputFile = new File(outputFileName);
        if (outputFile.exists()) {
            if (!outputFile.delete()) {
                throw new IllegalArgumentException("Can't delete following file: " + outputFileName);
            }
        }

        return String.format(cmd, inFullFilename, outputFileName);
    }
}
