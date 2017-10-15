package ws.cogito.magic.service;

import java.util.Map;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import ws.cogito.magic.model.Order;
import ws.cogito.magic.model.Orders;
import ws.cogito.magic.model.Prices;

@Profile({"qa","dk"})
@Component
public class RedisBatchProcessing implements BatchProcessing {

	private static final Logger logger = LoggerFactory.getLogger
			(RedisBatchProcessing.class);
	
	@Autowired 
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	Prices prices;
	
	@Override
	public void process(Orders orders)  throws Exception {
		
		logger.info("Processing: " + orders.getBatchId());	
				
		Consumer <Order> calculateTotal = order -> {
			
			Map <String, String> orderDetails = order.toMap();
			
			Integer total = order.getAmount() * prices.getPrice(order.getItem());
			
			orderDetails.put("total",total.toString());
			
			redisTemplate.opsForHash().putAll("order:"+ order.getId(), orderDetails);
		};		
		
		orders.getOrders().parallelStream().forEach(calculateTotal);		
		
		logger.info("Processed: " + orders.getBatchId());
	}
}