import java.util.ArrayList;
public class DKMeans
{
  private int NUM_CLUSTERS = 3;    
  //Number of Points
  private int NUM_ENTITIES = 15;
  private int DIMENSIONS = 4;

  private ArrayList<Entity> entities;
  private ArrayList<EntityCluster> clusters;
  
  public DKMeans() {
    this.entities = new ArrayList();
    this.clusters = new ArrayList();    	
  }

  public void init() {
    //Create Points
    entities = Entity.createRandomEntities(DIMENSIONS, NUM_ENTITIES);
    
    //Create Clusters
    //Set Random Centroids
    for (int i = 0; i < NUM_CLUSTERS; i++) {
      EntityCluster cluster = new EntityCluster(i);
      Entity centroid = Entity.createRandomEntity(DIMENSIONS);
      cluster.setCentroid(centroid);
      clusters.add(cluster);
    }

    for(Entity e : entities)
      {
	System.out.println(e);
      }
    
    
    //Print Initial state
    plotClusters();
  }


  public static void main(String[] args)
  {
    DKMeans dkmeans = new DKMeans();
    dkmeans.init();
    dkmeans.calculate();
  }

  private void plotClusters() {
    for (int i = 0; i < NUM_CLUSTERS; i++) {
      EntityCluster c = clusters.get(i);
      c.plotCluster();
    }
  }

  private void clearClusters() {
    for(EntityCluster cluster : clusters) {
      cluster.clear();
    }
  }

  public void calculate() {
    boolean finish = false;
    int iteration = 0;
    
    // Add in new data, one at a time, recalculating centroids with each new one. 
    while(!finish) {
      //Clear cluster state
      clearClusters();
      
      ArrayList<Entity> lastCentroids = getCentroids();
      
      //Assign points to the closer cluster
      assignCluster();// <-----------------------------------MARK
      
      //Calculate new centroids.
      calculateCentroids();//<--------------------------------MARK
      
      iteration++;
      
      ArrayList<Entity> currentCentroids = getCentroids();//<---------------MARK
      
      //Calculates total distance between new and old Centroids
      double distance = 0;
      for(int i = 0; i < lastCentroids.size(); i++) {
	distance += Entity.distanceEuclidean(lastCentroids.get(i),currentCentroids.get(i));
      }
      System.out.println("#################");
      System.out.println("Iteration: " + iteration);
      System.out.println("Centroid distances: " + distance);
      plotClusters();
      
      if(distance == 0) {
	finish = true;
      }
    }
  }


   private ArrayList<Entity> getCentroids() {
    	ArrayList centroids = new ArrayList(NUM_CLUSTERS);
    	for(EntityCluster cluster : clusters) {
    		Entity aux = cluster.getCentroid();
    		Entity point = new Entity(aux.getQualities());
    		centroids.add(point);
    	}
    	return centroids;
    }

 private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
        
        for(Entity point : entities) {
        	min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
            	EntityCluster c = clusters.get(i);
                distance = Entity.distanceEuclidean(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            clusters.get(cluster).addEntity(point);
        }
    }

  private void calculateCentroids()
  {
    for(EntityCluster cluster : clusters)
      {
	double sumX = 0;
	double sumY = 0;
	ArrayList<Entity> list = cluster.getEntities();
	int n_points = list.size();
	double[] sum = new double[DIMENSIONS];
	
	
	for(Entity point : list)
	  {
	    for(int i = 0 ; i < DIMENSIONS; i++)
	      sum[i] += point.getQualities().get(i);
	  }
        
	Entity centroid = cluster.getCentroid();
	if(n_points > 0)
	  {
	    ArrayList<Double> quals = new ArrayList<Double>();
	    for(int i = 0; i < DIMENSIONS; i++)
	      {
		quals.add(sum[i]/n_points);
	      }
	    centroid.setQualities(quals);
	  }
      }
    
  }
}
