package command;

import boggle.BoggleStats;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.HashSet;

public class DisplayGameStatsCommand implements Command{
    HashMap<String, Object> statsMap;
    public Stage stage;

    public DisplayGameStatsCommand(HashMap<String, Object> map, Stage s) {
        this.statsMap =  map;
        this.stage = s;
    }
    @Override
    public void execute() {
        GridPane normalSummaryLayout = (GridPane) stage.getScene().getRoot(); // get the layout of
        // the current scene to be updated
        Thread t = new Thread(()->{
            Platform.runLater(()->{

                for (int i = 5; i < 12; i++) {
                    int row = i;
                    normalSummaryLayout.getChildren().removeIf(
                            node -> GridPane.getColumnIndex(node) == 0 && GridPane.getRowIndex(node) == row);
                }

                int numRounds = BoggleStats.getInstance().getRound();

                Label numrounds = new Label("Total Number of Rounds Played: " + numRounds);
                Label totalPScore = new Label("Total Score for Human is: " + BoggleStats.getInstance().getPScoreTotal());
                Label totalCScore = new Label("Total Score for Computer is: " + BoggleStats.getInstance().getCScoreTotal());
                Label pAvgWords = new Label(
                        "Average Number of Words Found By Human: " + BoggleStats.getInstance().getStatsMap().get(
                                "Player Average Words"));
                Label cAvgWords = new Label(
                        "Average Number of Words Found By Computer: " + BoggleStats.getInstance().getStatsMap().get(
                                "Computer Average Words"));

                normalSummaryLayout.add(numrounds, 0, 5);
                normalSummaryLayout.add(totalPScore, 0, 6);
                normalSummaryLayout.add(totalCScore, 0, 7);
                normalSummaryLayout.add(pAvgWords, 0, 8);
                normalSummaryLayout.add(cAvgWords, 0, 9);

                GridPane.setHalignment(numrounds, HPos.CENTER);
                GridPane.setHalignment(totalPScore, HPos.CENTER);
                GridPane.setHalignment(totalCScore, HPos.CENTER);
                GridPane.setHalignment(pAvgWords, HPos.CENTER);
                GridPane.setHalignment(cAvgWords, HPos.CENTER);
            });
        });
        t.start();

    }
}
