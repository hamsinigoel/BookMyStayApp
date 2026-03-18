import java.util.*;

class Service {
    String name;
    double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }
}

class AddOnServiceManager {

    private HashMap<String, List<Service>> reservationServices = new HashMap<>();

    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println(service.name + " added to reservation " + reservationId);
    }

    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services for reservation " + reservationId);
            return;
        }

        double totalCost = 0;

        System.out.println("\nAdd-On Services for Reservation " + reservationId + ":");

        for (Service s : services) {
            System.out.println(s.name + " - $" + s.price);
            totalCost += s.price;
        }

        System.out.println("Total Add-On Cost: $" + totalCost);
    }
}

public class UseCase7AddOnServiceSelection {

    public static void main(String[] args) {

        System.out.println("Book My Stay - Hotel Booking System v7.0\n");

        AddOnServiceManager manager = new AddOnServiceManager();

        String reservationId = "RES-101";

        Service breakfast = new Service("Breakfast", 15);
        Service airportPickup = new Service("Airport Pickup", 30);
        Service spa = new Service("Spa Access", 50);

        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, airportPickup);
        manager.addService(reservationId, spa);

        manager.displayServices(reservationId);

        System.out.println("\nApplication exiting...");
    }
}