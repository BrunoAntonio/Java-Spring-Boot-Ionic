package com.bruno.springboot.services;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;


import com.bruno.springboot.domain.Categoria;
import com.bruno.springboot.dto.CategoriaDTO;
import com.bruno.springboot.repositories.CategoriaRepository;
import com.bruno.springboot.services.exceptions.DataIntegrityException;
import com.bruno.springboot.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	/*
	 * Sem tratamento de excepção public Categoria procurar(Integer id){
	 * Optional<Categoria> obj = repo.findById(id); return obj.orElse(null); }
	 */

	// Com tratamento de excepção
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	// Metodo de inserir categoria
	public Categoria insert(Categoria obj) {
		obj.setId(null); // para garantir que se está a inserir uma nova categoria
		return repo.save(obj);
	}

	// Metodo para actualizar categoria
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj,obj); 
		return repo.save(newObj);
	}

	// Metodo para apagar categoria
	public void delete(Integer id) {
		find(id); //para verificar se o id existe
		try {
		repo.deleteById(id);
		}
		catch (DataIntegrityViolationException e) { //excepção caso tente apagar uma categoria com produtos
			throw new DataIntegrityException("Não é possivel excluir uma categoria que tem produtos");
		}
	}
	// Metodo para obter todas as categorias
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	//Método para obter so alugumas categorias e não todas de uma vez porque o programa pode ficar pesado
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}
	
	//Método para converter objecto de CategoriaDTO para Categoria
	public Categoria fromDTO (CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(),objDTO.getNome());
	}
	
	//Sub-Método para actualizar a Categoria
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}

}
