package com.serwis.controller;

import com.serwis.entity.Cars;
import com.serwis.services.CarsService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by jakub on 05.04.2018.
 */
@Controller
public class AddCarController implements Initializable {

	@Autowired
	CarsService carsService;

	@FXML
	private TextField textFieldBrand;
	@FXML
	private TextField textFieldModel;
	@FXML
	private TextField textFieldVIN;
	@FXML
	private TextField textFieldRegistrationNumber;
	@FXML
	private ComboBox yearsProductionComboBox;
	@FXML
	private Button backButton;

	private ObservableList<Integer> yearsProduction = FXCollections.observableArrayList();


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		yearsProductionComboBox.setItems(doListYearsProduction());
		yearsProductionComboBox.getSelectionModel().select(0);
	}

	private ObservableList<Integer> doListYearsProduction() {
		yearsProduction.clear();
		for (int i = 2018; i >= 1970; i--) {
			yearsProduction.add(i);
		}
		return yearsProduction;
	}

	@FXML
	public void backAction(ActionEvent event) {
		Stage stage = (Stage) backButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void addCarAction(ActionEvent event) {
		if (textFieldBrand.getText().length() == 0) {
			notIntroducedBrand();
		} else if (textFieldModel.getText().length() == 0) {
			notIntroducedModel();
		} else if (textFieldVIN.getText().length() != 17) {
			badLenghtVINnumber();
		} else if (textFieldRegistrationNumber.getText().length() == 0) {
			notIntroducedRegistrationNumber();
		} else {
			Cars car = new Cars();
			car.setBrand(textFieldBrand.getText());
			car.setModel(textFieldModel.getText());
			car.setVIN(textFieldVIN.getText());
			car.setRegistration_number(textFieldRegistrationNumber.getText());
			car.setYear_production(yearsProduction.get(yearsProductionComboBox.getSelectionModel().getSelectedIndex()));
			carsService.addCar(car);
			alertAddedNewCar();
		}
	}

	private void badLenghtVINnumber() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Błąd");
		alert.setHeaderText("Numer VIN musi mieć 17 znaków");
		alert.getButtonTypes().setAll(ButtonType.OK);
		alert.showAndWait();
	}

	private void notIntroducedRegistrationNumber() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Błąd");
		alert.setHeaderText("Nie wprowadzono numeru rejestracyjnego pojazdu");
		alert.getButtonTypes().setAll(ButtonType.OK);
		alert.showAndWait();
	}

	private void notIntroducedModel() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Błąd");
		alert.setHeaderText("Nie wprowadzono modelu pojazdu");
		alert.getButtonTypes().setAll(ButtonType.OK);
		alert.showAndWait();
	}

	private void notIntroducedBrand() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Błąd");
		alert.setHeaderText("Nie wprowadzono marki pojazdu");
		alert.getButtonTypes().setAll(ButtonType.OK);
		alert.showAndWait();
	}

	private void alertAddedNewCar() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Dodano nowy samochód");
		alert.setHeaderText("Pomyślnie dodano nowy samochód");
		alert.getButtonTypes().setAll(ButtonType.OK);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			Stage stage = (Stage) backButton.getScene().getWindow();
			stage.close();
		}
	}
}
