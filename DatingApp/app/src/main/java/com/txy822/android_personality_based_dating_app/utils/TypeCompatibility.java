package com.txy822.android_personality_based_dating_app.utils;
import java.util.ArrayList;
import java.util.HashMap;

public class TypeCompatibility {
    private String userPersonalityType;
    private String matchPersonalityType;
    private  HashMap<String, Integer> INTJ_map;
    private  HashMap<String, Integer> INFJ_map;
    private  HashMap<String, Integer> ISFJ_map;
    private  HashMap<String, Integer> ISTJ_map;
    private  HashMap<String, Integer> INTP_map;
    private  HashMap<String, Integer> INFP_map;
    private  HashMap<String, Integer> ISFP_map;
    private  HashMap<String, Integer> ISTP_map;
    private  HashMap<String, Integer> ENTP_map;
    private  HashMap<String, Integer> ENFP_map;
    private  HashMap<String, Integer> ESFP_map;
    private  HashMap<String, Integer> ESTP_map;
    private  HashMap<String, Integer> ENTJ_map;
    private  HashMap<String, Integer> ENFJ_map;
    private  HashMap<String, Integer> ESFJ_map;
    private  HashMap<String, Integer> ESTJ_map;

//this class is to be done one database of sqlite or firebase, it is rough work
    public  TypeCompatibility(){
        this.userPersonalityType = userPersonalityType;
        this.INTJ_map=new HashMap<>();
        this.INFJ_map=new HashMap<>();
        this.ISFJ_map=new HashMap<>();
        this.ISTJ_map=new HashMap<>();
        this.INTP_map=new HashMap<>();
        this.INFP_map=new HashMap<>();
        this.ISFP_map=new HashMap<>();
        this.ISTP_map=new HashMap<>();
        this.ENTP_map=new HashMap<>();
        this.ENFP_map=new HashMap<>();
        this.ESFP_map=new HashMap<>();
        this.ESTP_map=new HashMap<>();
        this.ENTJ_map=new HashMap<>();
        this.ENFJ_map=new HashMap<>();
        this.ESFJ_map=new HashMap<>();
        this.ESTJ_map=new HashMap<>();

        this.matchPersonalityType = matchPersonalityType;
//        this.map=new HashMap();
    }
    public TypeCompatibility(String userPersonalityType, String matchPersonalityType) {
        this.userPersonalityType = userPersonalityType;

        this.matchPersonalityType = matchPersonalityType;
//        this.map=new HashMap();
    }

    public String getUserPersonalityType() {
        return userPersonalityType;
    }

    public void setUserPersonalityType(String userPersonalityType) {
        this.userPersonalityType = userPersonalityType;
    }

    public String getMatchPersonalityType() {
        return matchPersonalityType;
    }

    public void setMatchPersonalityType(String matchPersonalityType) {
        this.matchPersonalityType = matchPersonalityType;
    }

