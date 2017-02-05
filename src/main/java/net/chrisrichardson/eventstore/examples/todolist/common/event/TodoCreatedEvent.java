package net.chrisrichardson.eventstore.examples.todolist.common.event;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;

public class TodoCreatedEvent implements TodoEvent {

    TodoInfo todo;

    private TodoCreatedEvent() {
    }

    public TodoCreatedEvent(TodoInfo todo) {
        this.todo = todo;
    }

    public TodoInfo getTodo() {
        return todo;
    }

    public void setTodo(TodoInfo todo) {
        this.todo = todo;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
