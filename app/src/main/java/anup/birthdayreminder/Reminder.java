package anup.birthdayreminder;


public class Reminder {

    private int id;
    private String reminder;
    private long timeInMills;
    private String phoneNumber;

    public Reminder(int id, String reminder, long timeInMills, String phoneNumber ) {
        this.id =id;
        this.reminder = reminder;
        this.timeInMills = timeInMills;
        this.phoneNumber = phoneNumber;

    }

    public int getId() {
        return id;
    }

    public String getReminder() {
        return reminder;
    }

    public long getTimeInMills() {
        return timeInMills;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

