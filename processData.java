// Author: 2/C Cooper Guzzi
// 18 FEB 20
// Program used to take in IDS parameters from alert.java
// and process data appropriately for our K-Means program.
// Input: csv file created by alert.java file
// Output: processed data parameters to be used in Kmeans program.
import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.time.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class processData {
  //code taken from https://mkyong.com/java/java-convert-ip-address-to-decimal-number/
  // to convert string ip to long
  public static long ipToLong(String ipAddress) {

    String[] ipAddressInArray = ipAddress.split("\\.");

    long result = 0;
    for (int i = 0; i < ipAddressInArray.length; i++) {

      int power = 3 - i;
      int ip = Integer.parseInt(ipAddressInArray[i]);
      result += ip * Math.pow(256, power);

    }

    return result;
  }

  public static void main(String[] args) {
    if(args.length != 2) {
      System.out.println("Usage: java processData input.csv output.csv");
      System.exit(1);
    }

    BufferedReader csvReader = null;
		FileReader fr = null;
		BufferedWriter csvWriter = null;
		FileWriter fw = null;

    String filename = args[0];

    try {
      fr = new FileReader(args[0]);
      csvReader = new BufferedReader(fr);
      fw = new FileWriter(args[1]);
      csvWriter = new BufferedWriter(fw);

      //write fields to new file
      csvWriter.newLine();
      csvWriter.write("SrcIP, SrcPort, DestIP, DestPort, Timestamp, RuleID, AlertType");

      File f = new File(filename);
      String row = "";

      if(f.isFile()){
        try {
          csvReader = new BufferedReader(new FileReader(f));
          while((row = csvReader.readLine()) != null){
            String [] data = row.split(",");
            //process the data
            //data[] contents: sid, rev, message, classification, priority, timestamp,
            //srcIP, srcPort, destIP, destPort
            //convert IPs
            String srcIP = String.valueOf(ipToLong(data[6]));
            String destIP = String.valueOf(ipToLong(data[8]));
            String ts = String.valueOf(data[5]);

            //write data fields to output file
            csvWriter.newLine();
            String toWrite = srcIP+","+data[7]+","+destIP+","+data[9]+","
                +ts+","+data[2]+","+data[3];
            csvWriter.write(toWrite);
          }
        } catch (FileNotFoundException e){
          e.printStackTrace();
        } catch (IOException e){
          e.printStackTrace();
        } finally {
          if (csvReader != null) {
            try {
              csvReader.close();
              csvWriter.flush();
              csvWriter.close();
            } catch (IOException e){
              e.printStackTrace();
            }
          }
        }
        System.out.println("Data written to \"processedData.csv\".");
      }
      else{
        System.out.println("Error: File \"" + filename +"\" does not exist.");
        System.exit(1);
      }
    } catch (IOException e){
      e.printStackTrace();
    }

  }
}
