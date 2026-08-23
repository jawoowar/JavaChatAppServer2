This is the Server that works in conjunction with "JavaChatApp3".

This handles messages moving between person to person and keeping track of users messages.

This project is made up of 9 files which will be discussed below

Classes and functions
-

Main
- Handles the initiating of teh server, gathering information like wanted ports for the server to run on.
- Starts other processes such as "close"
- Listen for incoming messages and creates a "MessageHandler" if it is a new user

MessageHanlder
- formats messages to be sent out
- puts incoming messages into a recognised class to be worked with later
- writes to "Chatlog.txt", with incomming and outgoing messages
- updates user list when a new user joins
- removes users from user list when they disconnect
- closes Input, output and socket when server closes

Close
- Opens on its own thread
- listens for when closure string is inputter by server owner "/close"
- calls upon all function that close there portion of the program

Users
- holds user data for all connected users (username and MessageHandler) inside a concurrent hash map
- can be called upon to add or remove from user hash map
- sends out to connected users by itterating through the hashmap and calling the respective "Message Handlers"
- returns hashmap is called on

File Handler
- writes to *only* "ChatLog.txt"
- handles formatting and writing to chatLog
- reads from spesified file and returns to calling function

Message
- holds information about more recent message ("MsgType", "Sender", "Content")
- can be called to set or return held variables

WebServer
- Starts up httpServer on spesified port
- hosts website by calling upon the website class

Website
- reads the "index.html" file via "FileHandler.Read()"
- inputs relevent information into it using string replacement


Function of this program
-
This half the program completes the main functions of

- creating a server on a port the user spesifies
- stopping and starting the server based on user input
- having a web page for important information and connected users

Security conserns and ethical conserns with changes to counteract them
-

the program using the content of send messages to handle things server-side
the worry of someone simply typing them into the chat and causing unforseen issues on the server side 
- to fix this I used a "MsgType" tag so a normal user is unable to access these features through the main chat,
I also used spesific wording that then causes pre-written code to execute never sending actual code between server and client.

the nature of chat apps is that people can communicate and with that comes issues of people using platforms to conduct or plan illegal or anti-social activity
- to counteract this I implemented a chatlog that can only be written to so that if nessacery it can be used later to help if any intervatino takes place.


External library used
-
Gson
- com.google.code.gson


