package net.skinsrestorer;

import net.skinsrestorer.api.property.IProperty;

public class GProperty implements IProperty {
    private String name;
    private String value;
    private String signature;

    public GProperty(String name, String value, String signature) {
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public Object getHandle() {
        return null;
    }

    public String getName() {
        return null;
    }

    public void setName(String name) {

    }

    public String getValue() {
        return null;
    }

    public void setValue(String value) {

    }

    public String getSignature() {
        return null;
    }

    public void setSignature(String signature) {

    }
}
