package ru.iworking.personnel.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iworking.personnel.reserve.entity.Company;
import ru.iworking.personnel.reserve.entity.Vacancy;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findAllByCompany(Company company);
}
