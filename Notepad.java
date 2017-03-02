import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Notepad extends JFrame implements ActionListener{
	int n=-1;
	String filepath[]=new String[25];
	//boolean b1=false;
	JTextArea jta[]=new JTextArea[25];
	JTextArea tempjta;
	JScrollPane jsp;
	JMenuBar jmb;
	JMenu file;
	JMenuItem nw,opn,sv,svas,exit;
	JTabbedPane jtp;
	JPanel jp;
	
	Notepad(String s1){
		super(s1);
		jmb=new JMenuBar();
		file=new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		jmb.add(file);
		
		nw=new JMenuItem("new",KeyEvent.VK_N);
		nw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.ALT_MASK));
		nw.addActionListener(this);
		file.add(nw);
		
		opn=new JMenuItem("open",KeyEvent.VK_O);
		opn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.ALT_MASK));
		opn.addActionListener(this);
		file.add(opn);
		
		sv=new JMenuItem("save",KeyEvent.VK_S);
		sv.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.ALT_MASK));
		sv.addActionListener(this);
		file.add(sv);
		
		svas=new JMenuItem("save as",KeyEvent.VK_A);
		svas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.ALT_MASK));
		svas.addActionListener(this);
		file.add(svas);
		
		exit=new JMenuItem("exit",KeyEvent.VK_E);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.ALT_MASK));
		exit.addActionListener(this);
		file.add(exit);
		file.addSeparator();
		setJMenuBar(jmb);
		
		jtp=new JTabbedPane(JTabbedPane.TOP);
		newJTabbedPane();
		add(jtp);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(1200,655);
		}
		
		
	public static void main(String... s){
		new Notepad("subhas");
		}
	
	public void actionPerformed(ActionEvent e){
		if(e.getActionCommand().equals("exit"))
			System.exit(0);
		
		if(e.getActionCommand().equals("new"))
			newJTabbedPane();
		
		if(e.getActionCommand().equals("open"))
			myFileChooser();
		
		if(e.getActionCommand().equals("save as"))
			saveasFile();
		if(e.getActionCommand().equals("save")){
			if(filepath[jtp.getSelectedIndex()]==null)
				saveasFile();
			else
				saveFile();
			}
		}
	
	
	public void newJTabbedPane(){
		n+=1;
		jp=new JPanel();
		jta[n]=new JTextArea(35,107);
		jta[n].addFocusListener(new MyFocusListener(this));
		jsp=new JScrollPane(jta[n]);
		jp.add(jsp);
		jtp.addTab(("new"+(n+1)),jp);
		}
		
	public void myFileChooser(){
		try{
		JFileChooser jfc=new JFileChooser();
		int x=jfc.showOpenDialog(null);
		if(x==JFileChooser.APPROVE_OPTION){
			File f=jfc.getSelectedFile();
			String nam=jfc.getName(f);
			int index=jtp.getSelectedIndex();
			jtp.setTitleAt(index,nam);
			filepath[index]=f.getPath();
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String s;
			tempjta.setText("");
			while((s=br.readLine())!=null)
			{
				tempjta.append(s + "\n");
				}
			fr.close();
			}
		}
		catch(Exception e){}
		}
			
			
	public void saveasFile(){
		JFileChooser jfc=new JFileChooser();
		int x=jfc.showSaveDialog(null);
		if(x==JFileChooser.APPROVE_OPTION){
			try{
			File f=jfc.getSelectedFile();
			String nam=f.getName();
			jtp.setTitleAt(jtp.getSelectedIndex(),nam);
			filepath[jtp.getSelectedIndex()]=f.getPath();
			System.out.println(nam/*f.getPath()*/);
			FileWriter fw=new FileWriter(f);
			String str=tempjta.getText();
					PrintWriter pw=new PrintWriter(fw);
					pw.println(str);
					pw.flush();
					
			}
			catch(Exception e){}
			}
		}
		
	public void saveFile(){
		try{
		FileWriter fw=new FileWriter(filepath[jtp.getSelectedIndex()]);
		String str=tempjta.getText();
		PrintWriter pw=new PrintWriter(fw);
		pw.println(str);
		pw.flush();
		}
		catch(Exception e){
			System.out.println(e);
		}
		}
		
	
	
	}

class MyFocusListener extends FocusAdapter{
	Notepad np;
	MyFocusListener(Notepad np){
		this.np=np;
		}
	public void focusGained(FocusEvent fe){
		for(int i=0;i<np.jta.length;i++){
			if(fe.getSource()==np.jta[i]){
				np.tempjta=np.jta[i];
				break;
				}
			}
		}
	}
	
	
	
