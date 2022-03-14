// --== File Header ==--
// Name: Rahil Kapur
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// interface (implemented with proposal)

interface SearchBackEndInterface {
  public void addSong(SongDataInterface song);

  public boolean containsSong(SongDataInterface song);

  // returns list of the titles of all songs that contain the word titleWord in their song title
  public List<String> findTitles(String titleWord);

  // returns list of the artists of all songs that contain the word titleWord in their song title
  public List<String> findArtists(String titleWord);

  // returns the number of songs that contain the word titleWord in their song title, and were
  // published in year
  public int findNumberOfSongsInYear(String titleWord, int year);
}

/*
 * This class essentially makes use of the hashmap implemented from last week and maps each title
 * word to a list of songs containing that word in its title. It also contains 3 private instance
 * fields along with methods invoking the data by adding songs, seeing if songs are in the hashmap,
 * checking every song with a certain title keyword, checking every artist with a certain title
 * keyword. Along with this is a constructor which creates the hashmap by invoking the
 * loadAllfilesInDirectory Method.
 */


public class SearchBackEnd implements SearchBackEndInterface {
  private HashtableMap<String, ArrayList<SongDataInterface>> map; // These instance fields contain
                                                                  // the hashmap itself, the list of
                                                                  // songs per key, and the list of
                                                                  // all title words in teh data.
  private ArrayList<SongDataInterface> songList;
  private ArrayList<String> titleWordList;

  /*
   * This constructor initializes the Hashmap by mapping all the title words to a list of each song
   * containing that word in its title.
   */
  public SearchBackEnd(String directoryPath) throws FileNotFoundException {
    SongLoader song = new SongLoader();
    titleWordList = new ArrayList<String>();

    List<SongDataInterface> list = song.loadAllFilesInDirectory(directoryPath);
    // This method invokes all of the songs and this list stores all of these songs here.
    map = new HashtableMap<String, ArrayList<SongDataInterface>>();
    for (int i = 0; i < list.size(); i++) { // This for loop loops through all the songs and creates
                                            // an array from each title word in each song and splits
                                            // the array at the space so every word is an element in
                                            // the array.
      String temp = list.get(i).getTitle();
      String[] arr = temp.split(" ");

      for (int k = 0; k < arr.length; k++) { // This loop adds the title keywords to the list
                                             // containing all the title words. If already there, it
                                             // doesn't add it, otherwise it ads it.
        if (titleWordList.contains(arr[k])) {
          continue;
        }
        titleWordList.add(arr[k]);
      }
    }
    for (int a = 0; a < titleWordList.size(); a++) { // This loop goes through all of the words and
                                                     // creates a new arraylist per word.
      songList = new ArrayList<SongDataInterface>();
      for (int j = 0; j < list.size(); j++) { // This loop goes through each song in the list again
                                              // to get an array containing every title word in each
                                              // song.
        String[] songWords = list.get(j).getTitle().split(" ");
        for (int z = 0; z < songWords.length; z++) { // here we use another loop to compare the word
                                                     // in the arraylist, to the one in the array,
                                                     // and if they equal each other e simply add
                                                     // the song itself to the arraylist. We repeat
                                                     // this process until we fill up the arraylist
                                                     // for songs for this specific keyword.
          if (titleWordList.get(a).equals(songWords[z])) {
            songList.add(list.get(j));
          }
        }
      }
      map.put(titleWordList.get(a), songList); // At the end, we put the key-value pair in the
                                               // hashmap itself, the key being the title word and
                                               // the value being the list containg every song with
                                               // that word in its title.
    }
  }

  /*
   * This method simply adds another song to the following arraylists corresponding to the words in
   * its title. If the title already has a key value pair, we simply add the song to the arraylist.
   * Otherwise, we create a new key value pair, and put that pair in the hashmap.
   * 
   * @param song the song that we are seeing if we need to add.
   */
  @Override
  public void addSong(SongDataInterface song) {
    if (song == null) { // If the song is null we don't do anything.
      return;
    }
    String[] arr = song.getTitle().split(" ");
    for (int i = 0; i < arr.length; i++) { // This for loop goes through the array with each title
                                           // word from the song in the parameter and adds the song
                                           // to the arraylist or creates a new key value pair whe.
      if (map.containsKey(arr[i])) {
        ArrayList<SongDataInterface> temp = map.get(arr[i]);
        temp.add(song);
      } else {
        ArrayList<SongDataInterface> temp = new ArrayList<SongDataInterface>();
        temp.add(song);
        map.put(arr[i], temp);
      }
    }
  }

