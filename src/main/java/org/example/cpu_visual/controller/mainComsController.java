package org.example.cpu_visual.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.cpu_visual.VisualApp;
import org.example.cpu_visual.program.*;

import java.io.IOException;

public class mainComsController {
	Program prog = BProgram.build();

	@FXML
	void addCommand() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(
				VisualApp.class.getResource("add_command_dialog.fxml"));
		Scene scene = new Scene(fxmlLoader.load());
		Stage stage = new Stage();
		stage.setTitle("Выбор команды");
		stage.setScene(scene);
		stage.show();

		prog.reset();
	}

	@FXML
	void reset() {
		prog.reset();
	}

	@FXML
	void step() throws Exception {
		prog.runNext();
	}
}
