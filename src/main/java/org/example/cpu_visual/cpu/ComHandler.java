package org.example.cpu_visual.cpu;

import org.example.cpu_visual.program.Command;

public class ComHandler {
    ComHandler next;

    void run(Command command, CPU cpu) throws Exception {
        if (next != null) {
            next.run(command, cpu);
        }
        else {
            throw new Exception("Can't do task");
        }
    }

    ComHandler add(ComHandler next) {
        this.next = next;
        return next;
    }
}
