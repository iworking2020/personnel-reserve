package ru.iworking.personnel.reserve.model;

public class TreeStep {

    public enum StepType { CATEGORY, VALUE }

    private Long code;
    private String name;
    private StepType stepType;

    public TreeStep() { }

    public TreeStep(Long code, String name, StepType stepType) {
        this.code = code;
        this.name = name;
        this.stepType = stepType;
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

    public StepType getType() {
        return stepType;
    }
    public void setType(StepType stepType) {
        this.stepType = stepType;
    }

    @Override
    public String toString() {
        return name;
    }
}

