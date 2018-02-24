package com.gustavoalves.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavoalves.cursomc.domain.Estado;
import com.gustavoalves.cursomc.repositories.EstadoRepository;
import com.gustavoalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repo;
	
	public Estado find(Integer id) {
		Estado obj = repo.findOne(id);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Estado: " + Estado.class.getName());
		}

		return obj;
	}
	
	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}

}
