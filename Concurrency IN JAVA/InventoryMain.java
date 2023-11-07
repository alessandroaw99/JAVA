public class InventoryMain {
    private static int inventory = 0;    //initialising starting inventory as 0

    public static void main(String[] args) {
        // a Check to ensure the correct number of command-line arguments is provided
        if (args.length != 2) {
            System.out.println("Usage: java InventoryMain <num_add_operations> <num_remove_operations>");
            System.exit(1);
        }

        // Parse the command-line arguments to get the number of add and remove operations
        int numAddOperations = Integer.parseInt(args[0]);
        int numRemoveOperations = Integer.parseInt(args[1]);

        // Create arrays to hold add and remove threads
        Thread[] addThreads = new Thread[numAddOperations];
        Thread[] removeThreads = new Thread[numRemoveOperations];

        // Create and start add threads
        for (int i = 0; i < numAddOperations; i++) {
            addThreads[i] = new Thread(new AddOperation());
            addThreads[i].start();
        }

        // Create and start remove threads
        for (int i = 0; i < numRemoveOperations; i++) {
            removeThreads[i] = new Thread(new RemoveOperation());
            removeThreads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numAddOperations; i++) {
            try {
                addThreads[i].join(); // Wait for each add thread to finish
            } catch (InterruptedException e) { //for error handling
                e.printStackTrace();
            }
        }
        for (int i = 0; i < numRemoveOperations; i++) {
            try {
                removeThreads[i].join(); // Wait for each remove thread to finish
            } catch (InterruptedException e) {  //for error handling
                e.printStackTrace();
            }
        }

        // Print final inventory size 
        System.out.println("Final inventory size = " + inventory);
    }

    // Runnable class for adding items to the inventory
    static class AddOperation implements Runnable {
        @Override
        public void run() {
            synchronized (InventoryMain.class) { // Synchronize using the class itself
                inventory++; // Increment the inventory
                System.out.println("Added. Inventory size = " + inventory); // Print operation and inventory size
            }
        }
    }

    // Runnable class for removing items from the inventory
    static class RemoveOperation implements Runnable {
        @Override
        public void run() {
            synchronized (InventoryMain.class) { // Synchronize using the class itself
                inventory--; // Decrement the inventory
                System.out.println("Removed. Inventory size = " + inventory); // Print operation and inventory size
            }
        }
    }
}
