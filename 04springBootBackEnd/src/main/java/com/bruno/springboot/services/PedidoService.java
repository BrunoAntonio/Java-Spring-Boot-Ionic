package com.bruno.springboot.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bruno.springboot.domain.Cliente;
import com.bruno.springboot.domain.ItemPedido;
import com.bruno.springboot.domain.PagamentoComBoleto;
import com.bruno.springboot.domain.Pedido;
import com.bruno.springboot.enums.EstadoPagamento;
import com.bruno.springboot.repositories.ItemPedidoRepository;
import com.bruno.springboot.repositories.PagamentoRepository;
import com.bruno.springboot.repositories.PedidoRepository;
import com.bruno.springboot.security.UserSS;
import com.bruno.springboot.services.exceptions.AuthorizationException;
import com.bruno.springboot.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	/*
	 * Sem tratamento de excepção 
	 * public Pedido procurar(Integer id){
	 * Optional<Pedido> obj = repo.findById(id); 
	 * return obj.orElse(null); }
	 */

	//Com tratamento de excepção
	public Pedido find(Integer id){
        Optional<Pedido> optional = repo.findById(id);
        return optional.orElseThrow(() -> new ObjectNotFoundException( "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null); //garantir que é um nove pedido
		obj.setInstante(new Date());//data do pedido
		obj.setCliente(clienteService.find(obj.getCliente().getId())); //para procurar na base de dados o id do cliente
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj); //o pagamento tem de conhecer o pedido dele
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) { //data de vencimento casa seja com boleto
			PagamentoComBoleto pagto = (PagamentoComBoleto)obj.getPagamento();
			boletoService.preecherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId())); //para procurar na base de dados o id do produto
			ip.setPreco(ip.getProduto().getPreco()); //preço por item de produto igual ao preco do produto
			ip.setPedido(obj); //associar itempedido ao pedido obj	
		
		}
		itemPedidoRepository.saveAll(obj.getItens());  //gravar itens no banco de dados
		emailService.sendOrderConfirmationEmail(obj);
		//emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	//retornar apenas os pedidos do cliente que está logado
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}

}
