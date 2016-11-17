
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Interface extends JComponent{
	
	private AgenteLogico agenteLogico;
	private static final int MIN_PASSOS_POR_SEG = 1;
	private static final int MAX_PASSOS_POR_SEG = 20;
	private static final int INIT_PASSOS_POR_SEG = 1;
	
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
		JPanel mapaMentalPanel = new JPanel();
		mapaPanel.setPreferredSize(new Dimension(Main.FRAME_WIDTH/2,Main.FRAME_HEIGHT/2));
		mapa.setPreferredSize(new Dimension(Main.FRAME_WIDTH/2,Main.FRAME_HEIGHT/2));
		mapaMental.setPreferredSize(new Dimension(Main.FRAME_WIDTH/2,Main.FRAME_HEIGHT/2));
		mapaPanel.setLayout(new GridLayout(1,1));
		mapaMentalPanel.setLayout(new GridLayout(1,1));
		
		mapaMentalPanel.add(mapaMental);
		mapaPanel.add(mapa);
		
		
		
		this.add(mapaPanel);
		this.add(mapaMentalPanel);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
		JButton testButton = new JButton("LOAD MAPA");
		testButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				System.out.println("updateMapa");
				agenteLogico.updateMapa();
				testButton.setEnabled(false);
			}
		});
		JButton runButton=new JButton("GO");
		runButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Vish");
				Thread t1 = new Thread(agenteLogico);
				t1.start();
				runButton.setEnabled(false);
			}
		});
		JSlider passosPorSegundoSlider = new JSlider(JSlider.HORIZONTAL,MIN_PASSOS_POR_SEG,MAX_PASSOS_POR_SEG,INIT_PASSOS_POR_SEG);
		passosPorSegundoSlider.setMajorTickSpacing(2);
		passosPorSegundoSlider.setMinorTickSpacing(1);
		passosPorSegundoSlider.setPaintTicks(true);
		passosPorSegundoSlider.setPaintLabels(true);
		passosPorSegundoSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(!passosPorSegundoSlider.getValueIsAdjusting()){
					agenteLogico.setRefreshRate(1000/passosPorSegundoSlider.getValue());
				}
			}
			
		});
		JLabel estadoValores = new JLabel();
		estadoValores.setMinimumSize(new Dimension((Main.FRAME_WIDTH/4)+10,20));
		estadoValores.setMaximumSize(new Dimension((Main.FRAME_WIDTH/4)+10,20));
		estadoValores.setPreferredSize(new Dimension((Main.FRAME_WIDTH/4)+10,20));
		rightPanel.add(estadoValores);
		rightPanel.add(passosPorSegundoSlider);
		rightPanel.add(testButton);
		rightPanel.add(runButton);
		
		this.add(rightPanel,BorderLayout.EAST);
		
		this.agenteLogico = new AgenteLogico();
		agenteLogico.setMapa(mapa);
		agenteLogico.setMapaMental(mapaMental);
		agenteLogico.setLabel(estadoValores);
		agenteLogico.init();
		
		
		
		
		
	}
	
}