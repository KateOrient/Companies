import Companies.Companies;

import javax.xml.bind.SchemaOutputResolver;
import java.io.*;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Menu{
    private static Companies list = new Companies();
    private static Companies sublist = new Companies();

    public static void menu () throws IOException{
        int request;
        Scanner scanner = new Scanner(System.in);
        boolean work = true;

        System.out.println("Enter input file name:");
        try{
            list.loadFromFile(scanner.nextLine() + ".csv");
        }
        catch (FileNotFoundException e){
            System.out.println("File nor found.");
            work = false;
        }
        while (work){
            printMenu();
            try{
                request = scanner.nextInt();
                switch (request){
                    case 1:{
                        findShortName();
                        break;
                    }
                    case 2:{
                        findBranch();
                        break;
                    }
                    case 3:{
                        findActivity();
                        break;
                    }
                    case 4:{
                        findCountEmployees();
                        break;
                    }
                    case 5:{
                        findDateFoundation();
                        break;
                    }
                    case 6:{
                        work = false;
                        break;
                    }
                    default:{
                        throw new NumberFormatException();
                    }
                }
            }
            catch (NumberFormatException e){
                System.out.println("Wrong request.");
            }
        }
        scanner.close();
    }

    private static void printMenu (){
        System.out.println("МЕНЮ");
        System.out.println("1 -- Выбрать компании по краткому наименованию");
        System.out.println("2 -- Выбрать компании по отрали");
        System.out.println("3 -- Выбрать компании по виду деятельности");
        System.out.println("4 -- Выбрать компании по числу сотрудников");
        System.out.println("5 -- Выбрать компании по дате основания");
        System.out.println("6 -- Завершить работу приложения");
    }

    private static void findShortName () throws IOException{
        String fileName;
        String shortName;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter output file name:");
        fileName = scanner.nextLine();

        System.out.println("Enter shortname:");
        shortName = scanner.nextLine();

        sublist.clear();
        int indx = list.findShortName(shortName);
        if(indx!=-1){
            sublist.addCompany(list.getCompany(indx));
            sublist.printJSON(fileName + ".json");
            sublist.printXML(fileName + ".xml");
        }
    }

    private static void findBranch () throws IOException{
        String fileName;
        String branch;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter output file name:");
        fileName = scanner.nextLine();

        System.out.println("Enter branch:");
        branch = scanner.nextLine();

        sublist.clear();
        sublist = list.findBranch(branch);
        sublist.printJSON(fileName + ".json");
        sublist.printXML(fileName + ".xml");
    }

    private static void findActivity () throws IOException{
        String fileName;
        String activity;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter output file name:");
        fileName = scanner.nextLine();

        System.out.println("Enter activity:");
        activity = scanner.nextLine();

        sublist.clear();
        sublist = list.findActivity(activity);
        sublist.printJSON(fileName + ".json");
        sublist.printXML(fileName + ".xml");
    }

    private static void findCountEmployees () throws IOException{
        String fileName;
        int from, to;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter output file name:");
        fileName = scanner.nextLine();

        System.out.println("Enter employees count employees interval:");
        from = scanner.nextInt();
        to = scanner.nextInt();

        sublist.clear();
        sublist = list.findCountEmployees(from, to);
        sublist.printJSON(fileName + ".json");
        sublist.printXML(fileName + ".xml");
    }

    private static void findDateFoundation () throws IOException{
        String fileName;
        String dateFrom;
        String dateTo;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter output file name:");
        fileName = scanner.nextLine();

        scanner.useDelimiter(".");
        System.out.println("Enter foundation date interval:");
        dateFrom = scanner.nextLine();
        dateTo = scanner.nextLine();

        sublist.clear();
        sublist = list.findDateFoundation(Companies.dateFromString(dateFrom),Companies.dateFromString(dateTo));
        sublist.printJSON(fileName + ".json");
        sublist.printXML(fileName + ".xml");
    }
}
