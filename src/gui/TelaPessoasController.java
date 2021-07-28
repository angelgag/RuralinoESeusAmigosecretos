package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import negocios.beans.Pessoa;
import negocios.beans.Presente;
import negocios.exceptions.PessoaExisteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import negocios.ChecaRegraNegocio;

public class TelaPessoasController implements Initializable {
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
	private TextField nomeCompleto;
	@FXML
	private TextField apelido;
	@FXML
	private PasswordField senha;

	@FXML
	public void cadastrarPessoas(ActionEvent event) throws PessoaExisteException, IOException {
		try {
			String nome = nomeCompleto.getText().toString();
			String apelid = apelido.getText().toString();
			String password = senha.getText().toString();
			if (!(nome.equals("") || apelid.equals("") || password.equals(""))) {
				Pessoa p = new Pessoa(nome, apelid, password);
				checa.cadastrarPessoa(p);
				Alert alert = new Alert(AlertType.INFORMATION, "Voc� foi cadastrado com sucesso!");
				alert.setTitle("Confirma��o");
				alert.showAndWait();
				abrirTelaPessoas(event);
			} else {
				Alert alert = new Alert(AlertType.ERROR, "Campos preenchidos incorretamente!");
				alert.show();
			}
		} catch (PessoaExisteException pe) {
			Alert alert = new Alert(AlertType.ERROR,
					"N�o � poss�vel cadastrar 2 pessoas com mesmo apelido, tente novamente!");
			alert.show();
		} finally {
			limparCamposCadastrar();
		}
	}

	public void limparCamposCadastrar() {
		nomeCompleto.setText("");
		apelido.setText("");
		senha.setText("");
	}

	@FXML
	private ComboBox<Pessoa> lista;
	private List<Pessoa> listarPessoas = new ArrayList<>();
	private ObservableList<Pessoa> obsLista;

	@FXML
	public void listaPessoas(ActionEvent event) {
		listarPessoas.addAll(checa.listarPessoa());
		obsLista = FXCollections.observableArrayList(listarPessoas);

		lista.getItems().addAll(obsLista);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaPessoas(null);
		listarPresentes();
	}

	@FXML
	private ListView<Presente> viewAllPresentes;

	private List<Presente> listarPresentes = new ArrayList<>();
	private ObservableList<Presente> p;

	public void listarPresentes() {
		listarPresentes.addAll(checa.listarPresentes());
		p = FXCollections.observableArrayList(listarPresentes);
		viewAllPresentes.getItems().addAll(p);
	}

	@FXML
	private Button btnRemover;

	@FXML
	public void removerPresente() {
		Presente presente = viewPresentesPessoa.getSelectionModel().getSelectedItem();
		Pessoa pessoa = lista.getSelectionModel().getSelectedItem();
		if (presente != null && pessoa != null) {
			viewPresentesPessoa.getItems().remove(presente);
			pessoa.getListaDePresentes().remove(presente);
			checa.atualizar(pessoa);
		} else if (presente == null && pessoa != null) {
			Alert alert = new Alert(AlertType.ERROR,
					"Voc� n�o selecionou um presente da pessoa selecionada para deletar!");
			alert.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Voc� n�o selecionou uma pessoa para deletar os presentes!");
			alert.show();
		}
	}

	@FXML
	private Button btnAdicionar;
	@FXML
	private ListView<Presente> viewPresentesPessoa;
	private ObservableList<Presente> pp;

	@FXML
	public void pegarComboSelecionado() {
		try {
			viewPresentesPessoa.getItems().clear();
			Pessoa p = lista.getSelectionModel().getSelectedItem();

			if (viewAllPresentes.getSelectionModel().getSelectedItem() != null) {
				Presente p2 = viewAllPresentes.getSelectionModel().getSelectedItem();

				List<Presente> presentes = p.getListaDePresentes();
				boolean verificar = false;
				if (presentes.size() == 0) {
					p.setListaDePresentes(p2);
					checa.atualizar(p);
				} else {
					for (int i = 0; i < presentes.size(); i++) {
						if (p2.equals(presentes.get(i))) {
							verificar = true;
						}
					}
					if (!verificar) {
						p.setListaDePresentes(p2);
						checa.atualizar(p);
					} else {
						Alert alert = new Alert(AlertType.ERROR,
								"Presente j� cadastrado para est� pessoa! N�o � poss�vel cadastrar 2 presentes iguais!");
						alert.show();
					}
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR, "Voc� n�o selecionou um presente!");
				alert.show();
			}
			pp = FXCollections.observableArrayList(p.getListaDePresentes());
			viewPresentesPessoa.getItems().addAll(pp);
		} catch (NullPointerException npe) {
			Alert alert = new Alert(AlertType.ERROR, "Voc� n�o selecionou uma pessoa!");
			alert.show();
		}
	}

	@FXML
	public void pegarCombo() {
		try {
			viewPresentesPessoa.getItems().clear();
			Pessoa p = lista.getSelectionModel().getSelectedItem();
			pp = FXCollections.observableArrayList(p.getListaDePresentes());
			viewPresentesPessoa.getItems().addAll(pp);
		} catch (NullPointerException npe) {
			Alert alert = new Alert(AlertType.ERROR, "Voc� n�o selecionou uma pessoa!");
			alert.show();
		}
	}

	@FXML
	public void cancelar(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION,
				"Deseja cancelar o cadastro de pessoas e retornar para a tela principal?");
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
}
