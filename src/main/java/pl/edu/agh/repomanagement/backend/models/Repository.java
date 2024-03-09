package pl.edu.agh.repomanagement.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "repositories")
public class Repository {
    @Id
    private int id;
    private String name;

    private String url;

    public Repository() {}
    public Repository(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Object getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}