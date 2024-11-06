package org.example.cpu_visual.controller.program;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.example.cpu_visual.VisualApp;
import org.example.cpu_visual.controller.freqPerComController;
import org.example.cpu_visual.program.Program;
import org.example.cpu_visual.program.Task;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Map;

public class frequencyController implements IObserver {
	@FXML
	private GridPane dataGrid;

	@Override
	public void event(Program prog) {
		dataGrid.getChildren().clear();

		Map<Task, Long> data = prog.getSortedComs();
		for (Map.Entry<Task, Long> entry : data.entrySet()) {
			freqPerComController fc = new freqPerComController();
			FXMLLoader fxmlLoader = new FXMLLoader(
					VisualApp.class.getResource("windows/freq_per_com.fxml"));
			fxmlLoader.setController(fc);

			try {
				Pane pane = fxmlLoader.load();
				fc.setType(entry.getKey().toString());
				fc.setCount(entry.getValue().toString());

				dataGrid.addColumn(0, pane);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
