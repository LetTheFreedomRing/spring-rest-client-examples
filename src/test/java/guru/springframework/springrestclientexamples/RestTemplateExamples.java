package guru.springframework;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestTemplateExamples {
    public static final String API_ROOT = "https://api.predic8.de/shop/";

    @Test
    public void getCategories() throws Exception {
        String apiUrl = API_ROOT + "/categories/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode);
    }

    @Test
    public void getCustomers() throws Exception {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);
        System.out.println("Response");
        System.out.println(jsonNode);
    }

    @Test
    public void createCustomer() throws Exception {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        // create map for post
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Greg");
        postMap.put("lastname", "House");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode);
    }

    @Test
    public void updateCustomer() throws Exception {
        // first, create customer, that we will update later
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        // create map for post
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Greg");
        postMap.put("lastname", "House");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode);

        // update customer
        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Customer id : " + id);

        postMap.put("firstName", "New Greg");
        postMap.put("lastName", "New House");

        restTemplate.put(apiUrl + id, postMap);

        JsonNode updateNode = restTemplate.getForObject(apiUrl + id, JsonNode.class);

        System.out.println("Updated customer");
        System.out.println(updateNode);
    }

    @Test(expected = HttpClientErrorException.class)
    public void deleteCustomer() throws Exception {
        // create customer, that we will delete later
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        // create map for post
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Greg");
        postMap.put("lastname", "House");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        System.out.println("Response");
        System.out.println(jsonNode);

        // delete customer
        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        System.out.println("Customer id : " + id);

        restTemplate.delete(apiUrl + id);

        System.out.println("Customer deleted");

        // throws 404
        restTemplate.getForObject(apiUrl + id, JsonNode.class);
    }
}
