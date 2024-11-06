package org.example.cpu_visual.program;
import org.example.cpu_visual.cpu.ICPU;


public class Executer {
    ICPU cpu;

    public void run(Program prog) {
        try {
            for (Command command : prog) {
                cpu.run(command);
            }
        }
        catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    public Executer(ICPU cpu) {
        this.cpu = cpu;
    }
}
