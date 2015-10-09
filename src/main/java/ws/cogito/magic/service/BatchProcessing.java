package ws.cogito.magic.service;

import ws.cogito.magic.model.Orders;

public interface BatchProcessing {

	void process(Orders orders) throws Exception;

}