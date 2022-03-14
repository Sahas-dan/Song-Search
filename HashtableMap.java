// --== File Header ==--
// Name: Rahil Kapur
import java.util.NoSuchElementException;
import java.util.LinkedList;

/*
 * This class creates a Hashtable map that maps a specific key to a value. This is done using an
 * array of linked lists, where every index contains a linked list and inside each index in the
 * linked list contains a pair element which essentially holds both the key and the value, acting as
 * a node. The class contains two constructors and many methods that essentially alow you to add
 * more elements inside the hashtable map, access elements inside the map, get the size of the map,
 * remove everything inside the hashtable, and see if a key exists, remove a specific pair inside
 * the hashmap, and other private methods used to rehash or create a bigger array to store more
 * elements.
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  private LinkedList<Pair>[] hash; // this private instance field simply stores the array of linked
                                   // lists which contains pairs. This array is essentially is the
                                   // backbone of the hashmap.

  /*
   * This constructor simply creates a new instance of a HashtableMap when given a certain capacity.
   * 
   * @param capacity, the capacity/length of the array that we use to set the length of the array.
   */
  public HashtableMap(int capacity) {
    hash = (LinkedList<Pair>[]) new LinkedList[capacity];

  }

  /*
   * This constructor sets the default length/capacity of 20 for the array.
   */
  public HashtableMap() { // with default capacity = 20
    hash = (LinkedList<Pair>[]) new LinkedList[20];
  }

  /*
   * This private helper method rehashes the hashmap, which menas it creates a new array of double
   * the length as before and copies the elements from before to the new double length array.
   * 
   * @param arr the reference to the array we need to rehash.
   * 
   * @return the new array of LinkedList, which contains double the length as before with the same
   * elements as before.
   */
  private LinkedList[] rehashing(LinkedList[] arr) {
    LinkedList[] newHash = new LinkedList[arr.length * 2]; // Here we create a new array of linked
                                                           // list with double the length as the one
                                                           // before.
    for (int i = 0; i < arr.length; i++) { // This for loop simply goes through the array copying
                                           // all the old values from the old array into the new
                                           // array
      newHash[i] = arr[i];
    }
    return newHash;

  }

  /*
   * This private helper method simply obtains the loadfactor of an array.
   * 
   * @param size the size of the array, meaning the number of elements inside the array.
   * 
   * @param arr the reference to the actual array. Necessary because we need it to get the length of
   * the array.
   * 
   * @return the load factor of an array given its size and a reference to the array
   */
  private double getLoadFactor(int size, LinkedList[] arr) {
    double loadFactor = (double) size / arr.length; // this computation simply typecasts the return
                                                    // of dividing the size of an array by its
                                                    // length.
    return loadFactor;
  }

  /*
   * This method simply adds a key and its corresponding value to the hashmap. We first make sure
   * that the key is not null, or already in the array. If so, we return false. Otherwise, we go
   * through each element in the linked list looking for the key and if we find the specific key
   * we're looking for we return false. Otherwise, we get the index of the key by modulo the
   * hashcode of the key and the length of the array and adding the pair to the linked list in the
   * index of the array. If there is no linked list already in the index, we create it, otherwise we
   * simply add the key and value(pair) into the list.
   * 
   * @param key, the key that we're adding to the hashmap.
   * 
   * @param value, the value we're adding to the hashmap.
   * 
   * @return true if we succesfully added a new pair(key, value) to the hashtable map. Otherwise
   * return false if it is not succesful- whether key is null, or already in the hashtable map.
   */
  @Override
  public boolean put(KeyType key, ValueType value) {

    if (key == null) { // if Key is null or already in the hashmap, we return false, unsuccesfully
                       // adding the key value pair.
      return false;
    }

    for (int i = 0; i < hash.length; i++) { // This for loop simply simply goes through the array
                                            // and if we find the same key in the parameter in the
                                            // array, then we return false, otherwise we move on in
                                            // the method.
      if (hash[i] == null) {
        continue;
      }
      for (int r = 0; r < hash[i].size(); r++) { // This for loop essentially goes through the
                                                 // linked list at each index and looks to make sure
                                                 // that the key does not exist already.
        if (hash[i].get(r).getKey() == null) { // Here we just make sure that the key is not null.
          continue;
        }
        if (key == hash[i].get(r).getKey()) { // If the key does exist, we simply return false.
          return false;
        }
      }
    }

    int code = (Math.abs(key.hashCode())) % (hash.length); // here we get the index by modulo of the
                                                           // key's hashcode and the hash's length.
    if (hash[code] == null) {
      hash[code] = new LinkedList();
    }
    hash[code].add(new Pair(key, value)); // here we add the key-value pair in the linked list in
                                          // the specific index.
    double loadFactor = getLoadFactor(this.size(), hash); // obtaining the load factor using
    // getLoadFactor.
    if (loadFactor >= 0.80) { // If load factor is or exceeds 80%, then we rehash the hashmap.
      hash = rehashing(hash);
    }
    return true;
  }

  /*
   * This method simply lets you obtain a ValueType based on a key you pass in the method. If the
   * key does not exist within the method, then a NoSuchElementException is thrown. It goes through
   * the array and each linked list at each index in the array searching for the specified key. If
   * the key is found, we simply obtain the corresponding value and return it, otehrwise we throw
   * the exception.
   * 
   * @param key the key being used to search the hashmap to find the associated ValueType
   * 
   * @return the associated ValueType with the KeyType that is given as the param by searching the
   * array until it is found.
   * 
   * @throws NoSuchElementException if the key is NOT in the array, since no such element exists in
   * the array.
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    for (int i = 0; i < hash.length; i++) { // looping through the array to obtain all the keys
                                            // present and see if any of them match with the key in
                                            // the given parameter.
      if (hash[i] == null) { // if the index does not have any linked list, we go to the next index.
        continue;
      }
      for (int r = 0; r < hash[i].size(); r++) { // This for loop simply goes through the linked
                                                 // list at each index looking for the specified key
                                                 // at each linked list node.
        KeyType temp = (KeyType) hash[i].get(r).getKey();
        if (temp == null) { // if there is no key in the given index, then simply move on to the
                            // next
                            // index in the array.
          continue;
        }
        if (key.equals(temp)) { // if the two keys match, then we get the associated valueType and
                                // return
          // it.
          return (ValueType) (hash[i].get(r).getValue());
        }
      }
    }
    throw new NoSuchElementException(); // If we go through the entire array and do not find the
                                        // matching key, we throw a NoSuchElementException.
  }

  /*
   * This method calculates the number of actual number of elements in the array.
   * 
   * @return the number of elements in the array.
   */
  @Override
  public int size() {
    int counter = 0;
    for (int i = 0; i < hash.length; i++) { // Using a counter we count the number of elements in
                                            // the array, and return it.
      if (hash[i] == null) {
        continue;
      }
      for (int r = 0; r < hash[i].size(); r++) { // We go through every linked list node, counting
                                                 // every element inside each node, to make sure we
                                                 // get the correct size.
        if (hash[i].get(r) != null) {
          ++counter;
        }
      }
    }
    return counter;
  }

  /*
   * This method just checks if an array actually contains a certain key by looping through the
   * array and looping through each linked list, looking through each linked list node, at each
   * index searching for the corresponding key.
   * 
   * @param key, a KeyType that we check if it is in the array
   * 
   * @return true, if the key exists in the array, false otherwise.
   */
  @Override
  public boolean containsKey(KeyType key) {
    for (int i = 0; i < hash.length; i++) { // looping through the array, checkinf each index seeing
                                            // if that key matches with the key in the parameter.
      if (hash[i] != null) {
        for (int r = 0; r < hash[i].size(); r++) { // We look through each linked list, in each node
                                                   // looking for the corresponding key.
          KeyType temp = (KeyType) hash[i].get(r).getKey();
          if (temp.equals(key)) { // if the two keys do match, return true.
            return true;
          }
        }
      }
    }
    return false; // If we looped through the array and did not find the key, we return false.
  }

  /*
   * This remove method simply loops through the array looking for the same key in the parameter. If
   * it is find we get the coressponding value, and then set that index to null in the array and
   * return the value. If the key is not found we return null. We do this by looping through the
   * array and looping through the linked lists at each index.
   * 
   * @param key, the key we are searching for in the array.
   * 
   * @return the corresponding valuetype to the key from the hashmap, otherwise null if the key is
   * not present.
   */
  @Override
  public ValueType remove(KeyType key) {
    if (this.containsKey(key) == false) { // We make sure that the key actually exists using the
                                          // containsKey method.
      return null;
    }


    for (int r = 0; r < hash.length; r++) { // Here we go through the hashmap index by index,
                                            // looking for an index that has a linked list in it.
      if (hash[r] == (null)) {
        continue;
      }

      for (int i = 0; i < hash[r].size(); i++) { // This for loop goes through the linked list node
                                                 // by node comparing each key to find the key
                                                 // matching the parameter, when we do find it, we
                                                 // get the corresponing valuetype and remove the
                                                 // pair from the linkedlist and return the value.

        if (hash[r].get(i).getKey().equals(key)) {
          ValueType value = (ValueType) hash[r].get(i).getValue();

          hash[r].remove(i);

          return value;
        }
      }
    }
    return null; // IF this point is reached, the key does not exist, thus we return null.
  }

  /*
   * Here we simply clear the array, by removing all the elements.
   */
  @Override
  public void clear() {
    for (int i = 0; i < hash.length; i++) { // here we loop through the array and set every value to
                                            // null, clearing everything.
      hash[i] = null;
    }
  }
}
