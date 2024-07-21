package futbolLigiSimulasyonu;

import java.util.Random;

public class Takimlar 
{
	private String isim;
	int guc;
	private int puan;
	
	public Takimlar(String isim, int guc, int puan) {
		this.isim = isim;
		this.guc = guc;
		this.puan = puan;
	}	
	
	public String getIsim() {
		return isim;
	}
	
	public void setPuan(int puan) {
		this.puan = puan;
	}

	public int getPuan() {
		return puan;
	}
	
	public String macYap(Takimlar takim2)
	{
		int skor1 = 0, skor2 = 0;
				
		skor1 = golAt(this.guc);
		skor2 = golAt(takim2.guc);
		
		String sonuc = this.getIsim() + " " + skor1 + " --- " + skor2 + " " + takim2.getIsim();
		
		if(skor1 > skor2)
			this.puan += 3;
		else if(skor2 > skor1)
			takim2.puan += 3;
		else {
			this.puan ++;
			takim2.puan ++;
		}
		
		return sonuc;
	}
	
	public int golAt(int skor)
	{
		Random r = new Random();
		int atilanGolSayisi = (skor > 0) ? r.nextInt(skor) : 0;
		return atilanGolSayisi;
	}
}
