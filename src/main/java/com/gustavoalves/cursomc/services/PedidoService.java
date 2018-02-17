package com.gustavoalves.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gustavoalves.cursomc.domain.ItemPedido;
import com.gustavoalves.cursomc.domain.PagamentoComBoleto;
import com.gustavoalves.cursomc.domain.Pedido;
import com.gustavoalves.cursomc.domain.enums.EstadoPagamento;
import com.gustavoalves.cursomc.repositories.ClienteRepository;
import com.gustavoalves.cursomc.repositories.ItemPedidoRepository;
import com.gustavoalves.cursomc.repositories.PagamentoRepository;
import com.gustavoalves.cursomc.repositories.PedidoRepository;
import com.gustavoalves.cursomc.repositories.ProdutoRepository;
import com.gustavoalves.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ClienteRepository clienteRepository;

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);

		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Pedido: " + Pedido.class.getName());
		}

		return obj;
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}
		
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			ip.setDesconto(0.0);
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.save(obj.getItens());
		
		System.out.println(obj);
		
		return obj;
	}

}
