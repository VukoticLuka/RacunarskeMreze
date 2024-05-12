package Rokovi.Jan2_2023.Zadatak2;

public class ChessPlayer {
    private String name;
    private int elo;

    ChessPlayer(String name, int elo){
        this.name = name;
        this.elo = elo;
    }

    public int getElo() {
        return elo;
    }

    public String getName() {
        return name;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    @Override
    public String toString() {
        return this.name + " " + this.elo;
    }
}
