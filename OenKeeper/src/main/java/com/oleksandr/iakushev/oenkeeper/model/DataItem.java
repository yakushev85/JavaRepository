package com.oleksandr.iakushev.oenkeeper.model;

import java.util.UUID;

public class DataItem {
    private UUID id;
    private String title;
    private String description;
    private String sensitiveInfo;

    public UUID getId() {
        return id;
    }

    public void initId() {
        this.id = UUID.randomUUID();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSensitiveInfo() {
        return sensitiveInfo;
    }

    public void setSensitiveInfo(String sensitiveInfo) {
        this.sensitiveInfo = sensitiveInfo;
    }
}
