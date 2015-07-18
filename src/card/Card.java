package card;

import java.text.DecimalFormat;

public class Card {
	String name1;
	String name2;
	int tries = 0;
	int sucesses = 0;
	String rate;
	boolean done = false;
	boolean reverse = false;

	public Card(String n1, String n2, int t, int s){
		this.name1 = n1;
		this.name2 = n2;
		this.tries = t;
		this.sucesses = s;
		this.rate = calculRate(t,s);
		
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public void setDone(boolean d) {
		this.done = d;
	}

	public void setreverse(boolean r) {
		this.reverse = r;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public int getSucesses() {
		return sucesses;
	}

	public boolean getDone() {
		return done;
	}

	public boolean getreverse() {
		return reverse;
	}
	
	public void setSucesses(int sucesses) {
		this.sucesses = sucesses;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String calculRate(int t, int s){ 
		 
	double c = new Double(t);
	double result = s/c;
	double resultFinal = result*100;
	DecimalFormat df = new DecimalFormat("###");
	return df.format(resultFinal) + " %";
	}
	
	public void affichCard(){
		System.out.println("name1 :"+this.name1);
		System.out.println("name2 :"+this.name2);
		System.out.println("rate :"+this.rate);
	}
}


