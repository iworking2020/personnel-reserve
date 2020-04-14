package ru.iworking.personnel.reserve.model;

public class TreeStep {

    public enum Type { CATEGORY, VALUE }

    private Long code;
    private String name;
    private Type type;

    public TreeStep() { }

    public TreeStep(Long code, String name, Type type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public Long getCode() {
        return code;
    }
    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }
}

