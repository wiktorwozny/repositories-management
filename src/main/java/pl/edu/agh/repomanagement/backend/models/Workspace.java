package pl.edu.agh.repomanagement.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Document(collection = "workspaces")
public class Workspace {
    @Id
    private int id;
    private String name;
    @DBRef
    private List<Repository> repositories;

    public Workspace() {}
    public Workspace(String name) {
        this.name = name;
    }
    public Workspace(String name, List<Repository> repositories) {
        this.name = name;
        this.repositories = repositories;
    }
}