package at.nbsgames.judd.splatoonink;

public class Time {

    private int hours;
    private int minutes;
    private int seconds;

    Time(long startTime, long endTime) {
        long time = endTime - startTime;
        this.hours = (int) time / 3600;
        time = time % 3600;
        this.minutes = (int) time / 60;
        this.seconds = (int) time % 60;
    }

    public String formatTime() {
        String returner = this.hours + ":";
        if(this.minutes < 10){
            return returner + "0" + this.minutes;
        }
        return returner + this.minutes;
    }

    public String formatTimesWithSeconds() {
        return this.hours + ":" + this.minutes + ":" + this.seconds;
    }

    public int getHours() {
        return this.hours;
    }
    public int getMinutes(){
        return this.minutes;
    }
    public int getSeconds(){
        return this.seconds;
    }
}
