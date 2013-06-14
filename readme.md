## Getting Started

To bootstrap with an eclipse project:

    # setup eclipse project
    ./gradlew eclipse

Then, from within Eclipse, you can first import that project into your workspace and apply 
the Gradle nature:

 * Right-click on the project
 * Select **Configure** > **Convert to Gradle Project**

Project should build and library references and such updated.

### Note

Gradle project handling within Eclipse requires the [Gradle plugin]()

### Note

You may also directly import the Gradle project from **File** > **Import** > **Gradle** > **Gradle Project**
java -Dcom.sun.management.jmxremote -Xdebug -Xnoagent -server -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n -jar /Users/pdeschen/git/nuecho/dev/modules/thirdparty/jetty-runner/lib/jetty-runner-7.0.2.v20100331.jar --path hello-world build/libs/hello-world.war 
