package ws.cogito.magic.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="prices")
public final class Prices {
	
	private String dice;
	private String cards;
	private String rings;
	private String quarters;
	private String marbles;
	private String rabbit;
	private String hat;
	private String scarf;
	
	public Integer getPrice(String key) {
		
		switch (key) {
		
		case "Dice":
			
			return Integer.parseInt(getDice());
			
		case "Cards":
			
			return Integer.parseInt(getCards());
			
		case "Rings":
			
			return Integer.parseInt(getRings());
			
		case "Quarters":
			
			return Integer.parseInt(getQuarters());
			
		case "Marbles":
			
			return Integer.parseInt(getMarbles());
			
		case "Hat":
			
			return Integer.parseInt(getHat());
			
		case "Scarf":
			
			return Integer.parseInt(getScarf());

		default:
			
			return 0;
		}
	}
	
	public String getDice() {
		return dice;
	}

	public void setDice(String dice) {
		this.dice = dice;
	}

	public String getCards() {
		return cards;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

	public String getRings() {
		return rings;
	}

	public void setRings(String rings) {
		this.rings = rings;
	}

	public String getQuarters() {
		return quarters;
	}

	public void setQuarters(String quarters) {
		this.quarters = quarters;
	}

	public String getMarbles() {
		return marbles;
	}

	public void setMarbles(String marbles) {
		this.marbles = marbles;
	}

	public String getRabbit() {
		return rabbit;
	}

	public void setRabbit(String rabbit) {
		this.rabbit = rabbit;
	}

	public String getHat() {
		return hat;
	}

	public void setHat(String hat) {
		this.hat = hat;
	}

	public String getScarf() {
		return scarf;
	}

	public void setScarf(String scarf) {
		this.scarf = scarf;
	}	
}