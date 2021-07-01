package com.cutter.project.service.linkOperation.cuttingLink;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Class, that generate random link. Can generate link which already contains in database.
 *
 */

@Component
public class UncheckedGenerateRandomLink implements GenerateRandomLink {

    /**
     * Generate random link with particular length,
     * but don't check that generated links contains in database.
     *
     * @param linkLength length of link
     * @return new generated link
     */

    private String generateLinkFromBuffer(int linkLength) {

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

    @Override
    public String generate(int linkLength) {
        return "https://cuting-master.herokuapp.com/" + generateLinkFromBuffer(linkLength);
    }
}
