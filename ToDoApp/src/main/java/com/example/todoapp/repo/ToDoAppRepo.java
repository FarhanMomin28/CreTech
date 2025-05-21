package com.example.todoapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.todoapp.model.ToDo;

@Repository
public interface ToDoAppRepo extends JpaRepository<ToDo, Long>{

}
