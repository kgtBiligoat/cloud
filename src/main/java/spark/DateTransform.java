package spark;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTransform{
    /*
     * 将时间戳转换为时间
     */
    public String DateTransform(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt*1000);
        res = simpleDateFormat.format(date);
        return res;
    }
}