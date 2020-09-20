package ru.iworking.personnel.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iworking.personnel.reserve.entity.WorkType;

@Repository
public interface WorkTypeRepository extends JpaRepository<WorkType, Long> { }
