import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Essa classe ai carregar todas as imagens pro Tile e vai guardar elas
 * como BufferedImages em variaveis staticas. O Static initialization
 * block dela vai carregar tudo a partir das files .png e ela vai ter
 * um metodo que passa a imagem em função de um char passado, pra 
 * facilitar pro Tile, que nao vai ter que ter esse switch case.
 * Cima Baixo Direita Esquerda. Seila, qualquer coisa. É questao
 * de documentação só. Poderia fazer um enum tambem e resolve. Inimigo,
 * Duvida, etc.
 * @author Guilherme Simas
 *
 */
public class MapImage {
	public static BufferedImage norte;
	public static BufferedImage sul;
	public static BufferedImage leste;
	public static BufferedImage oeste;
	public static BufferedImage buraco;
	public static BufferedImage teleporte;
	public static BufferedImage inimigoWeak;
	public static BufferedImage inimigoStrong;
	public static BufferedImage ouro;
	public static BufferedImage powerup;
	public static BufferedImage saida;
	public static BufferedImage nothing;
	public static BufferedImage duvida;	
	
	private static final String norteFile = "norteFile.png";
	private static final String sulFile = "sulFile.png";
	private static final String lesteFile = "lesteFile.png";
	private static final String oesteFile = "oesteFile.png";
	private static final String buracoFile = "buracoFile.png";
	private static final String teleporteFile = "teleportFile.png";
	private static final String inimigoWeakFile = "inimigoWeakFile.png";
	private static final String inimigoStrongFile = "inimigoStrongFile.png";
	private static final String ouroFile = "ouroFile.png";
	private static final String powerupFile = "powerupFile.png";
	private static final String saidaFile = "saidaFile.png";
	private static final String nothingFile = "nothingFile.png";
	private static final String duvidaFile = "duvidaFile.png";
	
	static {
		try {
			norte = ImageIO.read(new File(norteFile));
			sul = ImageIO.read(new File(sulFile));
			leste = ImageIO.read(new File(lesteFile));
			oeste = ImageIO.read(new File(oesteFile));
			buraco = ImageIO.read(new File(buracoFile));
			teleporte = ImageIO.read(new File(teleporteFile));
			inimigoWeak = ImageIO.read(new File(inimigoWeakFile));
			inimigoStrong = ImageIO.read(new File(inimigoStrongFile));
			ouro = ImageIO.read(new File(ouroFile));
			powerup = ImageIO.read(new File(powerupFile));
			saida = ImageIO.read(new File(saidaFile));
			nothing = ImageIO.read(new File(nothingFile));
			duvida = ImageIO.read(new File(duvidaFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage getImage(char c){
		switch(c){
		case 'n':
			return norte;
		case 's':
			return sul;
		case 'e':
			return leste;
		case 'w':
			return oeste;
		case 'P':
			return buraco;
		case 'T':
			return teleporte;
		case 'd':
			return inimigoWeak;
		case 'D':
			return inimigoStrong;
		case 'O':
			return ouro;
		case 'U':
			return powerup;
		case 'x':
			return saida;
		case '.':
			return nothing;
		case '?':
			return duvida;
		default:
			return ouro;			
		}
	}
	
}
