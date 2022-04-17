package com.txy822.android_personality_based_dating_app.utils;

/**
 * Class type to capture  user profile data
 */
public class ProfileID {
    String profileId;
    public <T extends ProfileID> T withId(String s){
        this.profileId = s;
        return (T) this;
    }
}