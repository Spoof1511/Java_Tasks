package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
public static void testAllSqlTemplates(){
    Checker3000 gosql=new Checker3000();
    List<String> sqlList=new ArrayList<String>();
     StringBuffer stringBuffer=new StringBuffer("asd   _sa3q  alan scks  ");
    sqlList.add("asd");
    sqlList.add("alan");
    sqlList.add("scks");

    boolean result=(sqlList).equals(gosql.fetchAllTemplates(stringBuffer,gosql.getPLSQLNamesPattern()));
    if (result==true){
        System.out.println("Тест sql прошел!");
    }
    else {
        System.out.println("Тест sql не прошел! ");
    }
}

public static void  testAllEmailTemplates(){
    Checker3000 gomail=new Checker3000();
    List<String> emailList=new ArrayList<String>();
    StringBuffer stringBuffer=new StringBuffer("alan@edu.hse.ru asdqw2@u d rew school@moscow.org adqw q");
    emailList.add("alan@edu.hse.ru");
    emailList.add("school@moscow.org");

    boolean result= (emailList).equals(gomail.fetchAllTemplates(stringBuffer,gomail.getEMailPattern()));
    if (result==true){
        System.out.println("Тест email прошел!");
    }
    else {
        System.out.println("Тест email не прошел!");
    }
}
public static void testAllUrlTemplates(){
Checker3000 goUrl=new Checker3000();
    List<String >urllist=new ArrayList<String >();
    StringBuffer stringBuffer=new StringBuffer("< a Href = sdasdas > , asd a, <ahref = \"sad\"  >   <a HREF = \" adas dasd\"/>");
    urllist.add("< a Href = sdasdas >");
    urllist.add("<a HREF = \" adas dasd\"/>");

    boolean result=(urllist).equals(goUrl.fetchAllTemplates(stringBuffer,goUrl.getHrefURLPattern()));
    if (result==true){
        System.out.println("Тест url прошел!");
    }
    else {
        System.out.println("Тест url не прошел!");
    }
}

public static void testSql(){
    Checker3000 gosql=new Checker3000();
    String[]sqlArr=new String[5];
    sqlArr[0]="sadsad";
    sqlArr[1]="_323da";
    sqlArr[2]="asd93-asd";
    sqlArr[3]="qwe23er";
    sqlArr[4]="sdsd_3d2";
    for (int i=0;i<5;i++){
        if (gosql.checkAccordance(sqlArr[i],gosql.getPLSQLNamesPattern())==true){
            System.out.println("Sql "+sqlArr[i]+" прошел тест ");
        }
        else {
            System.out.println("Sql "+sqlArr[i]+" не прошел тест ");

        }
    }

}
public static void testEmail(){
    Checker3000 goEmail=new Checker3000();
    String[]emailArr=new String[5];
    emailArr[0]="alan@edu.hse.ru";
    emailArr[1]="123@der.org";
    emailArr[2]="_asd@h.ru";
    emailArr[3]="sadsad@.ru";
    emailArr[4]="qwe@as.net";
    for (int i=0;i<5;i++){
        if (goEmail.checkAccordance(emailArr[i],goEmail.getEMailPattern())==true){
            System.out.println("Email "+emailArr[i]+" прошел тест ");
        }
        else {
            System.out.println("Email "+emailArr[i]+" не прошел тест ");
        }
    }
}
public static void testUrl(){
    Checker3000 goUrl=new Checker3000();
    String[]urlArr=new String[5];
    urlArr[0]="<a href = sadasd/>";
    urlArr[1]="< a HReF = \" dssd sdvsd sv35 334\">";
    urlArr[2]="< ahref=gfhfh>";
    urlArr[3]="< A hreF =   ffdsdf>";
    urlArr[4]="< a href = \"hgjgjg\"  yy />";
    for (int i=0;i<5;i++){
        if (goUrl.checkAccordance(urlArr[i],goUrl.getHrefURLPattern())==true){
            System.out.println("Url "+urlArr[i]+" прошел тест ");
        }
        else {
            System.out.println("Url "+urlArr[i]+" не прошел тест ");
        }
    }
}
    public static   void main(String[] args) {
        testAllSqlTemplates();
        testAllEmailTemplates();
        testAllUrlTemplates();
        testSql();
        testEmail();
        testUrl();
    }
}