  /*
   * This method just checks that a certain song exists in our hashmap or not. It does this by
   * getting all the words in the title of the song. It then loops through all these titles, and
   * gets the corresponding arraylist with each title word and then loops through the arraylist and
   * checks if the title, artist, and year of the song in the arraylist matches with the one given
   * as the argument. If so, we return true. Otherwise we return false.
   * 
   * @param song the song we're checking if it exists in the hashmap or not.
   * 
   * @returns true if the song exists in the hashmap. False otherwise.
   */
  @Override
  public boolean containsSong(SongDataInterface song) {
    if (song == null) { // If the song is null, we return false.
      return false;
    }
    String[] arr = song.getTitle().split(" ");
    for (int a = 0; a < arr.length; a++) { // Here we loop through each title word from the song and
                                           // get its corresponding arraylist
      if (map.containsKey(arr[a]) == false) { // If the map does not contain the the title word,
                                              // that means the song does not exist so we return
                                              // false.
        return false;
      }
      ArrayList<SongDataInterface> allSongs = map.get(arr[a]);
      for (int i = 0; i < allSongs.size(); i++) { // Here we loop through the arraylist and get each
                                                  // song in the list and compare its title, artist
                                                  // and year with the song in the argument and if
                                                  // they all match then the song does exist and we
                                                  // return true. Otherwise we return false.
        if (song.getTitle().equals(allSongs.get(i).getTitle())) {
          if (song.getArtist().equals(allSongs.get(i).getArtist())) {
            if (song.getYearPublished() == (allSongs.get(i).getYearPublished())) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  /*
   * This method finds a list of all the titles with a certain title word in it. It does this by
   * looping through the arraylist at that key, and adds all the titles to a new arraylist.
   * 
   * @param titleWord the title word we're searching for.
   * 
   * @return null if the map doesn't have the title. Otherwise, the list of all titles with that
   * word in its title.
   */
  @Override
  public List<String> findTitles(String titleWord) {
    List<String> values = new ArrayList<String>();
    if (map.containsKey(titleWord) == false) { // If the hashmap does not contain the key, we simply
                                               // return null.
      return null;
    }
    ArrayList<SongDataInterface> temp = map.get(titleWord);
    for (int i = 0; i < temp.size(); i++) { // this loops through the arraylist and adds every title
                                            // to the arraylist containing all titles corresponding
                                            // to the key.
      values.add(temp.get(i).getTitle());
    }
    return values;
  }

  /*
   * This method finds all the artists corresponding to a certain key/title word. It does this by
   * looping through the arraylist at that key and adds all the artists to a new arraylist.
   * 
   * @param titleWord the title word we're searching for
   * 
   * @return null if the title is not a key, otherwise the list containing all artists with that key
   * as a word in its title.
   */
  @Override
  public List<String> findArtists(String titleWord) {
    List<String> values = new ArrayList<String>();
    if (map.containsKey(titleWord) == false) { // If the hashmap does not contain the key, we simply
                                               // return null.
      return null;
    }
    ArrayList<SongDataInterface> temp = map.get(titleWord);
    for (int i = 0; i < temp.size(); i++) { // This loops through the arraylist and adds every
                                            // artist to the arraylist containing all artists
                                            // corresponding ot the key.
      values.add(temp.get(i).getArtist());
    }
    return values;
  }

  /*
   * This method counts the number of songs with a certain title word in its title and a certain
   * year it was made. It does this by looping through the arraylist corresponding to that key, and
   * seeing if the year of that song matches with the one passed as the argument, we increase the
   * counter, which counts the number of songs that match the condition and in the end we return
   * that counter which is the number of songs wth a certain title wrod and a certain year.
   * 
   * @param titleWord the title word we're searching for
   * 
   * @param year the year we have to find the number of songs match up with.
   * 
   * @return the number of songs wth a certain title word in its title and a certain year it was
   * made.
   */
  @Override
  public int findNumberOfSongsInYear(String titleWord, int year) {
    int counter = 0;
    if (map.containsKey(titleWord) == false) { // If the map does not contain the key, we simply
                                               // return 0.
      return 0;
    }
    ArrayList<SongDataInterface> temp = map.get(titleWord);
    for (int i = 0; i < temp.size(); i++) { // we loop through the arraylist and see if the song's
                                            // published year matches the argument, if so we
                                            // incremeent, otherwise we do nothing and move to the
                                            // next song.
      if ((temp.get(i).getYearPublished()) == year) {
        ++counter;
      }
    }

    return counter;
  }

  public static void main(String[] args) {
    try {
      SearchBackEnd r = new SearchBackEnd("Users");
      int a = r.findNumberOfSongsInYear("with", 2018);
      System.out.println(a);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }



}


class SearchBackEndPlaceholder implements SearchBackEndInterface {
  private SongDataInterface onlySong;

  public void addSong(SongDataInterface song) {
    this.onlySong = song;
  }

  public boolean containsSong(SongDataInterface song) {
    return onlySong.equals(song);
  }

  public List<String> findTitles(String titleWord) {
    List<String> titles = new LinkedList<>();
    if (onlySong.getTitle().contains(titleWord))
      titles.add(onlySong.getTitle());
    return titles;
  }

  public List<String> findArtists(String titleWord) {
    List<String> artists = new LinkedList<>();
    if (onlySong.getArtist().contains(titleWord))
      artists.add(onlySong.getArtist());
    return artists;
  }

  public int findNumberOfSongsInYear(String titleWord, int year) {
    if (onlySong.getYearPublished() == year)
      return 1;
    return 0;
  }
}

// placeholder(s) (implemented with proposal, and possibly added to later)
