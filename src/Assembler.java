import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by HP PC on 6/27/2017.
 */
public class Assembler {
    private HashMap<String, Instruction> instructionSet = new HashMap<>();
    public Assembler(InputStream resourceAsStream) {
        Scanner scanner = new Scanner(resourceAsStream);
        while (scanner.hasNextLine()) {
            String opcode = scanner.next();
            String mnemonic = scanner.next();
            ArrayList<String> arguments = new ArrayList<>();
            String nextArgument = scanner.next();
            while (
                    nextArgument.equals("R1") ||
                    nextArgument.equals("R2") ||
                    nextArgument.equals("R3") ||
                    nextArgument.equals("R4") ||
                    nextArgument.equals("I") ||
                    nextArgument.equals("I/L") ||
                    nextArgument.equals("SA")
                    ) {
                arguments.add(nextArgument);
                nextArgument = scanner.next();
            }
            scanner.nextLine();
            Instruction instruction = new Instruction(mnemonic, opcode, arguments);
            instructionSet.put(mnemonic, instruction);
        }
    }

    public ArrayList<String> assemble(String fileName) throws FileNotFoundException {
        FileInputStream inputStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(inputStream);
        ArrayList<String> codes = new ArrayList<>();
        String firstToken = scanner.next();
        int codeLine = 0;
        HashMap<String, Integer> symbolTable = new HashMap<>();
        try {
            while (scanner.hasNextLine()) {
                if (!instructionSet.containsKey(firstToken)) {
                    symbolTable.put(firstToken, codeLine);
                    firstToken = scanner.next();
                }
                String mnemonic = firstToken;
                if (!instructionSet.containsKey(mnemonic)) {
                    System.out.println("Invalid mnemonic " + mnemonic);
                    return null;
                }
                if (scanner.hasNextLine())
                    scanner.nextLine();
                codeLine++;
                if (scanner.hasNext())
                    firstToken = scanner.next();
            }
            inputStream = new FileInputStream(fileName);
            scanner = new Scanner(inputStream);
            codeLine = 0;
            while (scanner.hasNextLine()) {
                firstToken = scanner.next();
                if (!instructionSet.containsKey(firstToken)) {
                    firstToken = scanner.next();
                }
                String mnemonic = firstToken;
                Instruction instruction = instructionSet.get(mnemonic);
                codes.add(instruction.code(scanner, symbolTable));
                if (scanner.hasNextLine())
                    scanner.nextLine();
            }
        } catch (Exception ex) {
            System.out.println("Syntax error at line " + (codeLine + 1) + ": " + ex.getMessage());
            // ex.printStackTrace();
            return null;
        }
        scanner.close();
        return  codes;
    }
}
