package io.github.guilhermebuenomartins.smarttaskscheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.guilhermebuenomartins.smarttaskscheduler.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

}
