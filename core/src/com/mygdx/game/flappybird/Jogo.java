package com.mygdx.game.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class Jogo extends ApplicationAdapter {

	private float larguraDispositivo; //Largura do Pano de Fundo
	private float alturaDispositivo; //Altura do Pano de Fundo
	private float posicaoInicialVerticalPassaro = 0; //Posição inicial vai se iniciar no zero
	private float variacao = 0; //Variação das imagens do passaro para gerar o movimento das asa
	private float posicaoCanoHorizontal;
	private float posicaoCanoVertical;
	private  float espacoEntreCanos;

	private int pontos = 0; //Movimento do eixo Y
	private int gravidade = 0; //Gravidade do paasaro conforme se movimenta

	private Texture[] passaros; //As imagens dos sprites
	private Texture fundo; //A imagem de fundo
	private Texture canoBaixo;
	private Texture canoTopo;
	private SpriteBatch batch; //Quantidades de sprites que vai ser criado
	BitmapFont textoPontuacao;
	private boolean passouCano = false;
	private Random random;

	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoCima;
	private Rectangle retanguloCanoBaixo;


	@Override
	public void create () {
		inicializaTexturas();
		inicializaObjetos();
		

	}

	private void inicializaObjetos() {

		random = new  Random();
		batch = new SpriteBatch(); //Instanciando um novo lotes de sprites

		larguraDispositivo = Gdx.graphics.getWidth(); //Pegar a largura do pano de fundo
		alturaDispositivo = Gdx.graphics.getHeight(); //Pegar a altura do pano de fundo
		posicaoInicialVerticalPassaro = alturaDispositivo / 2; //Fazer a posição inicial ficar entre o centro da tela e não mais na posição 0
		posicaoCanoHorizontal = larguraDispositivo;
		espacoEntreCanos = 350;

		textoPontuacao = new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);

		shapeRenderer = new ShapeRenderer();
		circuloPassaro = new Circle();
		retanguloCanoCima = new Rectangle();
		retanguloCanoBaixo = new Rectangle();
	}

	private void inicializaTexturas() {

		passaros = new Texture[3]; //Instanciando a quantidade de sprites a ser colocado
		passaros[0] = new Texture("passaro1.png"); //Imagem do passaro 1
		passaros[1] = new Texture("passaro2.png"); //Imagem do passaro 2
		passaros[2] = new Texture("passaro3.png"); //Imagem do passaro 3

		fundo = new Texture("fundo.png"); //Imagem do pano de fundo
		canoBaixo = new Texture("cano_baixo_maior.png"); //Imagem do cano inferior
		canoTopo = new Texture("cano_topo_maior.png"); //Imagem do cano superior
	}


	@Override
	public void render () {

		verificaEstadoJogo();
		desenharTexturas();
		detectarColisão();
		validarPontos();

		}

	private void detectarColisão() {
		circuloPassaro.set(50 + passaros[0].getWidth() / 2, posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);

		retanguloCanoBaixo.set(posicaoCanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical, canoBaixo.getWidth(), canoBaixo.getHeight());

		retanguloCanoCima.set(posicaoCanoHorizontal, alturaDispositivo / 2 - canoTopo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical, canoTopo.getWidth(), canoTopo.getHeight());

		boolean bateuCanoCima = Intersector.overlaps(circuloPassaro, retanguloCanoCima);
		boolean bateuCanoBaixo = Intersector.overlaps(circuloPassaro, retanguloCanoBaixo);

		if(bateuCanoBaixo || bateuCanoCima){
			Gdx.app.log("Log", "Bateu");
		}
	}

	private void validarPontos() {
		if(posicaoCanoHorizontal < 50 - passaros[0].getWidth()){
			if(!passouCano){
				pontos++;
				passouCano = true;
			}
		}
	}

	private void verificaEstadoJogo() {

		posicaoCanoHorizontal -= Gdx.graphics.getDeltaTime() * 200;
		if(posicaoCanoHorizontal < -canoBaixo.getHeight()){
			posicaoCanoHorizontal = larguraDispositivo;
			posicaoCanoVertical = random.nextInt(400) - 200;
			passouCano = false;
		}

		//Faz a posição inicial ao sofrer gravidade, mas ao tocar na tela ele flutua
		boolean toqueTela = Gdx.input.justTouched(); //Ao tocar na tela faz algo
		if(Gdx.input.justTouched()){ //Se ao clicar no touch
			gravidade = -25; //Faz o passaro flutuar para cima a cada toque na tela
		}

		if(posicaoInicialVerticalPassaro > 0 || toqueTela) //Se a posição inicial for maior que zero e tocar na tela
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;

		variacao += Gdx.graphics.getDeltaTime() * 10; //Suaviza a animação do bater das asas do passaro ao multiplicar por 10
		if(variacao > 3) //Se aa imagens do sprites ultrapassar de três, faz alguma coisa
			variacao = 0; //A imagem for maior que 3 retorna para zero

		gravidade++; //Adiciona gravidade
	}

	private void desenharTexturas() {

		batch.begin(); //Inicio do processo

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo); //Desenha o fundo da tela
		batch.draw(passaros[(int) variacao], 50, posicaoInicialVerticalPassaro); //Desenha a posição, a altura e a largura do pássaro
		batch.draw(canoBaixo, posicaoCanoHorizontal, alturaDispositivo/2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + posicaoCanoVertical);
		batch.draw(canoTopo, posicaoCanoHorizontal, alturaDispositivo / 2 + espacoEntreCanos / 2 + posicaoCanoVertical);
        textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo /2, alturaDispositivo - 100);

		batch.end(); //Fim do processo
	}

	@Override
    public void dispose () {

    }

}
