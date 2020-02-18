import java.util.ArrayList;

public class Party
{
  private ArrayList<Entity> data;

  public Party(ArrayList<Entity> data)
  {
    this.data = data;
  }

  public int dataSize()
  {
    return data.size();
  }
  
  public Entity request(int i)
  {
    return data.get(i);
  }
}
