import java.io.*;
import java.util.ArrayList;

public class DataManager {

    private static final String FILE_NAME = "bank_data.dat";

    public static void saveData(ArrayList<User> users) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(users);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static ArrayList<User> loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            ArrayList<User> users = (ArrayList<User>) in.readObject();
            System.out.println("Data loaded successfully!");
            return users;
        } catch (FileNotFoundException e) {
            System.out.println("No existing data found. Starting fresh!");
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
