package ws.cogito.magic;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class OrderInquiryTest {
	
	private static final Logger logger = LoggerFactory.getLogger
			(OrderInquiryTest.class);
	
	private RestTemplate restTemplate = new TestRestTemplate();
	
	@Autowired
	Environment environment;
	
	@Autowired 
	private RedisTemplate<String, String> redisTemplate;
	
    @Configuration
    static class ContextConfiguration {
    
    	//configure any beans...
    }
    
    @Before
    public void setup () throws Exception {
    		
    	if ((environment.getActiveProfiles()[0]).equals("qa")) {
    		
    		Map<String, String> orderDetails = new HashMap<String, String>();
    		orderDetails.put("id", "X999");
    		orderDetails.put("total", "240");
    		
			redisTemplate.opsForHash().putAll("order:X999", orderDetails);
    	}
    }
    
    @After
    public void tearDown () {
    	
    	if ((environment.getActiveProfiles()[0]).equals("qa")) {
    		
    		redisTemplate.getConnectionFactory().getConnection().flushAll(); 		
    	}
    }
    
    @Test
    public void testOrderInquiry() throws Exception {
    	
    	if ((environment.getActiveProfiles()[0]).equals("qa")) {
    		
    		//retrieve results from redis
        	ResponseEntity<String> response = restTemplate.getForEntity
        			("http://localhost:8080/order/X999", String.class);
        	
        	assertTrue(response.getBody().contains("240"));
        	
        	logger.debug("Response - " + response.getBody());
    	
    	} else {
    		
    		logger.debug("Profile N/A for this test");
    	}
    }     
}