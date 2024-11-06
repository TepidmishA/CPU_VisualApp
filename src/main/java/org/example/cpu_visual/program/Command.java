package org.example.cpu_visual.program;

import org.example.cpu_visual.exception.number_of_values;

public class Command {
    private Task task;
    private int val1, val2;

    private void StrToVals(String str1, String str2) {
        try {
            val1 = Integer.parseInt(str1);
        }
        catch (NumberFormatException err) {
            val1 = str1.charAt(0) - 97;
        }

        try {
            val2 = Integer.parseInt(str2);
        }
        catch (NumberFormatException err) {
            val2 = str2.charAt(0) - 97;
        }
    }

    public Task getTask() {
        return task;
    }

    public int getVal1() {
        return val1;
    }

    public int getVal2() {
        return val2;
    }

    public Command(String task) throws number_of_values {
        if (task.contains(" ")) {                           // "init 10 20"
            String[] parts = task.split(" ");

            if (parts.length == 3) {
                this.task = Task.valueOf(parts[0]);

                StrToVals(parts[1], parts[2]);
            }
            else {
                throw new number_of_values();
            }
        }
        else {                                              // "add"
            this.task = Task.valueOf(task);
        }
    }

    public Command(Task task) {                             // "add"
        this.task = task;
    }

    public Command(Task task, String num1, String num2) {   // "init", "10", "20"
        this.task = task;

        StrToVals(num1, num2);
    }

    public Command(String task, String num1, String num2) {   // "init", "10", "20"
        this.task = Task.valueOf(task);;

        StrToVals(num1, num2);
    }

    @Override
    public String toString() {
        return "Command{" +
                "task=" + task +
                ", val1=" + val1 +
                ", val2=" + val2 +
                '}';
    }
}
