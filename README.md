# targil4

This is exercise 4 in Advanced Programming 2 course.

Friends is a simple chat application on android between users.
The app allows users to register and create an account, log in, and start conversations with other registered users.

There are 4 pages:
![image](https://github.com/EladSoffer/targil4/assets/116814174/92ebd3a9-7cb5-4886-9a1e-906ec603e860)

Register page- 

The register page is where users can create a new account by providing their information and clicking the submit button.
The password must contain at least one uppercase letter and be at least 8 characters long. If a user already has an account, they can click the "Click here" button to navigate to the login page.

login page- 

The login page allows registered users to log in to their accounts by entering their credentials and clicking the submit button.
Only after entering the correct username and password, the user will be redirected to the chat page.
If a user doesn't have an account yet, they can click the "Click here" button to navigate to the registration page.

contacts page=

Users can add new conversations by clicking the "Add Contact" button and initiating a conversation with another registered user.
Users can switch between conversations by selecting the person they want to talk to.
To log out, users can click the "Logout" button located at the top-left corner of the screen.

chat page-

The chat page is where users can engage in real-time conversations with their friends.
The interface displays the conversation history, including sent and received messages,
with timestamps indicating when the messages were sent or received.

How to Run
Clone the repository.

Open a terminal and navigate to the root directory of the project.

Open any browser and navigate to http://localhost:5000, where you can register or log in to start chatting with your friends.

Database
Friends uses MongoDB as its database to store user information and chat data. Make sure you have MongoDB installed on your system and running before starting the application.

MongoDB Setup

To use MongoDB with the Friends chat application, please follow these steps to set up your MongoDB database:

Install MongoDB: Visit the MongoDB website and download the latest version of MongoDB for your operating system. Follow the installation instructions provided for your specific operating system.

Start MongoDB: Once MongoDB is installed, you need to start the MongoDB service. The process for starting the service may vary depending on your operating system. Here are a few common commands:

Windows: Open a command prompt as an administrator and run the following command: mongod

Mac/Linux: Open a terminal and run the following command: sudo service mongod start
