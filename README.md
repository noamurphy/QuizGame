# Project: Quiz Server

For this project you will create a Quiz Server and Client. The Quiz server
will be capable of hosting multiple connections at the same time
and for each connection it will be capable of asking quiz questions and keeping
track of users who have the longest streak of correct questions answered.

The quiz questions will come from the existing internet API: http://jservice.io/

The JService API has a large database of Jeopardy Game Show questions and answers.
In the Jeopardy Game show contestants choose questions within a specific category:

[Jeopardy Episode on Youtube](https://www.youtube.com/watch?v=pFhSKPOF_lI)

Our Quiz game will work differently than Jeopardy (we'll just use the JService API to help generate questions)

JService has approximately **18410** different Jeopardy categories of questions. Each category has some number of questions in it (hopefully more than 4 - typically some multiple of 5).

For our quiz game we'll select a Jeopardy category at random and then turn 4 of the questions from that category into a single multiple choice question.

Suppose we pick category 11473. The questions for this category can be seen here: [category view](https://jservice.io/popular/11473)

We will generate a question such as follows:

*Question from Category: no. 1 hitmakers of the '70s:*

*===================================================*

*1975: "Lucy In The Sky With Diamonds" (& it's not the Fab 4)?*

*===================================================*

*0: The Partridge Family*

*1: Elton John*

*2: The Bee Gees*

*3: KC & the Sunshine Band*

*===================================================*

*Answer: 1*

From the above One of the questions in the category `no. 1 hitmakers of the '70s`
is `1975: "Lucy In The Sky With Diamonds" (& it's not the Fab 4)?` (in the Jeopardy style this means name the band who had the 1975 no. 1 hit \"Lucy in the Sky with Diamonds\")

The answer to this question is `Elton John`

You are given code to parse JSON responses from the category endpoint of the
JService API in `JeopardyCategory.java`

The JeoparyCategory class takes as input a raw json string such as the one seen at this link: http://jservice.io/api/category?id=11473 and it parses out the category and creates Lists of the Questions and Answers (the question at index position x matches the answer at index position x). You will see there also exist unit tests for the JeoparyCategory class.

Algorithm to generate a question:

1. Pick an integer between 0 and 18410 at random (this will be your category)

2. Retrieve the JSON String from http://jservice.io/api/category?id=X (where x is the category number you generated previously)

3. Convert this into a JeopardyCategory using the supplied code.

4. Select a random question from the question list - this will be the text for your multiple choice questions, assign the corresponding answer to this question to a random multiple choice selection

5. select 3 other answers as other possible (but incorrect) answers for the other (not occupied by the real answer) multiple choice spots.


# Deliverable 1: QuizClient

## Deliverable 1 Due Date: Monday November 30th 11:59pm

You are supplied an example of the Quiz Server in a jar file - from this jar you can launch an example of a completed QuizServer project.

You will create a quiz client that communicates with this Quiz Server.

**Communication Protocol:**

The Quiz Server (and you Quiz Client) must communicate across sockets. They will use a simple protocol.

The Server will send messages to the client and some of those messages will require a response.

Each message that the server sends is of the form:

CODE\~MESSAGE\~

That is a Code followed by the tilde character `~` followed by the message and again followed by a tilde `~`. Note: a MSG may or may not contain newline `\n` or other special characters. It will contain newline characters for example if the message should be displayed over multiple lines.  

The following are the possible codes the server may send to the client (and any client required response):

| CODE | DESCRIPTION |
| --- | --- |
| MSG~ |  A message that should be displayed to the client will follow this code. There is no response required from this code. Examples of MSG~ are leaderboard statistics and welcome messages. Please note that messages often span multiple lines. The text that follows a MSG~ code will also be terminated by a tilde (`~`) |
| NAME~ | Following this code is text (terminated by `~`) asking for the user to enter their name. The server expects the client to respond in the form `Mary~` where Mary is the player's name.
| PLAYAGAIN~ | following this code is text (terminated by `~`) asking the client if they want another quiz question. The server expects a response to this code in the form `Y~` or `N~`, Y~ if they want another question and N~ if they do not.
| QUESTION~ |  Following this code is a multi-lined multiple choice question that terminates with `~`. The client is expected to respond with one of 0,1,2 or 3 followed by `~`.

When a client first connects to the server the server will send a MSG containing the greeting for the game. 

This will follow with a NAME message after which the server will wait for the client to send back a String containing the user's name (this reponse must be terminated with a tilde `~`). 

After these two sends (the greeting and request for the name) the server will enter in a repeating pattern as follows: 

1. The server sends a PLAYAGAIN then waits for a response. 

2. If the repsonse is Y~ then the server will send two MSG messages, the first gives the scoreboard and the second gives a message indicating the next question is being generated. 

3. This follows with a QUESTION code after which the server waits for the server response. The client should respond with a single number 0 to 4 followed by tilde. 

4. The server will respond with a MSG (the text of which indicates if the client was correct or not). Then the process repeats with the server asking if the user would like to PLAYAGAIN.

5. If the client response is not `Y~` the server will send a MSG with a good-bye text.

The following is an example back and forth between Server and Client

```txt
CLIENT connects to SERVER
SERVER sends MSG~Welcome\nTo The Game~
SERVER sends NAME~What's your name?~
CLIENT sends BETTY~
SERVER sends PLAYAGAIN~Enter Y to playagain~
CLIENT sends Y~
SERVER sends MSG~Here is the current leaderboard~
SERVER sends MSG~Wait while the next question is prepared~
SERVER sends QUESTION~The answer is 1.\nWhat is the answer?\n0)0\n1)1\n2)2\n3)3~
CLIENT sends 1~
SERVER sends MSG~Great job~
SERVER sends PLAYAGAIN~Enter Y to playagain~
CLIENT sends B~
SERVER sends MSG~Good bye~
```

You are given a .jar file that runs the above Quiz Server protocol. This allows you to test your client code without having to implement the server.


Deliverable Grading / 10:

| Gradable | Points | Comments |
| --- | --- | --- |
|Code Readability | 3 pts | Are there comments above any class and method.<br> Are variable names chosen correctly.|
|Code Design | 4 pts | Are your methods reusable?<br>Have you avoided duplicate code.<br>Have you avoided hiding String literals in the body of your methods?|
|Code Functionality| 3 pts |Does your client allow a game to be played using the provided Server.|


# Deliverable 2: LeaderBoard and Quiz Question Classes

## Deliverable 2: Due Date Friday December 4th 11:59pm

As part of your eventual Quiz Server in this stage of development you should implement 2 simple classes. The first class is a Thread-safe (that means multiple threads may try to access this at the same time) `LeaderBoard` class. A LeaderBoard will keep track of a user name and an integer (representing the number of questions they have successfully answered in a row)

Your leaderboard class should implement (at least) the following interface:

``` java

/**
  update the current correct question answered streak for
  user: name to be streaks

  @param name: the user to update the streak for
  @param streak: the amount of the user's current streak

*/
public void update (String name, int streak)  

/**
  Remove a user from the LeaderBoard
  @param name: the user to remove
**/
public void delete (String name)

/**
  Retrieve the active streak for a give user
  @param name: the user for which to retrieve the streak
  or 0 if no such user exists
  @return the current active streak for the given user or 0 if
          no such user exists
**/
public void int get(String name)

/**
  Convert the Leaderboard into an aesthetically pleasing (as per your own taste) String containing the Top 3 users with their active streaks
  @return a text version of the top 3 streaks
**/
public String prettyPrintTop3()

/
```

The second class you must implement as part of Deliverable 2 is a `MultipleChoiceQuestion` class. This class should represent a Multiple Choice Question with multiple possible choices. The choices are the numbers 0, 1, 2, 3, ...(in the order in which they are added)

As part of this deliverable your MultipleChoiceQuestion class should implement at least the following interface:

``` java

/**
  set the text for the question
  @param text: the text to use for the question
**/
public void setQuestionText(String text)


/**
  Add a choice for this multiple choice question

  @param choice the text associated with this choice (the answer text)
  @param correct whether this answer is correct or not
**/
public void addChoice(String choice, boolean correct)


/**
Return the text of the correct answer
@return the Test of the correct answer
*/
public String correct()


/**
  Determine if the given guess is correct or note
  @param guess the guess to evaluate

  @return true if guess matches to the solution
          false otherwise
**/
public boolean evaluate(int guess)


/**
Convert this multiple choice question into large
string containing the question and all possible solutions
@return a String representation of the multiple choice question
*/
public String toString()


```

As part of this deliverable you should implement the above 2 classes and provide unit-tests for both classes. Your unit tests should ensure the classes behave as designed.


Deliverable Grading /10:

| Gradable | Points | Comments |
| --- | --- | --- |
|Code readability| 3pts| (same as above)|
|Unit-tests | 7pts | Do your unit-tests offer full coverage for possible parameters and scenarios?|


# Deliverable 3 Completed Server

## Deliverable 3 Due Date: Tuesday December 8th 11:59pm

The final deliverable is part 3 - the completed server. Your server should operate at least as well as the provided example QuizServer jar file. You should ensure to use proper design choices and ensure to follow the above communication protocol. Your server should be compatible with the example provided client as well.


Grading for Deliverable 3 /20:

 Gradable | Points | Comments |
| --- | --- | --- |
|Code Readability| 6pts | All of the above code readability |
| Code Design | 6pts | as above |
| Code Functionality| 8pts | Does your server allow a completed quiz game to be played without crashing / other issues |

Overall Project Grading /40:

(Part 1 + Part 2 + Part 3)

# Demonstration

A demonstration of the client and server and how to run the jar files (in parallel) from IntelliJ can be found at the following link:

https://youtu.be/AHma1oCoP6A


