/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */

public class Xdate {
    public static final SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
    
     static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

    public static Date toDate(String date, String... pattern) {
        try {
            format.applyPattern("yyyy/MM/dd");
            return format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String toString(Date date, String... pattern) {
        if (pattern.length > 0) {
            format.applyPattern(pattern[0]);
        }
        if (date == null) {
            date = Xdate.now();
        }
        return format.format(date);
    }
    public static Date addDay(Date date, long days){
        date.setTime(date.getTime() + days *24 *60 *60 *1000);
        return date;
    }
    
    public static Date now(){
        return new Date();
    }
}
