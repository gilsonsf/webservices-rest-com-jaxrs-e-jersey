package br.com.alura.loja.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.ProjetoDAO;

@Path("projetos")
public class ProjetoResource {
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca()  {
		return new ProjetoDAO().busca(1L).toXML();
	}

}