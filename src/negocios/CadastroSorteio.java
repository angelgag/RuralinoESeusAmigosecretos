package negocios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import negocios.beans.AmigoSecretoSorteado;
import negocios.beans.Grupo;
import negocios.beans.Pessoa;
import negocios.exceptions.PoucasPessoasNoGrupoException;
import negocios.exceptions.SorteioJaRealizadoException;
import repositorios.RepositorioGrupos;
import repositorios.RepositorioSorteio;

public class CadastroSorteio {
	private RepositorioGrupos repositorioGrupos;
	private RepositorioSorteio repositorioSorteio;

	public CadastroSorteio() {
		this.repositorioGrupos = RepositorioGrupos.getInstance();
		this.repositorioSorteio = RepositorioSorteio.getInstance();
	}

	public void gerarSorteio(String nomeGrupo) throws SorteioJaRealizadoException, PoucasPessoasNoGrupoException {
		Grupo g = repositorioGrupos.buscar(nomeGrupo);
		if (!repositorioSorteio.existe(g.getNomeGrupo())) {
			if(g.getListaParticipantes().size() > 2) {
			Collections.shuffle(g.getListaParticipantes());

			List<AmigoSecretoSorteado> amigoSecreto = new ArrayList<>();

			for (int i = 0; i < g.getListaParticipantes().size(); i++) {
				if (i == 0) {
					AmigoSecretoSorteado amigos = new AmigoSecretoSorteado(
							g.getListaParticipantes().get(g.getListaParticipantes().size() - 1),
							g.getListaParticipantes().get(i));
					AmigoSecretoSorteado amigos1 = new AmigoSecretoSorteado(g.getListaParticipantes().get(i),
							g.getListaParticipantes().get(i + 1));
					amigoSecreto.add(amigos);
					amigoSecreto.add(amigos1);
				} else if (i != g.getListaParticipantes().size() - 1) {
					AmigoSecretoSorteado amigos2 = new AmigoSecretoSorteado(g.getListaParticipantes().get(i),
							g.getListaParticipantes().get(i + 1));
					amigoSecreto.add(amigos2);
				}
			}
			g.setListaAmigoSecreto(amigoSecreto);
			repositorioSorteio.cadastrar(g);
			}else {
				throw new PoucasPessoasNoGrupoException("N�o � possivel realizar o sorteio com menos de 2 pessoas no grupo!");
			}
		} else {
			throw new SorteioJaRealizadoException("Sorteio j� realizado pra esse grupo!");
		}
	}

	public Pessoa consultarAmigoSecreto(Grupo g, Pessoa p, String senha) throws SorteioJaRealizadoException {
		boolean verificarSorteio = repositorioSorteio.existe(g.getNomeGrupo());
		if (verificarSorteio) {
			for (int i = 0; i < g.getListaParticipantes().size(); i++) {
				if (p.equals(g.getListaParticipantes().get(i))) {
					if (senha.equals(g.getListaParticipantes().get(i).getSenha())) {
						if (i == g.getListaParticipantes().size() - 1) {
							return g.getListaParticipantes().get(0);
						} else {
							return g.getListaParticipantes().get(i + 1);
						}
					}
				}
			}
		}
		return null;

	}

	public boolean existeSorteio(String nomeGrupo) {
		return repositorioSorteio.existe(nomeGrupo);

	}
}
