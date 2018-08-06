# First Catering Ltd: Employee Card API

This API can be used by employees of Bows Formula One to register their existing employee cards, top them up with credit and then spend that credit in their office.

Bows Formula One will be supplying touch screen PC's that will act as the front end for this system.
From those PC's a user will be able to register their employee cards, top up the cards and see their transaction history.
Bows will also have scanners at each till so that customers can scan their cards to make purchases at the tills.

# Setup 
  
There are a few steps that need to be done before this API can be used.
  1. Clone this repository
  2. Make sure MySQL is set up on your machine.
  3. Open the project and let Gradle download all the dependencies.
  4. Run the application using either your IDE or from the command line.
  5. When the application is running requests to the API can be made to ``` http://localhost:8888/ ```
  all further URI's for the requests can be found in this documentation.
      https://firstcateringltdblueprint.docs.apiary.io/#
   
**Implementation**

   Employees of Bows Formula one will be able to registers their existing employee card once they have given the following information.

   * Employee ID
   * Name
   * Email
   * Mobile Number
   * Credit or Debit card number
   * Pin number


  Once a user has registered themselves, they will be able to log in with their employee id and pin number,
  this request will be sent to ``` http://localhost:8888/authentication ``` with the request body holding their employee id       and pin.
  When the user has been authenticated against the database a JWT will be constructed for that user.
  The JWT should be put into the header 'X-AUTHORIZATION', this header should then be sent with all subsequent requests.



**Manual Testing**

Once the API is up and running it is a good idea to test out a few request and make sure you are familiar with the different requests.
Using postman you can hit all the URI's you need to.

![screen shot 2018-08-06 at 15 07 02](https://user-images.githubusercontent.com/22473649/43721507-e2f9ee76-998a-11e8-83d7-61423558ed1d.png)


**Improvements**

Ideally in the future the authentication section of this project will be moved out into another micro-service that the touch screen PC can hit,
the reason for this is to separate out the business logic from the security side to keep the code base simpler.

Being able to freeze a customers card if they loose it would be a useful feature,
as it would mean that it could be unfrozen if they find it and then they would not have to re-register themselves.

An auto top up system would be another useful feature to ensure that customers always have money in their account.


