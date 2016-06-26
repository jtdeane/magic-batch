package ws.cogito.magic;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class MagicBatchTest {
	
	private static final Logger logger = LoggerFactory.getLogger
			(MagicBatchTest.class);
	
	private RestTemplate restTemplate = new TestRestTemplate();
	
	@Autowired
	Environment environment;
	
	@Autowired 
	private ApplicationContext applicationContext;
	
	
    @Configuration
    static class ContextConfiguration {
    
    	//configure any beans...
    }
    
    @Test
    public void testBatchProcessing() throws Exception {
    	
    	//Part I. Create the Order
    	URI expectedLocation = new URI ("http://localhost:8080/batch/A1234");
    	
    	//retrieve the test file
    	String json = getJSONFromFile("batch.json");
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<String> entity = new HttpEntity<String>(json,headers);

    	URI actualLocation = restTemplate.postForLocation
    			("http://localhost:8080/batch", entity);
    	
    	assertTrue(actualLocation.equals(expectedLocation));
    }
    
    @After
    public void tearDown () {
    	
    	if ((environment.getActiveProfiles()[0]).equals("qa")) {
    		
    		@SuppressWarnings("rawtypes")
			RedisTemplate redisTemplate = (RedisTemplate) applicationContext.getBean("redisTemplate");
    		
    		redisTemplate.getConnectionFactory().getConnection().flushAll(); 		
    	}
    }
    
    /**
     * Helper method for retrieving json from file
     * @param fileName
     * @return String
     */
    private static String getJSONFromFile(String fileName) {
        String json = null;
        StringBuffer text = new StringBuffer();
        String line = null;
        
        try (BufferedReader in = new BufferedReader
        		(new InputStreamReader(MagicBatchTest.class
                .getResourceAsStream(fileName)))) {
        	
            while ((line = in.readLine()) != null) {
                text.append(line);
            }

            json = text.toString();
			
		} catch (Exception e) {
			
			logger.debug("Failed to read test file " + fileName + " " + e.toString());
		}

        return json;
    }    

}