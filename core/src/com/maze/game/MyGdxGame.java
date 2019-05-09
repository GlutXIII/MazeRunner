package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.objects.DirectionEnum;
import com.maze.game.objects.GameMap;
import com.maze.game.objects.gameObjects.*;
import com.maze.game.objects.gameObjects.standard.*;
import com.maze.game.objects.gameObjects.winter.WinterWall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.maze.game.objects.utils.Constants.EMPTY_HALL_CODE;
import static com.maze.game.objects.utils.PathToFilesUtil.*;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;

	private Player player;
	private Enemy enemy;
	private GameMap gameMap = new GameMap();

	int flagStart =0;
	int tick=0;
	
	@Override
	public void create () {

		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 600, 600);
}

	@Override
	public void render () {
		mainGame();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}


	private void drawBackground(){
		batch.begin();
		batch.draw(new Texture(EMPTY_HALL), 0, 0,600,600);
		batch.end();
	}
	/**
	 * Main game function in which all game objects are updated and rendered
	 */
	private void mainGame() {
		if(flagStart!= 0 && checkVictoryCondition()){
			batch.begin();
			batch.draw(new Texture(VICTORY), 0, 0,600,600);
			batch.end();
		}else if(flagStart!= 0 && checkDefeatCondition()){
			batch.begin();
			batch.draw(new Texture(DEFEAT), 0, 0,600,600);
			batch.end();
		}
		else {
			if (flagStart == 0) {
				gameMap.init(30,30);
				gameMap.initFactory(EMPTY_HALL_CODE);

				//gameMap.insertPlayerObject(player.getX(), player.getY());
				gameMap.loadFromFile(MAP_FILE_PATH);
				player = ((Player)gameMap.getPlayer());
				enemy = (Enemy) gameMap.getEnemy();

				player.init();
				flagStart = 1;
				drawBackground();
			}

			renderMap();
			gameMap.movePlayer(player.getX(), player.getY());
			if(tick%50  == 0) {
				moveRandomlyEnemy();
				gameMap.moveEnemy(enemy.getX(), enemy.getY());
				tick = 0;
				}
			moveListener();
			tick ++;
		}
	}
	private boolean checkVictoryCondition(){
		for(int i= 0; i<30;i++) {
			for(int j=0;j<30;j++) {
				if(gameMap.get(i,j) instanceof VictoryPlace && player.getX()==i && player.getY() ==j && player.getKeyCounter()==3){
					return true;
				}

			}
		}
		return false;
	}

	private boolean checkDefeatCondition(){
		for(int i= 0; i<30;i++) {
			for(int j=0;j<30;j++) {
				if(gameMap.get(i,j) instanceof Enemy && player.getX()==i && player.getY() ==j){
					return true;
				}

			}
		}
		return false;
	}
	private void renderMap(){
		for(int i= 0; i<30;i++) {
			for(int j=0;j<30;j++) {

				GameObject gameObject = gameMap.get(i,j);
				gameObject.render(batch,i,j);

			}
		}
	}


	private void moveListener() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if(player.getY() != 29 && !(gameMap.get(player.getX(),player.getY()+1) instanceof Wall) && !(gameMap.get(player.getX(),player.getY()+1) instanceof WinterWall) && !(gameMap.get(player.getX(),player.getY()+1) instanceof Doors)) {
				player.moveTo(DirectionEnum.gora);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if(player.getY()!=0 && !(gameMap.get(player.getX(),player.getY()-1)instanceof Wall) && !(gameMap.get(player.getX(),player.getY()-1)instanceof WinterWall) && !(gameMap.get(player.getX(),player.getY()-1)instanceof Doors)) {

				player.moveTo(DirectionEnum.dol);
			}

		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if(player.getX()!=29 && !(gameMap.get(player.getX()+1,player.getY())instanceof Wall) && !(gameMap.get(player.getX()+1,player.getY())instanceof WinterWall) && !(gameMap.get(player.getX()+1,player.getY())instanceof Doors)) {

				player.moveTo(DirectionEnum.prawo);
			}

		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if(player.getX()!=0 && !(gameMap.get(player.getX()-1,player.getY()) instanceof Wall) && !(gameMap.get(player.getX()-1,player.getY()) instanceof WinterWall) && !(gameMap.get(player.getX()-1,player.getY()) instanceof Doors)) {

				player.moveTo(DirectionEnum.lewo);
			}

		}

		if (Gdx.input.isKeyPressed(Input.Keys.X)) {
				gameMap.destroy(player.getX(),player.getY());
		}

		if (Gdx.input.isKeyPressed(Input.Keys.O)) {
			gameMap.openDoors(player.getX(),player.getY());
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
		if (enemy.getY() != 29 && (gameMap.get(enemy.getX(), enemy.getY() + 1) instanceof EmptyHall) || (gameMap.get(enemy.getX(), enemy.getY() + 1) instanceof Player)) {
			possibilities.add("up");
		}
		if (enemy.getY() != 0 && (gameMap.get(enemy.getX(), enemy.getY() - 1) instanceof EmptyHall) || (gameMap.get(enemy.getX(), enemy.getY() - 1) instanceof Player)) {
			possibilities.add("down");
		}
		if (enemy.getX() != 29 && (gameMap.get(enemy.getX() + 1, enemy.getY()) instanceof EmptyHall) || (gameMap.get(enemy.getX() + 1, enemy.getY()) instanceof Player)) {
			possibilities.add("right");
		}
		if(enemy.getX()!=0 && (gameMap.get(enemy.getX()-1,enemy.getY()) instanceof EmptyHall) || (gameMap.get(enemy.getX()-1,enemy.getY()) instanceof Player)){
			possibilities.add("left");
		}

		if(!possibilities.isEmpty()){
			Random r = new Random();
			String result = possibilities.get(r.nextInt(possibilities.size()));

			switch(result){
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
}