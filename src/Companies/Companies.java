package Companies;

import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.logging.*;
import java.util.stream.Collectors;

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
    }

    public void loadFromFile (String fileName) throws IOException{
        Scanner scanner = new Scanner(new File(fileName)).useDelimiter(";|\r\n");
        Company c;

        while (scanner.hasNext()){
            c = new Company();
            c.setName(scanner.next());
            c.setShortName(scanner.next());
            c.setDateUpdate(Companies.dateFromString(scanner.next()));
            c.setAddress(scanner.next());
            c.setDateFoundation(Companies.dateFromString(scanner.next()));
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
        Field[] fields = Company.class.getDeclaredFields();
        sb.append("<companies>\n");
        for (Company item : list){
            sb.append("\t<company>\n");
            for(Field field:fields){
                try{
                    if (!(item.getFieldByName(field.getName()).equals("") ||
                            item.getFieldByName(field.getName()).equals(dateToString(dateFromString("0.0.0"))) ||
                            item.getFieldByName(field.getName()).equals("0"))){
                        sb.append("\t\t<").append(field.getName()).append(">").append(item.getFieldByName(field.getName()));
                        sb.append("</").append(field.getName()).append(">\n");
                    }
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
            sb.append("\t</company>\n");
        }
        sb.append("</companies>");
        ps.print(sb.toString());
        ps.close();
    }

    public void printJSON (String fileName) throws IOException{
        PrintStream ps = new PrintStream(fileName);
        StringBuilder sb = new StringBuilder();
        Field[] fields = Company.class.getDeclaredFields();
        sb.append("{\n");
        sb.append("\t\"companies\": [\n");
        int count = 0;
        for (Company item : list){
            sb.append("\t\t{\n");
            for(Field field: fields){
                try{
                    if (!(item.getFieldByName(field.getName()).equals("") ||
                            item.getFieldByName(field.getName()).equals(dateToString(dateFromString("0.0.0"))) ||
                            item.getFieldByName(field.getName()).equals("0"))){
                        sb.append("\t\t\t\"").append(field.getName()).append("\": \"").append(item.getFieldByName(field.getName()));
                        sb.append("\"");
                        if(!field.equals(fields[fields.length-1])){
                            sb.append(",");
                        }
                        sb.append("\n");
                    }
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
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
    }

    public void addCompany (Company company){
        list.add(company);
    }

    public int getCount (){
        return list.size();
    }

    public Companies findShortName (String shortName){
        Companies res = new Companies();
        list.stream().filter(item -> item.getShortName().equalsIgnoreCase(shortName)).forEach(item -> res.addCompany(item));
        LOGGER.info("Поиск по краткому наименованию. Найдено команий: " + res.getCount());
        return res;
    }

    public Companies findBranch (String branch){
        Companies res = new Companies();
        list.stream().filter(item -> item.getBranch().equalsIgnoreCase(branch)).forEach(item -> res.addCompany(item));
        LOGGER.info("Поиск по отрасли. Найдено команий: " + res.getCount());
        return res;
    }

    public Companies findActivity (String activity){

        Companies res = new Companies();
        list.stream().filter(item -> item.getActivity().equalsIgnoreCase(activity)).forEach(item -> res.addCompany(item));
        LOGGER.info("Поиск по отрасли. Найдено команий: " + res.getCount());
        return res;
    }

    public Companies findCountEmployees (int from, int to){
        if (from > to){
            throw new InvalidParameterException();
        }

        Companies res = new Companies();
        list.stream().filter(item -> item.getCountEmployees() >= from && item.getCountEmployees() <= to).forEach(item -> res.addCompany(item));
        LOGGER.info("Поиск по числу сотрудников. Найдено команий: " + res.getCount());
        return res;
    }

    public Companies findDateFoundation (Date from, Date to){
        if (from.after(to)){
            throw new InvalidParameterException();
        }
        Companies res = new Companies();
        list.stream().filter(item -> item.getDateFoundation().after(from) && item.getDateFoundation().before(to)).forEach(item -> res.addCompany(item));
        LOGGER.info("Поиск по дате основания. Найдено команий: " + res.getCount());
        return res;
    }

    public Company getCompany (int indx){
        if (indx < 0 || indx >= list.size()){
            throw new ArrayIndexOutOfBoundsException();
        }
        return list.get(indx);
    }

    public void clear (){
        list.clear();
    }

    public static String dateToString (Object obj){
        Date date = (Date)obj;
        StringBuilder sb = new StringBuilder().append(date.getDay()).append(".").append(date.getMonth()).append(".").append(date.getYear());
        return sb.toString();
    }

    public static Date dateFromString (String str){
        Date date = new Date();
        StringTokenizer st = new StringTokenizer(str, ".");
        try{
            date.setDate(Integer.parseInt(st.nextToken()));
            date.setMonth(Integer.parseInt(st.nextToken()));
            date.setYear(Integer.parseInt(st.nextToken()));
        }
        catch (Exception e){
            date.setDate(0);
            date.setMonth(0);
            date.setYear(0);
        }
        return date;
    }

    private void clearFields(List<String> fields){
        if(fields.contains("name")) list.forEach(c->c.setName(""));
        if(fields.contains("shortname")) list.forEach(c->c.setShortName(""));
        if(fields.contains("dateupdate")) list.forEach(c->c.setDateUpdate(Companies.dateFromString("0.0.0")));
        if(fields.contains("adress")) list.forEach(c->c.setAddress(""));
        if(fields.contains("datefoundation")) list.forEach(c->c.setDateFoundation(Companies.dateFromString("0.0.0")));
        if(fields.contains("countemployees")) list.forEach(c->c.setCountEmployees(0));
        if(fields.contains("auditor")) list.forEach(c->c.setAuditor(""));
        if(fields.contains("phone")) list.forEach(c->c.setPhone(""));
        if(fields.contains("email")) list.forEach(c->c.setEmail(""));
        if(fields.contains("branch")) list.forEach(c->c.setBranch(""));
        if(fields.contains("activity")) list.forEach(c->c.setActivity(""));
        if(fields.contains("link")) list.forEach(c->c.setLink(""));
    }

    private void clearAllFieldsExcept(List<String> fields){
        if(!fields.contains("name")) list.forEach(c->c.setName(""));
        if(!fields.contains("shortname")) list.forEach(c->c.setShortName(""));
        if(!fields.contains("dateupdate")) list.forEach(c->c.setDateUpdate(Companies.dateFromString("0.0.0")));
        if(!fields.contains("adress")) list.forEach(c->c.setAddress(""));
        if(!fields.contains("datefoundation")) list.forEach(c->c.setDateFoundation(Companies.dateFromString("0.0.0")));
        if(!fields.contains("countemployees")) list.forEach(c->c.setCountEmployees(0));
        if(!fields.contains("auditor")) list.forEach(c->c.setAuditor(""));
        if(!fields.contains("phone")) list.forEach(c->c.setPhone(""));
        if(!fields.contains("email")) list.forEach(c->c.setEmail(""));
        if(!fields.contains("branch")) list.forEach(c->c.setBranch(""));
        if(!fields.contains("activity")) list.forEach(c->c.setActivity(""));
        if(!fields.contains("link")) list.forEach(c->c.setLink(""));
    }

    public Companies processSQL (String query) throws Exception{
        List<String> queryWords = Arrays.asList(query.toLowerCase().split("[\\s,']+"));
        List<String> cols;
        Companies sublist = new Companies();
        if (query.toLowerCase().matches("select .+ from database where .+")){
            if(queryWords.indexOf("shortname")>queryWords.indexOf("where")){
                sublist = findShortName(queryWords.get(queryWords.indexOf("=")+1));
            }
            else if(queryWords.indexOf("activity")>queryWords.indexOf("where")){
                sublist = findActivity(queryWords.get(queryWords.indexOf("=")+1));
            }
            else if(queryWords.indexOf("countemployees")>queryWords.indexOf("where")){
                sublist = findCountEmployees(Integer.parseInt(queryWords.get(queryWords.indexOf("between")+1)),Integer.parseInt(queryWords.get(queryWords.indexOf("between")+2)));
            }
            if(!queryWords.contains("*")){
                cols = queryWords.stream().filter(s->(queryWords.indexOf(s)<queryWords.indexOf("from")&&queryWords.indexOf(s)!=0)).collect(Collectors.toList());
                sublist.clearAllFieldsExcept(cols);
            }
            return sublist;
        }
        LOGGER.warning("Wrong SQL query.");
        throw new Exception("Wrong SQL query.");
    }

    public void processFileSQL (String fileName) throws Exception{
        Scanner scanner = new Scanner(new File(fileName));
        Companies companies;
        StringBuilder sb = new StringBuilder("request");
        int indxFile = 1;
        while (scanner.hasNextLine()){
            companies = processSQL(scanner.nextLine());
            sb.append(indxFile).append(".json");
            companies.printJSON(sb.toString());
            sb.delete(sb.length() - ".json".length(), sb.length()).append(".xml");
            companies.printXML(sb.toString());
            sb.delete("request".length(), sb.length());
            indxFile++;
        }
    }
}
