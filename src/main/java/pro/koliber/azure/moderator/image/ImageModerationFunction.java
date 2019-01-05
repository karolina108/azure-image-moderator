package pro.koliber.azure.moderator.image;

import com.google.gson.Gson;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import pro.koliber.azure.moderator.image.model.ComputerVisionResult;
import pro.koliber.azure.moderator.image.model.ModerationResult;
import pro.koliber.azure.moderator.image.service.ComputerVisionService;
import pro.koliber.azure.moderator.image.service.ModerationService;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class ImageModerationFunction {
    public ImageModerationFunction() {
    }

    /**
     * This function listens at endpoint "/api/HttpTrigger-Java".
     * Please provide image url in POST request body: {"url":"https://www.example.com/image.jpg"}
     * The Function calls Cognitive Service - Computer Vision API v. 2.0 and evaluates images for adult content & tags.
     */
    @FunctionName("image-moderator")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        Optional<String> requestBody = request.getBody();

        if(requestBody.isPresent()) {

            String key = System.getenv().get("COMPUTER_VISION_KEY");
            String region = System.getenv().get("COMPUTER_VISION_REGION");

            context.getLogger().info("Calling Computer Vision API in " + region);

            ComputerVisionService visionService = new ComputerVisionService(key, region);

            try {
                ComputerVisionResult visionResult = visionService.callComputerVisionService(requestBody.get());

                ModerationService moderationService = new ModerationService();
                ModerationResult moderationResult = moderationService.moderateImage(visionResult);

                Gson gson = new Gson();
                String json = gson.toJson(moderationResult);

                return request
                        .createResponseBuilder(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(json)
                        .build();


            } catch (Exception e) {
                context.getLogger().info(e.getMessage());
                return request
                        .createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Could not process image")
                        .build();
            }
        }
        return request
                .createResponseBuilder(HttpStatus.BAD_REQUEST)
                .body("Please provide image url in request body")
                .build();
    }
}
