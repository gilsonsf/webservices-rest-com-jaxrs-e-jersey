package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Projeto {
	
	private String name;
	private long id;
	private int anoDeInicio;
	
	
	public Projeto() {
	}


	public Projeto(long id, String name,  int anoDeInicio) {
		this.id = id;
		this.name = name;
		this.anoDeInicio = anoDeInicio;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getAnoDeInicio() {
		return anoDeInicio;
	}
	
	public String toXML() {
		XStream xStream = new XStream();
		String xml = xStream.toXML(this);
		return xml;
	}


	public String toJson() {
		return new Gson().toJson(this);
	}
	
}
