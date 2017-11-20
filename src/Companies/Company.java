package Companies;

import java.util.*;

public class Company{
    private String name;
    private String shortName;
    private GregorianCalendar dateUpdate;
    private String address;
    private GregorianCalendar dateFoundation;
    private int countEmployees;
    private String auditor;
    private String phone;
    private String email;
    private String branch;
    private String activity;
    private String link;

    public Company (
            String name, String shortName, GregorianCalendar dateUpdate, String address, GregorianCalendar dateFoundation,
            int countEmployees, String auditor, String phone, String email, String branch, String activity, String link){
        this.name = name;
        this.shortName = shortName;
        this.dateUpdate = dateUpdate;
        this.address = address;
        this.dateFoundation = dateFoundation;
        this.countEmployees = countEmployees;
        this.auditor = auditor;
        this.phone = phone;
        this.email = email;
        this.branch = branch;
        this.activity = activity;
        this.link = link;
    }
    public Company(){}

    public void setName (String name){
        this.name = name;
    }

    public void setShortName (String shortName){
        this.shortName = shortName;
    }

    public void setDateUpdate (GregorianCalendar dateUpdate){
        this.dateUpdate = dateUpdate;
    }

    public void setAddress (String address){
        this.address = address;
    }

    public void setDateFoundation (GregorianCalendar dateFoundation){
        this.dateFoundation = dateFoundation;
    }

    public void setCountEmployees (int countEmployees){
        this.countEmployees = countEmployees;
    }

    public void setAuditor (String auditor){
        this.auditor = auditor;
    }

    public void setActivity (String activity){
        this.activity = activity;
    }

    public void setBranch (String branch){
        this.branch = branch;
    }

    public void setEmail (String email){
        this.email = email;
    }

    public void setLink (String link){
        this.link = link;
    }

    public void setPhone (String phone){
        this.phone = phone;
    }

    public String getName (){
        return name;
    }

    public int getCountEmployees (){
        return countEmployees;
    }

    public GregorianCalendar getDateUpdate (){
        return dateUpdate;
    }

    public String getShortName (){
        return shortName;
    }

    public String getAddress (){
        return address;
    }

    public String getAuditor (){
        return auditor;
    }

    public String getActivity (){
        return activity;
    }

    public String getBranch (){
        return branch;
    }

    public GregorianCalendar getDateFoundation (){
        return dateFoundation;
    }

    public String getEmail (){
        return email;
    }

    public String getLink (){
        return link;
    }

    public String getPhone (){
        return phone;
    }

    @Override
    public String toString (){
        StringBuilder sb = new StringBuilder(name).append(";").append(shortName).append(";").append(dateUpdate.get(5)).append(".");
        sb.append(dateUpdate.get(2)).append(".").append(dateUpdate.get(1)).append(";");
        sb.append(address).append(";").append(dateFoundation.get(5)).append(".");
        sb.append(dateFoundation.get(2)).append(".").append(dateFoundation.get(1)).append(";");
        sb.append(countEmployees).append(";").append(auditor).append(";").append(phone).append(";");
        sb.append(email).append(";").append(branch).append(";").append(activity).append(";").append(link);

        return sb.toString();
    }
}