    public int getCompatibility(String currentUserType, String matchUser){
        int value =0;
        if(currentUserType.equals("ESTJ")){
           value= ESTJ_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("ESFJ")){
            value= ESFJ_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("ENFJ")){
            value= ENFJ_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("ENTJ")){
            value= ENTJ_map.get(matchUser);
            return value;
        }

        if(currentUserType.equals("ESTP")){
            value= ESTP_map.get(matchUser);
            return value;
        }

        if(currentUserType.equals("ESFP")){
            value= ESFP_map.get(matchUser);
            return value;
        }

        if(currentUserType.equals("ENFP")){
            value= ENFP_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("ENTP")){
            value= ENTP_map.get(matchUser);
            return value;
        }

        if(currentUserType.equals("ISTP")){
            value= ISTP_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("ISFP")){
            value= ISFP_map.get(matchUser);
            return value;
        }

        if(currentUserType.equals("INFP")){
            value= INFP_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("INTP")){
            value= INTP_map.get(matchUser);
            return value;
        }

        if(currentUserType.equals("ISTJ")){
            value= ISTJ_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("ISFJ")){
            value= ISFJ_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("INFJ")){
            value= INFJ_map.get(matchUser);
            return value;
        }
        if(currentUserType.equals("INTJ")){
            value= INTJ_map.get(matchUser);
            return value;
        }


        return 0;
    }
    public void setCompatibilityMap(String [] types){

            for ( int i=0; i<types.length; i++) {
                if (types[i] ==  "ESTJ") {
                    ESTJ_map.put("ESTJ", 68);
                    ESTJ_map.put("ESFJ", 71);
                    ESTJ_map.put("ENFJ", 30);
                    ESTJ_map.put("ENTJ", 29);
                    ESTJ_map.put("ESTP", 30);
                    ESTJ_map.put("ESFP", 50);
                    ESTJ_map.put("ENFP", 50);
                    ESTJ_map.put("ENTP", 50);
                    ESTJ_map.put("ISTP", 86);
                    ESTJ_map.put("ISFP", 30);
                    ESTJ_map.put("INFP", 13);
                    ESTJ_map.put("INTP", 50);
                    ESTJ_map.put("ISTJ", 86);
                    ESTJ_map.put("ISFJ", 95);
                    ESTJ_map.put("INFJ", 13);
                    ESTJ_map.put("INTJ", 30);
                }
                if (types[i] == "ESFJ") {
                    ESFJ_map.put("ESFJ", 84);
                    ESFJ_map.put("ESTJ", 71);
                    ESFJ_map.put("ENFJ", 30);
                    ESFJ_map.put("ENTJ", 71);
                    ESFJ_map.put("ESTP", 30);
                    ESFJ_map.put("ESFP", 86);
                    ESFJ_map.put("ENFP", 50);
                    ESFJ_map.put("ENTP", 14);
                    ESFJ_map.put("ISTP", 50);
                    ESFJ_map.put("ISFP", 30);
                    ESFJ_map.put("INFP", 50);
                    ESFJ_map.put("INTP", 14);
                    ESFJ_map.put("ISTJ", 86);
                    ESFJ_map.put("ISFJ", 71);
                    ESFJ_map.put("INFJ", 50);
                    ESFJ_map.put("INTJ", 5);
                }
                if (types[i] == "ENFJ") {
                    ENFJ_map.put("ENFJ", 50);
                    ENFJ_map.put("ESFJ", 30);
                    ENFJ_map.put("ESTJ", 30);
                    ENFJ_map.put("ENTJ", 71);
                    ENFJ_map.put("ESTP", 71);
                    ENFJ_map.put("ESFP", 50);
                    ENFJ_map.put("ENFP", 14);
                    ENFJ_map.put("ENTP", 50);
                    ENFJ_map.put("ISTP", 14);
                    ENFJ_map.put("ISFP", 30);
                    ENFJ_map.put("INFP", 50);
                    ENFJ_map.put("INTP", 50);
                    ENFJ_map.put("ISTJ", 50);
                    ENFJ_map.put("ISFJ", 30);
                    ENFJ_map.put("INFJ", 50);
                    ENFJ_map.put("INTJ", 71);
                }
                if (types[i] == "ENTJ") {
                    ENTJ_map.put("ESTJ", 30);
                    ENTJ_map.put("ESFJ", 71);
                    ENTJ_map.put("ENFJ", 71);
                    ENTJ_map.put("ENTJ", 11);
                    ENTJ_map.put("ESTP", 30);
                    ENTJ_map.put("ESFP", 50);
                    ENTJ_map.put("ENFP", 50);
                    ENTJ_map.put("ENTP", 14);
                    ENTJ_map.put("ISTP", 14);
                    ENTJ_map.put("ISFP", 71);
                    ENTJ_map.put("INFP", 50);
                    ENTJ_map.put("INTP", 14);
                    ENTJ_map.put("ISTJ", 50);
                    ENTJ_map.put("ISFJ", 30);
                    ENTJ_map.put("INFJ", 86);
                    ENTJ_map.put("INTJ", 30);
                }
                if (types[i] == "ESTP") {
                    ESTP_map.put("ESTJ", 50);
                    ESTP_map.put("ESFJ", 30);
                    ESTP_map.put("ENFJ", 71);
                    ESTP_map.put("ENTJ", 30);
                    ESTP_map.put("ESTP", 4);
                    ESTP_map.put("ESFP", 50);
                    ESTP_map.put("ENFP", 50);
                    ESTP_map.put("ENTP", 86);
                    ESTP_map.put("ISTP", 50);
                    ESTP_map.put("ISFP", 30);
                    ESTP_map.put("INFP", 50);
                    ESTP_map.put("INTP", 50);
                    ESTP_map.put("ISTJ", 50);
                    ESTP_map.put("ISFJ", 30);
                    ESTP_map.put("INFJ", 50);
                    ESTP_map.put("INTJ", 71);
                }
                if (types[i] == "ESFP") {
                    ESFP_map.put("ESTJ", 50);
                    ESFP_map.put("ESFJ", 86);
                    ESFP_map.put("ENFJ", 50);
                    ESFP_map.put("ENTJ", 50);
                    ESFP_map.put("ESTP", 50);
                    ESFP_map.put("ESFP", 46);
                    ESFP_map.put("ENFP", 30);
                    ESFP_map.put("ENTP", 30);
                    ESFP_map.put("ISTP", 30);
                    ESFP_map.put("ISFP", 14);
                    ESFP_map.put("INFP", 50);
                    ESFP_map.put("INTP", 30);
                    ESFP_map.put("ISTJ", 71);
                    ESFP_map.put("ISFJ", 50);
                    ESFP_map.put("INFJ", 30);
                    ESFP_map.put("INTJ", 14);
                }
                if (types[i] == "ENFP") {
                    ENFP_map.put("ESTJ", 50);
                    ENFP_map.put("ESFJ", 50);
                    ENFP_map.put("ENFJ", 14);
                    ENFP_map.put("ENTJ", 50);
                    ENFP_map.put("ESTP", 50);
                    ENFP_map.put("ESFP", 30);
                    ENFP_map.put("ENFP", 46);
                    ENFP_map.put("ENTP", 71);
                    ENFP_map.put("ISTP", 71);
                    ENFP_map.put("ISFP", 86);
                    ENFP_map.put("INFP", 30);
                    ENFP_map.put("INTP", 30);
                    ENFP_map.put("ISTJ", 30);
                    ENFP_map.put("ISFJ", 50);
                    ENFP_map.put("INFJ", 71);
                    ENFP_map.put("INTJ", 50);
                }
                if (types[i] == "ENTP") {
                    ENTP_map.put("ESTJ", 50);
                    ENTP_map.put("ESFJ", 14);
                    ENTP_map.put("ENFJ", 50);
                    ENTP_map.put("ENTJ", 14);
                    ENTP_map.put("ESTP", 86);
                    ENTP_map.put("ESFP", 30);
                    ENTP_map.put("ENFP", 71);
                    ENTP_map.put("ENTP", 11);
                    ENTP_map.put("ISTP",71);
                    ENTP_map.put("ISFP", 50);
                    ENTP_map.put("INFP", 30);
                    ENTP_map.put("INTP",71);
                    ENTP_map.put("ISTJ", 30);
                    ENTP_map.put("ISFJ", 50);
                    ENTP_map.put("INFJ", 30);
                    ENTP_map.put("INTJ", 86);
                }
                if (types[i] == "ISTP") {
                    ISTP_map.put("ESTJ", 86);
                    ISTP_map.put("ESFJ",50);
                    ISTP_map.put("ENFJ", 14);
                    ISTP_map.put("ENTJ", 14);
                    ISTP_map.put("ESTP", 50);
                    ISTP_map.put("ESFP", 50);
                    ISTP_map.put("ENFP", 71);
                    ISTP_map.put("ENTP", 71);
                    ISTP_map.put("ISTP", 25);
                    ISTP_map.put("ISFP", 50);
                    ISTP_map.put("INFP", 30);
                    ISTP_map.put("INTP", 71);
                    ISTP_map.put("ISTJ", 71);
                    ISTP_map.put("ISFJ", 86);
                    ISTP_map.put("INFJ", 30);
                    ISTP_map.put("INTJ", 50);
                }
                if (types[i] == "ISFP") {
                    ISFP_map.put("ESTJ", 30);
                    ISFP_map.put("ESFJ", 30);
                    ISFP_map.put("ENFJ", 30);
                    ISFP_map.put("ENTJ", 71);
                    ISFP_map.put("ESTP", 30);
                    ISFP_map.put("ESFP", 14);
                    ISFP_map.put("ENFP", 86);
                    ISFP_map.put("ENTP", 50);
                    ISFP_map.put("ISTP", 50);
                    ISFP_map.put("ISFP", 11);
                    ISFP_map.put("INFP", 50);
                    ISFP_map.put("INTP", 50);
                    ISFP_map.put("ISTJ", 14);
                    ISFP_map.put("ISFJ", 30);
                    ISFP_map.put("INFJ", 86);
                    ISFP_map.put("INTJ", 71);
                }
                if (types[i] == "INFP") {
                    INFP_map.put("ESTJ", 14);
                    INFP_map.put("ESFJ", 50);
                    INFP_map.put("ENFJ", 50);
                    INFP_map.put("ENTJ", 50);
                    INFP_map.put("ESTP", 50);
                    INFP_map.put("ESFP", 71);
                    INFP_map.put("ENFP", 30);
                    INFP_map.put("ENTP", 30);
                    INFP_map.put("ISTP", 30);
                    INFP_map.put("ISFP", 50);
                    INFP_map.put("INFP", 25);
                    INFP_map.put("INTP", 71);
                    INFP_map.put("ISTJ", 30);
                    INFP_map.put("ISFJ", 14);
                    INFP_map.put("INFJ", 71);
                    INFP_map.put("INTJ", 50);
                }
                if (types[i] == "INTP") {
                    INTP_map.put("ESTJ", 50);
                    INTP_map.put("ESFJ", 14);
                    INTP_map.put("ENFJ", 30);
                    INTP_map.put("ENTJ", 14);
                    INTP_map.put("ESTP", 50);
                    INTP_map.put("ESFP", 30);
                    INTP_map.put("ENFP", 30);
                    INTP_map.put("ENTP", 71);
                    INTP_map.put("ISTP", 71);
                    INTP_map.put("ISFP", 50);
                    INTP_map.put("INFP", 71);
                    INTP_map.put("INTP", 25);
                    INTP_map.put("ISTJ", 30);
                    INTP_map.put("ISFJ", 50);
                    INTP_map.put("INFJ", 30);
                    INTP_map.put("INTJ", 86);
                }
                if (types[i] == "ISTJ") {
                    ISTJ_map.put("ESTJ", 86);
                    ISTJ_map.put("ESFJ", 86);
                    ISTJ_map.put("ENFJ", 50);
                    ISTJ_map.put("ENTJ", 50);
                    ISTJ_map.put("ESTP", 50);
                    ISTJ_map.put("ESFP", 71);
                    ISTJ_map.put("ENFP", 30);
                    ISTJ_map.put("ENTP", 30);
                    ISTJ_map.put("ISTP", 71);
                    ISTJ_map.put("ISFP", 14);
                    ISTJ_map.put("INFP", 30);
                    ISTJ_map.put("INTP", 30);
                    ISTJ_map.put("ISTJ", 94);
                    ISTJ_map.put("ISFJ", 86);
                    ISTJ_map.put("INFJ", 30);
                    ISTJ_map.put("INTJ", 14);
                }
                if (types[i] == "ISFJ") {
                    ISFJ_map.put("ESTJ", 95);
                    ISFJ_map.put("ESFJ", 71);
                    ISFJ_map.put("ENFJ", 30);
                    ISFJ_map.put("ENTJ", 30);
                    ISFJ_map.put("ESTP",30);
                    ISFJ_map.put("ESFP", 71);
                    ISFJ_map.put("ENFP", 50);
                    ISFJ_map.put("ENTP", 50);
                    ISFJ_map.put("ISTP", 86);
                    ISFJ_map.put("ISFP", 30);
                    ISFJ_map.put("INFP", 14);
                    ISFJ_map.put("INTP", 50);
                    ISFJ_map.put("ISTJ", 86);
                    ISFJ_map.put("ISFJ", 94);
                    ISFJ_map.put("INFJ", 14);
                    ISFJ_map.put("INTJ", 30);
                }
                if (types[i] == "INFJ") {
                    INFJ_map.put("ESTJ", 14);
                    INFJ_map.put("ESFJ", 30);
                    INFJ_map.put("ENFJ", 50);
                    INFJ_map.put("ENTJ", 86);
                    INFJ_map.put("ESTP", 50);
                    INFJ_map.put("ESFP", 30);
                    INFJ_map.put("ENFP", 71);
                    INFJ_map.put("ENTP", 30);
                    INFJ_map.put("ISTP", 30);
                    INFJ_map.put("ISFP", 86);
                    INFJ_map.put("INFP", 71);
                    INFJ_map.put("INTP", 30);
                    INFJ_map.put("ISTJ", 30);
                    INFJ_map.put("ISFJ", 14);
                    INFJ_map.put("INFJ", 68);
                    INFJ_map.put("INTJ", 50);
                }
                if (types[i] == "INTJ") {
                    INTJ_map.put("ESTJ", 30);
                    INTJ_map.put("ESFJ", 5);
                    INTJ_map.put("ENFJ", 71);
                    INTJ_map.put("ENTJ", 30);
                    INTJ_map.put("ESTP", 71);
                    INTJ_map.put("ESFP", 14);
                    INTJ_map.put("ENFP", 50);
                    INTJ_map.put("ENTP", 86);
                    INTJ_map.put("ISTP", 50);
                    INTJ_map.put("ISFP", 71);
                    INTJ_map.put("INFP", 50);
                    INTJ_map.put("INTP", 86);
                    INTJ_map.put("ISTJ", 14);
                    INTJ_map.put("ISFJ", 30);
                    INTJ_map.put("INFJ", 50);
                    INTJ_map.put("INTJ", 3);
                }
            }
    }
}
