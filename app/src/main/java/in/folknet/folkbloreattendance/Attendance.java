package in.folknet.folkbloreattendance;

public class Attendance {
    String Area;
    String FG;
    String FID;
    String Japa;
    String Name;
    String Session;
    String Time;
    String ZZColor;
    String ZFL, ZTL, ZZDATE, ZZone, Zmob, Zread;

    public Attendance() {

    }

    public Attendance(String Area, String FG, String FID, String Japa, String Name, String Session,
                      String ZFL, String ZTL, String ZZDATE, String ZZone, String Zmob, String Zread, String Time, String ZZColor) {
        this.Area = Area;
        this.FG = FG;
        this.FID = FID;
        this.Japa = Japa;
        this.Name = Name;
        this.Session = Session;
        this.ZFL = ZFL;
        this.ZTL = ZTL;
        this.ZZDATE = ZZDATE;
        this.ZZone = ZZone;
        this.Zmob = Zmob;
        this.Zread = Zread;
        this.Time = Time;
        this.ZZColor = ZZColor;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getFG() {
        return FG;
    }

    public void setFG(String FG) {
        this.FG = FG;
    }

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }

    public String getJapa() {
        return Japa;
    }

    public void setJapa(String japa) {
        Japa = japa;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public String getZFL() {
        return ZFL;
    }

    public void setZFL(String ZFL) {
        this.ZFL = ZFL;
    }

    public String getZTL() {
        return ZTL;
    }

    public void setZTL(String ZTL) {
        this.ZTL = ZTL;
    }

    public String getZZDATE() {
        return ZZDATE;
    }

    public void setZZDATE(String ZZDATE) {
        this.ZZDATE = ZZDATE;
    }

    public String getZZone() {
        return ZZone;
    }

    public void setZZone(String ZZone) {
        this.ZZone = ZZone;
    }

    public String getZmob() {
        return Zmob;
    }

    public void setZmob(String zmob) {
        Zmob = zmob;
    }

    public String getZread() {
        return Zread;
    }

    public void setZread(String zread) {
        Zread = zread;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getZZColor() {
        return ZZColor;
    }

    public void setZZColor(String ZZColor) {
        this.ZZColor = ZZColor;
    }
}
