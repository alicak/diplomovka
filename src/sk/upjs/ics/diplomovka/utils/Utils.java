package sk.upjs.ics.diplomovka.utils;

public class Utils {

    public static int randomInt(int min, int max) {
        return (int) Math.floor(Math.random() * max + min);
    }

    public static int randomInt(int max) {
        return (int) Math.floor(Math.random() * max);
    }

}
