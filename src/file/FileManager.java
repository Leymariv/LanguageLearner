package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

import org.omg.PortableInterceptor.SUCCESSFUL;

import card.Card;

public class FileManager {

	private BufferedReader br;
	public LinkedList<Card> cardList;
	private String s1,s2;

	public FileManager(String name) throws IOException{

		br = new BufferedReader(new InputStreamReader(new FileInputStream(name)));
		cardList = null;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public String getS2() {
		return s2;
	}

	public void setS2(String s2) {
		this.s2 = s2;
	}

	public void BuildFile() throws IOException{
		String line;
		String name1;
		String name2;
		int sucesses = 0;
		int tries = 0;
		Card c = null;	

		if ((line = br.readLine()) !=null){
			cardList = new LinkedList<>();
		}

		while(line != null){

			name1 = line.substring(0,line.indexOf("/"));
			name2 = line.substring(line.indexOf("/")+1);
			line = name2;
			if (name2.contains("/")){  //if the file has already been read we have a score 
				name2 = line.substring(0,line.indexOf("/"));
				if (line.contains("/")){
					String temp = line;
					line = temp.substring(temp.indexOf("/")+1);
					sucesses = Integer.parseInt(line.substring(0,line.indexOf("/")));
					temp = line.substring(line.indexOf("/")+1);				
					if (!temp.contains("/")){
						tries = Integer.parseInt(temp);
					}
					else tries = Integer.parseInt(temp.substring(0,temp.indexOf("/")));
				}
			}
			c = new Card(name1, name2, tries, sucesses);
			cardList.add(addSorted(c), c);
			line = br.readLine();
		}
	}

	public int addSorted (Card c){//return the place where the card should be insert in the file
		int val = 0;
		if(cardList.isEmpty()){
			return 0;
		}
		else{
			for (int i=0 ; i< cardList.size(); i++){
				if(c.getTries() < cardList.get(i).getTries()){
					return i;
				}
			}
			return cardList.size();
		}
	}

	public void affCardList(){
		for (int i = 0 ; i < cardList.size(); i ++){
			System.out.println("\t card "+(i+1)+": ");
			System.out.println(cardList.get(i).getName1()+" "+
					cardList.get(i).getName2()+" "+
					cardList.get(i).getTries()+" "+
					cardList.get(i).getSucesses()+" "+
					cardList.get(i).getRate());
		}
	}

	public void writeFile(String name ) throws IOException{
		FileWriter fw = new FileWriter(new File(name));
		BufferedWriter output =new BufferedWriter(fw);
		String finalString = "";

		for (int i = 0 ; i < cardList.size(); i ++){
			String name1 = cardList.get(i).getName1();
			String name2 = cardList.get(i).getName2();
			int sucesses = cardList.get(i).getSucesses();
			int tries = cardList.get(i).getTries();
			String rate = cardList.get(i).getRate();
			finalString += name1+"/"+name2+"/"+sucesses+"/"+tries+"/"+rate+"\n";
		}

		output.write(finalString);
		output.flush();
		output.close();
	}

	public void recoverFile(String name ) throws IOException{
		FileWriter fw = new FileWriter(new File(name));
		BufferedWriter output =new BufferedWriter(fw);
		String finalString = "";

		for (int i = 0 ; i < cardList.size(); i ++){
			String name1 = cardList.get(i).getName1();
			String name2 = cardList.get(i).getName2();
			finalString += name1+"/"+name2+"\n";
		}

		output.write(finalString);
		output.flush();
		output.close();
	}


	public Card getCard (String s1){
		Card c = null;

		for(int i = 0 ; i< cardList.size() ; i++){
			if (cardList.get(i).getName1().equals(s1) || cardList.get(i).getName2().equals(s1)) c = cardList.get(i);
		}

		return c;
	}

	public void setCard(String name1, int s){// enter 1 if success and 0 if miss
		for(int i = 0 ; i< cardList.size() ; i++){
			if (cardList.get(i).getName1().equals(name1) || cardList.get(i).getName2().equals(name1)){
				int pre_scs = cardList.get(i).getSucesses();//previous nbre of success
				int pre_trs = cardList.get(i).getTries();
				cardList.get(i).setSucesses(pre_scs +s);
				cardList.get(i).setTries(pre_trs +1);
				String rate = cardList.get(i).calculRate(pre_trs +1,pre_scs +s);
				cardList.get(i).setRate(rate);
			}
		}
	}

	// set the boolean s : true means that the card has already been picked at least once
	public void setCard(String name1, boolean s){
		for(int i = 0 ; i< cardList.size() ; i++){
			if (cardList.get(i).getName1().equals(name1)){
				cardList.get(i).setDone(s);
			}
		}
	}


	public void pickCard(){

		int random = 0 + (int)(Math.random() * ((cardList.size() - 1) + 1));
		Card c = cardList.get(random);
		this.s1 = c.getName1();
		this.s2 = c.getName2();

	}

	public void pickCardV2(){

		int random = 0 + (int)(Math.random() * ((cardList.size() - 1) + 1));
		Card c = cardList.get(random);
		if (c.getSucesses() >= 5) {
			c.setreverse(true);
			this.s2 = c.getName1();
			this.s1 = c.getName2();

		}
		else {
		this.s1 = c.getName1();
		this.s2 = c.getName2();
		}
		if (this.s2.contains("-")){
			this.s2 = c.getName1().substring(0,c.getName1().indexOf("-"));
		}
	}
}


