import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static spark.Spark.*;
// assignmnet: https://courses.codepath.com/courses/interview_training/unit/7#!assignments
/**
 * Examples
 * https://github.com/perwendel/spark
 * https://www.boxuk.com/insight/blog-posts/creating-rest-api-quickly-using-pure-java
 */

public class Main {
    static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Starting main class...");
        urlUtil urlutil = new urlUtil();

        get("/hello", (request, response) -> "Hello World!");

        /**
         * Example: http://localhost:4567/YGbExa
         */
        get("/:linkId", (request, response) -> {
            response.type("application/json");

            String linkId = request.params(":linkId");
            log.info("Getting url [{}]", linkId);
            if(urlutil.containsLinkId(linkId)) {
                response.status((302));
                return urlutil.getShortenedUrl(linkId);
            } else {
                response.status(404);
                return "{\"ERROR\":\"LinkId not found\"}";
                // return null;
            }
        });

        /**
         * Example: http://localhost:4567/?url=12345&id=asdf
         *
         * Couldn't figure out how to get form-data to work
         *  so did queryParams as work-around
         */
        post("/", (request, response) -> {
            response.type("application/json");

            String url = request.queryParams("url");
            String id = request.queryParams("id");
            String newLinkId = RandomAlphaNum.generateString(6);

            log.info("Setting url [{}]", url);

            if(id != null && urlutil.containsLinkId(id) ) {
                response.status(409);
                return "{\"ERROR\":\"Id already in use\"}";
            } else if (id != null ) {
                newLinkId = id;
            }
            log.info("Setting with linkId [{}]", id);

            response.status(201);
            urlutil.setShortenedUrl(newLinkId, url);
            return "{\"Location\":\""+newLinkId+"\"}";
        });
    }
}
