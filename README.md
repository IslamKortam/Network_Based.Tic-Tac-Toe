 # Network_Based.Tic-Tac-Toe
An Implementation of Tic Tac Toe game over a Network with single/multi pl

## Approach:
-----------------
Tic Tac Toe Game is a  Network-based game created and developed with java programming language based on socket programming and java database connectivity (JDBC) concepts.
 The game consists of two applications Server Application (hosts the game and initiates a connection with database) where you can start or stop the application. the other application is the  Client application where all client actions are handled. 

-------------------------------------------

### 1. 	Server-Side Application 
The server is run first then the admin should press the start button to start the connection with the data 
Base and wait for clients to join the game. in the server-side application, all action logs are displayed on 
The text area is below the buttons. You can also view all players recorded in the game from the players’ button.

a. Server home page

![Alt text]()

b. Players list in server  

![Alt text]()  

### 2.	Client-Side Application:

#### 2.1. Application starts with a sign-in page :

If the client already has an account on the game, he will be asked to enter his 
Email and Password. Information entered by the player will be sent to the server to check whether this player’s information exists or not.

![Alt text]()
    
#### 2.2. Sign up page :
If you don’t have an account in the game you need to create an account  in the game

![Alt text]()

-----------------------
### 1.	Home page  :
As a player in the game, you have two options to play :

•	Single-mode:  you will play with the robot depending on AI  there is Two modes in AI hard mode or normal mode 

•	MultiPlayer-mode: for playing in multi-player mode

•	Load game: to view unfinished games that you saved 

•	Sign out button: to sign out from the game 
![Alt text]()

#### 1.1. Single-mode:
On the game board , you can see your avatar name and score against the robot avatar and score. When the game is finished, a prompt will appear to tell you the result

![Alt text]()

#### 1.2 Multi-mode:

after choosing the multi-player mode, each player will have the player list shown with the username, score, and status for each player (OFFLINE, ONLINE, IN_MULTIPLAYER_GAME, or IN_SINGLE_PLAYER_GAME ), 
and if the status of the player is ONLINE then a button appears to make the player able to  invite a specific player to challenge him in a game. You can find the button if you hovered over the player’s row.

![Alt text]()

when player1 clicks on the button, an invitation for the multiplayer game is sent to player2 and an alert appears to player2 on its side, telling him that player1 wants to challenge him and asking him if he accepts,

![Alt text]()

if user2 accepted the challenge, the game starts and the board of the game appears on both sides for the two users, the small circle indicates the player’s turn. you can also save the game by pressing the save button. The one who sends requests for the game will get the “X” symbol and the other will get the “O” symbol. You can also quit the game by pressing the home button.

![Alt text]()

o   You can also chat with each other 

![Alt text]()


•	By clicking the home button,  it means you are quitting the game 
If you pressed ok, you will be sent to the home page  and you will be a loser 

![Alt text]()

•	If you clicked on save button : a request will be sent to the other player
The game will be stored you can view it and replay from load game button on home screen .

![Alt text]()

#### 1.3	 Load game 

Where you can view all saved game you can replay only if the second player is online 

![Alt text]()

•	Second user will be informed that you want to reload a game 

![Alt text]()

#### 1.4	 Sign out 
if you clicked the sign-out button your status will be changed to  offline, and you will be 
sent to login  page .

[For Detailed Documentation: ](https://drive.google.com/file/d/1OXRYxIZUAlknXIEknLMJ8WuqgITxbhdQ/view)






