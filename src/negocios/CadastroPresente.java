package negocios;

import java.util.List;

import negocios.beans.Presente;
import repositorios.RepositorioPresentes;

public class CadastroPresente {
	private RepositorioPresentes repositorio;

	public CadastroPresente() {
		this.repositorio = RepositorioPresentes.getInstance();
	}

	public void cadastrar(Presente p) {
		this.repositorio.cadastrar(p);
	}

	public void remover(String categoria) {
		Presente p = this.repositorio.buscar(categoria);
		if (p != null && p.getCategoria().equals(categoria)) {
			this.repositorio.remover(categoria);
		}
	}

	public List<Presente> listarPresentes() {
		return repositorio.listar();
	}

	public Presente buscar(String categoria) {
		return this.repositorio.buscar(categoria);
	}

	public boolean existe(String categoria) {
		return this.repositorio.existe(categoria);
	}
}
