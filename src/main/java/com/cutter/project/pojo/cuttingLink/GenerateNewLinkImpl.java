package com.cutter.project.pojo.cuttingLink;

import com.cutter.project.dao.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateNewLinkImpl implements GenerateNewLink {

    @Autowired
    private LinkRepository linkRepository;

    private int maximalCountOfIteration = 5;

    private String generateLink() {

        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 10;

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    public String cut() {
        String link;

        int currentIteration = 0;

        do {
            link = generateLink();
            if(++currentIteration >= maximalCountOfIteration) {
                throw new RuntimeException("A lot of iteration");
            }
        } while (linkRepository.findByCuttedLink(link) != null);

        return "localhost/" + link;

    }

    public void setMaximalCountOfIteration(int maximalCountOfIteration) {
        this.maximalCountOfIteration = maximalCountOfIteration;
    }
}
