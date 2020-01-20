package hello;

public class File {
    private String content;
    private String name;
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public File(String name, String content) {
        this.name = name;
        this.content = content;
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
