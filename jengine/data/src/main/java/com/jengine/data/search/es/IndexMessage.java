package com.jengine.data.search.es;

/**
 * IndexMessage
 *
 * @author bl07637
 * @date 4/12/2017
 * @since 0.1.0
 */
public class IndexMessage {

    private String index;
    private String type;
    private String routing;
    private String id;
    private String source;

    public IndexMessage(String index, String type, String routing, String id, String source) {
        this.index = index;
        this.type = type;
        this.routing = routing;
        this.id = id;
        this.source = source;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
