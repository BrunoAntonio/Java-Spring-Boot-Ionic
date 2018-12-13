package com.bruno.springboot.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bruno.springboot.enums.Perfil;
import com.bruno.springboot.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	@Column(unique=true) //garantir que no banco de dados não há repetição de um email, mas acrescentou-se a validação personalizada no ClienteNewDTO para controlar melhor a excepção lancada e personalizar a mensagem
	private String email;
	private String cpfOucnpj;
	private Integer tipo; //para armazenar internamente o tipo de cliente como numero inteiro(ver enum com numeração 1,2)
	
	@JsonIgnore// para não aparecer o bcrypt da senha no json ao recuperar os dados no sistema
	private String senha;
	
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL) //Cascade para quando se apagar um cliente tambem apagar o seu endereco
	private List<Endereco> enderecos = new ArrayList<>();
	
	
	@ElementCollection
	@CollectionTable(name="telefone")
	private Set<String> telefones = new HashSet<>();//não foi criada a classe telefone por ser uma entidade fraca que só possuia uma variavel e optou-se por criar um set com os numeros de telefone

	@ElementCollection(fetch=FetchType.EAGER) //eager para garantir que os perfis são procurados sempre que procuro um cliente na base de dados
	@CollectionTable(name="perfis")
	private Set<Integer> perfis = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos= new ArrayList<>();
	
	
	
	public Cliente() {
		addPerfil(Perfil.CLIENTE); //adicionar o perfil de cliente quando se cria um novo cliente
	}

	public Cliente(Integer id, String nome, String email, String cpfOucnpj, TipoCliente tipo, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOucnpj = cpfOucnpj;
		this.tipo = (tipo == null) ? null : tipo.getCod();  //para armazenar internamente o tipo de cliente como numero inteiro
		//se tipo TipoCliente for nulo atribui nulo caso contrario atribui o codigo
		this.senha=senha;
		addPerfil(Perfil.CLIENTE);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOucnpj() {
		return cpfOucnpj;
	}

	public void setCpfOucnpj(String cpfOucnpj) {
		this.cpfOucnpj = cpfOucnpj;
	}

	public TipoCliente getTipo() { //para armazenar internamente o tipo de cliente como numero inteiro
		return TipoCliente.toEnum(tipo);
	}

	public void setTipo(TipoCliente tipo) { //para armazenar internamente o tipo de cliente como numero inteiro
		this.tipo = tipo.getCod();
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	public Set <Perfil> getPerfis(){
		return perfis.stream().map(x ->Perfil.toEnum(x)).collect(Collectors.toSet()); //converter de inteiro para o tipo enum
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	

	
	
	
	
	
	
}
