package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;

public class ClienteTest {

	private HttpServer server;
	private Client client;
	private WebTarget target;
	
	@Before
	public void startaServidor() {
		this.server = Servidor.startaServidor();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		this.client = ClientBuilder.newClient(config);
		this.target = client.target("http://localhost:8080");
		
		/*this.client = ClientBuilder.newClient();
		this.target = client.target("http://localhost:8080");*/
	}
	
	@After
	public void stopServidor() {
		this.server.stop();
	}
	
	@Test
	public void testaQueAConexaoComOServidorFunciona() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://www.mocky.io");
		String conteudo = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(String.class);
		//Carrinho carrinho = target.path("/v2/52aaf5deee7ba8c70329fb7d").request().get(Carrinho.class);
		
		Assert.assertTrue(conteudo.contains("<rua>Rua Vergueiro 3185"));
	}

	@Test
	public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
		Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
		//Carrinho carrinho = (Carrinho)new XStream().fromXML(conteudo);
		System.out.println(carrinho);
		
		Assert.assertTrue(carrinho.getRua().contains("Rua Vergueiro 3185"));
	}

	@Test
	public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
		//String conteudo = target.path("/projetos/1").request().get(String.class);
		//Projeto projeto = (Projeto)new XStream().fromXML(conteudo);

		Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
		
		System.out.println(projeto);
		
		Assert.assertTrue(projeto.getName().contains("Minha loja"));
	}
	
	@Test
    public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
        //String conteudo = target.path("/projetos/1").request().get(String.class);
		Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
		 Assert.assertEquals(1L, projeto.getId(),0);
    }
	
	@Test
	public void testaPost() {
		Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");

        //String xml = carrinho.toXML();
        //Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);
        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
        
        Response response = target.path("/carrinhos").request().post(entity);
        
        Assert.assertEquals(201, response.getStatus());
        
        String location = response.getHeaderString("Location");
        Carrinho carrinhoCarregado = client.target(location).request().get(Carrinho.class);
        Assert.assertEquals("Tablet", carrinhoCarregado.getProdutos().get(0).getNome());
        
		
	}
}
