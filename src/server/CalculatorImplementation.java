package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {

    //this is for shared stack which is used by multiple clients.

    // Stack<Integer> stack = new Stack<>();

    // public CalculatorImplementation() throws RemoteException{
    //     super();
    // }

    //initialize map for each client assosiated with client id.
    private static Map<Integer, Stack<Integer>> clientStacks = new HashMap<>();

    public CalculatorImplementation() throws RemoteException{
            super();
        }
    
    private static Stack<Integer> getClientStack(int clientId){
        return clientStacks.computeIfAbsent(clientId, k -> new Stack<>());
    }

    @Override
    //this method push the value on the top of the stack
    public synchronized void pushValue(int clientId, int val) throws RemoteException {
        Stack<Integer> stack = getClientStack(clientId);
        stack.push(val);

    }

    @Override
    //this method take a string operator as a parameter. if the operator is min, then it will find the min value of the stack
    //if the operator is max, then it will the max value of the stack.
    // if the operator is lcm, then it will return the lcm of stack values. for example, if the stack has 4,5,6 values, then the lcm will be 60.
    // if the operator is gcd, then it will return the gcd of stack values. for example, if the stack has 12,18 values, then the gcd will be 6.
    public synchronized void pushOperation(int clientId, String operator) throws RemoteException {
        Stack<Integer> stack = getClientStack(clientId);
        if (!operator.equals("min") && !operator.equals("max") && !operator.equals("lcm") && !operator.equals("gcd")) {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
            int result;
            switch (operator){
                case "min":
                    result = stack.stream().min(Integer :: compareTo).orElseThrow(() -> new IllegalStateException("No values to compare for min operation"));
                    break;
                case "max":
                    result = stack.stream().max(Integer::compareTo).orElseThrow(() -> new IllegalStateException("No values to compare for min operation"));
                    break;
                case "lcm":
                    result = getLCM(stack);
                    break;
                case "gcd":
                    result = getGCD(stack);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
            stack.clear();
            stack.push(result);
        }
        //calculate the gcd
        private int getGCD(Stack<Integer> stack) {
        int result = stack.pop();
        while (!stack.isEmpty()) {
            result = gcd(result, stack.pop());
        }
        return result;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }


    //calculate lcm
    private int getLCM(Stack<Integer> stack) {
        int result = stack.pop();
        while(!stack.isEmpty()){
            result = lcm(result, stack.pop());
        }
        return result;
    }

    private int lcm(int a, int b) {
        return (a * b) / gcd(a,b);
    }


    @Override
    //this method pop or return the last element of the stack if the stack is not empty.
    public synchronized int pop(int clientId) throws RemoteException {
        Stack<Integer> stack = getClientStack(clientId);
        if(!stack.isEmpty()){
            return stack.pop();
        }
         throw new RemoteException("The Stack is empty!");
    }

    @Override
    //this method return true if the stack is empty otherwise it returns false
    public synchronized boolean isEmpty(int clientId) throws RemoteException {
        Stack<Integer> stack = getClientStack(clientId);
        return stack.isEmpty();
    }

    @Override
    //if the stack is not empty then this method first take the time that you want to stop/pause
    // the execution of program.time will be taken in millisecond.after the time is over the method will return the last element of the stack.
    //for example, if you want to pause the execution of the program for 2 seconds then take 2000 milliseconds in thread.sleep()method.
    //after 2 second the method will return the last element of the stack.
    public synchronized int delayPop(int clientId, int millis) throws RemoteException {
        Stack<Integer> stack = getClientStack(clientId);
        if(!stack.isEmpty()){
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return stack.pop();
    }
}
