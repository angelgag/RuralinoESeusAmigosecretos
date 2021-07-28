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

public interface IChecaRegraNegocio {
	// Pessoa
	void cadastrarPessoa(Pessoa p) throws PessoaExisteException;
	Pessoa buscarPessoa(String apelido);
	List<Pessoa> listarPessoa();
	void removerPessoa(String apelido);
	void atualizar(Pessoa p);

	// Grupo
	void cadastrarGrupo(Grupo g) throws GrupoExisteException;
	Grupo buscarGrupo(String nomeGrupo);
	List<Grupo> listarGrupos();
	void removerGrupo(String nomeGrupo);
	List<AmigoSecretoSorteado> metodoDepurador(String nomeGrupo);

	// Presente
	void cadastrarPresente(Presente p);
	Presente buscarPresente(String categoria);
	List<Presente> listarPresentes();
	void removerPresente(String categoria);
	
	// Sorteio
	void gerarSorteio(String nomeGrupo) throws SorteioJaRealizadoException, PoucasPessoasNoGrupoException;
	Pessoa consultarAmigoSecreto(Grupo g, Pessoa p, String senha) throws SorteioJaRealizadoException;
	boolean existeSorteio(String nomeGrupo);
}
