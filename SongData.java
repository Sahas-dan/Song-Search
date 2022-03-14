// --== File Header ==--
// Name: Sahas Dandapantula

interface SongDataInterface {
  public String getTitle();

  public String getArtist();

  public int getYearPublished();
}

// public class (implemented primarilly in final app week)


public class SongData implements SongDataInterface {

  private String title;
  private String Artist;
  private int YearPublished;

  public SongData(String title, String Artist, int YearPublished) {
    this.title = title;
    this.Artist = Artist;
    this.YearPublished = YearPublished;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  @Override
  public String getArtist() {
    return this.Artist;
  }

  @Override
  public int getYearPublished() {
    return this.YearPublished;
  }

}


class SongDataPlaceholderA implements SongDataInterface {
  public String getTitle() {
    return "Song A Vowel";
  }

  public String getArtist() {
    return "Artist X";
  }

  public int getYearPublished() {
    return 1900;
  }
}


class SongDataPlaceholderB implements SongDataInterface {
  public String getTitle() {
    return "Song B Consonant";
  }

  public String getArtist() {
    return "Artist Y";
  }

  public int getYearPublished() {
    return 2000;
  }
}


class SongDataPlaceholderC implements SongDataInterface {
  public String getTitle() {
    return "Song C Consonant";
  }

  public String getArtist() {
    return "Artist X";
  }

  public int getYearPublished() {
    return 2021;
  }
}
