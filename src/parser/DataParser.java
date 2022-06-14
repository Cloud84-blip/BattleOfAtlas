package parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import game.*;


public class DataParser {
	/**
	 * Cette classe permet de lire les fichiers txt afin d'extraire leur contenu pour etre utilise ailleurs.
	 */
	private static ArrayList<String> lines = parse();
	public static String CardPath = CombatPanel.getPath()+"/assets/cartes/";
	
	public static Carte getCarte(Personnage source, Personnage cible, String name){
		String cdata = getCardData(name);
		String[] tmp = cdata.split(";");
		ArrayList<Effet> ae = new ArrayList<>();
		Effet e = new Effet(source, cible, Integer.parseInt(tmp[4].trim()), Integer.parseInt(tmp[2].trim()));
		ae.add(e);
		return new Carte(0, tmp[0], Integer.parseInt(tmp[1].trim()), Integer.parseInt(tmp[2].trim()), CardPath+tmp[3].trim(), ae);
	}
	
	private static String getCardData(String name){
		for (String s : DataParser.lines){
			if (s.contains(name)){
				return s;
			}
		}
		return "";
	}

	public static String[] getCardNameList(String type){
		Path path = Paths.get(CombatPanel.getPath()+"/cardDatas/decksHeros.txt");
		try {
			List<String> read = Files.readAllLines(path);
			String r = String.join("", read).trim();
			String[] rz = r.split(";");
			for (String line : rz){
				if (!line.startsWith("#")){
					if (line.startsWith(type)){
						String[] liste = line.split("=");
						return liste[1].split(",");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getCardNameListEnnemi(String type){
		Path path = Paths.get(CombatPanel.getPath()+"/cardDatas/deckEnnemis.txt");
		try {
			List<String> read = Files.readAllLines(path);
			String r = String.join("", read).trim();
			String[] rz = r.split(";");
			for (String line : rz){
				if (!line.startsWith("#")){
					if (line.startsWith(type)){
						String[] liste = line.split("=");
						return liste[1].split(",");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getCardNameLieu(String type){
		Path path = Paths.get(CombatPanel.getPath()+"/cardDatas/cartesRecompenseEtBoutique.txt");
		try {
			List<String> read = Files.readAllLines(path);
			String r = String.join("", read).trim();
			String[] rz = r.split(";");
			for (String line : rz){
				if (!line.startsWith("#")){
					if (line.startsWith(type)){
						String[] liste = line.split("=");
						return liste[1].split(",");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getCardNameBoss(String type){
		Path path = Paths.get(CombatPanel.getPath()+"/cardDatas/recompensesBoss.txt");
		try {
			List<String> read = Files.readAllLines(path);
			String r = String.join("", read).trim();
			String[] rz = r.split(";");
			for (String line : rz){
				if (!line.startsWith("#")){
					if (line.startsWith(type)){
						String[] liste = line.split("=");
						return liste[1].split(",");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Ennemi choixEnnemi() {
		Path path = Paths.get(CombatPanel.getPath()+"/cardDatas/ennemisListe.txt");
		ArrayList<Ennemi> listeEnnemisValables = new ArrayList<Ennemi>();
		try {
			List<String> read = Files.readAllLines(path);
			String r = String.join("", read).trim();
			String[] rz = r.split(";");
			for (String line : rz){
				if (!line.startsWith("#")){
					String[] liste = line.split(",");
					if (liste[1].trim().equals(FrameMain.zoneActuelle) && Boolean.parseBoolean(liste[2].trim()) == (FrameMain.combatBoss)) {
						Ennemi ennemi = new Ennemi(liste[0].trim(), FrameMain.decksEnnemis.get(liste[0].trim()), Integer.parseInt(liste[3].trim()));
						listeEnnemisValables.add(ennemi);
					}
				}
			}
			Random random = new Random();
			
			return listeEnnemisValables.get(random.nextInt(listeEnnemisValables.size()));

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<String> getEnnemiList() {
		ArrayList<String> res = new ArrayList<String>();
		
		Path path = Paths.get(CombatPanel.getPath()+"/cardDatas/deckEnnemis.txt");
		try {
			List<String> read = Files.readAllLines(path);
			String r = String.join("", read).trim();
			String[] rz = r.split(";");
			
			for (String line : rz){
				if (!line.startsWith("#")){
					String[] liste = line.split("=");
					res.add(liste[0].trim());
				}
			}
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static ArrayList<String> parse(){
		File file = new File(CombatPanel.getPath()+"/cardDatas/cartesListe.txt");
		ArrayList<String> lines = new ArrayList<>();
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()){
				String s = sc.nextLine();
				if (!s.startsWith("#")){
					lines.add(s);
				}
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
}