import java.awt.image.BufferedImage;

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
	
	private static final String norteFile = "norte.png";
	private static final String sulFile = "sul.png";
	private static final String lesteFile = "leste.png";
	private static final String oesteFile = "oeste.png";
	private static final String buracoFile = "buraco.png";
	private static final String teleporteFile = "teleporte.png";
	private static final String inimigoWeakFile = "inimigoWeak.png";
	private static final String inimigoStrongFile = "inimigoStrong.png";
	private static final String ouroFile = "ouro.png";
	private static final String powerupFile = "";
	private static final String saidaFile = "";
	
	static {
		
	}
}
