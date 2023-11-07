Coursework Submission - Concurrency in Java

Student Name: Alessandro  Amsata-Awani
Student ID: 38863316

This is the submission for the Concurrency in Java exercise. InventoryMain, demonstrates concurrent add and remove operations on an inventory.

To run the program:

1. Compile the code:
   - Open terminal.
   - Navigate to the directory where the `InventoryMain.java` file is located.
   - Compile the code using the `javac` command:  javac InventoryMain.java
     

2. Run the program:
   - Once the compilation is successful, you can run the program using the `java` command, providing the desired number of add and remove operations as command-line arguments. For example:
    
     java InventoryMain <num_add_operations> <num_remove_operations>
     

   Replace `<num_add_operations>` and `<num_remove_operations>` with the desired number of add and remove operations. Example:
     
     java InventoryMain 10 5
     

   This command will run the program with 10 add operations and 5 remove operations.

3. Observe the results:
   - The program will execute the specified number of add and remove operations concurrently and display the results on the terminal.
