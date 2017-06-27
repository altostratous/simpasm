import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by HP PC on 6/27/2017.
 */
public class Instruction {
    private String nmemonic, opcode;
    private ArrayList<String> arguments;
    public Instruction(String mnemonic, String opcode, ArrayList<String> arguments) {
        this.nmemonic = mnemonic;
        this.opcode = opcode;
        this.arguments = arguments;
    }

    public String code(Scanner scanner, HashMap<String, Integer> symbolTable) throws Exception {
        String code = opcode +"000000000000000000000000000";
        for (String argument :
                arguments) {
            if (argument.startsWith("R")) {
                String registerAddress = scanner.next();
                if (!registerAddress.startsWith("$")) {
                    throw new Exception("Bad formed argument " + argument);
                }
                registerAddress = registerAddress.substring(1, registerAddress.length());
                int registerAddressNumber = Integer.parseInt(registerAddress);
                if (registerAddressNumber < 0 || registerAddressNumber > 31) {
                    throw new Exception("Register number should be between 1 and 31");
                }
                String registerBinaryAddress = Integer.toBinaryString(registerAddressNumber);
                if (registerBinaryAddress.length() != 32)
                    registerBinaryAddress ="0000000000000000000" + registerBinaryAddress;
                registerBinaryAddress = registerBinaryAddress.substring(registerBinaryAddress.length() - 5, registerBinaryAddress.length());
                if (argument.equals("R1")) {
                    code = place(code, registerBinaryAddress, 26, 22);
                } else if (argument.equals("R2")) {
                    code = place(code, registerBinaryAddress, 21, 17);
                } else if (argument.equals("R3")) {
                    code = place(code, registerBinaryAddress, 16, 12);

                } else if (argument.equals("R4")) {
                    code = place(code, registerBinaryAddress, 11, 7);

                }
            } else {
                Integer integerValue = 0;
                if (argument.equals("I") || argument.equals("SA")) {
                    integerValue = scanner.nextInt();
                } else if (argument.equals("I/L")) {
                    String value = scanner.next();
                    if (symbolTable.containsKey(value)) {
                        integerValue = symbolTable.get(value);
                    } else {
                        integerValue = Integer.parseInt(value);
                    }
                }
                String binary = "00000000000000000000000000000000" + Integer.toBinaryString(integerValue);
                int length = 16;
                if (argument.equals("SA"))
                    length = 5;
                binary = binary.substring(binary.length() - length, binary.length());
                if (argument.equals("SA"))
                    code = place(code, binary, 6, 2);
                else
                    code = place(code, binary, 16, 1);
            }
        }
        return code;
    }

    private String place(String code, String registerBinaryAddress, int startIndex, int endIndex) {
        int innerStartIndex = 31 - startIndex;
        int innerEndIndex = 31 - endIndex;
        return code.substring(0, innerStartIndex) + registerBinaryAddress + code.substring(innerEndIndex + 1, code.length());
    }
}
