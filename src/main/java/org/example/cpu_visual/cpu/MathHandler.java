package org.example.cpu_visual.cpu;

import org.example.cpu_visual.program.Command;

public class MathHandler extends ComHandler {
    @Override
    void run(Command command, CPU cpu) throws Exception {
        switch (command.getTask()) {
            case add:
                cpu.add();
                break;
            case sub:
                cpu.sub();
                break;
            case mul:
                cpu.mul();
                break;
            case div:
                cpu.div();
                break;
            default:
                super.run(command, cpu);
        }
    }
}
