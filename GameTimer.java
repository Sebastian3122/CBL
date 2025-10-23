import javax.swing.Timer;

public class GameTimer {
    Timer timer;
    int elapsedTime = 0;
    int seconds = 0;
    int minutes = 0;
    String secondsString;
    String minutesString;
    Runnable onTimeUpdate;

    public GameTimer() {
        secondsString = String.format("%02d", seconds);
        minutesString = String.format("%02d", minutes);
    }

    public void setOnTimeUpdate(Runnable callback) {
        this.onTimeUpdate = callback;
    }

    public void startTimer() {
        timer = new Timer(1000, e -> {
            elapsedTime += 1000;
            minutes = (elapsedTime / 60000) % 60;
            seconds = (elapsedTime / 1000) % 60;
            secondsString = String.format("%02d", seconds);
            minutesString = String.format("%02d", minutes);
            if (onTimeUpdate != null) {
                onTimeUpdate.run();
            }
        });
        timer.start();
    }

    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    public void resetTimer() {
        elapsedTime = 0;
        minutes = 0;
        seconds = 0;
        secondsString = String.format("%02d", seconds);
        minutesString = String.format("%02d", minutes);
    }

    public String getTimeString() {
        return minutesString + ":" + secondsString;
    }

    public int getElapsedTimeSeconds() {
        return elapsedTime / 1000;
    }
}
