package spit.comps.collegemate;

/**
 * Created by Mithil on 5-10-2018.
 */

public class AnnouncementItem {

    public String alert_id,date,time,title,importance,description,link;

    public AnnouncementItem(String alert_id, String date, String time, String title, String importance,
                            String description, String link)
    {
        this.alert_id=alert_id;
        this.date=date;
        this.time=time;
        this.title=title;
        this.importance=importance;
        this.description=description;
        this.link=link;
    }
}
