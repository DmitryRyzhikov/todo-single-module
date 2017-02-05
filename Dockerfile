FROM java:openjdk-8u91-jdk
CMD java ${JAVA_OPTS} -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5005,suspend=n -jar eventuate-examples-java-spring-todo-list-single-module.jar
COPY build/libs/eventuate-examples-java-spring-todo-list-single-module.jar .
