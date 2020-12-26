package ru.iworking.personnel.reserve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iworking.personnel.reserve.entity.ApplicationProperty;

import java.util.List;

@Repository
public interface ApplicationPropertiesRepository extends JpaRepository<ApplicationProperty, Long> {

    List<ApplicationProperty> findAllByName(String name);

}
