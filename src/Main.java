import Companies.Companies;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Main{
    public static void main (String[] args) throws Exception{
        // PrintStream ps = new PrintStream("logfile.txt");
        long startTime;
        startTime = System.currentTimeMillis();
        Menu.menu();
        //ps.println("Время работы:" + (System.currentTimeMillis() - startTime));
    }
}
