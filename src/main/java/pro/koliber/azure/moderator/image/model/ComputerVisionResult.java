package pro.koliber.azure.moderator.image.model;

import java.io.Serializable;
import java.util.List;

public class ComputerVisionResult implements Serializable {

    private Adult adult;

    private List<Tag> tags;

    private String requestId;

    private Metadata metadata;

    public Adult getAdult() {
        return adult;
    }

    public void setAdult(Adult adult) {
        this.adult = adult;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "ComputerVisionResult{" +
                "adult=" + adult +
                ", tags=" + tags +
                ", requestId='" + requestId + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
