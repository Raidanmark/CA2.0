package org.example.huobi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.example.huobi.model.ApiResponse;
import org.example.huobi.model.CryptoData;
import org.example.huobi.model.MarketData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HuobiApi {

    // Good practice to define constants
    private static final String APPLICATION_JSON = "application/json";
    private static final String BASE_URL = "https://api.huobi.pro";

    private final CloseableHttpClient client;
    private final ObjectMapper objectMapper;

    public HuobiApi(CloseableHttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public List<CryptoData> settingsCommonSymbols(Map<String, String> parameters) throws IOException, URISyntaxException {
        String endpoint =  "/v2/settings/common/symbols";
        //This one is needed to convert complex nested objects from JSON to Java POJOs
        TypeReference<ApiResponse<List<CryptoData>>> typeReference = new TypeReference<>() {};
        return makeAPICall(endpoint, parameters, typeReference);
    }

    public List<MarketData> marketTickers(Map<String, String> parameters) throws IOException, URISyntaxException {
        String endpoint =  "/market/tickers";
        TypeReference<ApiResponse<List<MarketData>>> typeReference = new TypeReference<>() {};
        return makeAPICall(endpoint, parameters, typeReference);
    }

    // Generic method to execute API calls and  convert JSON to POJOs
    // We want accept parameters from our class user as simple Map of strings, so user dont know if we use any special
    // datatype or library for passing parameters to HTTP call.
    // Type reference is needed for complex nested object hierarchies, and nested collections, otherwise we use default one
    // for simple stuff, it can figure it out automatically
    private <T> T makeAPICall(String endpoint, Map<String, String> parameters, TypeReference<ApiResponse<T>> typeReference) throws IOException, URISyntaxException {
        List<NameValuePair> callParameters = parameters.entrySet()
                .stream()
                .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        String responseContent;

        // Build the URI with parameters
        URIBuilder query = new URIBuilder(BASE_URL + endpoint);
        query.addParameters(callParameters);

        // Create the GET request
        HttpGet request = new HttpGet(query.build());
        request.setHeader(HttpHeaders.ACCEPT, APPLICATION_JSON);

        // Execute the request in try-with-resources to prevent leaks
        try (CloseableHttpResponse response = client.execute(request)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode < 200 || statusCode >= 300) {
                throw new IOException("API request failed with status code: " + statusCode);
            }
            HttpEntity entity = response.getEntity();
            responseContent = EntityUtils.toString(entity);
            EntityUtils.consume(entity); // Ensure the entity content is fully consumed
            //if type reference is provided then we use it otherwise we create a default one
            TypeReference<ApiResponse<T>> tr = typeReference != null ? typeReference : new TypeReference<>() {};
            // Deserialize and return response content as T
            ApiResponse<T> apiResponse = objectMapper.readValue(responseContent, tr);
            return apiResponse.data();
        }
    }
}
