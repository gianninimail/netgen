package testes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class ContaLinhas 
{
	StringBuffer str_entrada = new StringBuffer("");
	long linhas_total = 0 ;
	long linhas_programa = 0;
	
	public void execute() {
	}
	
	public void clearCommArea() {
		this.str_entrada= new StringBuffer("");
	}
	
	public ContaLinhas(String caminho) {
		try{
			File dir = new File(caminho);
	        	File[] files = dir.listFiles();
			for(int i=0;i<files.length;i++)
			{
				int l1 = 0;
				File f=new File(files[i].getPath());
				BufferedReader arquivo=new BufferedReader(new FileReader(f));
				StringBuffer str = new StringBuffer();
				String s;
				while((s=arquivo.readLine())!=null)
				{
					str.append(s);
					l1 = l1 +1;
				}
				linhas_programa = l1;
				linhas_total = linhas_total + l1;				
				System.out.println("Programa   - \t " + files[i].getPath() + ": \t" + linhas_programa);
				linhas_programa = 0;
				
				arquivo.close();
			}
				System.out.println("Total      - \t " + caminho +" : \t"  + linhas_total);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			return;
		}
	}
	
	/**
	 * Returns the linhas_programa.
	 * @return long
	 */
	public long getLinhas_programa()
	{
		return linhas_programa;
	}
	
	/**
	 * Returns the linhas_total.
	 * @return long
	 */
	public long getLinhas_total()
	{
		return linhas_total;
	}
	
	/**
	 * Sets the linhas_programa.
	 * @param linhas_programa The linhas_programa to set
	 */
	public void setLinhas_programa(long linhas_programa)
	{
		this.linhas_programa = linhas_programa;
	}
	
	/**
	 * Sets the linhas_total.
	 * @param linhas_total The linhas_total to set
	 */
	public void setLinhas_total(long linhas_total)
	{
		this.linhas_total = linhas_total;
	}
}