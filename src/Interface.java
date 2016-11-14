
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Interface extends JComponent{
	
	private AgenteLogico agenteLogico;
	
	public Interface(){
		super();
		/*
		 * Flow layout porque eu so quero colocar esse panel dos 
		 * mapas e outro com as informaçoes e os botoes em sequencia
		 */
		this.setLayout(new FlowLayout());
		/*
		 * Criei os dois mapas, vou passar ambos pra consulta que
		 * vai saber o que passar para cada um. O jeito que cada um
		 * lida com a informaçao é igual e portanto eles são a mesma
		 * classe. Só receberam uma lista de toUpdate que ela deve
		 * iterar e desenhar cada componente.
		 */
		Mapa mapa = new Mapa();
		Mapa mapaMental = new Mapa();
		/*
		 * Box Layout porque eu quero um embaixo do outro só,
		 * achei melhor que Grid mas tenho que ver o que acontece
		 * quando dou resize
		 */
		JPanel mapaPanel = new JPanel();
		mapaPanel.setPreferredSize(new Dimension(Main.FRAME_WIDTH/2,Main.FRAME_HEIGHT));
		mapaPanel.setLayout(new BoxLayout(mapaPanel,BoxLayout.Y_AXIS));
		mapaPanel.add(mapa);
		mapaPanel.add(mapaMental);
		
		this.add(mapaPanel);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		JButton testButton = new JButton("Hello");
		testButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Hello");
			}
		});
		rightPanel.add(testButton);
		
		this.add(rightPanel,BorderLayout.EAST);
		
		this.agenteLogico = new AgenteLogico();
		agenteLogico.setMapa(mapa);
		agenteLogico.setMapaMental(mapaMental);
		agenteLogico.init();
		
		
		
		
		
	}
	
}