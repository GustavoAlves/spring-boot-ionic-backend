package com.gustavoalves.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gustavoalves.cursomc.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	// @Transactional(readOnly = true)
	// @Query("SELECT obj FROM Estado WHERE obj.nome LIKE %:nome% ORDER BY obj.nome")
	// List<Estado> search();

	@Transactional(readOnly = true)
	public List<Estado> findAllByOrderByNome();

}
