import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

class BookingService {

    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();
    private int idCounter = 1;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation r) {

        String type = r.roomType;

        if (inventory.getAvailability(type) <= 0) {
            System.out.println("Reservation failed for " + r.guestName + " (No rooms available)");
            return;
        }

        String roomId = type.replace(" ", "").toUpperCase() + "-" + idCounter++;

        allocatedRooms.putIfAbsent(type, new HashSet<>());

        Set<String> assigned = allocatedRooms.get(type);

        if (assigned.contains(roomId)) {
            System.out.println("Duplicate room detected.");
            return;
        }

        assigned.add(roomId);

        inventory.decrement(type);

        System.out.println("Reservation confirmed: " + r.guestName +
                " | Room Type: " + type +
                " | Room ID: " + roomId);
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v6.0\n");

        BookingRequestQueue queue = new BookingRequestQueue();

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));

        RoomInventory inventory = new RoomInventory();

        BookingService service = new BookingService(inventory);

        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            service.processReservation(r);
        }

        System.out.println("\nApplication exiting...");
    }
}
