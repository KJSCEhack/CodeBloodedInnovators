package spit.comps.collegemate.Items;

public class ProjectType1Item {

    public String id,name,organizer,contact,date,venue,description,time,registration_status,poster,event_type;
    public ProjectType1Item(String id,String name,String organizer,String contact,
                            String date,String venue,String description,String time,String registration_status,String poster,
                            String event_type)
    {
        this.id=id;
        this.name=name;
        this.organizer = organizer;
        this.contact=contact;
        this.date=date;
        this.venue=venue;
        this.description=description;
        this.time=time;
        this.registration_status=registration_status;
        this.poster=poster;
        this.event_type=event_type;
    }

}
