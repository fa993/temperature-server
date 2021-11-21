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
