package com.cutter.project.service;

import com.cutter.project.dao.LinkRepository;
import com.cutter.project.model.Link;
import com.cutter.project.service.linkOperation.cuttingLink.GenerateRandomLink;
import com.cutter.project.service.linkOperation.matchingLink.LinkCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final LinkCheck linkCheck;
    private final GenerateRandomLink generateNewLink;

    @Value("${generate.linkLen}")
    private int linkLength;

    @Autowired
    public LinkService(LinkRepository linkRepository, LinkCheck linkCheck,
                       GenerateRandomLink generateNewLink) {

        this.linkRepository = linkRepository;
        this.linkCheck = linkCheck;
        this.generateNewLink = generateNewLink;
    }

    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    public Link findByCuttedLink(String cuttedLink) {
        return linkRepository.findByCuttedLink(cuttedLink);
    }

    public String cutLinkAndReturnCutted(String link) {

        Link linkFromDb = linkRepository.findByOriginalLink(link);
        if (linkFromDb != null) {
            return linkFromDb.getCuttedLink();
        }

        try {

            String newLink;

            do {
                newLink = generateNewLink.generate(linkLength);
            } while (linkRepository.findByCuttedLink(newLink) != null);

            linkRepository.save(new Link(link, newLink));

            return newLink;

        } catch (RuntimeException exp) {
            return "";
        }
    }

    private boolean isLink(String link) {
        return linkCheck.isLink(link);
    }

}
