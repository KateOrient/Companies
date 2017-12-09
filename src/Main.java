import Companies.*;

import java.io.PrintStream;
import java.lang.reflect.Field;

public class Main{
    public static void main (String[] args) throws Exception{
        //Menu.menu();
        Companies companies = new Companies();
        companies.loadFromFile("input.csv");
        companies.processFileSQL("requests.txt");
    }
}
