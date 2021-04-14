package it.polito.tdp.regine.model;

import java.util.ArrayList;
import java.util.List;

public class Regine {

	// N è il numero di righe e colonne della scacchiera
	//   (righe e colonne numerate da 0 a N-1)
	// ad ogni livello posizioniamo una regina in una nuova riga
	
	// soluzione parziale: lista delle colonne in cui mettere le regine (prime righe)
	// 		List<Integer>
	// livello = quante righe sono già piene
	// livello = 0 => nessuna riga piena (devo mettere la regina nella riga 0)
	// livello = 3 => 3 righe piene (0, 1, 2), devo mettere la regina nella riga 3
	// [0]
	//     [0, 2]
	//            [0, 2, 1]
	private int N;
	private List<Integer> soluzione;
	
	public List<Integer> risolvi(int N){// N è il numero di righe e colonne della scacchiera
		this.N=N;
		List<Integer> parziale=new ArrayList<Integer>();//non LinkedList perche sotto facciamo .get: nella lista e' troppo complesso, aggiungo ritardo!
		this.soluzione=null;
		
		cerca(parziale,0); //passiamo parziale e livello
		
		return this.soluzione;
		
	}
	
	//cerca==true --> trovato; 
	//cerca==false --> cerca ancora
	
	private boolean cerca(List<Integer>parziale, int livello) { //[0,6,4,7]
		
		if(livello==N) {
			//CASO TERMINALE
			this.soluzione=new ArrayList<>(parziale); //aggiorno soluzione
			return true; //1:ho la soluzione
		} 
		else {
			for(int colonna=0; colonna<N; colonna++) {//possibili posizioni in cui posso mettere la regina
				// if la pos nella casella [livello][colonna] è valida
				// se sì, aggiungi a parziale e fai ricorsione
				if(posValida(parziale,colonna)) {//per mantenere codice ricorsivo il piu' chiaro possibile
					
					/*meno efficiente perche' devo copiare un arraylist in un altro:
					List<Integer> parzialeNuovo=new ArrayList(parziale);//non ho bisogno di fare backtracking
					parzialeNuovo.add(colonna);
					cerca(parziale,livello+1);*/ 
					
					//metodo piu' efficiente perche' rimuove solamente
					parziale.add(colonna);//aggiungo sempre qualcosa alla mia lista-->[0,6,4,7,1]
					boolean trovato=cerca(parziale,livello+1);//conseguenze per i livelli successivi-->dopo aver fatto la prova TOLGO 1 e metto 5
					if(trovato)
						return true;//2:ogni procedura ritorna alla precedente perche' e' stata trovata la soluzione, senza andare avanti perche non serve
					parziale.remove(parziale.size()-1);//tolgo elemento appena aggiunto per fare un'altra prova: BACKTRACKING
													   //devo ricordarmi che quando salgo di livello devo rimettere a posto le cose!
				}
			}
			return false; //bisogna continuare a cercare
		}
	}
	private boolean posValida(List<Integer> parziale, int colonna) {
		int livello=parziale.size();//oppure potevo passarlo come parametro della funzione
		
		//controlla se viene mangiata in verticale: controllo per colonna
		if(parziale.contains(colonna)) //non possiamo mettere regine nella stessa colonna perche' si mangiano
			return false;
		
		//controllo per diagonale (per riga non e' necessario perche' e' insito in cio' che abbiamo fatto prima
		//confronta la posizione (livello,colonna) con (r,c) delle regine esistenti
		//livello dice la riga a cui siamo arrivati
		for(int r=0; r<livello;r++) {
			int c=parziale.get(r);
			if(r+c==livello+colonna||r-c==livello-colonna) 
				return false;
			
		}
		return true;
	}
	
	
	
}
