# simpasm
This project is the assembler for the simple cpu project simpu.

## Installation and usage
You can follow the following to install and use the assembler. The output file can be used as the instmemory.v file in the simpu project.

    # Installing the assembler
    git clone https://github.com/altostratous/simpasm.git .
    cd src
    javac SimpASM.java
    # Assembling sample.asm file into instmemory.v file
    java SimpASM sample.asm instmemory.v