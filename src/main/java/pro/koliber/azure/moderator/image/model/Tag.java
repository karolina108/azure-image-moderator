package pro.koliber.azure.moderator.image.model;

import java.io.Serializable;

public class Tag implements Serializable {

    private String name;
    private Double confidence;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
