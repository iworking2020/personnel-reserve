package ru.iworking.personnel.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iworking.personnel.reserve.entity.ResumeState;

@Repository
public interface ResumeStateRepository extends JpaRepository<ResumeState, Long> { }
