package hucode.news_app.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author "Husniddin Ulachov"
 * @created 12:15 PM on 7/2/2022
 * @project Edu-Center
 */
@Component
public class DateFormatUtil {
    public Date stringtoDate(String str){
        Date date = new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        try {
             date = formatter.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
