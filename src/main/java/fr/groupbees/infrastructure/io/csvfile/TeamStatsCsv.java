package fr.groupbees.infrastructure.io.csvfile;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

public class TeamStatsCsv implements Serializable {

    @CsvBindByName(column = "team_name")
    private String teamName;

    @CsvBindByName(column = "team_score")
    private String teamScore;

    @CsvBindByName(column = "team_scorers")
    private String scorers;

    public TeamStatsCsv() {
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(String teamScore) {
        this.teamScore = teamScore;
    }

    public String getScorers() {
        return scorers;
    }

    public void setScorers(String scorers) {
        this.scorers = scorers;
    }
}
