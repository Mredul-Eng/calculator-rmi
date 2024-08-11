package client;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import server.Calculator;


public class CalculatorClient {

    public static void main(String[] args) {
        int numberOfClients = 5; // Number of client threads to simulate

        for (int i = 1; i <= numberOfClients; i++) {
            Thread clientThread = new Thread(new Client(i));
            clientThread.start();
        }
    }
}

class Client implements Runnable {

    private int clientID;
    private static final Object lock = new Object();

    public Client(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public void run() {
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.213.175", 1533); //get the port number in which the server will run
                    Calculator calculator = (Calculator) registry.lookup("CalculatorService");
                    System.out.println("Client with id " + clientID + " is running....");
                    synchronized(lock){
                        Scanner clientInput = new Scanner(System.in);
                        while (true){
                            System.out.println("Client " + clientID + " - Enter your choice: ");
                             int command = clientInput.nextInt();
                             handleClientCommand(clientID, calculator, command, clientInput);
                         } 
                    }
                   

        } catch (Exception e) {
            System.err.println("Client " + clientID + " exception: " + e.toString());
            e.printStackTrace();
        }
    }
        private static void handleClientCommand(int clientId, Calculator calculator, int command, Scanner clientInput) {
        
            try{
                switch (command){
                    case 1:
                        System.out.println("Enter the value to push: ");
                        int val = clientInput.nextInt();
                        calculator.pushValue(clientId, val);
                        System.out.println("Push value: " + val);
                        break;
                    case 2:
                        clientInput.nextLine();
                        System.out.println("Enter operation (min, max, lcm, gcd): ");
                        String operator = clientInput.nextLine();
                        if(operator.equals("min") || operator.equals("max") || operator.equals("lcm") || operator.equals("gcd")){
                            calculator.pushOperation(clientId,operator);
                            int result = calculator.pop(clientId);
                            System.out.println("Result after " + operator + " operation: " + result);
                        }
                        else {
                            System.out.println("Invalid operator!");
                        }
                        break;
                    case 3:
                        int result = calculator.pop(clientId);
                        System.out.println("The popped value: " + result);
                        break;
                    case 4:
                        System.out.println("Is the Stack is empty: " + calculator.isEmpty(clientId));
                        break;
                    case 5:
                        System.out.println("Enter the time that you want to delay in milliseconds: ");
                        int time = clientInput.nextInt();
                        int delayedResult = calculator.delayPop(clientId,time);
                        System.out.println("Popped value after the delay: " + delayedResult);
                        break;
                    default:
                        System.out.println("Invalid choice!!!Please enter a valid option.");
        
                }
            }
            catch(RemoteException e){
                e.printStackTrace();
            }
    }
}
