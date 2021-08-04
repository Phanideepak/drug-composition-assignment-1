package com.licious.app.repository;

import com.licious.app.model.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositionsRepository extends JpaRepository<Composition,Integer> {

}
