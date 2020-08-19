package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {

  /**
   * Convert contents from CSV file at the given path to a boolean array
   * 
   * @param fileIn the path to the CSV file to be read
   */
  public static boolean[][] read(String fileIn) {

    try {

      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
      String line = "";
      ArrayList<ArrayList<Boolean>> seedList = new ArrayList<ArrayList<Boolean>>();

      line = bufferedReader.readLine();

      while (line != null) {

        String[] cellRow = line.split(",");
        ArrayList<Boolean> row = new ArrayList<Boolean>();

        for (int i = 0; i < cellRow.length; i++) {
          row.add(toBoolean(cellRow[i]));
        }

        seedList.add(row);

        line = bufferedReader.readLine();

      }

      bufferedReader.close();

      boolean[][] seed = new boolean[seedList.get(0).size()][seedList.size()];

      // Convert ArrayList to array
      for (int i = 0; i < seed[0].length; i++)
        for (int j = 0; j < seed.length; j++)
          seed[j][i] = seedList.get(i).get(j);

      return seed;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;

  }

  /**
   * Converts the inputed String to boolean
   * 
   * @param str String to be converted
   * @return true if the String is 1, false otherwise
   */
  private static boolean toBoolean(String str) {

    if (str.equals("1")) {
      return true;
    } else {
      return false;
    }

  }

  public static void write(Cell[][] generation, String fileOut) {

    int bufferSize = 8 * 1024;

    try {

      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileOut, true), bufferSize);

      for (int i = 0; i < generation[0].length; i++) {
        for (int j = 0; j < generation.length; j++) {
          bufferedWriter.write(generation[j][i].getLiving() ? "1" : "0");
        }
        bufferedWriter.write("\n");
      }
      bufferedWriter.write("\n");

      bufferedWriter.close(); // Flushes/closes the stream

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void wipe(String fileOut) {
    
    try {
      
      FileWriter fileWriter = new FileWriter(fileOut);
      fileWriter.write("");
      fileWriter.close();
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
  
}
