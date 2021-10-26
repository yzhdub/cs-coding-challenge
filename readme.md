# Spring Batch


### Environment:

- Java 8 ( code compile level )
  
- Maven 3.8

- Intellij Community IDE

- HyperSQL 2.6.0 ( running with JDK11 )

### Assumptions / Constraints:

1. Json object available in a single line, not crossing multiple lines
   
2. ID is String value, not in UUID

3. Not enough unit tests provided due to time restriction, I tried to finish all within 2 hrs.

### Pre-build

Please update datasource URL based on your environment:
spring.datasource.url=jdbc:hsqldb:hsql://localhost/mydb2

If you are using HSQLDB, please start it before execute the application.
Or you can use the in-memory version by removing all from application.properties

### Build:

$ mvn clean package

### Execution:

$ java -jar target\cs-server-log-processing-0.0.1-SNAPSHOT.jar filepath=<Location_of_log_file>

ie.
$ java -jar target\cs-server-log-processing-0.0.1-SNAPSHOT.jar filepath=d:\CS\cs-coding\src\main\resources\logfile.txt

### Log

Log will be available on console and also will be appended to cs-app-log.txt in the project root directory

### Screenshots:

Build from command line:

![img.png](img1-build.png)

Execution from command line:

![img.png](img2-execution.png)

Database result after execution:

![img.png](img3-dbresult.png)
