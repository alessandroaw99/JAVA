import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Collection;

public class YourCollectionTest implements CollectionTest {
    private Collection<Person> arrayList;
    private Collection<Person> linkedList;
    private HashMap<String, Person> hashMap;
    private int size;

    public YourCollectionTest() {
        // Initialize the data structures for ArrayList, LinkedList, and HashMap
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        hashMap = new HashMap<>();
        // Initialize the size of the collections
        size = 0;
    }

    @Override
    public void setSize(int size) {
        // Set the size of the collections for testing
        this.size = size;
    }

    @Override
    public void runTest(CollectionType type, TestType test, int iterations) {
        // Perform the specified test on the chosen collection type
        switch (test) {
            case ADD:
                // Add test data to the collection
                addTestData(type);
                break;
            case INDEX:
                // Retrieve data by index in the collection
                indexTestData(type, iterations);
                break;
            case SEARCH:
                // Search for data in the HashMap
                searchTestData(type, iterations);
                break;
        }
    }

    private void addTestData(CollectionType type) {
        // Add test data to the selected collection
        for (int i = 0; i < size; i++) {
            // Create a Person object with a name and age
            Person person = new Person("Person" + i, i);
            // Add the Person object to the chosen collection
            switch (type) {
                case ARRAY_LIST:
                    arrayList.add(person);
                    break;
                case LINKED_LIST:
                    linkedList.add(person);
                    break;
                case HASH_MAP:
                    // Use the person's name as the key in the HashMap
                    hashMap.put(person.getName(), person);
                    break;
            }
        }
    }

    private void indexTestData(CollectionType type, int iterations) {
        // Perform the index test by retrieving data based on index
        int collectionSize = (size > 0) ? size : 1; // Make sure the size is at least 1

        for (int i = 0; i < iterations; i++) {
            // Find the middle index in the collection
            int index = collectionSize / 2;
            switch (type) {
                case ARRAY_LIST:
                    // Retrieve data from the ArrayList by index
                    ((ArrayList<Person>) arrayList).get(index);
                    break;
                case LINKED_LIST:
                    // Retrieve data from the LinkedList by index
                    ((LinkedList<Person>) linkedList).get(index);
                    break;
            }
        }
    }

    private void searchTestData(CollectionType type, int iterations) {
        // Perform the search test by searching for data in the HashMap
        for (int i = 0; i < iterations; i++) {
            // Generate the name to search, which is expected to be in the middle
            String nameToSearch = "Person" + (size / 2);
            switch (type) {
                case HASH_MAP:
                    // Search for a Person in the HashMap using the name as the key
                    hashMap.get(nameToSearch);
                    break;
            }
        }
    }
}
