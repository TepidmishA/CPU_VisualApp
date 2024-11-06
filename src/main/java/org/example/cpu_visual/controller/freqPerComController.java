package org.example.cpu_visual.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.cpu_visual.program.Task;

public class freqPerComController {
	@FXML
	Label type;
	@FXML
	Label count;

	public void setType(String type) {
		this.type.setText(type);
	}

	public void setCount(String count) {
		this.count.setText(count);
	}
}
