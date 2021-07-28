package gui;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import negocios.beans.Grupo;
import negocios.beans.Pessoa;
import negocios.exceptions.GrupoExisteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocios.ChecaRegraNegocio;

public class TelaGruposController implements Initializable {
	ChecaRegraNegocio checa = ChecaRegraNegocio.getInstance();

	@FXML
	protected Button btnPessoas;
	@FXML
	protected Button btnGrupos;
	@FXML
	protected Button btnPresentes;
	@FXML
	protected Button btnSorteio;

	@FXML
	public void abrirTelaPessoas(ActionEvent event) throws IOException {//abre a tela de pessoas
		BorderPane testPane = FXMLLoader.load(getClass().getResource("/gui/TelaPessoas.fxml"));

		Scene scene = new Scene(testPane);
		Stage primaryStage = new Stage(StageStyle.DECORATED);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Pessoas");
		primaryStage.show();
		btnPessoas.getScene().getWindow().hide();
	}

	@FXML
	public void abrirTelaGrupos(ActionEvent event) throws IOException {//abre a tela de Grupos
		BorderPane testPane = FXMLLoader.load(getClass().getResource("/gui/TelaGrupos.fxml"));

		Scene scene = new Scene(testPane);
		Stage primaryStage = new Stage(StageStyle.DECORATED);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Grupos");
		primaryStage.show();
		btnGrupos.getScene().getWindow().hide();
	}

	@FXML
	public void abrirTelaPresentes(ActionEvent event) throws IOException {//abre a tela de presentes
		BorderPane testPane = FXMLLoader
				.load(getClass().getResource("/gui/TelaPresentes.fxml"));

		Scene scene = new Scene(testPane);
		Stage primaryStage = new Stage(StageStyle.DECORATED);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Cadastro de Presentes");
		primaryStage.show();
		btnPresentes.getScene().getWindow().hide();
	}

	@FXML
	public void abrirTelaSorteio(ActionEvent event) throws IOException {//abre a tela de sorteio
		BorderPane testPane = FXMLLoader.load(getClass().getResource("/gui/TelaSorteio.fxml"));

		Scene scene = new Scene(testPane);
		Stage primaryStage = new Stage(StageStyle.DECORATED);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sorteio");
		primaryStage.show();
		btnSorteio.getScene().getWindow().hide();
	}

	@FXML
	protected Button btnSalvar;
	@FXML
	protected Button btnCancelar;
	@FXML
	private TextField nomeGrupo;
	@FXML
	private DatePicker data;

