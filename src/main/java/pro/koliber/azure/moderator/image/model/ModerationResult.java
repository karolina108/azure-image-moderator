package pro.koliber.azure.moderator.image.model;

import java.io.Serializable;
import java.util.List;

public class ModerationResult implements Serializable {

    private ModerationStatus status = ModerationStatus.NO_INFO;
    private Adult adultInfo;
    private List<Tag> tags;

    public ModerationStatus getStatus() {
        return status;
    }

    public void setStatus(ModerationStatus status) {
        this.status = status;
    }

    public Adult getAdultInfo() {
        return adultInfo;
    }

    public void setAdultInfo(Adult adultInfo) {
        this.adultInfo = adultInfo;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
