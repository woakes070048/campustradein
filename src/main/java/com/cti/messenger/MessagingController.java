package com.cti.messenger;

import com.cti.common.annotation.Controller;
import com.cti.common.annotation.Route;
import com.cti.controller.AbstractController;
import com.cti.controller.RequiresAuthenticationFilter;
import com.cti.controller.RequiresBodyFilter;
import com.cti.controller.RequiresJsonFilter;
import com.google.gson.JsonObject;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import javax.inject.Inject;

/**
 * @author ifeify
 */
@Controller
public class MessagingController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(MessagingController.class);
    @Inject
    private MessagingService messagingService;

    @Route
    public void handleSendMessage() {
        Spark.before(new RequiresAuthenticationFilter());
        Spark.before(new RequiresBodyFilter());
        Spark.before(new RequiresJsonFilter());

        Spark.post("/message", (request, response) -> {
            try {
                Message message = gson.fromJson(request.body().trim(), Message.class);
                messagingService.send(message);
                response.status(HttpStatus.SC_CREATED);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("result", "ok");
                return jsonObject;
            } catch (Exception e) {
                logger.error("Error occurred sending message to user", e);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("result", "error");
                return jsonObject;
            }
        }, gson::toJson);
    }
}
