package pro.koliber.azure.moderator.image;

import com.google.gson.Gson;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import pro.koliber.azure.moderator.image.model.ModerationResult;
import pro.koliber.azure.moderator.image.service.ModerationService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    @FunctionName("HttpTrigger-Java")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        // Request to Computer Vision API

        HttpClient httpClient = HttpClients.createDefault();
        URIBuilder builder = null;

        String key = System.getenv().get("COMPUTER_VISION_KEY");
        Optional<String> requestBody = request.getBody();

        if(requestBody.isPresent()) {

            try {
                builder = new URIBuilder("https://westeurope.api.cognitive.microsoft.com/vision/v2.0/analyze");
                builder.setParameter("visualFeatures", "Tags,Adult");
                URI uri = builder.build();

                HttpPost postRequest = new HttpPost(uri);
                postRequest.setHeader("Content-Type", "application/json");
                postRequest.setHeader("Ocp-Apim-Subscription-Key", key);

                // Request
                StringEntity stringEntity = new StringEntity(requestBody.get());
                postRequest.setEntity(stringEntity);

                // Response
                HttpResponse response = httpClient.execute(postRequest);
                HttpEntity entity = response.getEntity();

                if(null != entity){

                    ModerationService service = new ModerationService();

                    ModerationResult result = service.moderateImage(EntityUtils.toString(entity));

                    Gson gson = new Gson();
                    String json = gson.toJson(result);

                    return request.createResponseBuilder(HttpStatus.OK).header("Content-Type", "application/json").body(json).build();

                } else {
                    return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Something went wrong").build();
                }


            } catch (URISyntaxException | IOException e) {
                context.getLogger().info(e.getMessage());
            }

        }
        return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not process image").build();
    }
}
