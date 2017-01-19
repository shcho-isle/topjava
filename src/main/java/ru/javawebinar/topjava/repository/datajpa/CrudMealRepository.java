package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * gkislin
 * 02.10.2016
 */
@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Override
    @Transactional
    Meal save(Meal meal);

    Meal findByIdAndUserId(int id, int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=?1 AND m.user.id=?2")
    int delete(int id, int userId);

    @Modifying
    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC")
    List<Meal> findAll(@Param("userId") int userId);

    @Modifying
    @Query("SELECT m FROM Meal m WHERE m.user.id=?3 AND m.dateTime BETWEEN ?1 AND ?2 ORDER BY m.dateTime DESC")
    List<Meal> findBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
