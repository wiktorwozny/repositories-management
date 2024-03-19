package pl.edu.agh.repomanagement.serviceTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.records.LastCommit;
import pl.edu.agh.repomanagement.backend.services.LastCommitService;
import pl.edu.agh.repomanagement.backend.services.LastCommitServiceImpl;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LastCommitServiceTest {

    private LastCommitService lastCommitService;

    @Test
    void testGetLastCommit() {
        lastCommitService = new LastCommitServiceImpl();
        Repository repo = new Repository("Test repo", "https://github.com/wiktorwozny/repositories-management");

        LastCommit lastCommit = lastCommitService.getLastCommit(repo);

        assertFalse(lastCommit.commitName().isEmpty());
        assertTrue(lastCommit.date().before(new Date()));
        assertTrue(lastCommit.url().startsWith("https://github.com/wiktorwozny/repositories-management/commit"));
    }
}
