package ws.cogito.magic.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ws.cogito.magic.model.Order;
import ws.cogito.magic.model.Orders;
import ws.cogito.magic.model.Prices;

@Component
@Profile("dev")
public class DefaultBatchProcessing implements BatchProcessing {	
	
	private static final Logger logger = LoggerFactory.getLogger
			(DefaultBatchProcessing.class);	
	
	@Autowired
	Prices prices;	
	
	@Override
	public void process (Orders orders)  throws Exception {

		logger.debug("Default Processor - Processing: \n" + orders);
		
		Consumer <Order> calculateTotal = order -> {
			
			Integer total = order.getAmount() * prices.getPrice(order.getItem());
			
			logger.debug("Order " + order.getId() + " Total: " + total.toString());
		};
		
		orders.getOrders().parallelStream().forEach(calculateTotal);
		
		logger.debug("Default Processor - Processed: \n" + orders);
	}

}