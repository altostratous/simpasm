import com.sun.xml.internal.ws.api.pipe.ClientTubeAssemblerContext;
import jdk.internal.util.xml.impl.Input;
import sun.applet.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpASM {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("java SimpASM [input file name] [output file name]");
            return;
        }
        Assembler assembler = new Assembler(Main.class.getResourceAsStream("/simpu.scheme"));
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file name: " + args[0]);
            return;
        }
        ArrayList<String> codes = null;
        try {
            codes = assembler.assemble(args[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream printStream = null;
        try {
            printStream = new PrintStream(new FileOutputStream(args[1]));
        } catch (FileNotFoundException e) {
            System.out.println("Bad output file name.");
        }
        if (codes != null) {
            Scanner scanner = new Scanner(Main.class.getResourceAsStream("/template.v"));
            String line = scanner.nextLine();
            while (!line.equals("{MEMORY}")){
                printStream.println(line);
                line = scanner.nextLine();
            }
            int counter = 0;
            for (String code :
                    codes) {
                printStream.printf("\t\t\tmem[%d] <=32'b%s_%s;", counter, code.substring(0, 16), code.substring(16, 32));
                printStream.println();
                counter++;
            }
            while (scanner.hasNextLine())
                printStream.println(scanner.nextLine());
        }
        printStream.close();
    }
}
