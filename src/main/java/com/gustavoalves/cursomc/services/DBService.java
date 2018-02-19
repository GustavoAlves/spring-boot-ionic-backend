package com.gustavoalves.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gustavoalves.cursomc.domain.Categoria;
import com.gustavoalves.cursomc.domain.Cidade;
import com.gustavoalves.cursomc.domain.Cliente;
import com.gustavoalves.cursomc.domain.Endereco;
import com.gustavoalves.cursomc.domain.Estado;
import com.gustavoalves.cursomc.domain.ItemPedido;
import com.gustavoalves.cursomc.domain.Pagamento;
import com.gustavoalves.cursomc.domain.PagamentoComBoleto;
import com.gustavoalves.cursomc.domain.PagamentoComCartao;
import com.gustavoalves.cursomc.domain.Pedido;
import com.gustavoalves.cursomc.domain.Produto;
import com.gustavoalves.cursomc.domain.enums.EstadoPagamento;
import com.gustavoalves.cursomc.domain.enums.Perfil;
import com.gustavoalves.cursomc.domain.enums.TipoCliente;
import com.gustavoalves.cursomc.repositories.CategoriaRepository;
import com.gustavoalves.cursomc.repositories.CidadeRepository;
import com.gustavoalves.cursomc.repositories.ClienteRepository;
import com.gustavoalves.cursomc.repositories.EnderecoRepository;
import com.gustavoalves.cursomc.repositories.EstadoRepository;
import com.gustavoalves.cursomc.repositories.ItemPedidoRepository;
import com.gustavoalves.cursomc.repositories.PagamentoRepository;
import com.gustavoalves.cursomc.repositories.PedidoRepository;
import com.gustavoalves.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public void instantiateTestDatabase() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informática"),
				  cat2 = new Categoria(null, "Escritório"),
				  cat3 = new Categoria(null, "Cama mesa e banho"),
				  cat4 = new Categoria(null, "Eletrônico"),
				  cat5 = new Categoria(null, "Jardinagem"),
				  cat6 = new Categoria(null, "Decoração"),
				  cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00),
				p2 = new Produto(null, "Impressora", 800.00),
				p3 = new Produto(null, "Mouse", 80.00),
				p4 = new Produto(null, "Mesa de escritório", 300.00),
				p5 = new Produto(null, "Toalha", 50.00),
				p6 = new Produto(null, "Colcha", 200.00),
				p7 = new Produto(null, "TV true color", 1200.00),
				p8 = new Produto(null, "Roçadeira", 800.00),
				p9 = new Produto(null, "Abajour", 100.00),
				p10 = new Produto(null, "Pendente", 180.00),
				p11 = new Produto(null, "Shampoo", 90.00);
		
		Estado est1 = new Estado(null, "Minas Gerais"),
			   est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1),
			   c2 = new Cidade(null, "São Paulo", est2),
			   c3 = new Cidade(null, "Campinas", est2);
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria_silva@outlook.com", "36378912377", TipoCliente.PESSOAFISICA, pe.encode("123456")),
				cli2 = new Cliente(null, "Ana Costa", "gustavo_alves@outlook.com", "16717928158", TipoCliente.PESSOAFISICA, pe.encode("123456"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1),
				 e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2),
				 e3 = new Endereco(null, "Avenida Floriano Peixoto", "2015", null, "Centro", "38777015", cli2, c2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1),
			   ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6),
				  pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0),
				   ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0),
				   ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);
		
		ped1.setPagamento(pagto1);
		ped2.setPagamento(pagto2);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		cli2.getTelefones().addAll(Arrays.asList("27362333", "93455493"));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		cli2.addPerfil(Perfil.ADMIN);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		categoriaRepository.save(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.save(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));
		estadoRepository.save(Arrays.asList(est1, est2));
		cidadeRepository.save(Arrays.asList(c1, c2, c3));
		clienteRepository.save(Arrays.asList(cli1, cli2));
		enderecoRepository.save(Arrays.asList(e1, e2, e3));
		pedidoRepository.save(Arrays.asList(ped1, ped2));
		pagamentoRepository.save(Arrays.asList(pagto1, pagto2));
		itemPedidoRepository.save(Arrays.asList(ip1, ip2, ip3));
	}

}
