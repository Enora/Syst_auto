import java.util.HashMap;
import java.util.Map;

public class Couleur {
	
	public Map<String, int[]> colorTable;
	
	public Couleur(){
		this.colorTable = new HashMap();
		int[] a = {54, 66, 66};
		this.colorTable.put("BLEU", a);
		int[] b = {250, 38, 10};
		this.colorTable.put("ROUGE", b);
		int[] c = {99, 26, 6};
		this.colorTable.put("MARRON", c);
		int[] d = {26, 17, 7};
		this.colorTable.put("NOIR", d);
		int[] e = {300, 288, 155};
		this.colorTable.put("BLANC", e);
		int[] f = {91, 186, 10};
		this.colorTable.put("VERT", f);
	}
	
	public static enum couleur {
        BLEU, ROUGE, MARRON, NOIR, BLANC, VERT
    }
	
	public void setColors(String name, int[] value) {
		colorTable.put(name, value);
	}
	
}
