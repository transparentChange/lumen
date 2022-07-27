package bookmarks.lumen.service

import bookmarks.lumen.data.BookmarkRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

@Slf4j
@Service
class LoggerService {
    @Autowired private BookmarkRepository bkRepository;

    //@Scheduled(cron = "0 1 1 ? * *") every day
   // @Scheduled(cron = "* * * * * *")
    public void scheduledSummary() {
        log.info("Total bookmarks: " + bkRepository.count());
    }

    // combine with scheduled summary
    //@Scheduled(cron = "* * * * * *")
    public void upload() {
        //URL url = getClass().getResource("./logs/lumen-2022-05-26-1.log");
        File file = new File("./logs/lumen-2022-05-26-1.log");
        BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
        System.out.println("creationTime: " + attr.creationTime());
    }
}
