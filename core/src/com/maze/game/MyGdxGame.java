package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.objects.DirectionEnum;
import com.maze.game.objects.GameMap;
import com.maze.game.objects.gameObjects.GameObject;
import com.maze.game.objects.gameObjects.standard.*;
import com.maze.game.objects.gameObjects.winter.WinterWall;
import com.maze.game.objects.graph.Dijkstra;
import com.maze.game.objects.graph.WeightedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.maze.game.objects.utils.Constants.EMPTY_HALL_CODE;
import static com.maze.game.objects.utils.PathToFilesUtil.*;

public class MyGdxGame extends ApplicationAdapter {
    SpriteBatch batch;

    private Player player;
    private Enemy enemy;
    private int[] pred;
    private GameMap gameMap = new GameMap();
    private final WeightedGraph graph2 = new WeightedGraph(900);

    private int playerPreviousY = -5;
    private int playerPreviousX = -5;


    int flagStart = 0;
    int tick = 0;

    @Override
    public void create() {

        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 600, 600);
    }

    @Override
    public void render() {
        mainGame();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    private void drawBackground() {
        batch.begin();
        batch.draw(new Texture(EMPTY_HALL), 0, 0, 600, 600);
        batch.end();
    }

    /**
     * Main game function in which all game objects are updated and rendered
     */
    private void mainGame() {

        if (flagStart != 0 && checkVictoryCondition()) {
            batch.begin();
            batch.draw(new Texture(VICTORY), 0, 0, 600, 600);
            batch.end();
        } else if (flagStart != 0 && checkDefeatCondition()) {
            batch.begin();
            batch.draw(new Texture(DEFEAT), 0, 0, 600, 600);
            batch.end();
        } else {
            if (flagStart == 0) {
                gameMap.init(30, 30);
                gameMap.initFactory(EMPTY_HALL_CODE);
                //gameMap.insertPlayerObject(player.getX(), player.getY());
                gameMap.loadFromFile(MAP_FILE_PATH);
                createGraph();
                //System.out.println(graph.toString());


                player = ((Player) gameMap.getPlayer());
                enemy = (Enemy) gameMap.getEnemy();


                player.init();
                setLebels();
                graph2.print();
                flagStart = 1;
                drawBackground();
            }

            renderMap();
            gameMap.movePlayer(player.getX(), player.getY());
            if (tick % 50 == 0) {
                moveRandomlyEnemy();
                gameMap.moveEnemy(enemy.getX(), enemy.getY());
                tick = 0;
            }
            moveListener();
            tick++;
        }
    }

    private void setLebels() {
        for (int i = 0; i < 900; i++
        ) {
            graph2.setLabel(i, i);
        }
    }

    private boolean checkVictoryCondition() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (gameMap.get(i, j) instanceof VictoryPlace && player.getX() == i && player.getY() == j && player.getKeyCounter() == 3) {
                    return true;
                }

            }
        }
        return false;
    }

    private boolean checkDefeatCondition() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (gameMap.get(i, j) instanceof Enemy && player.getX() == i && player.getY() == j) {
                    return true;
                }

            }
        }
        return false;
    }

    private void renderMap() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {

                GameObject gameObject = gameMap.get(i, j);
                gameObject.render(batch, i, j);

            }
        }
    }


    private void moveListener() {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (player.getY() != 29 && !(gameMap.get(player.getX(), player.getY() + 1) instanceof Wall) && !(gameMap.get(player.getX(), player.getY() + 1) instanceof WinterWall) && !(gameMap.get(player.getX(), player.getY() + 1) instanceof Doors)) {
                player.moveTo(DirectionEnum.gora);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            if (player.getY() != 0 && !(gameMap.get(player.getX(), player.getY() - 1) instanceof Wall) && !(gameMap.get(player.getX(), player.getY() - 1) instanceof WinterWall) && !(gameMap.get(player.getX(), player.getY() - 1) instanceof Doors)) {

                player.moveTo(DirectionEnum.dol);
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (player.getX() != 29 && !(gameMap.get(player.getX() + 1, player.getY()) instanceof Wall) && !(gameMap.get(player.getX() + 1, player.getY()) instanceof WinterWall) && !(gameMap.get(player.getX() + 1, player.getY()) instanceof Doors)) {

                player.moveTo(DirectionEnum.prawo);
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (player.getX() != 0 && !(gameMap.get(player.getX() - 1, player.getY()) instanceof Wall) && !(gameMap.get(player.getX() - 1, player.getY()) instanceof WinterWall) && !(gameMap.get(player.getX() - 1, player.getY()) instanceof Doors)) {

                player.moveTo(DirectionEnum.lewo);
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            gameMap.destroy(player.getX(), player.getY());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.O)) {
            gameMap.openDoors(player.getX(), player.getY());
        }


        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            gameMap.initFactory("1");
            try {
                gameMap.rerenderMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.N)) {
            gameMap.initFactory(EMPTY_HALL_CODE);
            try {
                gameMap.rerenderMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void moveRandomlyEnemy() {
        Enemy enemy = (Enemy) gameMap.getEnemy();
        List<String> possibilities = new ArrayList<String>();
        if (enemy.getY() != 29 && (gameMap.get(enemy.getX(), enemy.getY() + 1) instanceof EmptyHall) || (gameMap.get(enemy.getX(), enemy.getY() + 1) instanceof Player) || (gameMap.get(enemy.getX(), enemy.getY() + 1) instanceof OpenDoors)) {
            possibilities.add("up");
        }
        if (enemy.getY() != 0 && (gameMap.get(enemy.getX(), enemy.getY() - 1) instanceof EmptyHall) || (gameMap.get(enemy.getX(), enemy.getY() - 1) instanceof Player) || (gameMap.get(enemy.getX(), enemy.getY() - 1) instanceof OpenDoors)) {
            possibilities.add("down");
        }
        if (enemy.getX() != 29 && (gameMap.get(enemy.getX() + 1, enemy.getY()) instanceof EmptyHall) || (gameMap.get(enemy.getX() + 1, enemy.getY()) instanceof Player) ||  (gameMap.get(enemy.getX() + 1, enemy.getY()) instanceof OpenDoors)) {
            possibilities.add("right");
        }
        if (enemy.getX() != 0 && (gameMap.get(enemy.getX() - 1, enemy.getY()) instanceof EmptyHall) || (gameMap.get(enemy.getX() - 1, enemy.getY()) instanceof Player) || (gameMap.get(enemy.getX() - 1, enemy.getY()) instanceof OpenDoors)) {
            possibilities.add("left");
        }


        if (!possibilities.isEmpty()) {

            List<DirectionEnum> prefarableDirection = getPrefarable();
            for (DirectionEnum directionEnum : prefarableDirection
            ) {
                if (directionEnum == DirectionEnum.dol && possibilities.contains("down")) {
                    enemy.moveTo(DirectionEnum.dol);
                    return;
                }
                if (directionEnum == DirectionEnum.gora && possibilities.contains("up")) {
                    enemy.moveTo(DirectionEnum.gora);
                    return;
                }
                if (directionEnum == DirectionEnum.prawo && possibilities.contains("right")) {
                    enemy.moveTo(DirectionEnum.prawo);
                    return;
                }
                if (directionEnum == DirectionEnum.lewo && possibilities.contains("left")) {
                    enemy.moveTo(DirectionEnum.lewo);
                    return;
                }
            }
            Random r = new Random();
            String result = possibilities.get(r.nextInt(possibilities.size()));

            switch (result) {
                case "up":
                    enemy.moveTo(DirectionEnum.gora);
                    break;
                case "down":
                    enemy.moveTo(DirectionEnum.dol);
                    break;
                case "right":
                    enemy.moveTo(DirectionEnum.prawo);
                    break;
                case "left":
                    enemy.moveTo(DirectionEnum.lewo);
                    break;

            }
        }
    }

    private List<DirectionEnum> getPrefarable() {


        int i = 0;
        int prev = 0;

        List<DirectionEnum> priorityMoveQueue = new ArrayList<>();

        //DFSPaths dfs1 = new DFSPaths(graph, enemy.getX() + enemy.getY() * 30);

        //resultPath = dfs1.getPathTo((player.getX() + player.getY() * 30));
        pred = Dijkstra.dijkstra(graph2, player.getX() + player.getY() * 30);
        ArrayList<Integer> resultPath = Dijkstra.getPath(graph2, pred, player.getX() + player.getY() * 30, enemy.getX() + enemy.getY() * 30);
        System.out.println("\nDFS - sciezka");
        Collections.reverse(resultPath);
        for (int it : resultPath) {
            System.out.print(it + " ");
        }
        System.out.println("\n----------");

        for (int it : resultPath) {
            if (i == 1) {
                int result = it - prev;
                switch (result) {
                    case 1:
                        priorityMoveQueue.add(DirectionEnum.prawo);
                        System.out.println("1");
                        break;
                    case -1:
                        priorityMoveQueue.add(DirectionEnum.lewo);
                        System.out.println("-1");

                        break;
                    case 30:
                        System.out.println("30");

                        priorityMoveQueue.add(DirectionEnum.gora);
                        break;
                    case -30:
                        System.out.println("-30");

                        priorityMoveQueue.add(DirectionEnum.dol);
                        break;
                }
            }
            prev = it;

            i++;
        }

        playerPreviousY = player.getY();
        playerPreviousX = player.getX();

        return priorityMoveQueue;
    }

    private void createGraph() {

        for (int i = 0; i < 29; i++) {
            for (int j = 0; j < 29; j++) {
                if ((gameMap.get(i, j) instanceof OpenDoors ||gameMap.get(i, j) instanceof Doors ||gameMap.get(i, j) instanceof VictoryPlace || gameMap.get(i, j) instanceof Key ||gameMap.get(i, j) instanceof EmptyHall || gameMap.get(i, j) instanceof Player || gameMap.get(i, j) instanceof Enemy) && (gameMap.get(i + 1, j) instanceof EmptyHall || gameMap.get(i + 1, j) instanceof Player || gameMap.get(i + 1, j) instanceof Enemy || gameMap.get(i + 1, j) instanceof Key || gameMap.get(i + 1, j) instanceof VictoryPlace || gameMap.get(i + 1, j) instanceof Doors || gameMap.get(i + 1, j) instanceof OpenDoors)) {

                    graph2.addEdge(i + (j * 30), (i + 1) + (j * 30), 1);
                    graph2.addEdge((i + 1) + (j * 30), i + (j * 30), 1);


//					adj.get(i+(j*30)).add(new Node((i+1)+(j*30), 1));
//					adj.get((i+1)+(j*30)).add(new Node(i+(j*30), 1));

//					graph.addEdge(i+(j*30),(i+1)+(j*30));
                }
                if ((gameMap.get(i, j) instanceof VictoryPlace || gameMap.get(i, j) instanceof Key || gameMap.get(i, j) instanceof EmptyHall ||gameMap.get(i, j) instanceof Doors ||gameMap.get(i, j) instanceof OpenDoors || gameMap.get(i, j) instanceof Player || gameMap.get(i, j) instanceof Enemy) && (gameMap.get(i, j + 1) instanceof EmptyHall || gameMap.get(i, j + 1) instanceof Player || gameMap.get(i, j + 1) instanceof Enemy ||  gameMap.get(i, j + 1) instanceof Key || gameMap.get(i, j + 1) instanceof VictoryPlace || gameMap.get(i, j + 1) instanceof Doors || gameMap.get(i, j + 1) instanceof OpenDoors )) {

                    graph2.addEdge(i + (j * 30), i + (j + 1) * 30, 1);
                    graph2.addEdge(i + (j + 1) * 30, i + (j * 30), 1);


//					adj.get(i+(j*30)).add(new Node(i + (j+1)*30, 1));
//					adj.get(i + (j+1)*30).add(new Node(i+(j*30), 1));
//
//					graph.addEdge(i+(j*30), i + (j+1)*30);

                }
            }
        }
    }
}