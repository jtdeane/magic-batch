package ws.cogito.magic.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ws.cogito.magic.model.Orders;
import ws.cogito.magic.service.BatchProcessing;

/**
 * Handles submissions of magic orders
 * @author jeremydeane
 */
@RestController
@RequestMapping("/")
public class BatchProcessingController {
	
	private final Logger logger = LoggerFactory.getLogger (this.getClass());
	
	@Autowired
	private BatchProcessing batchProcessing;
	
	/**
	 * Process a batch of orders
	 * @param order
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "batch", method=RequestMethod.POST)
	public void postBatch (@RequestBody Orders orders, 
			HttpServletResponse response) throws Exception {
		
		logger.info("Received Batch " + orders.getBatchId());
		
		//in a production this URL would be generated based on the environment..
		response.setHeader(HttpHeaders.LOCATION, 
				"http://localhost:8080/batch/" + orders.getBatchId());
		
		response.setStatus(HttpStatus.ACCEPTED.value());
		
		batchProcessing.process(orders);
	}	
}