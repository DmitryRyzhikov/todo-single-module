# Useful information
* [Website](http://eventuate.io) and [Documentation](http://eventuate.io/whyeventdriven.html)
* [Original source code](https://github.com/eventuate-examples/eventuate-examples-java-spring-todo-list)
* Set variable [DOCKER_HOST_IP](http://eventuate.io/docs/usingdocker.html)

# Check virtual machine ID (to set it as DOCKER_HOST_IP)
    docker-machine ip

# For standalone
Check is DOCKER_HOST_IP is set:

    echo $DOCKER_HOST_IP

Set DOCKER_HOST, build and run application. Line about export DOCKER_HOST_IP saves IP of running docker machine
into variable DOCKER_HOST_IP

    ./gradlew assemble -P eventuateDriver=local
    export DOCKER_HOST_IP=$(docker-machine ip)
    docker-compose -f docker-compose-eventuate-local.yml build
    docker-compose -f docker-compose-eventuate-local.yml up -d

# Check logs during app start
Get ID of container we want to get log for (in this example ID of container with name todosinglemodule_standaloneservice)

    docker ps

Check log of appropriate container

    docker logs [CONTAINER_ID]


# Accessing application (use real IP of docker-machine)

    http://192.168.99.100:8080/swagger-ui.html

# How it works

To work with Eventuate we should create:
 * Commands that contain information about what should be done. Example: CreateTodoCommand

 * Event sourcing-based aggregates that process commands and emit domain events. It is possible
  to emit more than one event in course of command processing. Example of aggregate responsible
  for processing create command TodoAggregate.process(..):


        public List<Event> process(CreateTodoCommand cmd) {
            if (this.deleted) {
                return Collections.emptyList();
            }
            return EventUtil.events(new TodoCreatedEvent(cmd.getTodo()));
        }

  Aggregates are created, stored and updated by AggregateRepository, that should be configured and injected
  as Spring bean in any necessary service. Check TodoBackendConfiguration:


        @Bean
        public AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository(EventuateAggregateStore eventStore) {
            return new AggregateRepository<>(TodoAggregate.class, eventStore);
        }
 * Services that are invoked by external requests and update aggregates by sending them commands. Example: TodoService
 * Event handlers that subscribe to domain events and do one of three things:
    - Update aggregates
    - Update materialized views
    - Invoke external systems

 Example: TodoQueryWorkflow.create(...) - that listens for TodoCreatedEvent and then actually creates ToDo objects
  based on info extracted from event. Nice thing here that any number of event handlers can listen for the same
  event and react accordingly.



## Create
 * Access http://192.168.99.100:8080/swagger-ui.html and fire POST request todo-controller/todos to create ToDo obj.
 * Request goes to TodoController.saveTodo(...) -> TodoService.save(...) where method


        aggregateRepository.save(new CreateTodoCommand(todo));
 is called. After this method completion new TodoCreatedEvent will be persisted to store.
 * There are two event listeners, that will react on this persisted event. This is TodoQueryWorkflow that will
  actually saves ToDo object to local DB and TodoLogWorkflow that will just log the event.

