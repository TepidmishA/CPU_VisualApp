package org.example.cpu_visual.controller.cpu;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.example.cpu_visual.controller.program.IObserver;
import org.example.cpu_visual.cpu.ICPU;
import org.example.cpu_visual.program.Program;

public class regsController implements IObserver {

	@FXML
	private GridPane regGrid;
	private Label[] regCells = new Label[4]; // Массив для хранения ячеек регистров (с 0)

	private int TOTAL_REGS = 4;


	@FXML
	private void initialize() {
		for (int i = 0; i < TOTAL_REGS; i++) {
			Label cell = new Label(String.valueOf((char) ('A' + i)));
			cell.setStyle("-fx-font-size: 14px;");
			regGrid.add(cell, i, 0);

			Label tmp = new Label("*");
			regCells[i] = tmp;
			regGrid.add(tmp, i, 1);
		}
	}

	@Override
	public void event(Program prog) {
		if (prog.isNewRegs()) {
			prog.setNewRegs(false);
			for (int i = 0; i < TOTAL_REGS; i++ ) {
				regCells[i].setText("*");
			}
		}
		else {
			int[] regs = prog.getRegsStatus();

			for (int i = 0; i < TOTAL_REGS; i++ ) {
				regCells[i].setText(Integer.toString(regs[i]));
			}
		}
	}
}
