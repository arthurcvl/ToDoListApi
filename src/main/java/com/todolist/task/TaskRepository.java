package com.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    //TODO -> "Criar" os findByName, findByZZZ
}
