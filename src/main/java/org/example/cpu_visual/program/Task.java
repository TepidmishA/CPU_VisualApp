package org.example.cpu_visual.program;

public enum Task {
    ld,     // <reg> <address>
    st,     // <reg> <address>
    mv,     // move <reg> <reg>
    init,   // <address> <val>
    print,
    add,    // d = a + b
    sub,    // d = a - b
    mul,    // d = a * b
    div     // d = a / b
}
