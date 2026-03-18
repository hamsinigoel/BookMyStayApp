import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public void validateRoomType(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
    }

    public void checkAvailability(String roomType) throws InvalidBookingException {
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {
        validateRoomType(roomType);
        checkAvailability(roomType);

        int available = inventory.get(roomType);
        inventory.put(roomType, available - 1);

        System.out.println("Room allocated successfully for type: " + roomType);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v9.0\n");

        RoomInventory inventory = new RoomInventory();

        String[] bookingRequests = {
                "Single Room",
                "Suite Room",
                "Luxury Room",
                "Double Room"
        };

        for (String roomType : bookingRequests) {

            try {
                System.out.println("Processing booking request for: " + roomType);
                inventory.allocateRoom(roomType);
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
            }
        }

        inventory.displayInventory();

        System.out.println("\nApplication exiting...");
    }
}