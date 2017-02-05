package net.chrisrichardson.eventstore.examples.todolist.common.event;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TodoDeletedEvent implements TodoEvent {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
