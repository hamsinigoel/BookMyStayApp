import java.util.*;

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
    }

    public synchronized boolean allocateRoom(String roomType) {

        int available = inventory.getOrDefault(roomType, 0);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        }

        return false;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory State:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

class BookingQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getRequest() {
        return queue.poll();
    }
}

class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            Reservation r = queue.getRequest();

            if (r == null)
                break;

            boolean allocated = inventory.allocateRoom(r.roomType);

            if (allocated) {
                System.out.println(Thread.currentThread().getName() +
                        " confirmed booking for " + r.guestName +
                        " (" + r.roomType + ")");
            } else {
                System.out.println(Thread.currentThread().getName() +
                        " failed booking for " + r.guestName +
                        " (" + r.roomType + " - no rooms left)");
            }
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v11.0\n");

        RoomInventory inventory = new RoomInventory();
        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Double Room"));
        queue.addRequest(new Reservation("Eva", "Double Room"));
        queue.addRequest(new Reservation("Frank", "Double Room"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inventory.displayInventory();

        System.out.println("\nApplication exiting...");
    }
}