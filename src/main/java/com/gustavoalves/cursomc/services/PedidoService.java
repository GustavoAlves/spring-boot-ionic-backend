package com.gustavoalves.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavoalves.cursomc.domain.Pedido;
import com.gustavoalves.cursomc.repositories.PedidoRepository;
import com.gustavoalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	public Pedido buscar(Integer id) {
		Pedido obj = repo.findOne(id);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Pedido: " + Pedido.class.getName());
		}

		return obj;
	}

}
