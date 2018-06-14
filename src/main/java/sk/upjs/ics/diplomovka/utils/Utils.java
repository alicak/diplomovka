package sk.upjs.ics.diplomovka.utils;

public class Utils {

    public static int MINUTES_IN_DAY = 24 * 60;

    public static int randomInt(int min, int max) {
        return (int) Math.floor(Math.random() * max + min);
    }

    public static int randomInt(int max) {
        return (int) Math.floor(Math.random() * max);
    }

    public static String minutesToTime(int minutes) {
        String h = Integer.toString(minutes / 60);
        String m = Integer.toString(minutes % 60);
        if (h.length() < 2)
            h = "0" + h;
        if (m.length() < 2)
            m = "0" + m;

        return h + ":" + m;
    }

    public static int timeToMinutes(String time) {
        String[] timeArray = time.split(":");
        int hours = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);
        return hours * 60 + minutes;
    }

}
