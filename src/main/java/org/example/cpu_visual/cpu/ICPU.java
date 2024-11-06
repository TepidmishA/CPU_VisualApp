package org.example.cpu_visual.cpu;

import org.example.cpu_visual.program.Command;

public interface ICPU {
    // ???
    int[] getMem();
    int[] getRegs();

    void run(Command command) throws Exception;
    void reset();
}
