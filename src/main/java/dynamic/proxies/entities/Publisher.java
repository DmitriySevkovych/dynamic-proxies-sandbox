package dynamic.proxies.entities;

import lombok.Data;

@Data
public class Publisher {

	private String name;

	public String employ(Author author) {
		return "Employ " + author.toString();
	}

}
