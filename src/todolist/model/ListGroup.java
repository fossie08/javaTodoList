package todolist.model;

import java.io.Serializable;
import java.util.UUID;

public class ListGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;

    public ListGroup(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}