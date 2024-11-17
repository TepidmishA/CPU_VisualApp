package org.example.cpu_visual.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.example.cpu_visual.controller.program.mainController;
import org.example.cpu_visual.program.BProgram;
import org.example.cpu_visual.program.Command;
import org.example.cpu_visual.program.Program;
import org.example.cpu_visual.program.Task;

import java.util.Objects;

public class addCommandDialogController {
	Program prog = BProgram.build();

	@FXML
	private ComboBox<Task> typeComboBox;
	@FXML
	private TextField leftArgumentField;
	@FXML
	private TextField rightArgumentField;
	@FXML
	private Button addButton;

	public void initialize() {
		// Добавляем значения из enum Task в ComboBox
		typeComboBox.getItems().setAll(Task.values());
		typeComboBox.setOnAction(event -> onCommandSelected());

		// Добавляем обработчики для отслеживания изменений текста в полях ввода
		leftArgumentField.textProperty().addListener((observable, oldValue, newValue) -> updateAddButtonState());
		rightArgumentField.textProperty().addListener((observable, oldValue, newValue) -> updateAddButtonState());


		leftArgumentField.setDisable(true);
		rightArgumentField.setDisable(true);

		addButton.setDisable(true);
	}

	private void onCommandSelected() {
		Task selectedTask = typeComboBox.getValue();

		switch (selectedTask) {
			case Task.print: case Task.add: case Task.sub: case Task.mul: case Task.div:
				leftArgumentField.setDisable(true);
				rightArgumentField.setDisable(true);
				addButton.setDisable(false);
				break;
			default:
				leftArgumentField.setDisable(false);
				rightArgumentField.setDisable(false);
				updateAddButtonState();
		}
	}

	// Метод для обновления состояния кнопки "Добавить"
	private void updateAddButtonState() {
		// Активируем кнопку, только если оба поля заполнены
		boolean bothFieldsFilled = !leftArgumentField.getText().isEmpty() && !rightArgumentField.getText().isEmpty();
		addButton.setDisable(!bothFieldsFilled);
	}

	@FXML
	void onAdd() {
		Task selectedTask = typeComboBox.getValue();
		String leftArgument = leftArgumentField.getText();
		String rightArgument = rightArgumentField.getText();

		Command tmp;
		if (Objects.equals(leftArgument, "")) {
			tmp = new Command(selectedTask);
			switch (selectedTask) {
				// regs
				case add: case sub: case mul: case div:
					prog.regsAdd(3, -1);
					break;
			}
		}
		else {
			tmp = new Command(selectedTask, leftArgument, rightArgument);

			switch (selectedTask) {
				// regs
				case Task.ld: case Task.mv:
					prog.regsAdd(Integer.parseInt(leftArgument), -1);
					break;
			}
		}

		prog.addCom(tmp);

		closeWindow();
	}

	@FXML
	void onCancel() {
		closeWindow();
	}

	@FXML
	private void closeWindow() {
		Stage stage = (Stage) typeComboBox.getScene().getWindow();
		stage.close();
	}
}
