package org.ayamemc.ayame.util;

public class JavaUtil {
    public static boolean tryClass(String clazz){
        try{
            Class.forName(clazz);
            return true;
        }catch(ClassNotFoundException e){
            return false;
        }
    }
}
