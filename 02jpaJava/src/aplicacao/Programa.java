package aplicacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dominio.Pessoa;

public class Programa {

	public static void main(String[] args) {
		
		Pessoa p1 = new Pessoa (null,"Bruno","bruno@gmail.com");
		Pessoa p2 = new Pessoa (null,"Sara","sara@gmail.com");
		Pessoa p3 = new Pessoa (null,"T�nia","tania@gmail.com");
		
		
		//Criar o EntityManager
		//"exemplo-jpa" � o nome no persistence.xml
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplojpa");
		
		//fazer a conec��o e acesso a dados
		EntityManager em= emf.createEntityManager();
		
		//Fazer uma transac��o com o banco de dados
		em.getTransaction().begin();
		
		//Inserir na base de dados
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		
		
		em.remove(p1);
		
		//Confirmar as altera��es
		em.getTransaction().commit();
		
		//Procurar na base de dados por id
		Pessoa p =em.find(Pessoa.class, 11);
	
		
		System.out.println(p);
		System.out.println("Terminado");
		
		
		
		em.close();
		emf.close();

	}

}
