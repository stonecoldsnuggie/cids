import java.util.ArrayList;

public class EntityCluster
{
  public ArrayList<Entity> Entities;
  public Entity centroid;
  public int id;

  public EntityCluster(int id)
  {
    this.id = id;
    this.Entities = new ArrayList<Entity>();
    this.centroid = null;
  }

  public ArrayList<Entity> getEntities() {
    return Entities;
  }

  public void addEntity(Entity e) {
    Entities.add(e);
  }

  public void setEntities(ArrayList e) {
    this.Entities = e;
  }

  public Entity getCentroid() {
    return centroid;
  }
  
  public void setCentroid(Entity centroid) {
    this.centroid = centroid;
  }
  

  public int getId() {
    return id;
  }
  
  public void clear() {
    Entities.clear();
  }
  
  public void plotCluster() {
    System.out.println("[Cluster: " + id+"]");
    System.out.println("[Centroid: " + centroid + "]");
    System.out.println("[Entities: \n");
    for(Entity e : Entities) {
      System.out.println(e);
    }
    System.out.println("]");
  }
}
