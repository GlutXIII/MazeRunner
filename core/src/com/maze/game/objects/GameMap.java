package com.maze.game.objects;


import com.maze.game.objects.factory.AbstractFactory;
import com.maze.game.objects.factoryfactory.FactoryFactoryUtil;
import com.maze.game.objects.factory.WinterFactoryUtil;
import com.maze.game.objects.gameObjects.*;
import com.maze.game.objects.gameObjects.standard.EmptyHall;
import com.maze.game.objects.gameObjects.standard.Player;
import com.maze.game.objects.gameObjects.winter.WinterEmptyHall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static com.maze.game.objects.factory.GameObjectStringMap.getString;
import static com.maze.game.objects.utils.Constants.EMPTY_HALL_CODE;
import static com.maze.game.objects.utils.PathToFilesUtil.EMPTY_HALL;

public class GameMap {

    private FactoryFactoryUtil factoryFactoryUtil = new FactoryFactoryUtil();
    private AbstractFactory abstractFactoryUtil;
    private int width;
    private int height;
    private GameObject[][] map= new GameObject[width][height];
    private int oldPlayerX;
    private int oldPlayerY;

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

    public void movePlayer(int newX,int newY){

        try {
            map[oldPlayerX][oldPlayerY] = abstractFactoryUtil.createGameObject(EMPTY_HALL_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        oldPlayerX = newX;
        oldPlayerY = newY;
        map[newX][newY] = new Player();
    }

    public GameObject get(int x, int y){
        return map[x][y];
    }

    public void destroy(int x, int y){
        if(x!=0 &&  y!=0 && x!=29 && y!=29){
            map[x+1][y]= new EmptyHall();
            map[x-1][y]=new EmptyHall();
            map[x][y+1]=new EmptyHall();
            map[x][y-1]=new EmptyHall();
        }
    }

    public void rerenderMap() throws Exception {
        for(int i= 0; i<width;i++) {
            for(int j=0;j<height;j++) {
               GameObject gameObject = map[i][j];
               map[i][j] = abstractFactoryUtil.createGameObject(getString(gameObject));
            }
        }
    }
}
