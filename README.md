# Java RMI Calculator Application

## Overview

This project is a simple distributed calculator application using Java RMI (Remote Method Invocation). The application allows multiple clients to interact with a remote calculator server simultaneously. Each client can perform operations such as `min`, `max`, `lcm`, and `gcd` on their own stack of integers. The server manages the stacks and operations for each client independently.

## Project Structure

- **server/CalculatorServer.java**: Initializes the server and binds the `CalculatorService` to a specific port.
 
- **server/CalculatorImplementation.java**: Implements the `Calculator` interface, providing methods for clients to push values onto a stack, perform operations (min, max, lcm, gcd), and pop values off the stack.

- **client/CalculatorClient.java**: Simulates multiple client interactions with the remote calculator service. Each client runs in a separate thread and interacts with the server to perform various operations.

- **server/CalculatorServerTest.java**: Contains JUnit tests to verify the correct functionality of the server's methods.

## Features

- **pushValue(int clientId, int val)**: Pushes a value onto the stack for the specified client.
 
- **pushOperation(int clientId, String operator)**: Performs an operation (`min`, `max`, `lcm`, `gcd`) on the values in the stack for the specified client.

- **pop(int clientId)**: Pops the top value from the stack of the specified client.
 
- **isEmpty(int clientId)**: Checks if the stack of the specified client is empty.
 
- **delayPop(int clientId, int millis)**: Delays the popping of the top value from the stack for a specified number of milliseconds.

### Running the Server

1. Compile and start the server:
   ```bash
   javac -d out/production src/server/CalculatorServer.java src/server/CalculatorImplementation.java
   java -cp out/production server.CalculatorServer

2. Compile and start the client:
 ```bash
 javac -d out/production src/client/CalculatorClient.java
 java -cp out/production client.CalculatorClient

To know more details, please visit my repository (https://github.com/Mredul-Eng/calculator-rmi.git)
