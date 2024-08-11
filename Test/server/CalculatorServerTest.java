package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorServerTest {
    private Calculator calculator;
    int clientId;

    @BeforeEach
    void setUp() throws Exception{
        Registry registry = LocateRegistry.getRegistry("192.168.213.175", 1533);
        Calculator server = new CalculatorImplementation();
        registry.rebind("CalculatorService", server);

        calculator = (Calculator) registry.lookup("CalculatorService");
       clientId= (int) Thread.currentThread().getId();
    }


    @Test
    public void testPushValue() throws RemoteException {
        calculator.pushValue(1,12);
        assertEquals(12, calculator.pop(1));
        System.out.println("Success testPushValue for " + clientId);

       calculator.pushValue(2,14);
       assertEquals(14, calculator.pop(2));

       calculator.pushValue(3,16);
       assertEquals(16, calculator.pop(3));

    }

    @Test
    public void testPushOperation() throws RemoteException{
        //for client 1
        calculator.pushValue(1, 12);
        calculator.pushValue(1, 18);
        calculator.pushOperation(1, "gcd");
        assertEquals(6, calculator.pop(1));
        System.out.println("Success testPushOperation for GCD with client id: " + clientId);

        //for client 2
        calculator.pushValue(2, 24);
        calculator.pushValue(2, 28);
        calculator.pushOperation(2, "gcd");
        assertEquals(4, calculator.pop(2));
        System.out.println("Success testPushOperation for GCD with client id: " + clientId);

        calculator.pushValue(1, 4);
        calculator.pushValue(1, 5);
        calculator.pushValue(1, 6);
        calculator.pushOperation(1, "lcm");
        assertEquals(60, calculator.pop(1));
        System.out.println("Success testPushOperation for lcm with client id: " + clientId);
    }

    @Test
    public void testPopOperation() throws RemoteException{
        calculator.pushValue(1, 20);
        assertEquals(20,calculator.pop(1));
        System.out.println("Success testPopOperation for " + clientId);
        calculator.pushValue(2, 40);
        assertEquals(40,calculator.pop(2));
        System.out.println("Success testPopOperation for " + clientId);
    }

    @Test
    public void testDelayPoppedOperation() throws RemoteException{
        //for client 1
        calculator.pushValue(1, 100);
        assertEquals(100,calculator.delayPop(1, 2000));

        //for client 2
        calculator.pushValue(2, 234);
        assertEquals(234,calculator.delayPop(2, 2000));

    }
}