package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Checker3000 implements Checker {

    @Override
    public Pattern getPLSQLNamesPattern() {
            String sqlNameRegex="\\b[a-zA-Z][a-zA-Z0-9_$]{0,29}\\b";
        Pattern sqlPattern=Pattern.compile(sqlNameRegex);


        return sqlPattern;
    }

    @Override
    public Pattern getHrefURLPattern() {
          String urlRegex="<\\s*[aA]\\s+[hH][rR][eE][fF]\\s*=\\s*([^\\s>]*|(\"[^\">]*\")*)\\s*\\/?>";
            Pattern urlPattern=Pattern.compile(urlRegex);

        return urlPattern;
    }

    @Override
    public Pattern getEMailPattern() {

        Pattern emailPattern=Pattern.compile("\\b([a-zA-Z0-9][a-zA-Z0-9_.-]{0,21})@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9][.])*(ru|org|net|com)\\b");


        return emailPattern;
    }

    @Override
    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {
        if ((inputString!=null&&pattern==null)||(inputString==null&&pattern!=null)) throw new  IllegalArgumentException("Illegal arg!");
        if ((inputString==null)&&(pattern==null)) {
            return  true;
        }
        return  pattern.matcher(inputString).matches();
    }

    @Override
    public List<String> fetchAllTemplates(StringBuffer inputString, Pattern pattern) throws IllegalArgumentException {
        if ((inputString!=null&&pattern==null)||(inputString==null&&pattern!=null)) throw new  IllegalArgumentException("Illegal arg!");
          List<String> matchedStrings=new ArrayList<>();
        Matcher match=pattern.matcher(inputString);

        while (match.find()){
            matchedStrings.add(match.group());
        }

        return  matchedStrings;
    }
}
