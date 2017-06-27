start       ADD     $1  $1  $1
            BEQ     $8  $10 start
label       MUL     $15 $16 $17 $18
            BNEQ    $8  $10 label
            BNEQ    $8  $10 2
            SLL     $31 $30 5
