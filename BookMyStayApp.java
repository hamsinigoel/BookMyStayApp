import java.util.*;

abstract class Room {
    String type;
    double price;

    public Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    public void displayDetails(int available) {
        System.out.println("Room Type: " + type);
        System.out.println("Price per night: $" + price);
        System.out.println("Available Rooms: " + available);
        System.out.println();
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 80);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 120);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 250);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public Set<String> getRoomTypes() {
        return inventory.keySet();
    }
}

class RoomSearchService {

    private RoomInventory inventory;
    private HashMap<String, Room> rooms = new HashMap<>();

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;

        rooms.put("Single Room", new SingleRoom());
        rooms.put("Double Room", new DoubleRoom());
        rooms.put("Suite Room", new SuiteRoom());
    }

    public void searchAvailableRooms() {

        System.out.println("Available Rooms:\n");

        for (String type : inventory.getRoomTypes()) {

            int available = inventory.getAvailability(type);

            if (available > 0) {
                rooms.get(type).displayDetails(available);
            }
        }
    }
}

public class UseCase4RoomSearch {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v4.0\n");

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.searchAvailableRooms();

        System.out.println("Application exiting...");
    }
}

