package hello;

public class File {
    private String content;
    private String name;
    private Long id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public File(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "File[name='" + getName() + "'; id='"
                + getId() + "'; content=" + getContent()
                + "']";
    }



}
