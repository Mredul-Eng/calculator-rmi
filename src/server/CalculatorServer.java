package server;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            Calculator calculator = new CalculatorImplementation(); // create server remote object
            Registry registry = LocateRegistry.createRegistry(1403); //the server will run 8010 port in our local server or system
            registry.rebind("CalculatorService", calculator);
            System.out.println("The Calculator Server is running.....");
        } catch (RemoteException e) {
            System.out.println("Server exception: " + e.toString());
            throw new RuntimeException(e);
        }
    }
}
