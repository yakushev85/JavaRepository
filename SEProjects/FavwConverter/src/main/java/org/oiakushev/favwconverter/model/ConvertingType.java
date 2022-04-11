package org.oiakushev.favwconverter.model;

public enum ConvertingType {
    MKV(0, "ffmpeg -i \"%s\" \"%s\"", "mkv"),
    AVI(1, "ffmpeg -i \"%s\" \"%s\"", "avi"),
    MP4(2, "ffmpeg -i \"%s\" \"%s\"", "mp4"),

    MP3(3, "ffmpeg -i \"%s\" -acodec libmp3lame -vn \"%s\"", "mp3"),
    OGG(4, "ffmpeg -i \"%s\" -acodec libvorbis -vn \"%s\"", "ogg"),
    AAC(5, "ffmpeg -i \"%s\" -acodec libfaac -vn \"%s\"", "aac");

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

    public String generateCmd(String filename, String pathTo) {
        int indexExt = filename.lastIndexOf(".");
        String filenameWithoutExt = (indexExt < 0)?filename:filename.substring(0, indexExt);

        return String.format(cmd, filename, pathTo + filenameWithoutExt + "." + ext);
    }
}
