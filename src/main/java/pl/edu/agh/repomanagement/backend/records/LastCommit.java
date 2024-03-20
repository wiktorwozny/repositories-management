package pl.edu.agh.repomanagement.backend.records;

import java.util.Date;

public record LastCommit(String userName, String commitName, Date date, String url) {
    @Override
    public String toString() {
        return "LastCommit{ " +
                "userName: " + userName +
                " commitName: " + commitName +
                " date: " + date +
                " url: " + url +
                '}';
    }
}
