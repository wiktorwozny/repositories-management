package pl.edu.agh.repomanagement.backend.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import pl.edu.agh.repomanagement.backend.ObjectIdJsonSerializer;

import java.util.List;
import java.util.Objects;

@Document(collection = "workspaces")
public class Workspace {
    @Id
    @JsonSerialize(using = ObjectIdJsonSerializer.class)
    private ObjectId id;
    private String name;
    @DBRef
    private List<Repository> repositories;


    public Workspace() {
    }

    public Workspace(String name) {
        this.name = name;
    }

    public Workspace(String name, List<Repository> repositories) {
        this.name = name;
        this.repositories = repositories;
    }

    public String getName() {
        return name;
    }

    public ObjectId getId() {
        return id;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workspace workspace = (Workspace) o;
        return Objects.equals(id, workspace.id) &&
                Objects.equals(name, workspace.name) &&
                Objects.equals(repositories, workspace.repositories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, repositories);
    }
}