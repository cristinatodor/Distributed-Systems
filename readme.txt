The source codes are in a folder called JustHungry. This folder contains 5 folders: Client, FrontEnd, Server, Server2, Server3. 

How to run the system:

The connection between the Server(s) and the FrontEnd is established through Java RMI.
The servers can operate on different machines or on the same machine and different ports. 
The IP address and port number are specified in the config.properties file, on the lines starting with Server1_IP, Server1_Port, Server2_IP, Server2_Port, and Server3_IP, Server3_Port, respectively. 

-Server-
Open a new terminal window. Go to Server folder. 
Run rmiregistry 37001 in order to start the rmi. 
Open a new terminal window. Go to Server folder. 

(Linux and macOs)
Compile with:
export CLASSPATH=".:lib/json-simple-1.1.1.jar"
javac rmi/proxy/*.java
Run with:
export CLASSPATH=".:lib/json-simple-1.1.1.jar"
java rmi.proxy.Server

(Windows)
Compile with:
javac -cp lib\json-simple-1.1.1.jar; rmi/proxy/Server.java
Run:
java -cp lib\json-simple-1.1.1.jar; rmi.proxy.Server



-Server2-
Open a new terminal window. Go to Server2 folder. 
Run rmiregistry 37002 in order to start the rmi. 
Open a new terminal window. Go to Server2 folder. 

(Linux and macOs)
Compile with:
export CLASSPATH=".:lib/json-simple-1.1.1.jar"
javac rmi/proxy/*.java
Run with:
export CLASSPATH=".:lib/json-simple-1.1.1.jar"
java rmi.proxy.Server2

(Windows)
Compile with:
javac -cp lib\json-simple-1.1.1.jar; rmi/proxy/Server2.java
Run:
java -cp lib\json-simple-1.1.1.jar; rmi.proxy.Server2


-Server3-
Open a new terminal window. Go to Server folder. 
Run rmiregistry 37003 in order to start the rmi. 
Open a new terminal window. Go to Server folder. 

(Linux and macOs)
Compile with:
export CLASSPATH=".:lib/json-simple-1.1.1.jar"
javac rmi/proxy/*.java
Run with:
export CLASSPATH=".:lib/json-simple-1.1.1.jar"
java rmi.proxy.Server3

(Windows)
Compile with:
javac -cp lib\json-simple-1.1.1.jar; rmi/proxy/Server3.java
Run with:
java -cp lib\json-simple-1.1.1.jar; rmi.proxy.Server3

The connection between the Client and FrontEnd is established through Java sockets. 
The socket used is specified in the config.properties, on the lines starting Socket_Port and Client_Port. These must have the same value. 

-FrontEnd-
Open a new terminal window. Go to FrontEnd folder.
Compile with:
javac rmi/proxy/*.java
Run with:
java rmi.proxy.FrontEnd

-Client-
Open a new terminal window. Go to Client folder.
Compile with:
javac rmi/proxy/Client.java
Run with:
java rmi.proxy.Client


The system uses an external web service for retrieving information about the postcode.
This can be found at https://postcodes.io/. 
