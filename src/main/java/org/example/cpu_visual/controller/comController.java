package org.example.cpu_visual.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.cpu_visual.controller.program.mainController;
import org.example.cpu_visual.program.BProgram;
import org.example.cpu_visual.program.Command;
import org.example.cpu_visual.program.Program;
import org.example.cpu_visual.program.Task;

public class comController {
	Program prog = BProgram.build();
	Command cmd;
	boolean isRunning = false;

	@FXML
	Label type_label;
	@FXML
	Label L_arg_label;
	@FXML
	Label R_arg_label;


	public comController(Command cmd) {
		this.cmd = cmd;
	}

	public void setStatus() {
		isRunning = true;
	}

	public void setCom(Command cmd) {
		this.cmd = cmd;
		Task tmp = cmd.getTask();
		type_label.setText(String.valueOf(tmp));
		if (isRunning) {
			type_label.setStyle("-fx-text-fill: red;");
		}

		switch (tmp) {
			case Task.ld: case Task.st:
				L_arg_label.setText(String.valueOf((char) ('a' + cmd.getVal1())));
				R_arg_label.setText(String.valueOf(cmd.getVal2()));
				break;
			case Task.mv:
				L_arg_label.setText(String.valueOf((char) ('a' + cmd.getVal1())));
				R_arg_label.setText(String.valueOf((char) ('a' + cmd.getVal2())));
				break;
			case Task.init:
				L_arg_label.setText(String.valueOf(cmd.getVal1()));
				R_arg_label.setText(String.valueOf(cmd.getVal2()));
				break;
		}
	}

	public void moveUp() {
		prog.comMoveUp(cmd);
	}

	public void moveDown() {
		prog.comMoveDown(cmd);
	}

	public void remove() {
		prog.comRemove(cmd);
	}
}
