package com.maze.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.maze.game.objects.DirectionEnum;
import com.maze.game.objects.GameMap;
import com.maze.game.objects.gameObjects.*;
import com.maze.game.objects.gameObjects.standard.Doors;
import com.maze.game.objects.gameObjects.standard.Player;
import com.maze.game.objects.gameObjects.standard.VictoryPlace;
import com.maze.game.objects.gameObjects.standard.Wall;
import com.maze.game.objects.gameObjects.winter.WinterWall;

import static com.maze.game.objects.utils.Constants.EMPTY_HALL_CODE;
import static com.maze.game.objects.utils.PathToFilesUtil.*;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;

	private Player player;
	private GameMap gameMap = new GameMap();

	int flagStart =0;
	
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
		}
		else {
			if (flagStart == 0) {
				gameMap.init(30,30);
				gameMap.initFactory(EMPTY_HALL_CODE);

				//gameMap.insertPlayerObject(player.getX(), player.getY());
				gameMap.loadFromFile(MAP_FILE_PATH);
				player = ((Player)gameMap.getPlayer());
				player.init();
				flagStart = 1;
				drawBackground();
			}

			renderMap();
			gameMap.movePlayer(player.getX(), player.getY());
			moveListener();
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
}
