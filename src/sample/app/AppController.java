package sample.app;

import javafx.scene.control.Label;
import sample.Employee;

import java.awt.*;

public class AppController {

    public Label nameLabel;
    public Label eposition;

    public void getEmployee(Employee employee){
        System.out.println(employee);
        nameLabel.setText(employee.fname+" "+employee.lname);
        eposition.setText(employee.position);
    }
}
