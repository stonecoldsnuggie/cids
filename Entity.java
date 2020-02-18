import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Entity
{
  private ArrayList<Double> qualities;
  private int d;
  private int cluster_number = 0;

  public Entity(ArrayList<Double> qualities)
  {
    this.d = qualities.size();
    this.qualities = qualities;
  }

  public double get(int i)
  {
    return qualities.get(i);
  }

  public ArrayList<Double> getQualities()
  {
    return qualities;
  }
  
  public int getD()
  {
    return d;
  }

  public void setCluster(int n) {
    this.cluster_number = n;
  }
  
  public int getCluster() {
    return this.cluster_number;
  }
  
  public static Entity createRandomEntity(int d)
  {
    Random r = new Random();
    ArrayList<Double> toRet = new ArrayList<Double>();
    for(int i  = 0 ; i  < d ; i ++)
      {
	toRet.add(r.nextDouble());
      }
    return new Entity(toRet);
  }
  protected static ArrayList<Entity> createRandomEntities(int d, int number)
  {
    ArrayList<Entity> ret = new ArrayList(number);
    for(int i = 0; i < number; i++) {
      ret.add(createRandomEntity(d));
    }
    return ret;
  }

  protected static double distanceEuclidean(Entity a, Entity b)
  {
    double sum = 0;
    for(int i = 0; i  < a.getD(); i++ )
      sum += Math.pow(a.get(i) - b.get(i),2);
    return Math.sqrt(sum);
  }

  public String toString()
  {
    String ret = "";
    for(int i = 0 ; i < d-1; i++)
      ret += qualities.get(i) + ",";
    ret += qualities.get(d-1);
    return "(" + ret + ")";
  }

  public static void main(String[] args)
  {
    System.out.println(Entity.createRandomEntity(5));
  }
}
