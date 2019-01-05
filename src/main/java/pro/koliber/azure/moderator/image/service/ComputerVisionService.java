package pro.koliber.azure.moderator.image.service;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import pro.koliber.azure.moderator.image.model.ComputerVisionResult;

import java.net.URI;

public class ComputerVisionService {

    private final String key;
    private final String region;

    public ComputerVisionService(String key, String region) {
        this.key = key;
        this.region = region;
    }


    public ComputerVisionResult callComputerVisionService(String body) throws Exception {

        HttpClient httpClient = HttpClients.createDefault();
        URIBuilder builder = null;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://");
        stringBuilder.append(region);
        stringBuilder.append(".api.cognitive.microsoft.com/vision/v2.0/analyze");


        builder = new URIBuilder(stringBuilder.toString());
        builder.setParameter("visualFeatures", "Tags,Adult");
        URI uri = builder.build();

        HttpPost postRequest = new HttpPost(uri);
        postRequest.setHeader("Content-Type", "application/json");
        postRequest.setHeader("Ocp-Apim-Subscription-Key", key);

        //Request
        StringEntity stringEntity = new StringEntity(body);
        postRequest.setEntity(stringEntity);

        // Response
        HttpResponse response = httpClient.execute(postRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        HttpEntity entity = response.getEntity();

        if(statusCode == 200) {
            Gson gson = new Gson();
            return gson.fromJson(EntityUtils.toString(entity), ComputerVisionResult.class);
        } else {
            throw new Exception("Computer Vision API ERROR: " + EntityUtils.toString(entity));
        }
    }

    private ComputerVisionResult getComputerVisionResult(String json){

        Gson gson = new Gson();
        return gson.fromJson(json, ComputerVisionResult.class);
    }
}
