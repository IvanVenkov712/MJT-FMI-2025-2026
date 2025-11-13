package bg.sofia.uni.fmi.mjt.dungeon;

import bg.sofia.uni.fmi.mjt.dungeon.actor.Enemy;
import bg.sofia.uni.fmi.mjt.dungeon.actor.Hero;
import bg.sofia.uni.fmi.mjt.dungeon.actor.Position;
import bg.sofia.uni.fmi.mjt.dungeon.treasure.Treasure;

public class GameEngine {
    private static final String[] messages = {
        "You moved successfully to the next position.",
        "Wrong move. There is an obstacle and you cannot bypass it.",
        "Enemy died.",
        "Hero is dead! Game over!",
        "You have successfully passed through the dungeon. Congrats!",
        "Unknown command entered."
    };

    private static final String okPos = messages[0];
    private static final String wrongMove = messages[1];
    private static final String enemyDead = messages[2];
    private static final String heroDead = messages[3];
    private static final String gameWon = messages[4];
    private static final String unknown = messages[5];

    private static final char startMarker = 'S';
    private static final char endMarker = 'G';
    private static final char heroMarker = 'H';
    private static final char enemyMarker = 'E';
    private static final char treasureMarker = 'T';
    private static final char freeMarker = '.';
    private static final char forbiddenMarker = '#';

    public GameEngine(char[][] map, Hero hero, Enemy[] enemies, Treasure[] treasures){
        this.map = map;
        this.hero = hero;
        this.enemies = enemies;
        this.treasures = treasures;
        this.position = new Position(0, 0);
        for(int i = 0; i < map.length; ++i){
            for(int j = 0; j < map[i].length; ++j){
                if(map[i][j] == startMarker){
                    position = new Position(i, j);
                }
            }
        }
        this.map[position.row()][position.col()] = heroMarker;
    }

    public Position getHeroPosition(){
        return position;
    }

    public char[][] getMap() {
        return map;
    }

    public Hero getHero(){
        return hero;
    }

    public String makeMove(Direction direction){
        Position newPos = position.move(direction);
        if(!isValidPosition(newPos)) {
            return wrongMove;
        }
        String msg = "";
        char c = map[newPos.row()][newPos.col()];
        if(c == freeMarker){
            msg = okPos;
        }
        else if(c == endMarker){
            return gameWon;
        }
        else if(c == treasureMarker){
            msg = treasures[currTreasure++].collect(hero);
        }
        else if(c == enemyMarker){
            boolean battleWon = battle(enemies[currEnemy++]);
            if(battleWon){
                msg = enemyDead;
            }
            else{
                return heroDead;
            }
        }
        else{
            return unknown;
        }
        map[position.row()][position.col()] = freeMarker;
        position = newPos;
        map[position.row()][position.col()] = heroMarker;
        return msg;
    }
    private boolean isValidPosition(Position position){
        return posInMap(position) && map[position.row()][position.col()] != forbiddenMarker;
    }
    private boolean posInMap(Position position){
        int rowMax = map.length - 1;
        if(position.row() < 0 || position.row() > rowMax){
            return false;
        }
        int colMax = map[position.row()].length - 1;
        return position.col() >= 0 && position.col() <= colMax;
    }
    private boolean battle(Enemy enemy){
        while(hero.isAlive() && enemy.isAlive()){
            enemy.takeDamage(hero.attack());
            if(!enemy.isAlive()){
                return true;
            }
            hero.takeDamage(enemy.attack());
        }
        return hero.isAlive();
    }

    Position position;
    char[][] map;
    Hero hero;
    Enemy[] enemies;
    int currEnemy = 0;
    Treasure[] treasures;
    int currTreasure = 0;
}
