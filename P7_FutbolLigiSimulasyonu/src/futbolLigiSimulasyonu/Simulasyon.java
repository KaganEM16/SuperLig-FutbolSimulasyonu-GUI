package futbolLigiSimulasyonu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Simulasyon extends JFrame implements ActionListener
{
	String[] takimIsimleri = {"Besiktas", "Fenerbahce", "Galatasaray", "Trabzonspor", "Basaksehir", "Kasimpasa", "Sivasspor", "Adana Demirspor",
			"Alanyaspor", "Antalyaspor", "Rizespor", "Samsunspor", "Hatayspor", "Gaziantep FK", "Kayserispor", "Konyaspor", "Goztepe", "Bursaspor"};
	Takimlar[] takimlar;
	JPanel[] paneller;	
	JLabel[] basliklar;
	JLabel[] takimKisimlari;
	JLabel[] eslesmeler;
	JLabel[] eslesmeSonuclari;
	JLabel[] bitisMesajları;
	JButton[] devamButonu;
	ImageIcon[] resimler;
	int hafta = 1;
	List<String> ilkYarıFikstür;
    List<String> ikinciYarıFikstür;
    	
	public Simulasyon()
	{
		super("Süper Lig Simulasyonu");
		int en = 800, boy = 800;
		this.setSize(en+14, boy+37);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		
		takimlar = new Takimlar[takimIsimleri.length];
		int guc = 5;
		for(int i=0 ; i<takimlar.length ; i++) {			
			takimlar[i] = new Takimlar(takimIsimleri[i], guc, 0);
			guc = (i<3)? 5 : (i >= 10)? 3 : 4;				
		}
		
		basliklar = new JLabel[4];
		basliklar[0] = new JLabel("Süper Lig Simulasyonu'na Hoş Geldiniz");
		basliklar[1] = new JLabel("Süper Lig'te " + hafta + ".Hafta Öncesi Puan Durumu Şu Şekilde");
		basliklar[2] = new JLabel(hafta + ". Hafta Eleşmeleri Şu Şekilde");
		basliklar[3] = new JLabel(hafta + ". Hafta Eleşmelerinin Sonuçları Şu Şekilde");
		for(int i=0 ; i<basliklar.length ; i++) {
			etiketAyarla(basliklar[i], 25, Color.RED);
		}
		
		paneller = new JPanel[5];
		for(int i=0 ; i<paneller.length ; i++) {
			paneller[i] = new JPanel();
			paneller[i].setSize(en, boy);
			paneller[i].setBackground(Color.BLACK);
			paneller[i].setLayout((i==0 || i==4)? new FlowLayout() : (i==1)? new GridLayout(20,1) : new GridLayout(11,1));
			if(i<4)
				paneller[i].add(basliklar[i]);
		}		
		this.add(paneller[0]);
		
		resimler = new ImageIcon[2];
		for(int i=0 ; i<resimler.length ; i++){
			ImageIcon duzenlenecekkapakResmi = new ImageIcon("Resimler/" + (i) + ".png");
			Image duzenlenecekResim = duzenlenecekkapakResmi.getImage();
			Image duzenlenenResim = duzenlenecekResim.getScaledInstance(700, (i==0)?600 : 400, Image.SCALE_SMOOTH);				
			resimler[i] = new ImageIcon(duzenlenenResim);
		}
		JLabel kapakResmi = new JLabel(resimler[0]);
		
		devamButonu = new JButton[4];
		for(int i=0 ; i<devamButonu.length ; i++) {
			devamButonu[i] = new JButton((i==0)? "Başla" : "Devam");
			devamButonu[i].addActionListener(this);
			devamButonu[i].setBorderPainted(false);
			devamButonu[i].setBackground(Color.DARK_GRAY);
			devamButonu[i].setFont(new Font("Verdana", Font.BOLD, 30));
			devamButonu[i].setForeground(Color.WHITE);
		}
		devamButonu[0].setPreferredSize(new Dimension(200, 80));

//		paneller[0]'ın Hazırlanışı:		
		paneller[0].add(kapakResmi);
		paneller[0].add(devamButonu[0]);
		
//		paneller[1]'in Hazırlanışı:		
		takimKisimlari = new JLabel[takimlar.length];
		for(int i=0 ; i<takimKisimlari.length ; i++) {
			takimKisimlari[i] = new JLabel(takimlar[i].getIsim() + "   " + takimlar[i].getPuan());
			etiketAyarla(takimKisimlari[i], 20, Color.WHITE);
			paneller[1].add(takimKisimlari[i]);
		}		
		paneller[1].add(devamButonu[1]);

//		Fikstürlerin Hazırlanışı:
		ilkYarıFikstür = new ArrayList<>();
        ikinciYarıFikstür = new ArrayList<>();
        for (int bulunulanHafta = 0; bulunulanHafta < takimlar.length - 1; bulunulanHafta++) {
            for (int maç = 0; maç < takimlar.length / 2; maç++) {
                int evSahibi = (bulunulanHafta + maç) % (takimlar.length - 1);
                int deplasman = (takimlar.length - 1 - maç + bulunulanHafta) % (takimlar.length - 1);
                // Son takım her zaman sabit kalır, bu nedenle onu tekrar ekliyoruz
                if (maç == 0) {
                    deplasman = takimlar.length - 1;
                }
                ilkYarıFikstür.add(takimlar[evSahibi].getIsim() + " vs " + takimlar[deplasman].getIsim());
                ikinciYarıFikstür.add(takimlar[deplasman].getIsim() + " vs " + takimlar[evSahibi].getIsim());
            }
        }
        
//		paneller[2]'nin Hazırlanışı:
        eslesmeler = new JLabel[takimlar.length / 2];
        for (int i = 0; i < takimlar.length / 2; i++) {
            eslesmeler[i] = new JLabel();
            etiketAyarla(eslesmeler[i], 20, Color.WHITE);
            paneller[2].add(eslesmeler[i]);
        }
        paneller[2].add(devamButonu[2]);		
		
//		paneller[3]'ün Hazırlanışı:
        eslesmeSonuclari = new JLabel[takimlar.length / 2];
        for (int i = 0; i < takimlar.length / 2; i++) {
            eslesmeSonuclari[i] = new JLabel();
            etiketAyarla(eslesmeSonuclari[i], 20, Color.WHITE);
            paneller[3].add(eslesmeSonuclari[i]);
        }
        paneller[3].add(devamButonu[3]);
        
//		paneller[4]'ün Hazırlanışı:
        bitisMesajları = new JLabel[3];
        bitisMesajları[0] = new JLabel("Süper Lig Sona Erdi");
        bitisMesajları[2] = new JLabel("Oynadığınız İçin Teşekkürler");
        bitisMesajları[1] = new JLabel();
        for(int i=0 ; i<bitisMesajları.length ; i++)
        	etiketAyarla(bitisMesajları[i], 30, Color.LIGHT_GRAY);
        
		this.add(paneller[0]);		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if ((JButton) e.getSource() == devamButonu[0]) {
            paneller[0].setVisible(false);
            this.add(paneller[1]);
            paneller[1].setVisible(true);
        } else if ((JButton) e.getSource() == devamButonu[1]) {
            if(hafta < 35) {
            	paneller[1].setVisible(false);

            	if(hafta <= 17) {
	                for (int i = 0; i < takimlar.length / 2; i++) {
	                    eslesmeler[i].setText(ilkYarıFikstür.get((hafta - 1) * (takimlar.length / 2) + i));
	                }
            	}else {
            		for (int i = 0; i < takimlar.length / 2; i++) {
	                    eslesmeler[i].setText(ikinciYarıFikstür.get((hafta - 17 - 1) * (takimlar.length / 2) + i));
	                }
            	}

                paneller[2].setVisible(true);
                this.add(paneller[2]);
            }else {
            	// paneller[4]'ün Eklenmesi:
            	String[] sampiyonBilgisi = takimKisimlari[0].getText().split("   ");
            	bitisMesajları[1].setText("<html>Süper Lig Şampiyonu, " + sampiyonBilgisi[1] + " Puan ile<br>" + sampiyonBilgisi[0] + " Oldu.</html>");            	
            	for(int i=0 ; i<bitisMesajları.length ; i++)
            		paneller[4].add(bitisMesajları[i]);
            	JLabel kapanisResmi = new JLabel(resimler[1]);
            	paneller[4].add(kapanisResmi);
            	paneller[1].setVisible(false);
            	this.add(paneller[4]);
            }
        } else if ((JButton) e.getSource() == devamButonu[2]) {
            paneller[2].setVisible(false);

            for (int i = 0; i < takimlar.length / 2; i++) {
            	String eslesme = eslesmeler[i].getText();
            	String[] oynayanTakimlar = eslesme.split(" vs ");
            	for(int j=0 ; j<takimlar.length ; j++) {            		
            		if(takimlar[j].getIsim().equals(oynayanTakimlar[0])) {            			
            			for(int k=0 ; k<takimlar.length ; k++) {            				
                    		if(takimlar[k].getIsim().equals(oynayanTakimlar[1])) {
                    			eslesmeSonuclari[i].setText(takimlar[j].macYap(takimlar[k]));                    			 
                    		}
            			}
            		}
            	} 
            }

            paneller[3].setVisible(true);
            this.add(paneller[3]);
        } else {
            paneller[3].setVisible(false);

            for (int i = 0; i < takimKisimlari.length; i++) {
                takimKisimlari[i].setText(takimlar[i].getIsim() + "   " + takimlar[i].getPuan());
            }

            hafta++;
            if(hafta < 34) {
            	puanSirala();
            	basliklar[1].setText("Süper Lig'te " + hafta + ".Hafta Öncesi Puan Durumu Şu Şekilde");
            	basliklar[2].setText(hafta + ". Hafta Eleşmeleri Şu Şekilde");
            	basliklar[3].setText(hafta + ". Hafta Eleşmelerinin Sonuçları Şu Şekilde");
            }else {
            	if(hafta == 34)
            		basliklar[1].setText("Süper Lig'te Son Hafta Öncesi Puan Durumu Şu Şekilde");
            	else {
            		basliklar[1].setText("Süper Lig'te Yarış Sona Erdi! Puan Durumu Şu Şekilde");
            	}
            	basliklar[2].setText("Son Hafta Eleşmeleri Şu Şekilde");
            	basliklar[3].setText("Son Hafta Eleşmelerinin Sonuçları Şu Şekilde");
            }

            paneller[1].setVisible(true);
            this.add(paneller[1]);
        }
	}
	
	public void puanSirala() {
		Arrays.sort(takimlar, (t1, t2) -> Integer.compare(t2.getPuan(), t1.getPuan()));
        for (int i = 0; i < takimlar.length; i++) {
            takimKisimlari[i].setText(takimlar[i].getIsim() + "   " + takimlar[i].getPuan());
        }
	}
	
	public void etiketAyarla(JLabel etiket, int boyut, Color renk) {
		etiket.setFont(new Font("Verdana", Font.BOLD, boyut));
		etiket.setForeground(renk);
		etiket.setHorizontalAlignment(0);
	}	
}
