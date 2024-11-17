package org.example.cpu_visual.controller.program;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.cpu_visual.VisualApp;
import org.example.cpu_visual.controller.addCommandDialogController;
import org.example.cpu_visual.controller.comController;
import org.example.cpu_visual.controller.freqPerComController;
import org.example.cpu_visual.controller.mainComsController;
import org.example.cpu_visual.cpu.BCPU;
import org.example.cpu_visual.cpu.ICPU;
import org.example.cpu_visual.program.BProgram;
import org.example.cpu_visual.program.Command;
import org.example.cpu_visual.program.Executer;
import org.example.cpu_visual.program.Program;

import java.io.IOException;

public class mainController implements IObserver {
	Program prog = BProgram.build();

	@FXML
	GridPane allComs;

	@FXML
	private VBox leftVBox;
	@FXML
	private VBox rightVBox;

	private void addWindow(VBox container, String name) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(VisualApp.class.getResource(name));
		Pane pane = fxmlLoader.load();
		container.getChildren().add(pane);
		prog.addObserver(fxmlLoader.getController());
	}

	private void initVBox() {
		FXMLLoader fxmlLoader;
		Pane pane;
		try {
			addWindow(rightVBox, "windows/regs.fxml");
			addWindow(rightVBox, "windows/mems.fxml");
			addWindow(rightVBox, "windows/frequency.fxml");

			fxmlLoader = new FXMLLoader(VisualApp.class.getResource("windows/main_controls.fxml"));
			pane = fxmlLoader.load();
			leftVBox.getChildren().add(pane);

			fxmlLoader = new FXMLLoader(VisualApp.class.getResource("windows/coms_grid.fxml"));
			ScrollPane scrPane = fxmlLoader.load();
			leftVBox.getChildren().add(scrPane);
			allComs = (GridPane) fxmlLoader.getNamespace().get("allComs");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void initProg() {
		try {
			prog.addCom(new Command("init 10 20"));
			prog.addCom(new Command("init" ,"11", "25"));
			prog.addCom(new Command("ld", "a" ,"10"));
			prog.addCom(new Command("ld", "b" ,"11"));
			prog.addCom(new Command("ld", "c" ,"11"));
			prog.addCom(new Command("add"));
			prog.addCom(new Command("mv", "a" ,"d"));
			prog.addCom(new Command("add"));
			prog.addCom(new Command("print"));
		}
		catch (Exception err) {
			System.out.println(err.getMessage());
		}
	}

	@FXML
	private void initialize() {
		prog.addObserver(this);

		initProg();
		initVBox();
		event(prog);
	}

	@Override
	public void event(Program prog) {
		if (allComs != null) {
			allComs.getChildren().clear();

			Command currCom = prog.getCurrCom();
			for (Command c: prog) {
				comController cc = new comController(c);

				FXMLLoader fxmlLoader = new FXMLLoader(
						VisualApp.class.getResource("command_view.fxml"));
				fxmlLoader.setController(cc);
				try {
					Pane pane = fxmlLoader.load();
					if (currCom == c) cc.setStatus();

					cc.setCom(c);
					allComs.addColumn(0, pane);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
