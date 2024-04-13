package pl.edu.agh.repomanagement.backend.services;

import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.Repository;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

@Service
public class CloneServiceImpl implements CloneService {
    @Override
    public String generateCloneCommand(Repository repo) {
        return "git clone " + repo.getUrl();
    }

    @Override
    public void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
