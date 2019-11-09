package com.img.snt.Model;

import java.util.List;

public class RSSObject {
    public String status;
    public Feed feed;
    public List<WebNews> items;

    public RSSObject(String status, Feed feed, List<WebNews> items) {
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    public RSSObject() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public List<WebNews> getItems() {
        return items;
    }

    public void setItems(List<WebNews> items) {
        this.items = items;
    }
}
