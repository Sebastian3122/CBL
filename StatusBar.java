import java.awt.*;
import javax.swing.*;

public class StatusBar {
    JLabel textLabel = new JLabel();
    JButton restartButton = new JButton("Restart");
    JPanel textPanel = new JPanel();
    JPanel flagCounter = new JPanel();
    int nrf = 25; // variable to see how many flags we have left;
    int personalBest = Integer.MAX_VALUE;

    public StatusBar() {
        textLabel.setFont(new Font("Arial", Font.BOLD, 25)); // set the font
        textLabel.setHorizontalAlignment(JLabel.LEFT);
        textLabel.setText(" Flags:" + nrf
                + "                                 Time: "
                + "00:00");
        textLabel.setOpaque(true);

        restartButton.setFocusable(false);
        restartButton.setFont(new Font("Arial", Font.PLAIN, 18));

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.CENTER);
        textPanel.add(restartButton, BorderLayout.EAST);
    }

    public void updateLabel(String timeString) {
        String bestTime;
        if (personalBest == Integer.MAX_VALUE) {
            bestTime = "--:--";
        } else {
            int bestMinutes = personalBest / 60;
            int bestSeconds = personalBest % 60;
            bestTime = String.format("%02d:%02d", bestMinutes, bestSeconds);
        }

        textLabel.setText(" Flags:" + nrf
                + "                                 Time: "
                + timeString
                + "        Best: " + bestTime);
    }

    public void updateLabel() {
        updateLabel("00:00");
    }

    public void setGameOverText(String timeString) {
        textLabel.setText("Time: " + timeString + "                      You Lost! Try again!");
        textLabel.setHorizontalAlignment(JLabel.LEFT);
    }

    public void setWinText(String timeString) {
        String bestTime = String.format("%02d:%02d", personalBest / 60, personalBest % 60);
        textLabel.setText("Time: " + timeString + "                  You Won!   Best: " + bestTime);
    }

    public void setFlags(int flags) {
        nrf = flags;
    }

    public int getFlags() {
        return nrf;
    }

    public void decrementFlags() {
        nrf--;
    }

    public void incrementFlags() {
        nrf++;
    }

    public void setPersonalBest(int best) {
        personalBest = best;
    }

    public int getPersonalBest() {
        return personalBest;
    }

    public JPanel getTextPanel() {
        return textPanel;
    }

    public JButton getRestartButton() {
        return restartButton;
    }
}

