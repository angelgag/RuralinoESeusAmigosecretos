package negocios;

import java.util.List;

import negocios.beans.AmigoSecretoSorteado;
import negocios.beans.Grupo;
import negocios.exceptions.GrupoExisteException;
import repositorios.RepositorioGrupos;


public class CadastroGrupo {
	private RepositorioGrupos repositorio;

	public CadastroGrupo() {
		this.repositorio = RepositorioGrupos.getInstance();
	}

	public void cadastrar(Grupo g) throws GrupoExisteException {
			if (!this.existe(g.getNomeGrupo())) {
				this.repositorio.cadastrar(g);
			} else {
				throw new GrupoExisteException("J� existe um grupo cadastrado com esse nome!");
			}
	}

	public void remover(String nomeGrupo) {
		Grupo g = this.repositorio.buscar(nomeGrupo);
		if (g != null && g.getNomeGrupo().equals(nomeGrupo)) {
			this.repositorio.remover(nomeGrupo);
		}
	}

	public List<Grupo> listarGrupos() {
		return repositorio.listar();
	}

	public Grupo buscar(String nomeGrupo) {
		return this.repositorio.buscar(nomeGrupo);
	}

	public boolean existe(String nomeGrupo) {
		return this.repositorio.existe(nomeGrupo);
	}
	
	public List<AmigoSecretoSorteado> metodoDepurador(String nomeGrupo) {
		Grupo grupo = this.repositorio.buscar(nomeGrupo);
		return grupo.getListaAmigoSecreto();		
	}
}
