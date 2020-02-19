// Author: 2/C Cooper Guzzi
// 18 FEB 20
// Program used to take in IDS parameters from alert.java
// and process data appropriately for our K-Means program.
// Input: csv file created by alert.java file
// Output: processed data parameters to be used in Kmeans program.
import java.util.ArrayList;
import java.util.List;

public class processData {
  //code taken from https://mkyong.com/java/java-convert-ip-address-to-decimal-number/
  // to convert string ip to long
  public long ipToLong(String ipAddress) {

    String[] ipAddressInArray = ipAddress.split("\\.");

    long result = 0;
    for (int i = 0; i < ipAddressInArray.length; i++) {

      int power = 3 - i;
      int ip = Integer.parseInt(ipAddressInArray[i]);
      result += ip * Math.pow(256, power);

    }

    return result;
  }

  //convert string of timestamp to unix time
  public Integer tsToUnix(String timestamp){

  }

  

  public static void main(String[] args) {
    if(args.length != 1) {
      System.out.println("Usage: java processData filename.csv");
      System.exit(1);
    }
    //arg [1]: file name of csv file
    String filename = args[0];
    System.out.println(filename);
  }
}
