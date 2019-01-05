package pro.koliber.azure.moderator.image.model;

public class Metadata {

    private Integer width;
    private Integer height;
    private String format;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "width=" + width +
                ", height=" + height +
                ", format='" + format + '\'' +
                '}';
    }
}
