package com.simakov.diploma.Repository;

import com.simakov.diploma.Model.Promo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PromoRepository extends JpaRepository<Promo, Long> {
}
