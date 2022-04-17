package com.txy822.android_personality_based_dating_app.utils;

/**
 * Profile user detail
 */
public class Profile extends ProfileID {

    private Integer age;
    private String ageRangePreference;
    private String dateOfBirth;
    private String fullName;
    private String img_url;
    private double latitude;
    private String location;
    private double longitude;
    private String personalityType;
    private String summary;


    public Profile(){

    }
    /**
     * Constructor of Profile
     * @param age int age
     * @param ageRangePreference String age preferences
     * @param dateOfBirth date date of birth
     * @param fullName String full name
     * @param img_url String image url
     * @param location String location
     * @param personalityType String personality type
     * @param summary String summary
     */
    public Profile(Integer age, String ageRangePreference, String dateOfBirth, String fullName, String img_url, String location, String personalityType, String summary) {
        this.age = age;
        this.ageRangePreference = ageRangePreference;
        this.dateOfBirth = dateOfBirth;
        this.fullName = fullName;
        this.img_url = img_url;
        this.location = location;
        this.personalityType = personalityType;
        this.summary = summary;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAgeRangePreference() {
        return ageRangePreference;
    }

    public void setAgeRangePreference(String ageRangePreference) {
        this.ageRangePreference = ageRangePreference;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "fullName='" + fullName + '\'' +
                '}';
    }
}


