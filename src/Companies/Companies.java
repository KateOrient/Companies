package Companies;

import java.security.InvalidParameterException;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.logging.*;

public class Companies{
    private List<Company> list;
    private static final Logger LOGGER = Logger.getLogger(Companies.class.getName());
    private static final LogManager logManager = LogManager.getLogManager();

    static{
        try{
            logManager.readConfiguration(new FileInputStream("logger.properties"));

        }
        catch (IOException exception){

            LOGGER.log(Level.SEVERE, "Error in loading configuration", exception);
        }

    }

    public Companies (){
        list = new ArrayList<Company>();
        LOGGER.info("Создание объекта Companies завершено.");
    }

    public void loadFromFile (String fileName) throws IOException{
        Scanner scanner = new Scanner(new File(fileName)).useDelimiter(";|\r\n");
        Company c;

        while (scanner.hasNext()){
            c = new Company();
            c.setName(scanner.next());
            c.setShortName(scanner.next());
            String[] date = scanner.next().split("[.]");
            try{
                if (date.length != 3){
                    throw new NumberFormatException();
                }
                c.setDateUpdate(new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])));
            }
            catch (NumberFormatException e){
                c.setDateUpdate(new GregorianCalendar());
            }
            c.setAddress(scanner.next());
            date = scanner.next().split("[.]");
            try{
                if (date.length != 3){
                    throw new NumberFormatException();
                }
                c.setDateFoundation(new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0])));
            }
            catch (NumberFormatException e){
                c.setDateFoundation(new GregorianCalendar());
            }
            try{
                c.setCountEmployees(scanner.nextInt());
            }
            catch (InputMismatchException e){
                c.setCountEmployees(0);
            }
            c.setAuditor(scanner.next());
            c.setPhone(scanner.next());
            c.setEmail(scanner.next());
            c.setBranch(scanner.next());
            c.setActivity(scanner.next());
            c.setLink(scanner.next());
            list.add(c);
        }
        scanner.close();
        LOGGER.info("Считывание с файла " + fileName + " завершено.");
    }

    public void print (){
        for (Company item : list){
            System.out.println(item.toString());
        }
        LOGGER.info("Вывод на экран завершен.");
    }

    public void printXML (String fileName) throws IOException{
        PrintStream ps = new PrintStream(fileName);
        StringBuilder sb = new StringBuilder();
        sb.append("<companies>\n");
        for (Company item : list){
            sb.append("\t<company>\n");
            sb.append("\t\t<name>").append(item.getName()).append("</name>\n");
            sb.append("\t\t<shortname>").append(item.getShortName()).append("</shortname>\n");
            sb.append("\t\t<update>").append(dateToString(item.getDateUpdate())).append("</update>\n");
            sb.append("\t\t<address>").append(item.getAddress()).append("</address>\n");
            sb.append("\t\t<foundation>").append(dateToString(item.getDateFoundation())).append("</foundation>\n");
            sb.append("\t\t<employees>").append(item.getCountEmployees()).append("</employees>\n");
            sb.append("\t\t<auditor>").append(item.getAuditor()).append("</auditor>\n");
            sb.append("\t\t<phone>").append(item.getPhone()).append("</phone>\n");
            sb.append("\t\t<email>").append(item.getEmail()).append("</email>\n");
            sb.append("\t\t<branch>").append(item.getBranch()).append("</branch>\n");
            sb.append("\t\t<activity>").append(item.getActivity()).append("</activity>\n");
            sb.append("\t\t<link>").append(item.getLink()).append("</link>\n");
            sb.append("\t</company>\n");
        }
        sb.append("</companies>");
        ps.print(sb.toString());
        ps.close();
        LOGGER.info("Создание файла " + fileName + " в формате XML завершено.");
    }

    public void printJSON (String fileName) throws IOException{
        PrintStream ps = new PrintStream(fileName);
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("\t\"companies\": [\n");
        int count = 0;
        for (Company item : list){
            sb.append("\t\t{\n");
            sb.append("\t\t\t\"name\": \"").append(item.getName()).append("\",\n");
            sb.append("\t\t\t\"shortname\": \"").append(item.getShortName()).append("\",\n");
            sb.append("\t\t\t\"update\": \"").append(dateToString(item.getDateUpdate())).append("\",\n");
            sb.append("\t\t\t\"address\": \"").append(item.getAddress()).append("\",\n");
            sb.append("\t\t\t\"foundation\": \"").append(dateToString(item.getDateFoundation())).append("\",\n");
            sb.append("\t\t\t\"employees\": ").append(item.getCountEmployees()).append(",\n");
            sb.append("\t\t\t\"auditor\": \"").append(item.getAuditor()).append("\",\n");
            sb.append("\t\t\t\"phone\": \"").append(item.getPhone()).append("\",\n");
            sb.append("\t\t\t\"email\": \"").append(item.getEmail()).append("\",\n");
            sb.append("\t\t\t\"branch\": \"").append(item.getBranch()).append("\",\n");
            sb.append("\t\t\t\"activity\": \"").append(item.getActivity()).append("\",\n");
            sb.append("\t\t\t\"link\": \"").append(item.getLink()).append("\"\n");
            sb.append("\t\t}");
            count++;
            if (count != list.size()){
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("\t]\n");
        sb.append("}");
        ps.print(sb.toString());
        ps.close();
        LOGGER.info("Создание файла " + fileName + " в формате JSON завершено.");
    }

    public void addCompany (Company company){
        list.add(company);
    }

    public int getCount (){
        return list.size();
    }

    public int findShortName (String shortName){
        for (Company item : list){
            if (item.getShortName().equalsIgnoreCase(shortName)){
                return list.indexOf(item);
            }
        }
        LOGGER.info("Поиск по краткому наименованию завершен.");
        return -1;

    }

    public Companies findBranch (String branch){
        list.sort(new Comparator<Company>(){
            @Override
            public int compare (Company o1, Company o2){
                return o1.getBranch().compareToIgnoreCase(o2.getBranch());
            }
        });
        ListIterator<Company> li = list.listIterator();
        Companies sublist = new Companies();
        Company c;
        while (li.hasNext()){
            c = li.next();
            if (c.getBranch().compareToIgnoreCase(branch) == 0){
                sublist.addCompany(c);
            }
            if (sublist.getCount() != 0 && c.getBranch().compareToIgnoreCase(branch) != 0){
                break;
            }
        }
        LOGGER.info("Поиск по отрасли завершен.");
        return sublist;
    }

    public Companies findActivity (String activity){
        list.sort(new Comparator<Company>(){
            @Override
            public int compare (Company o1, Company o2){
                return o1.getActivity().compareToIgnoreCase(o2.getActivity());
            }
        });
        ListIterator<Company> li = list.listIterator();
        Companies sublist = new Companies();
        Company c;
        while (li.hasNext()){
            c = li.next();
            if (c.getActivity().compareToIgnoreCase(activity) == 0){
                sublist.addCompany(c);
            }
            if (sublist.getCount() != 0 && c.getActivity().compareToIgnoreCase(activity) != 0){
                break;
            }
        }
        LOGGER.info("Поиск по виду деятельности завершен.");
        return sublist;
    }

    public Companies findCountEmployees (int from, int to){
        if(from>to){
            throw new InvalidParameterException();
        }
        list.sort(new Comparator<Company>(){
            @Override
            public int compare (Company o1, Company o2){
                return o1.getCountEmployees() - o2.getCountEmployees();
            }
        });
        ListIterator<Company> li = list.listIterator();
        Companies sublist = new Companies();
        Company c;
        while (li.hasNext()){
            c = li.next();
            if (c.getCountEmployees() >= from && c.getCountEmployees() <= to){
                sublist.addCompany(c);
            }
            if (c.getCountEmployees() > to){
                break;
            }
        }
        LOGGER.info("Поиск по числу сотрудников завершен.");
        return sublist;
    }

    public Companies findDateFoundation (GregorianCalendar from, GregorianCalendar to){
        list.sort(new Comparator<Company>(){
                      @Override
                      public int compare (Company o1, Company o2){
                          return o1.getDateFoundation().compareTo(o2.getDateFoundation());
                      }
                  }
        );
        ListIterator<Company> li = list.listIterator();
        Companies sublist = new Companies();
        Company c;
        while (li.hasNext()){
            c = li.next();
            if (c.getDateFoundation().compareTo(from) >= 0 && c.getDateFoundation().compareTo(to) <= 0){
                sublist.addCompany(c);
            }
            if (c.getDateFoundation().compareTo(to) > 0){
                break;
            }
        }
        LOGGER.info("Поиск по дате основания завершен.");
        return sublist;
    }

    public Company getCompany(int indx){
        if(indx<0||indx>=list.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        return list.get(indx);
    }

    public void clear(){
        list.clear();
    }

    public static String dateToString(GregorianCalendar date){
        StringBuilder sb = new StringBuilder().append(date.get(5)).append(".").append(date.get(2)).append(".").append(date.get(1));
        return sb.toString();
    }

    public static GregorianCalendar dateFromString(String dateStr){
        int[] date = new int[3];
        StringTokenizer st = new StringTokenizer(dateStr,".");
        try{
            date[2] = Integer.parseInt(st.nextToken());
            date[1] = Integer.parseInt(st.nextToken());
            date[0] = Integer.parseInt(st.nextToken());
        }
        catch (NumberFormatException e){
            System.out.println("Wrong date format.");
        }
        return new GregorianCalendar(date[0],date[1],date[2]);
    }
}
