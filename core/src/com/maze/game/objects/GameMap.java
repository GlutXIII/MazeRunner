package com.maze.game.objects;


import com.maze.game.objects.factory.AbstractFactory;
import com.maze.game.objects.factoryfactory.FactoryFactoryUtil;
import com.maze.game.objects.gameObjects.*;
import com.maze.game.objects.gameObjects.standard.Doors;
import com.maze.game.objects.gameObjects.standard.EmptyHall;
import com.maze.game.objects.gameObjects.standard.Key;
import com.maze.game.objects.gameObjects.standard.Player;
import com.maze.game.objects.gameObjects.winter.WinterKey;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.maze.game.objects.factory.GameObjectStringMap.getString;
import static com.maze.game.objects.utils.Constants.EMPTY_HALL_CODE;
import static com.maze.game.objects.utils.Constants.OPEN_DOORS_CODE;

public class GameMap {

    private FactoryFactoryUtil factoryFactoryUtil = new FactoryFactoryUtil();
    private AbstractFactory abstractFactoryUtil;
    private int width;
    private int height;
    private GameObject[][] map= new GameObject[width][height];
    private int oldPlayerX;
    private int oldPlayerY;
    private GameObject lastPlayerGameObject;

    public void init(int width,int height){
        map = new GameObject[width][height];
        this.width = width;
        this.height = height;
        for(int i= 0; i<width;i++) {
            for(int j=0;j<height;j++) {
                map[i][j]= new EmptyHall();
            }
        }
    }

    public void initFactory(String number){
        try {
            abstractFactoryUtil = factoryFactoryUtil.createAbstractFactory(number);
            lastPlayerGameObject = abstractFactoryUtil.createGameObject(EMPTY_HALL_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filePath){
        File file = new File(filePath);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int i =0;
        int j =0;
        while (sc.hasNext()) {

            try {
                GameObject gameObject =abstractFactoryUtil.createGameObject(sc.next());
             //   gameObject.init();
                map[i][j] = gameObject;

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (i == 29) {
                j++;
                i=0;
            } else {
                i++;
            }
        }



}
    public void insertPlayerObject(int x,int y){
        map[x][y] = new Player();
    }
    public GameObject getPlayer(){
        for(int i= 0; i<width;i++) {
            for(int j=0;j<height;j++) {
               if( map[i][j] instanceof Player){
                   return map[i][j];
               }
            }
        }
        return null;
    }
    public void movePlayer(int newX,int newY){
       GameObject player = map[oldPlayerX][oldPlayerY];

        try {
            if(lastPlayerGameObject instanceof Key || lastPlayerGameObject instanceof WinterKey) {
                ((Player) player).setKeyCounter(((Player) player).getKeyCounter()+1);
                map[oldPlayerX][oldPlayerY] = abstractFactoryUtil.createGameObject(EMPTY_HALL_CODE);
            }else {
                map[oldPlayerX][oldPlayerY] = abstractFactoryUtil.createGameObject(getString(lastPlayerGameObject));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        lastPlayerGameObject = map[newX][newY];
        oldPlayerX = newX;
        oldPlayerY = newY;
        map[newX][newY] = player;

    }

    public GameObject get(int x, int y){
        return map[x][y];
    }

    public void destroy(int x, int y){
        if(x!=0 &&  y!=0 && x!=29 && y!=29){
            try {
                map[x+1][y]=abstractFactoryUtil.createGameObject(EMPTY_HALL_CODE);
                map[x-1][y]=abstractFactoryUtil.createGameObject(EMPTY_HALL_CODE);
                map[x][y+1]=abstractFactoryUtil.createGameObject(EMPTY_HALL_CODE);
                map[x][y-1]=abstractFactoryUtil.createGameObject(EMPTY_HALL_CODE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void rerenderMap() throws Exception {
        for(int i= 0; i<width;i++) {
            for(int j=0;j<height;j++) {
               GameObject gameObject = map[i][j];
               if(gameObject instanceof Player){
                map[i][j]= gameObject;
               }else {
                   map[i][j] = abstractFactoryUtil.createGameObject(getString(gameObject));
               }
               }
        }
    }

    public void openDoors(int x, int y){
        if(x!=0 &&  y!=0 && x!=29 && y!=29){
            try {
                if(map[x+1][y] instanceof Doors){
                    map[x+1][y]=abstractFactoryUtil.createGameObject(OPEN_DOORS_CODE);
                }
                if( map[x-1][y] instanceof Doors){
                    map[x-1][y]=abstractFactoryUtil.createGameObject(OPEN_DOORS_CODE);
                }
                if(map[x][y+1] instanceof Doors){
                    map[x][y+1]=abstractFactoryUtil.createGameObject(OPEN_DOORS_CODE);
                }
                if(map[x][y-1] instanceof Doors){
                    map[x][y-1]=abstractFactoryUtil.createGameObject(OPEN_DOORS_CODE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
