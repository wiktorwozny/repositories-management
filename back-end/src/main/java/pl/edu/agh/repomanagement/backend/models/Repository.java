package pl.edu.agh.repomanagement.backend.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.agh.repomanagement.backend.ObjectIdJsonSerializer;
import pl.edu.agh.repomanagement.backend.records.LastCommit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "repositories")
public class Repository {
    @Id
    @JsonSerialize(using = ObjectIdJsonSerializer.class)
    private ObjectId id;
    private String name;

    private String url;

    private LastCommit lastCommit;

    @DBRef
    private List<Comment> comments = new ArrayList<>();;

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

    public LastCommit getLastCommit() {
        return lastCommit;
    }

    public List<Comment> getComments(){return comments;}

    public void setLastCommit(LastCommit lastCommit) {
        this.lastCommit = lastCommit;
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

    public void addComment(Comment comment){this.comments.add(comment);}

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