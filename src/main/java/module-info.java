open module org.example.cpu_visual {
    requires javafx.controls;
    requires javafx.fxml;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires java.sql;
	requires org.hibernate.orm.core;
	requires java.persistence;

	requires java.naming;
	requires sqlite.dialect;

//    opens org.example.cpu_visual to javafx.fxml;
    exports org.example.cpu_visual;
    exports org.example.cpu_visual.controller;
//    opens org.example.cpu_visual.controller to javafx.fxml;
	exports org.example.cpu_visual.controller.cpu;
//	opens org.example.cpu_visual.controller.cpu to javafx.fxml;
	exports org.example.cpu_visual.controller.program;
//	opens org.example.cpu_visual.controller.program to javafx.fxml;
}