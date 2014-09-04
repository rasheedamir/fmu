##Project FMU - Fördjupade Medicinska Utredningar

##JIRA Link:
[Inera FMU](https://inera-certificate.atlassian.net/browse/FMU)

##Architecture Link:
[FMU Architecture](https://drive.google.com/file/d/0B_H5gem6D0BZd3dwTGdVZWlNeWs)

##Technology Stack:

###Backend (Server Side)

1. Language(s): _Java 1.7_,_Groovy_
2. Production Database: _MySql 5.6.4+_
3. Test Database: _H2_
4. Dependency & Build Management: _Maven 3.0_
5. Database Migration Tool: _Liquibase 3_
6. Logging Abstraction: _SFL4J_
7. Logging Implementation: _logback_
8. Unit Testing: _JUnit 4_ — The de-facto standard for unit testing Java applications.
9. Persistence (Data Access): _JPA_, _Hibernate 4_ (One of the most popular JPA implementations.), _Spring Data JPA_ (Makes it easy to easily implement JPA based repositories), _Spring ORMs_ (Core ORM support from the Spring Framework)
10. BPM: _Activiti 5.15_
11. Mocking: _Mockito_
12. Application Server (embedded): _Tomcat_
13. Object to JSON Mapping: _Jackson_
14. Date Time Utility: _Joda-Time_
15. Assertion Library: _Hamcrest_ - A library of matcher objects (also known as constraints or predicates) allowing assertThat style JUnit assertions.
16. Integration Test Support: _Spring Test_ — integration test support for Spring applications.
17. JDBC Connection Pool: _HikariCP_ - [HikariCP](http://brettwooldridge.github.io/HikariCP/) is a high performance JDBC connection pool.
18. Properties file format: _YAML_
19. @ToString: [_lombok_](http://projectlombok.org/)
20. AOP: _Spring AOP_ Logging, Transactions, 
21. Profiles: _Maven & Spring Profiles_
26. Local Caching: [_Ehcache_](http://ehcache.org/)
27. i18n: 
28. Templating Engine: thymeleaf (if a single Web page application isn't enough for our needs)
29. [_Spring Boot_](http://projects.spring.io/spring-boot/): For easy configuration
30. Security: [_Spring Security_](http://docs.spring.io/spring-security/site/index.html)
31. Mail: 

###Frontend (Client Side)

Single Web Page Application (SPA)

1. Responsive Web Design!
2. [HTML5 Boilerplate](http://html5boilerplate.com/)
3. [Twitter Bootstrap](http://getbootstrap.com/)
4. [AngularJS](http://angularjs.org/)
5. Build, Optimization & Live Reload: [_Grunt_](http://gruntjs.com/) : is the most widely used and most mature build tool for your JavaScript and CSS assets
6. Testing: [_Karma_](http://karma-runner.github.io/) & [_PhantomJS_](http://phantomjs.org/)
7. Dependency Management: [_Bower_](http://bower.io/)
8. Full internationalization support with [_Angular Translate_](https://github.com/angular-translate/angular-translate)
9. Protractor
10. 

###Other

1. Source Code Management: _Git_
2. IDE: _IntelliJ (Recommended), STS, Eclipse Juno (Must for Activiti) or NetBeans_
3. REST API Documentation: [_Swagger_](https://helloreverb.com/developers/swagger)
4. Monitoring: [_Metrics_](http://metrics.codahale.com/)

###Code Base
1. Download and install Git.
2. Right the folder which you want to be the home for the codebase, and choose Git Bash.(For integration of IntelliJ with Git, refer to  the section below _Setting Up the Environment->IntelliJ_ point 4 onward)
3. Paste `git clone https://github.com/rasheedamir/fmu.git`
4. Give the password (if) prompted.
    
###Project Structure

  1. `src\integration-test` - Specifically for integration tests!
      - `src\integration-test\java`    

      - `src\integration-test\resources`   
    
  2. `src\main`
      -  `src\main\java`    
      
      -  `src\main\resources`   
      
      -  `src\main\webapp` 

  3. `src\test` - Pure unit tests only!
      -  `src\test\java`    
      
      -  `src\test\javascript`   
      
      -  `src\test\resources`       

##Setting up the Environment:

###- JAVA

- Ubuntu (Manually):

1. Create "Tools" folder in your home directory. (If not present already)
2. Create "Java" folder inside "Tools" folder.
3. Go to oracle website to download [_Java 7_] (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html). Download the binary (usually its a zip file) of required version. [I downloaded jdk-7u67-linux-x64.tar.gz so, I will use it in upcoming steps]
4. Copy the downloaded file and paste it in "Java" folder. Then right click and extract. Delete the zip file; its not required anymore now
5. Create a symlink at _"~/Tools/java"_ and that link points to one of the specific Java version directories. Run this command (open terminal CTR+ALT+T): _ln -s ~/Tools/Java/jdk1.7.0_ _ _67 ~/Tools/java_
6. From command terminal run: _nano .bashrc_ The .bashrc file will open in editor. (Please note this file is usually hidden in the home folder.)
7. Update the JAVA_HOME to point the symlink
I update the ".bashrc" file in my home folder. And I do this: (The file has been opened above).
export JAVA_HOME=~/Tools/java
export PATH=$JAVA_HOME/bin:$PATH
CTRL+O & then CTRL+X
8. Add different aliases for the versions you have to first remove existing symlink and then create new symlink.
E.g. for Java I have defined following aliases in my .bashrc file:
_alias java7='rm ~/Tools/java && ln -s ~/Tools/Java/jdk1.7.0_ _ _67 ~/Tools/java'_
Close terminal and open again!
9. To switch the version just open the terminal and type: java7 and you are done! This way you can switch to different versions.
10.  To verify the installation of Java. Run this command on terminal: _java -version_

###- Maven

- Ubuntu (Manually)

1. Create "Tools" folder in your home directory. (If not present already)
2. Create "Maven" folder inside "Tools" folder.
3. Download [_Maven_](http://maven.apache.org/download.cgi). Download the binary (usually its a zip file) of required version. [I downloaded apache-maven-3.2.3 so, I will use it in upcoming steps]
4. Copy the downloaded file and paste it in "Maven" folder. Then right click and extract. Delete the zip file; its not required anymore now.
4. Create a symlink at _"~/Tools/Maven"_ and that link points to one of the specific Maven version directories. Run this command (open terminal CTR+ALT+T): _ln -s ~/Tools/Maven/apache-maven-3.2.1 ~/Tools/maven_
5. From command terminal run: nano .bashrc The .bashrc file will open in editor. (Please note this file is usually hidden in the home folder.)
6. Update the MAVEN_HOME to point the symlink
I update the ".bashrc" file in my home folder. And I do this: (The file has been opened above).
export MAVEN_HOME=~/Tools/maven
export PATH=$MAVEN_HOME/bin:$PATH
CTRL+O & then CTRL+X
7. Add different aliases for the versions you have to first remove existing symlink and then create new symlink. E.g. for Maven I have defined following aliases in my .bashrc file:
alias mvn323='rm ~/Tools/maven && ln -s ~/Tools/Maven/apache-maven-3.2.3 ~/Tools/maven'
8. To switch the version just open the terminal and type: mvn323 or mvn305 and you are done! This way you can switch to different versions.
9.  To verify the installation of Maven. Run this command on terminal:
mvn -version

###- IntelliJ

1. Install an IDE. Here we will assume the usage of IntelliJ. Download [IntelliJ IDEA](http://www.jetbrains.com/idea/download/index.html). Ultimate version needs to be bought. Install IntelliJ.
2. Configure Groovy in IntelliJ.
    - At startup after IntelliJ installation, add support for the plugin of Grails.
    - Otherwise, you can later change it from _File -> Settings_. Then choose plugins, and check _Grails_.
3. Enable Git in IntelliJ, as mentioned in the step above.
4. After that, go to _Project->Settings_, under _Version Control_, click Git. On the right hand panel, you need to give the address to the git executable, named _git.exe_, present in `.../Git/bin/`.
5. For the ssh drop down right below the executable field, its preferable to choose "Built In".
6. For more information on this issue, refer to [jetbrains website](http://www.jetbrains.com/idea/webhelp/using-git-integration.html).
7. For projects being developed on cross-platform operatins systems, windows uses CRLF line endings(a format) and Linux, OS X use LF line ending format. If not taken care of these line endings will be changed from one format to the other, causing in merge conflicts. There are 2 possible solutions:
    - If using from Git, you need to change the 'core.autocrlf' property in the Git config to 'true'(for Windows) or to 'input' in case of Linux.
    - From IntelliJ itself wth Git integrated, you need to place an xml file here: `.IntelliJIdea12\config\codestyles\Default _1_.xml`, which contains policies for the commiting.

More info on this topic can be found [here](http://stackoverflow.com/questions/3206843/how-line-ending-conversions-work-with-git-core-autocrlf-between-different-operat)

###- Eclipse (for Activiti Diagram Only)

#### Install Eclipse Juno

In addition to installing the maven package and the eclipse package, and all their dependencies, you need to install the m2e extension. The best way to do this is using the Eclipse Marketplace, but the marketplace is not installed by default in the Ubuntu package.

#### Install the Eclipse Marketplace
1. Open Eclipse, go to Help -> Install New Software...
2. Select All Available Sites in the Work with dropdown menu.
3. Wait for the list of software to populate; sometimes it takes a very long time.
4. Expand the General Purpose Tools group, and tick Marketplace Client.
5. Click Next, and again, accept the terms and conditions and click Finish.

This will install the marketplace. You will need to restart _Eclipse_ for the change to take effect.

#### Install m2e
1. Open Eclipse, go to Help -> Eclipse Marketplace...
2. Wait for it to finish loading.
3. Enter "maven" in the search box and press return.
4. Click the Install button next to Maven Integration for Eclipse by Eclipse.org (NB: there is a similar item above called "Maven Integration for Eclipse WTP" by Red Hat, Inc; this is not the correct one).

Again, you will need to restart _Eclipse_ for this to take effect.

#### Install Activiti BPMN 2.0 Designer 
The following installation instructions are verified on Eclipse Juno.

Go to Help -> Install New Software. In the following panel, click on Add button and fill in the following fields:

Name: Activiti BPMN 2.0 designer
Location: http://activiti.org/designer/update/

Make sure the "Contact all updates sites.." checkbox is checked, because all the necessary plugins will then be downloaded by Eclipse.

###- Git
- Window users:

1. Download latest version of git
    - [msgit for windows](https://code.google.com/p/msysgit/downloads/list?q=full+installer+official+git)
2. Select point 3 _Run git and included unix tools from the windows command prompt_, when needed. _Path_ will be updated during installation.
3. Open console and check `git --version`. The result should be like `git version 1.9.0.msysgit.0`.
4. If git installation successful, generate ssh keys and add it to GitHub/Bitbucket account, follow to the [Bitbuckt official guide](https://confluence.atlassian.com/display/BITBUCKET/Set+up+SSH+for+Git).

- Ubuntu/Debian users:

1. Git Core: `sudo apt-get install git`
2. Git GUI:  `sudo apt-get install git-gui`

IMPORTANT! For projects being developed on cross-platform operating systems, windows uses CRLF line endings(a format) and Linux, OS X use LF line ending format. If not taken care of these line endings will be changed from one format to the other, causing in merge conflicts. You need to change the `core.autocrlf` property in the Git config to `true`(for Windows) or to `input` in case of Linux.

On Ubuntu you can find the ".gitconfig" under the home directory. It's usually hidden.

###- Node

Node.js is a Javascript platform for server-side programming that allows users to build network applications quickly. In most cases, you'll also want to also install npm, which is the Node.js package manager. This will allow you to easily install modules and packages to use with Node.js.

- Ubuntu 14.04:

An alternative that can get you a more recent version of Node.js is to add a PPA (personal package archive) maintained by Chris Lea. This will probably have more up-to-date versions of Node.js than the official Ubuntu repositories.

1. First, you need to install the PPA in order to get access to its contents: `sudo add-apt-repository ppa:chris-lea/node.js`
2. The PPA will be added to your configuration. However, you still need to update your local package cache for your server to become aware of the new packages:  `sudo apt-get update`
3. After that, you can install the Node.js package: `sudo apt-get install nodejs` and you can verify it by running `node --version`
4. You'll probably want to install npm as well: `sudo apt-get install npm` and you can verify it by running `npm --version`

- Windows:

1. If you don't already have Node.js installed, download the latest [Node.js](http://nodejs.org/download/) (if you use the .msi installer a reboot might be required for the npm to be registerd correctly)
2. Open a command line prompt and run the following commands
    - `npm update -g npm` , to ensure that you have the latest version of npm

###- Bower

- Ubuntu 14.04:

1. `sudo npm install -g bower`
2. Now verify the version by running `bower --version`

- Windows:

1. `npm install -g bower` ,`to install Bower. Make sure that Git is installed prior becasue Bower is dependent on it
2. Now verify the version by running `bower --version`    

###- Grunt

- Ubuntu 14.04:

1. `npm install -g grunt-cli`
2. Now verify the version by running `grunt --version`

- Windows:

1. `npm install -g grunt-cli` , to install the Grunt's command line interface (CLI) and put it on the system path
2. Now verify the version by running `grunt --version`

###- MySql
- Window users:

1. Download latest version of [MySql community server](http://dev.mysql.com/downloads/mysql/)
2. Run `.exe` or `.msi` file and follow the instructions.
3. Select _Developer default_.
4. Specify password for _root_ user.

Follow [this guide](http://www.mysqltutorial.org/install-mysql/), if there is any questions.

- Ubuntu/Debian users: `sudo apt-get install mysql-server mysql-client`

#### Database Setup

  1. Create a new database named `fmu`
  2. MySql Settings:
      - username = `root`,
      - password = `root`,
      - host = `localhost`,
      - port = `3306`.
  3. Open the terminal and type `mysql -u root -p`
  4. Enter the password when prompted `root`
  5. Create database `CREATE DATABASE fmu CHARACTER SET utf8 COLLATE utf8_general_ci;` (The output should be "Query OK, 1 row affected")

In case you need to drop existing database; `DROP DATABASE fmu;`

HikariCP MySQL recommended settings can be found here: `https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration`

###- kdiff3

- Ubuntu/Debian users

1. It's helps when you need to merge conflicting files! watch some youtube video to learn how to use it! 
2. Go to [kdiff3](https://apps.ubuntu.com/cat/applications/kdiff3/)
3. Select your ubuntu version from the left
4. Then simply click the install button!
5. Change merge tool in "Git Gui" options; Edit -> Options...

###- dbVisualizer

##Logging

###Log file location
  - Application logs can be found here: `fmu/logs`

###Log configuration
  - For unit testing log configuration can be found here:  `fmu/src/test/resources/logback-test.xml`
  - For production log configuration can be found here:  `fmu/src/main/resources/logback.xml`
      
##TroubleShooting
  - There may be errors while running the application:
  - 

##Generating Liquibase ChangeLog

`mvn resources:resources liquibase:update -P<profile_name>`
e.g.
`mvn resources:resources liquibase:diff -Pprod`

Invoking the resources is necessary in order to have the liquibase.properties placeholders filtered. The -P option tells Maven the profile to use and thus the set of values (from the filter properties file) to use for filtering.

##Running/Debugging the Application

###Eclipse

####Eclipse set and forget run configurations
Right-click on the `project folder -> Run as ->`
From here there should be many different Maven run configurations but if you want to add your own configurations or run any Maven commands.

#####Create ´dev´ run configuration
1. Right-click on the `project folder -> Run as`
2. click `Run Configurations`
2. fill in a name, lets call it fmu-dev in the `Name` field
3. browse to the correct `Base directory`, it should be something like `${workspace_loc:/fmu}`
4. fill in `spring-boot:run -Pdev` maven command under `Goals`
5. fill in ``dev` under `Profiles` and Click Run.

Now the dev run configuration should be available directly from the dropdown menu at the green triangle `Run` icon.

Repeat the same step for other profiles if needed.

####Increase permGem size while building with Maven
Maven is currently having issues with memory. To increase the pemGem memory size include this VM arguments `XX:MaxPermSize=512m` in the `run-configuration -> JRE`. 512m can be adjusted to any number you see fit

###IntelliJ

###Add _'resources'_ directory to classpath in IntelliJ 13
1. Click on the Project view or unhide it by clicking on the "1: Project" button on the left border of the window or by pressing Alt + 1
2. Find your project or sub-module and click on it to highlight it, then press F4, or right click and choose "Open Module Settings"
3. Click on the dependencies tab
4. Click the "+" button on the right and select "Jars or directories..."
5. Find your path and click OK
6. In the dialog with "Choose Categories of Selected File", choose classes (even if it's properties), press OK and OK again

You can now run your application and it will have the selected path in the classpath.

###As a "main" Java class
1. Go to `Run` then `Edit Configurations...`
2. Then click on `+` sign on the top left corner then select `Application`
3. Give name `Application` (or whatever you like)
4. Main class `se.inera.fmu.Application`
5. VM Options `-XX:MaxPermSize=256M` (this is must otherwise you might run in to PermGem or OutOfMemory issues)

From your IDE, right-click on the `se.inera.fmu.Application` class at the root of your Java package hierarchy, and run it directly. You should also be able to debug it as easily.

The application will be available on `http://localhost:8080`.
Note: The default profile is `dev` so, it will run on port `8080`

###Console

###Deploy frontend only (Grunt)
Since front end code does not depend on back-end code the app can be built and run by itself using grunt. The following commands should be used at the project root folder when building front end resources:
1. `npm install` to fetch all packages specified in the Package.json file
2. `bower install` to fetch all packages specified in the bower.json file
3. `grunt` to run all grunt tasks to make sure nothing breaks

When invoking these commands they should be invoked in this order. 

There is also `grunt shell:assemble` command to run all these commands at once.

###Live-coding
When developing front-end app live-coding could be a useful feature to use while developing the UI. 
use `grunt server` to start live-coding, all changes made to the .html, .js, .css files will be loaded and refreshed automatically by grunt. 

###Deploy both frontend & backend (Maven)
You can launch the Java server with Maven by running following command at the project root folder:

`mvn spring-boot:run -P<profile_name>`
e.g.
`mvn spring-boot:run -Pprod -Drun.jvmArguments="-XX:MaxPermSize=256M"`

The application will be available on `http://localhost:9090`

Please note that due to `PerGem` & `OutOfMemory` issues on few systems we have to pass this extra argument when running through maven: `-Drun.jvmArguments="-XX:MaxPermSize=256M"`

If you want more information on using Maven, please go to `http://maven.apache.org`

##Profiles

fmu comes with three "profiles":

  - _"dev"_ for development: it focuses on ease of development and productivity
  - _"integration-test"_ for integration-test: ...
  - _"prod"_ for production: it focuses on performance and scalability  

Those profiles come in two different configurations:

1. The _Maven_ profiles are used at build time. For example mvn -Pprod package will package a production application.
2. The _Spring_ profiles work a run time. Some Spring beans will behave differently, depending on the profile.

Spring profiles are set by Maven, so we have a consistency between the two methods: of course, we have a "prod" profile on _Maven_ and _Spring_ at the same time.

###dev
In default mode, fmu will use the `"dev"` profile
If you run the application without Maven, launch the "Application" class (you can probably run it easily from your IDE by right-clicking on it).

If you run the application with Maven, run `mvn -Pdev spring-boot:run -Drun.jvmArguments="-XX:MaxPermSize=256M"`

###prod
In production, fmu has to run with the `"prod"` profile
Use Maven to build the application with the "prod" profile: `mvn -Pprod spring-boot:run -Drun.jvmArguments="-XX:MaxPermSize=256M"`

##Testing
###Unit Tests (Java)
We use the Surefire Maven plugin to run our unit tests. Run following command to run unit tests:

`mvn clean test`

During development unit test's can be run from within the IDE.

###Integration Tests (Java)
The Failsafe Maven plugin is used to execute our integration tests. Run following command to run integration tests:
 
`mvn clean verify -P integration-test`

###Unit Tests (JavaScript)
Front end unit tests can be run directly using command line at the project folder.
Use `grunt karma` for unit testing 

###E2E tests
Front end e2e tests can be run using `grunt protractor:singlerun` command. Make sure the rest server is active while running these tests.

####Run seperate test cases in the test specs
Use
 `ddescribe()` or `iit()` instead of `describe()` and `it()` to single out the test cases you want to run

##Authentication

Cookie-Based Authentication (Session)

##Endpoints

Spring-Boot Actuator endpoints allow you to monitor and interact with your application. Spring Boot includes a number of built-in endpoints and you can also add your own. For example the health endpoint provides basic application `health` information.

The way that endpoints are exposed will depend on the type of technology that you choose. Most applications choose HTTP monitoring, where the ID of the endpoint is mapped to a URL. For example, by default, the `health` endpoint will be mapped to `/health`.

Here you can see list of [endpoints](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints)

##H2 Console

When running application in "dev" mode then H2 Console can be accessed through the URL: `http://localhost:8080/console`
For database URL use: `jdbc:h2:mem:fmu`

##Plugins

###EditorConfig
EditorConfig helps developers define and maintain consistent coding styles between different editors and IDEs. Read more [here](http://editorconfig.org/)