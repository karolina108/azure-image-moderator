package pro.koliber.azure.moderator.image.model;

import java.io.Serializable;
import java.util.List;

public class ComputerVisionResult implements Serializable {

    private String requestId;
    private Metadata metadata;
    private Adult adult;
    private List<Tag> tags;

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

    @Override
    public String toString() {
        return "ComputerVisionResult{" +
                "requestId='" + requestId + '\'' +
                ", metadata=" + metadata +
                ", adult=" + adult +
                ", tags=" + tags +
                '}';
    }
}
