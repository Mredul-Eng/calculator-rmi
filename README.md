# Java RMI Calculator Application

This project demonstrates a simple distributed system using Java RMI (Remote Method Invocation). It includes a server that provides calculator services and multiple clients that interact with the server concurrently. Each client operates on its own stack, enabling individual computations. The project is designed with multithreading to handle multiple clients simultaneously, and all methods are thoroughly tested using JUnit.

## Table of Contents

- [Overview](#overview)
- [Project Structure](#project-structure)
- [Client Workflow](#client-workflow)
- [Running the Application](#running-the-application)
- [JUnit Testing Methodology](#junit-testing-methodology)


## Overview

The application consists of the following key components:

- **Calculator Interface (`Calculator.java`)**: Defines the remote operations that the server offers.
- **Calculator Implementation (`CalculatorImplementation.java`)**: Implements the methods defined in the `Calculator` interface.
- **Calculator Server (`CalculatorServer.java`)**: Registers the `CalculatorImplementation` with the RMI registry, making it available to remote clients.
- **Calculator Client (`CalculatorClient.java`)**: A test client that connects to the server and performs various operations by taking user inputs from the terminal.
- **Client (`Client.java`)**: A runnable class representing individual clients. Multiple instances of this class are used to simulate concurrent clients interacting with the server.

## Client Workflow

### `CalculatorClient.java`

- Starts multiple client threads to simulate concurrent access to the remote calculator service.
- Each client interacts with the server through a command-line interface, where users can perform operations such as pushing values to the stack, performing calculations, popping values, checking if the stack is empty, and delaying operations.

### `Client.java`

- Implements the `Runnable` interface and represents an individual client.
- Connects to the RMI registry, looks up the calculator service, and interacts with it by handling user input commands.
- Operations include:
  1. **Push a value to the stack**: Each client can push values to its own stack on the server.
  2. **Perform an operation**: The client can perform operations (`min`, `max`, `lcm`, `gcd`) on the values in its stack and retrieve the result.
  3. **Pop a value from the stack**: The client can pop the top value from its stack.
  4. **Check if the stack is empty**: The client can check whether its stack is empty.
  5. **Delay pop**: The client can delay the pop operation by a specified amount of time.

## Running the Application

1. Ensure that the RMI server is running and accessible.
2. Compile and run `CalculatorClient.java`. This will start multiple client threads.
3. Each client thread will prompt the user to enter commands to interact with the calculator service.

```bash
javac CalculatorClient.java
java CalculatorClient
