package pl.edu.agh.repomanagement.backend.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.edu.agh.repomanagement.backend.ObjectIdJsonSerializer;

@Document(collection = "comments")
public class Comment {
    @Id
    @JsonSerialize(using = ObjectIdJsonSerializer.class)
    private ObjectId id;
    private String prUrl;
    private String text;

    public Comment() {}

    public Comment(String prUrl, String text) {
        this.prUrl = prUrl;
        this.text = text;
    }

    public ObjectId getId() {
        return id;
    }

    public String getPrUrl() {
        return prUrl;
    }

    public String getText() {
        return text;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setPrUrl(String prUrl) {
        this.prUrl = prUrl;
    }

    public void setText(String text) {
        this.text = text;
    }
}
