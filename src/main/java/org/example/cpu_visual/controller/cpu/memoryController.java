package org.example.cpu_visual.controller.cpu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import org.example.cpu_visual.controller.program.IObserver;
import org.example.cpu_visual.cpu.ICPU;
import org.example.cpu_visual.program.Program;

public class memoryController implements IObserver {
	private int ROWS = 205; // Количество строк
	private int COLS = 5;  // Количество столбцов
	private int TOTAL_CELLS = ROWS * COLS - 1;
	private int COLUMN_WIDTH = 50; // Ширина каждого столбца
	private int ROW_HEIGHT = 10;   // Высота каждой строки

	@FXML
	private ScrollPane scrPane;
	@FXML
	private GridPane memoryGrid;
	private Label[] memoryCells = new Label[TOTAL_CELLS + 1]; // Массив для хранения ячеек памяти (с 1)

	@FXML
	private void initialize() {

		// Задаем равномерную ширину для каждого столбца перед добавлением ячеек
		memoryGrid.getColumnConstraints().clear();
		for (int i = 0; i < COLS; i++) {
			ColumnConstraints colConstraints = new ColumnConstraints();
			colConstraints.setMinWidth(COLUMN_WIDTH);
			colConstraints.setPrefWidth(COLUMN_WIDTH);
			colConstraints.setMaxWidth(COLUMN_WIDTH);
			memoryGrid.getColumnConstraints().add(colConstraints);
		}

		memoryGrid.getRowConstraints().clear();
		for (int i = 0; i < ROWS; i++) {
			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setMinHeight(ROW_HEIGHT);
			rowConstraints.setPrefHeight(ROW_HEIGHT);
			rowConstraints.setMaxHeight(ROW_HEIGHT);
			memoryGrid.getRowConstraints().add(rowConstraints);
		}

		// Инициализируем ячейки в сетке
		int index = 1;
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (index > TOTAL_CELLS) break;

				Label cell = new Label(index + ":0");
				cell.setStyle("-fx-font-size: 14px;");
				memoryCells[index] = cell;
				memoryGrid.add(cell, col, row);
				index++;
			}
		}
	}

	@Override
	public void event(Program prog) {
		int[] vals = prog.getMemStatus();

		for (int i = 1; i < TOTAL_CELLS; i++ ) {
			// проверяем значение по умолчанию
			if (0 != vals[i]) {
				memoryCells[i].setStyle("-fx-text-fill: red;");
			} else {
				memoryCells[i].setStyle("-fx-text-fill: black;");
			}
			memoryCells[i].setText((i) + ":" + vals[i]);
		}
	}
}
