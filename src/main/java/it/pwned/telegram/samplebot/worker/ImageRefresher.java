package it.pwned.telegram.samplebot.worker;

import it.pwned.telegram.samplebot.image.ImageSource;
import it.pwned.telegram.samplebot.image.type.SectionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class ImageRefresher {

    private static final Logger log = LoggerFactory.getLogger(ImageRefresher.class);

    private final ImageSource imageSource;

    public ImageRefresher(ImageSource imageSource) {
        this.imageSource = imageSource;
    }

    @Scheduled(initialDelay = 1000 * 60, fixedDelay = 1000 * 60 * 5)
    public void refresh() {
        java.util.List<SectionInfo> sections = imageSource.getSectionsInfo();

        for(SectionInfo section : sections) {
            if(section.newImages < 50) {
                log.trace(String.format("Refreshing section: %s", section));
                imageSource.refreshSectionImages(section.sectionName);
            }
        }

    }

}
