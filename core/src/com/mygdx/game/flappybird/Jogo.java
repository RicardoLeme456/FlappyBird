package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Jogo extends ApplicationAdapter {

	private int movimentaY = 0; //Movimento do eixo Y
	private int movimentaX = 1000; //Movimento do eixo X
	private SpriteBatch batch; //Quantidades de sprites que vai ser criado
	private Texture[] passaros; //As imagens dos sprites
	private Texture cano;
	private Texture cano2;
	private Texture fundo; //A imagem de fundo

	private float larguraDispositivo; //Largura do Pano de Fundo
	private float alturaDispositivo; //Altura do Pano de Fundo
	private float variacao = 0; //Variação das imagens do passaro para gerar o movimento das asa
	private float gravidade = 0; //Gravidade do paasaro conforme se movimenta
	private float posicaoInicialVerticalPassaro = 0; //Posição inicial vai se iniciar no zero


	
	@Override
	public void create () {
		batch = new SpriteBatch(); //Instanciando um novo lotes de sprites
		passaros = new Texture[3]; //Instanciando a quantidade de sprites a ser colocado
		passaros[0] = new Texture("passaro1.png"); //Imagem do passaro 1
		passaros[1] = new Texture("passaro2.png"); //Imagem do passaro 2
		passaros[2] = new Texture("passaro3.png"); //Imagem do passaro 3
		cano = new Texture("cano_topo_maior.png"); //Imagem do cano superior
		cano2 = new Texture("cano_baixo_maior.png"); //Imagem do cano inferior
		fundo = new Texture("fundo.png"); //Imagem do pano de fundo

		larguraDispositivo = Gdx.graphics.getWidth(); //Pegar a largura do pano de fundo
		alturaDispositivo = Gdx.graphics.getHeight(); //Pegar a altura do pano de fundo
		posicaoInicialVerticalPassaro = alturaDispositivo / 2; //Fazer a posição inicial ficar entre o centro da tela e não mais na posição 0


	}

	@Override
	public void render () {

		batch.begin(); //Inicio do processo

		if(variacao > 3) //Se aa imagens do sprites ultrapassar de três, faz alguma coisa
			variacao = 0; //A imagem for maior que 3 retorna para zero
			boolean toqueTela = Gdx.input.justTouched(); //Ao tocar na tela faz algo
			if(Gdx.input.justTouched()){ //Se ao clicar no touch
				gravidade = -25; //Faz o passaro flutuar para cima a cada toque na tela
			}

			if(posicaoInicialVerticalPassaro > 0 || toqueTela) //Se a posição inicial for maior que zero e tocar na tela
				posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade; //Faz a posição inicial ao sofrer gravidade, mas ao tocar na tela ele flutua

			batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo); //Desenha o fundo da tela
			batch.draw(passaros[(int) variacao], 30, posicaoInicialVerticalPassaro); //Desenha a posição, a altura e a largura do pássaro
            batch.draw(cano, movimentaX, 1000); //Cano superior faz o movimento
		    batch.draw(cano2, movimentaX,- 400); //Cano inferior faz o movimento

			variacao += Gdx.graphics.getDeltaTime() * 10; //Suaviza a animação do bater das asas do passaro ao multiplicar por 10

			gravidade++; //Adiciona gravidade
			movimentaX--; //Adiciona Movimentação do eixo x
			movimentaY--; //Adiciona Movimentação do eixo y
			batch.end(); //Fim do processo
		}

    @Override
    public void dispose () {

    }

}
