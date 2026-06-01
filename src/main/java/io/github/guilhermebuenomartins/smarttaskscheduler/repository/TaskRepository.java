package io.github.guilhermebuenomartins.smarttaskscheduler.repository;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.guilhermebuenomartins.smarttaskscheduler.model.Status;
import io.github.guilhermebuenomartins.smarttaskscheduler.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    Page<Task> findByStatusIn(Set<Status> statuses, Pageable pageable);
}
