package com.users.razvan.users.utils;

public class Utils {

    public static String getInitials(String userName) {
        String[] userNames = userName.split(" ");
        String initials = "";

        for (String name : userNames) {
            initials = initials.concat(name.charAt(0) + "");
        }

        return initials.toUpperCase();
    }

}
