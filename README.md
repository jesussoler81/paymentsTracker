"# paymentsTracker" 


######################
# PRE-REQUISITES #####
######################

- Git, Maven and Java 8 installed and configured in your computer.
	
	
######################
# ASSUMPTIONS ########
######################

- No option -help needed. 

- In case of problems loading the file specified as a parameter, a generic error will be displayed 
warning that the file couldn't be entirely loaded.  In case of some lines in the file contain
errors, a message error will be displayed for each problematic line and no generic warning will be displayed.
The application will always run independently of errors in the parameter file.

- While reading the input from the console in case of incorrect inputs a message will be displayed.

- Not any combination of 3 upper-case letters is valid. The currencies available are limited to 
USD, GBP, CAD, AUD, EUR and CZK (In case that more currencies are needed it will be enough to add 
them to com.bsc.payments.utils.Currency enumeration). 

- The rates for the currency converter to USD are provided via property file (ratesToUSD.properties). This
file must have a rate for each currency (except for USD). In case it is not possible to load the file or some 
rate is missing, the application can not continue and will stop.

- The input amounts can have decimals

- Displaying the balance:
	Header and footer is required to the output to make it look nicer.
	The amounts will have a maximum of 10 digits, if more than 10 digits are used, the output won't be so nice formatted.
	Only one decimal is required to be displayed for amounts and 3 for converted to USD amounts

- The application is thread-safe always that the Model and View are accessed through the controller.

######################
# HOW-TO #############
######################
- How to compile the application?
 Go to Tracker folder and type:
  > mvn clean install

- How to run the application from the command line?
 After compiling go to Tracker folder and type (fileToBeLoaded is an optional parameter):
  > java -cp target\Tracker-1.0-SNAPSHOT.jar com.bsc.payments.Tracker fileToBeLoaded
  
- src/test/resources contains 3 example input files.


