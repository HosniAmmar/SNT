package com.img.snt.Model;

public class Source {
    public String sourceName;
    public String sourceUrl;
    public String language;

    public String category;//Actualités / Economie / Bourse …

    public String type;// A la une / News /TopNews
    public int limit;
    public int  priority;

    public Source() {
    }

    public Source(String sourceName, String sourceUrl, String language, String category, String type, int limit, int priority) {
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.language = language;
        this.category = category;
        this.type = type;
        this.limit = limit;
        this.priority = priority;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLimite() {
        return limit;
    }

    public void setLimite(int limit) {
        this.limit = limit;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}

