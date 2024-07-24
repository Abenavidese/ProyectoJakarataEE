package web.ic.ec.biblioteca.model;

import java.util.List;


import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import web.ic.ec.biblioteca.dao.LibroDAO;
import web.ic.ec.biblioteca.main.Libros;

@Stateless
public class GestionLibro {

	@Inject
	private LibroDAO daoLibro;
	
	public Libros getLibro(int id) throws Exception {
		Libros libro = daoLibro.read(id);
		if(libro == null)
			throw new Exception("Libro no existe");
		return libro;
	}
	
	public List<Libros> getLibros(){
		return daoLibro.getAll();
	}
	
	public void createLibro(Libros libro) throws Exception {
		if(libro.getLibroId() < 0) {
			throw new Exception("Id Incorrecto");
		}
		daoLibro.insert(libro);
	}
	
	public void updateLibro(Libros libro) throws Exception{
		if(libro.getLibroId() < 0) {
			throw new Exception("Id Incorrecta");
		}
		daoLibro.update(libro);
	}
	
	public void deleteLibro(int id) throws Exception{
		Libros cliente = daoLibro.read(id);
		if(cliente == null) {				
			throw new Exception("Libro no existe");
		}
		else {
			daoLibro.delete(id);
		}
		
	}
}