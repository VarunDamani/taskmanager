package com.worldline.taskmanager.repository;

import com.worldline.taskmanager.entity.TaskGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroupEntity, Long> {
    
    @Query("SELECT tg FROM TaskGroupEntity tg LEFT JOIN FETCH tg.tasks")
    List<TaskGroupEntity> findAllWithTasks();
}
