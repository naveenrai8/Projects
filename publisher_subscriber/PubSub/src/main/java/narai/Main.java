package narai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            logger.info("i = {}", i);
        }
        logger.error("Errpr");
        logger.error("Errpr", new RuntimeException("Errpr"));
    }
}
