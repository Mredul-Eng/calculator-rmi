package client;

import server.Calculator;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int command;
        try {
            Registry registry = LocateRegistry.getRegistry(8010); //get the port number in which the server will run
            Calculator calculator = (Calculator) registry.lookup("rmi://localhost/CalculatorService");
            while (true){
                System.out.print("Enter your choice: ");
                command = input.nextInt();

                switch (command){
                    case 1:
                        System.out.println("Enter the value to push: ");
                        int val = input.nextInt();
                        calculator.pushValue(val);
                        System.out.println("Push value: " + val);
                        break;
                    case 2:
                        input.nextLine();
                        System.out.println("Enter operation (min, max, lcm, gcd): ");
                        String operator = input.nextLine();
                        if(operator.equals("min") || operator.equals("max") || operator.equals("lcm") || operator.equals("gcd")){
                            calculator.pushOperation(operator);
                            int result = calculator.pop();
                            System.out.println("Result after " + operator + " operation: " + result);
                        }
                        else {
                            System.out.println("Invalid operator!");
                        }
                        break;
                    case 3:
                        int result = calculator.pop();
                        System.out.println("The popped value: " + result);
                        break;
                    case 4:
                        System.out.println("Is the Stack is empty: " + calculator.isEmpty());
                        break;
                    case 5:
                        System.out.println("Enter the time that you want to delay in milliseconds: ");
                        int time = input.nextInt();
                        int delayedResult = calculator.delayPop(time);
                        System.out.println("Popped value after the delay: " + delayedResult);
                        break;
                    default:
                        System.out.println("Invalid choice!!!Please enter a valid option.");

                }
            }
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

    }
}
