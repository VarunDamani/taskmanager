package com.worldline.taskmanager.repository;

import com.worldline.taskmanager.entity.TaskGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroupEntity, Long> {
}
