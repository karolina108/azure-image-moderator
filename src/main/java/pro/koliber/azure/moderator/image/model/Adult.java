package pro.koliber.azure.moderator.image.model;

import java.io.Serializable;

public class Adult implements Serializable {

    private Boolean isAdultContent;
    private Boolean isRacyContent;
    private Double adultScore;
    private Double racyScore;

    public Boolean getAdultContent() {
        return isAdultContent;
    }

    public void setAdultContent(Boolean adultContent) {
        isAdultContent = adultContent;
    }

    public Boolean getRacyContent() {
        return isRacyContent;
    }

    public void setRacyContent(Boolean racyContent) {
        isRacyContent = racyContent;
    }

    public Double getAdultScore() {
        return adultScore;
    }

    public void setAdultScore(Double adultScore) {
        this.adultScore = adultScore;
    }

    public Double getRacyScore() {
        return racyScore;
    }

    public void setRacyScore(Double racyScore) {
        this.racyScore = racyScore;
    }

    @Override
    public String toString() {
        return "Adult{" +
                "isAdultContent=" + isAdultContent +
                ", isRacyContent=" + isRacyContent +
                ", adultScore=" + adultScore +
                ", racyScore=" + racyScore +
                '}';
    }
}
