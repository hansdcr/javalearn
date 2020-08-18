package dynamicclass;


import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OfficeBetter{

    public static void main(String[] args) {
        try {
            Class c = Class.forName(args[0]);
            IOffice oa = (IOffice) c.newInstance();
            oa.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
