package pl.edu.agh.repomanagement.backend.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "repositories")
public class Repository {
    @Id
    private ObjectId id;
    private String name;

    private String url;

    public Repository() {}
    public Repository(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repository repository = (Repository) o;
        return Objects.equals(id, repository.id) &&
                Objects.equals(name, repository.name) &&
                Objects.equals(url, repository.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, url);
    }
}