	@FXML
	public void cadastrarGrupos(ActionEvent event) throws GrupoExisteException, IOException {
		try {
			String nome = nomeGrupo.getText().toString();
			LocalDate date = data.getValue();
			if ((date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now())) && nome != null) {
				Grupo g = new Grupo(nome, date);
				checa.cadastrarGrupo(g);
				Alert alert = new Alert(AlertType.INFORMATION, "Grupo cadastrado com sucesso!");
				alert.setTitle("Confirma��o");
				alert.showAndWait();
				abrirTelaGrupos(event);
			} else {
				Alert alert = new Alert(AlertType.INFORMATION,
						"N�o � poss�vel cadastrar um grupo para o sorteio em uma data passada, tente novamente em outra data!");
				alert.show();
			}
		} catch (GrupoExisteException ge) {
			Alert alert = new Alert(AlertType.INFORMATION, "J� existe um grupo com esse nome, tente novamente!");
			alert.show();
		} catch (NullPointerException npe) {
			Alert alert = new Alert(AlertType.ERROR, "Campos n�o preenchidos corretamente!");
			alert.show();
		} finally {
			limparCamposCadastro();
		}
	}

	public void limparCamposCadastro() {
		nomeGrupo.setText(null);
		data.setValue(null);
	}

	@FXML
	private ComboBox<Grupo> lista;
	private List<Grupo> listarGrupos = new ArrayList<>();
	private ObservableList<Grupo> obsLista;

	@FXML
	public void listarGrupos() {
		listarGrupos.addAll(checa.listarGrupos());
		obsLista = FXCollections.observableArrayList(listarGrupos);

		lista.getItems().addAll(obsLista);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listarGrupos();
		listarPessoas();
	}

	@FXML
	private ListView<Pessoa> viewAllPessoas;

	private List<Pessoa> listarPessoas = new ArrayList<>();
	private ObservableList<Pessoa> p;

	public void listarPessoas() {
		listarPessoas.addAll(checa.listarPessoa());
		p = FXCollections.observableArrayList(listarPessoas);
		viewAllPessoas.getItems().addAll(p);
	}

	@FXML
	private Button btnAdicionar;
	@FXML
	private ListView<Pessoa> viewPessoasNoGrupo;
	private ObservableList<Pessoa> pp;

	@FXML
	public void pegarComboSelecionado() {
		try {
			viewPessoasNoGrupo.getItems().clear();
			Grupo g = lista.getSelectionModel().getSelectedItem();
			if (!checa.existeSorteio(g.getNomeGrupo())) {
				if (viewAllPessoas.getSelectionModel().getSelectedItem() != null) {
					Pessoa p = viewAllPessoas.getSelectionModel().getSelectedItem();

					List<Pessoa> pessoas = g.getListaParticipantes();
					boolean verificar = false;
					if (pessoas.size() == 0) {
						g.setListaParticipantes(p);
					} else {
						for (int i = 0; i < pessoas.size(); i++) {
							if (p.equals(pessoas.get(i))) {
								verificar = true;
							}
						}
						if (!verificar) {
							g.setListaParticipantes(p);
						} else {
							Alert alert = new Alert(AlertType.ERROR, "Pessoa j� cadastrada no grupo!");
							alert.show();
						}
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR, "Voc� n�o selecionou uma pessoa!");
					alert.show();
				}
				pp = FXCollections.observableArrayList(g.getListaParticipantes());
				viewPessoasNoGrupo.getItems().addAll(pp);
			} else {
				pp = FXCollections.observableArrayList(g.getListaParticipantes());
				viewPessoasNoGrupo.getItems().addAll(pp);
				Alert alert = new Alert(AlertType.ERROR,
						"Sorteio para este grupo j� foi realizado, n�o � poss�vel adicionar mais pessoas!");
				alert.show();
			}
		} catch (NullPointerException npe) {
			Alert alert = new Alert(AlertType.ERROR, "Voc� n�o selecionou um grupo!");
			alert.show();
		}
	}

	@FXML
	public void pegarCombo() {
		viewPessoasNoGrupo.getItems().clear();
		Grupo g = lista.getSelectionModel().getSelectedItem();
		pp = FXCollections.observableArrayList(g.getListaParticipantes());
		Collections.shuffle(pp);
		viewPessoasNoGrupo.getItems().addAll(pp);
	}

	@FXML
	public void cancelar(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Deseja cancelar o cadastro de grupos e retornar para a tela principal?");
		alert.setTitle("Voltar para tela principal");
		alert.showAndWait().ifPresent(type -> {
			if (alert.getResult() == ButtonType.OK) {
				BorderPane testPane;
				try {
					testPane = FXMLLoader
							.load(getClass().getResource("/gui/TelaPrincipal.fxml"));
					Scene scene = new Scene(testPane);
					Stage primaryStage = new Stage(StageStyle.DECORATED);
					primaryStage.setScene(scene);
					primaryStage.setTitle("Tela Principal");
					primaryStage.show();
					btnCancelar.getScene().getWindow().hide();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@FXML
	private Button btnRemover;

	@FXML
	public void removerPessoaDoGrupo() {
		Grupo grupo = lista.getSelectionModel().getSelectedItem();
		Pessoa pessoa = viewPessoasNoGrupo.getSelectionModel().getSelectedItem();
		if (grupo != null && pessoa != null) {
			viewPessoasNoGrupo.getItems().remove(pessoa);
			grupo.getListaParticipantes().remove(pessoa);
		} else if (grupo != null && pessoa == null) {
			Alert alert = new Alert(AlertType.ERROR,
					"Voc� n�o selecionou uma pessoa do grupo selecionado para deletar!");
			alert.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Voc� n�o selecionou um grupo para deletar as pessoas!");
			alert.show();
		}
	}
}
