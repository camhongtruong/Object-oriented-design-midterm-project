package controller;

import java.awt.event.KeyEvent;

import model.Bird;
import model.GroupOfTubes;

public interface IController {

	public void controllRelease();

	public void changeCharacter(int number);

	public void restart();

	public void checkCollision();

	public int score();

}
