package negocios;

import java.util.List;

import negocios.beans.AmigoSecretoSorteado;
import negocios.beans.Grupo;
import negocios.beans.Pessoa;
import negocios.beans.Presente;
import negocios.exceptions.GrupoExisteException;
import negocios.exceptions.PessoaExisteException;
import negocios.exceptions.PoucasPessoasNoGrupoException;
import negocios.exceptions.SorteioJaRealizadoException;

public class ChecaRegraNegocio implements IChecaRegraNegocio {
	private static ChecaRegraNegocio instance;

	private CadastroPessoa cadastroPessoa;
	private CadastroGrupo cadastroGrupo;
	private CadastroPresente cadastroPresente;
	private CadastroSorteio cadastroSorteio;

	private ChecaRegraNegocio() {
		this.cadastroPessoa = new CadastroPessoa();
		this.cadastroGrupo = new CadastroGrupo();
		this.cadastroPresente = new CadastroPresente();
		this.cadastroSorteio = new CadastroSorteio();
	}

	public static ChecaRegraNegocio getInstance() {
		if (instance == null) {
			instance = new ChecaRegraNegocio();
		}
		return instance;
	}

	@Override
	public void cadastrarPessoa(Pessoa p) throws PessoaExisteException {
		cadastroPessoa.cadastrar(p);

	}

	@Override
	public Pessoa buscarPessoa(String apelido) {
		return cadastroPessoa.buscar(apelido);
	}

	@Override
	public List<Pessoa> listarPessoa() {
		return cadastroPessoa.listarPessoas();
	}

	@Override
	public void removerPessoa(String apelido) {
		cadastroPessoa.remover(apelido);

	}

	@Override
	public void atualizar(Pessoa p) {
		cadastroPessoa.atualizar(p);

	}

	@Override
	public void cadastrarGrupo(Grupo g) throws GrupoExisteException {
		cadastroGrupo.cadastrar(g);
	}

	@Override
	public Grupo buscarGrupo(String nomeGrupo) {
		return cadastroGrupo.buscar(nomeGrupo);
	}

	@Override
	public List<Grupo> listarGrupos() {
		return cadastroGrupo.listarGrupos();
	}

	@Override
	public void removerGrupo(String nomeGrupo) {
		cadastroGrupo.remover(nomeGrupo);

	}

	@Override
	public List<AmigoSecretoSorteado> metodoDepurador(String nomeGrupo) {
		return cadastroGrupo.metodoDepurador(nomeGrupo);
	}

	@Override
	public void cadastrarPresente(Presente p) {
		cadastroPresente.cadastrar(p);
	}

	@Override
	public Presente buscarPresente(String categoria) {
		return cadastroPresente.buscar(categoria);
	}

	@Override
	public List<Presente> listarPresentes() {
		return cadastroPresente.listarPresentes();
	}

	@Override
	public void removerPresente(String categoria) {
		cadastroPresente.remover(categoria);
	}

	@Override
	public void gerarSorteio(String nomeGrupo) throws SorteioJaRealizadoException, PoucasPessoasNoGrupoException {
		cadastroSorteio.gerarSorteio(nomeGrupo);

	}

	@Override
	public Pessoa consultarAmigoSecreto(Grupo g, Pessoa p, String senha) throws SorteioJaRealizadoException {
		return cadastroSorteio.consultarAmigoSecreto(g, p, senha);
	}

	@Override
	public boolean existeSorteio(String nomeGrupo) {
		return cadastroSorteio.existeSorteio(nomeGrupo);
	}

}
