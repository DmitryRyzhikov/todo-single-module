package net.chrisrichardson.eventstore.examples.todolist.backend;

import io.eventuate.DispatchedEvent;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoCreatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoUpdatedEvent;

@EventSubscriber(id = "todoLogSideEventHandlers")
public class TodoLogWorkflow {

    private TodoQueryService todoQueryService;

    public TodoLogWorkflow(TodoQueryService todoQueryService) {
        this.todoQueryService = todoQueryService;
    }

    @EventHandlerMethod
    public void create(DispatchedEvent<TodoCreatedEvent> de) {
        System.out.println("@@@Todo created " + de.getEvent().toString());
    }

    @EventHandlerMethod
    public void delete(DispatchedEvent<TodoDeletedEvent> de) {
        System.out.println("@@@Todo deleted " + de.getEvent().toString());
    }

    @EventHandlerMethod
    public void update(DispatchedEvent<TodoUpdatedEvent> de) {
        System.out.println("@@@Todo updated " + de.getEvent().toString());
    }
}
