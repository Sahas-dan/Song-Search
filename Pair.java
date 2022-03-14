// --== File Header ==--
// Name: Rahil Kapur
/*
 * This class simply creates a key value "pair" so to say; essentially its a node that holds both a
 * key and a value so it is easier to access both the key and value directly.
 */
public class Pair<Key, Value> {
  private Key currentKey; // We have two private instance fields which designate the current key
                          // and value being held in the pair.
  private Value currentValue;


  /*
   * Simple constructor that sets both instance fields to the parameters given.
   * 
   * @param r the keytype that is to be set to the current keytype in this current pair.
   * 
   * @param a the valuetype that is to be set to the current valuetype in this current pair.
   */
  public Pair(Key r, Value a) {
    this.currentKey = r;
    this.currentValue = a;
  }


  /*
   * Simple getter method that returns the key in this specific pair.
   * 
   * @return the current KeyType in the pair.
   */
  public Key getKey() {
    return this.currentKey;
  }

  /*
   * Simple getter method that returns the value in this specific pair.
   * 
   * @return the current ValueType in the pair.
   */
  public Value getValue() {
    return this.currentValue;
  }

}
