# temperature-server

A Simple Web Server for collecting and displaying temperature data

Requirements for running:

1. Java 17
2. maria-db 10.6.4

Instructions for running:

1. Clone this repository
2. Setup local database using the given sql dump file at src/main/resources
3. Fill in the correct user and password for mysql in application.properties
4. Navigate into this project
5. Run both the folllowing commands in the terminal
6. Run command "mvn clean install"
7. Run command "java -jar target/temperature-server-0.0.jar"
8. Hit website at http://localhost:8080 to see the temperature map

Goals:

Short term: To statistically model the impact of global warming caused by collections of human complex by using individual temperature measurements and correlating them with their contribution to global warming.

Long term: In the long term we envisage that every individual will be required to financially compensate for his effect on global warming. We propose that the information generated and produced by analysing the data from this system can be used to reward net heat absorbers with reference to the goals set by the various international organisation (such as the Paris Accords, COP26 to name a few). This application will become regulatory in nature and set clear goals for an individual that he can measure and comprehend as opposed to setting abstract goals for a large amount of people and/or countries.
We intend to take the fight against global warming starting from our very homes.