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
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileWriter;

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

  //convert string of timestamp to unix time in seconds
  //MUST BE FIXED
  public static long tsToUnix(String timestamp){
    DateFormat dateFormat = new SimpleDateFormat("MM/dd-HH:mm:ss.SSSSS", Locale.ENGLISH); //Specify your locale

    long unixTime = 0;
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30")); //Specify your timezone
    try {
      unixTime = dateFormat.parse(timestamp).getTime();
      unixTime = unixTime / 1000;
    } catch (ParseException e) {
      e.printStackTrace();
    }
        return unixTime;
  }



  public static void main(String[] args) {
    if(args.length != 1) {
      System.out.println("Usage: java processData filename.csv");
      System.exit(1);
    }
    //arg [1]: file name of csv file
    String filename = args[0];

    //write fields to new file
    FileWriter csvWriter = new FileWriter("processedData.csv");
    csvWriter.append("Source IP");
    csvWriter.append(",");
    csvWriter.append("Source Port");
    csvWriter.append(",");
    csvWriter.append("Destination IP");
    csvWriter.append(",");
    csvWriter.append("Destination Port");
    csvWriter.append(",");
    csvWriter.append("Timestamp");
    csvWriter.append(",");
    csvWriter.append("RuleID");
    csvWriter.append(",");
    csvWriter.append("Alert Type");
    csvWriter.append("\n");

    File f = new File(filename);

    //GETTING ERROR ON ROW NEED TO FIX
    if(f.isFile()){
      BufferedReader csvReader = new BufferedReader(new FileReader(f));
      while((row = csvReader.readLine()) != null){
        String [] data = row.split(",");
        //process the data
        //data[] contents: sid, rev, message, classification, priority, timestamp,
        //srcIP, srcPort, destIP, destPort
        //convert IPs and timestamp
        String srcIP = String.valueOf(ipToLong(data[6]));
        String destIP = String.valueOf(ipToLong(data[8]));
        String ts = String.valueOf(tsToUnix(data[5]));

        //write data fields to output file
        csvWriter.append(srcIP);
        csvWriter.append(",");
        csvWriter.append(data[7]);
        csvWriter.append(",");
        csvWriter.append(destIP);
        csvWriter.append(",");
        csvWriter.append(data[9]);
        csvWriter.append(",");
        csvWriter.append(ts);
        csvWriter.append(",");
        csvWriter.append(data[2]);
        csvWriter.append(",");
        csvWriter.append(data[3]);
        csvWriter.append("\n");
      }
      csvReader.close();
      csvWriter.flush();
      csvWriter.close();
      System.out.println("Data written to \"processedData.csv\".");
    }
    else{
      System.out.println("Error: File \"" + filename +"\" does not exist.");
      System.exit(1);
    }
  }
}
