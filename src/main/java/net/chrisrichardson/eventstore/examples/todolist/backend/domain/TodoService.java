package net.chrisrichardson.eventstore.examples.todolist.backend.domain;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.eventuate.AggregateRepository;
import io.eventuate.EntityWithIdAndVersion;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.CreateTodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.DeleteTodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.DeleteTodosCommand;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.TodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.UpdateTodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;

public class TodoService {

    private final AggregateRepository<TodoAggregate, TodoCommand> aggregateRepository;
    private final AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository;

    public TodoService(AggregateRepository<TodoAggregate, TodoCommand> todoRepository,
            AggregateRepository<TodoBulkDeleteAggregate, TodoCommand> bulkDeleteAggregateRepository) {
        this.aggregateRepository = todoRepository;
        this.bulkDeleteAggregateRepository = bulkDeleteAggregateRepository;
    }

    public CompletableFuture<EntityWithIdAndVersion<TodoAggregate>> save(TodoInfo todo) {
        return aggregateRepository.save(new CreateTodoCommand(todo));
    }

    public CompletableFuture<EntityWithIdAndVersion<TodoAggregate>> remove(String id) {
        return aggregateRepository.update(id, new DeleteTodoCommand());
    }

    public CompletableFuture<EntityWithIdAndVersion<TodoAggregate>> update(String id, TodoInfo newTodo) {
        return aggregateRepository.update(id, new UpdateTodoCommand(id, newTodo));
    }

    public CompletableFuture<EntityWithIdAndVersion<TodoBulkDeleteAggregate>> deleteAll(List<String> ids) {
        return bulkDeleteAggregateRepository.save(new DeleteTodosCommand(ids));
    }
}
