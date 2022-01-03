package controller;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import model.Bird;
import model.GroupOfTubes;
import model.Tube;
import view.FlappyBird;
import view.GameView;

public class Controller implements IController {
	private Bird b;// model
	private GameView view;// view
	private GroupOfTubes tubeColumn;
	private int score;

	public Controller(Bird bird) {
		super();
		this.b = bird;
		this.view = new GameView(b, this);
		view.createView();
		view.addKeyListener(new GameKey(this, view));
		view.eventForMenu();
	}

	@Override
	public void controllRelease() {
		b.fly();
	}

	@Override
	public void changeCharacter(int number) {
		b.changeCharacter(number);
	}

	@Override
	public void restart() {
		b.setLocationForBird();
		view.setTubeColumn(new GroupOfTubes());
		tubeColumn = view.getTubeColumn();
	}

	@Override
	public void checkCollision() {
		Rectangle birdRect = b.getRect();
		Rectangle tubeRect;
		for (int i = 0; i < tubeColumn.getTubes().size(); i++) {
			tubeRect = tubeColumn.getTubes().get(i).getRect();
			if (birdRect.intersects(tubeRect) || b.getY() + b.getHeight() > GameView.HEIGHT) {
				b.getHitSound().play();
				score = 0;
				view.endGame();

			}
		}
	}

	@Override
	public int score() {
		for (Tube t : tubeColumn.getTubes()) {
			if (b.getX() + b.getWidth() / 2 > t.getX() + t.getWidth() / 2 - 5
					&& b.getX() + b.getWidth() / 2 < t.getX() + t.getWidth() / 2 + 5) {
				score++;
				b.getGetScoreSound().play();
			}
		}
		return score;

	}

	private class GameKey extends KeyAdapter {
		private GameView view;
		private IController controler;

		public GameKey(IController controler, GameView view) {
			this.controler = controler;
			this.view = view;
		}

		@Override
		public void keyPressed(KeyEvent e) {
			view.restart();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			controler.controllRelease();
		}
	}

}