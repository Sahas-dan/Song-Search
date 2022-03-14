// --== File Header ==--
// Name: Sahas Dandapantula
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// interface (implemented with proposal)

interface SongLoaderInterface {
  public List<SongDataInterface> loadFile(String csvFilePath) throws FileNotFoundException;

  public List<SongDataInterface> loadAllFilesInDirectory(String directoryPath)
      throws FileNotFoundException;
}

// public class (implemented primarilly in final app week)


public class SongLoader implements SongLoaderInterface {

  @Override
  public List<SongDataInterface> loadFile(String csvFilePath) throws FileNotFoundException {


    if (csvFilePath.isEmpty() || csvFilePath == null) {
      throw new FileNotFoundException();
    }

    File filetoCheck = new File(csvFilePath);

    if (filetoCheck.exists() == false) {
      throw new FileNotFoundException();
    }
    List<String> list = new ArrayList<>();

    FileReader file = new FileReader(csvFilePath);



    // Scanner scnr = new Scanner(f);
    BufferedReader reader = new BufferedReader(file);



    try {
      String temp = "";
      while ((temp = reader.readLine()) != null) {
        list.add(temp); // gets entire lines into the list index
      }
      reader.close();

    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    for (int i = 0; i < list.size(); i++) {
      System.out.println(list.get(i));
    }

    int size = 0;
    String[][] twoARR = new String[list.size()][size];
    if (csvFilePath.contains("500")) {
      size = 9;
      twoARR = new String[list.size()][size];
    } else {
      size = 15;
      twoARR = new String[list.size()][size];
    }


    for (int i = 0; i < list.size(); i++) {
      for (int increment = 0; increment < size; increment++) {

        String temp = new String(""); // creates a new string
        int j = 0; // sets a counter for quotation marks
        for (int k = 0; k < list.get(i).length(); k++) {
          if (list.get(i).charAt(k) == ',') { // if there is a comma
            if (j % 2 == 0) { // checks to make sure its even

              // for the 500 file the date has a string how to make into an int? ->march,5

              twoARR[i][increment] = temp;

              temp = ""; // resets the string
              j = 0; // done late so excuse if error
              increment++;
              continue;
            }
          }
          temp += list.get(i).charAt(k); // adds strings into this temporary string
          if (list.get(i).charAt(k) == '"') {
            j++; // gets the count of the quotation marks
          }

          if (k == list.get(i).length() - 1) {
            twoARR[i][increment] = temp;
            increment++;
          }
        }

      }
    }



    List<SongDataInterface> loadFile = new ArrayList<>();


    int countTitle = 0;
    int countArtist = 0;
    int countYear = 0;
    if (csvFilePath.contains("500")) {
      countTitle = 0;
      countArtist = 3;
      countYear = 6;
    }

    else {
      countTitle = 1;
      countArtist = 2;
      countYear = 4;
    }
    String temp = "";
    for (int i = 1; i < list.size(); i++) {

      twoARR[i][countTitle] = twoARR[i][countTitle].replaceAll("\"", "");
      twoARR[i][countArtist] = twoARR[i][countArtist].replaceAll("\"", "");
      temp = twoARR[i][countYear].replaceAll("\\D", "");

      int year = 0;
      if (!temp.equals("")) {
        year = Integer.parseInt(temp);
      }
      SongData data = new SongData(twoARR[i][countTitle], twoARR[i][countArtist], year);
      loadFile.add(data);

    }

    for (int k = 0; k < loadFile.size(); k++) {
      // System.out.println(loadFile.get(k).getArtist());
      // System.out.println(loadFile.get(k).getTitle());
      // System.out.println(loadFile.get(k).getYearPublished());
    }

    return loadFile;

  }

  @Override
  public List<SongDataInterface> loadAllFilesInDirectory(String directoryPath)
      throws FileNotFoundException {
    if (directoryPath.equals("") || directoryPath == null) {
      throw new FileNotFoundException();
    }

    File file = new File(directoryPath);
    File[] allFilesInDirectory = file.listFiles();

    List<SongDataInterface> listToReturn = new ArrayList<>();
    for (int i = 0; i < allFilesInDirectory.length; i++) {
      System.out.println(allFilesInDirectory[i].getAbsolutePath());
      String directory = allFilesInDirectory[i].getAbsolutePath();
      File filetoCheck = new File(directory);
      if (filetoCheck.exists() == false) {
        throw new FileNotFoundException();
      }
      List<SongDataInterface> loadFile = loadFile(directory);
      for (int k = 0; k < loadFile.size(); k++) {
        listToReturn.add(loadFile.get(k));
      }
    }

    // read both strings w/ the new extension into loadFile method
    // then create new list with the size of both of the other loadFile return size
    // then read in the first list with a for loop into this methods list
    // read in second list with a for loop into this methods list -> maybe ignore first node for
    // this one because it is a header node


    // TODO Auto-generated method stub
    return listToReturn;
  }

  public static void main(String[] args) throws FileNotFoundException {
    SongLoader song = new SongLoader();
    song.loadFile("/Users/sahasdandapantula/eclipse-workspace/Song Search/src/Top 500 Songs.csv");
    // song.loadAllFilesInDirectory("/Users/sahasdandapantula/eclipse-workspace/Song
    // Search/src/top10s.csv");
  }


}


class SongLoaderPlaceholder implements SongLoaderInterface {
  public List<SongDataInterface> loadFile(String csvFilePath) throws FileNotFoundException {
    List<SongDataInterface> list = new LinkedList<>();
    list.add(new SongDataPlaceholderA());
    list.add(new SongDataPlaceholderB());
    return list;
  }

  public List<SongDataInterface> loadAllFilesInDirectory(String directoryPath)
      throws FileNotFoundException {
    List<SongDataInterface> list = new LinkedList<>();
    list.add(new SongDataPlaceholderA());
    list.add(new SongDataPlaceholderB());
    list.add(new SongDataPlaceholderC());
    return list;
  }
}
