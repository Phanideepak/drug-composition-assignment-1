package com.licious.app.repository;

import com.licious.app.model.Molecule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoleculesRepository  extends JpaRepository<Molecule,Integer> {
}
