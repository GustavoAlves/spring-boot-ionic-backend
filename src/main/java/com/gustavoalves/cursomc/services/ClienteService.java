package com.gustavoalves.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavoalves.cursomc.domain.Cliente;
import com.gustavoalves.cursomc.repositories.ClienteRepository;
import com.gustavoalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Cliente: " + Cliente.class.getName());
		}

		return obj;
	}

}
