package ws.cogito.magic.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Profile("qa")
@RestController
@RequestMapping("/")
public class OrderInquiryController {
	
	@Autowired 
	private RedisTemplate<String, String> redisTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger
			(OrderInquiryController.class);
	
	@RequestMapping(value = "order/{orderId}", method=RequestMethod.GET)
	public @ResponseBody String getOrder (@PathVariable String orderId, 
			HttpServletResponse response) throws Exception {
		
		String body = null;
		
		logger.debug("Processing Order Inquiry ");
		
		Object value = redisTemplate.opsForHash().get("order:"+orderId, "total");
		
		if (value != null) {
			
			body = "Order " +  orderId + " Total: " + (String) value;
			
		} else {
			
			body = "Order " +  orderId + " not found.";
		}
		
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
		
		return body;
	}
}