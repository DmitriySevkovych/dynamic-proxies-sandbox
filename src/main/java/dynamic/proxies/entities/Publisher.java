package dynamic.proxies.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher
{

  private String name;

  public String employ(Author author)
  {
    return "Employ " + author.toString();
  }

}
