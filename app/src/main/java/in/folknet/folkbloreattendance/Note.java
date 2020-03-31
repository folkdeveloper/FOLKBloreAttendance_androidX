package in.folknet.folkbloreattendance;

public class Note {
    String area, fg, fid, japa, name, session, time, zfl, zmob, zread, ztl, zzdate, zone, color,
            url, occupation, program, category, program_location;

    public static final String TAG = "Note";

    public Note() {

    }

    public Note(String area, String name, String fg, String ztl) {
        this.area = area;
        this.name = name;
        this.fg = fg;
        this.ztl = ztl;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProgram_location() {
        return program_location;
    }

    public void setProgram_location(String program_location) {
        this.program_location = program_location;
    }
}
