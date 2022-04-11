package org.oiakushev.favwconverter.model;

public enum ConvertStatus {
    READY(0, "Ready"),
    CONVERTING(1, "Converting"),
    COMPLETED(2, "Completed");

    private final int index;
    private final String displayText;

    ConvertStatus(int index, String displayText) {
        this.index = index;
        this.displayText = displayText;
    }

    public int getIndex() {
        return index;
    }

    public String getDisplayText() {
        return displayText;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
