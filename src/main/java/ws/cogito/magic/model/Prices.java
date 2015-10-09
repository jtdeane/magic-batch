package ws.cogito.magic.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(locations = "classpath:prices.properties")
public class Prices {

	private Map<String, String> prices = new HashMap<String, String>();
	
	public Integer getPrice(String key) {
		return Integer.parseInt(prices.get(key));
	}

	public Map<String, String> getPrices() {
		return prices;
	}

	public void setPrices(Map<String, String> prices) {
		this.prices = prices;
	}
}
