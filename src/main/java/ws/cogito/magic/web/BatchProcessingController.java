package ws.cogito.magic.web;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
	
	private static final Logger logger = LoggerFactory.getLogger
			(BatchProcessingController.class);
	
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
		
		logger.debug("Received Batch " + orders.getBatchId());
		
		//in a production this URL would be generated based on the environment..
		response.setHeader(HttpHeaders.LOCATION, 
				"http://localhost:8080/batch/" + orders.getBatchId());
		
		batchProcessing.process(orders);
	}
	
	/**
	 * Simple Health Check
	 * @throws Exception
	 */
	@RequestMapping(value = "health", method=RequestMethod.GET)
	public String getHealth (HttpServletResponse response) throws Exception {
		
		logger.debug("Processing Health Check ");
		
	    response.setContentType("text/plain");
	    response.setCharacterEncoding("UTF-8");
		
		return "All Systems Go";
	}	
}