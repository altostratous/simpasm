# simpasm
This project is the assembler for the simple cpu project simpu which is not published yet and under development.

## Installation and usage
You can follow the following to install and use the assembler. The output file can be used as the instmemory.v file in the simpu project.

    # Installing the assembler
    git clone https://github.com/altostratous/simpasm.git .
    cd src
    javac SimpASM.java
    # Assembling sample.asm file into instmemory.v file
    java SimpASM sample.asm instmemory.v
    
In an advanced scenario if you want to have your own instruction set you can make some changes in the simpu.scheme file. The output file template is also customizable by changing the template.v file. 
  
If these two configuration files didn't help feel free to make PRs or send issues as feature requests or bug reports.
    
## Assembly
Here's a sample assembly program to be assembled using this assembler. 

    start       ADD     $1  $1  $1
                BEQ     $8  $10 start
    label       MUL     $15 $16 $17 $18
                BNEQ    $8  $10 label
                BNEQ    $8  $10 2
                SLL     $31 $30 5