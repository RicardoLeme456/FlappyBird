package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {
	SpriteBatch batch;
	Texture passaro;
	Texture fundo;

	private float larguraDispositivo;
	private float alturaDispositivo;

	private int movimentaY = 0;
	private int movimentaX = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		fundo = new Texture("fundo.png");
		passaro = new Texture("passaro1.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();

	}

	@Override
	public void render () {

		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
		batch.draw(passaro, movimentaX, movimentaY);

		movimentaX++;
		movimentaY++;
		batch.end();

	}
	
	@Override
	public void dispose () {

	}
}
