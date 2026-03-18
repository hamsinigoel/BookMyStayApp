import java.io.*;
import java.util.*;

class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

class RoomInventory implements Serializable {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void displayInventory() {
        System.out.println("\nInventory State:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    public static void saveState(RoomInventory inventory, List<Reservation> history) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            oos.writeObject(history);

            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    public static Object[] loadState() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory inventory = (RoomInventory) ois.readObject();
            List<Reservation> history = (List<Reservation>) ois.readObject();

            System.out.println("System state restored successfully.");

            return new Object[]{inventory, history};

        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v12.0\n");

        RoomInventory inventory;
        List<Reservation> bookingHistory;

        Object[] state = PersistenceService.loadState();

        if (state != null) {
            inventory = (RoomInventory) state[0];
            bookingHistory = (List<Reservation>) state[1];
        } else {
            inventory = new RoomInventory();
            bookingHistory = new ArrayList<>();
        }

        bookingHistory.add(new Reservation("RES-101", "Alice", "Single Room"));
        bookingHistory.add(new Reservation("RES-102", "Bob", "Double Room"));

        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            r.display();
        }

        inventory.displayInventory();

        PersistenceService.saveState(inventory, bookingHistory);

        System.out.println("\nApplication exiting...");
    }
}