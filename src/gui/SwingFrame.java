package gui;


import file.FileManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.Port;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoundedRangeModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.SwingConstants;

import card.Card;

import javax.swing.JFormattedTextField;

import org.w3c.dom.Text;

import javax.swing.JPopupMenu;


public class SwingFrame extends JFrame {
	private JTextField questionField;
	private JTextField answerField;
	private JTextField txtPleaseImport;
	private JButton btnNextWord;
	private FileManager f;	
	private String fileName;
	private JTextField textField_2;
	private JTextField textField_3;
	private JOptionPane jop1;
	private JTextField countField;
	private String path;

	private int countTot = 0, countSuc = 0, countDone = 0;


	public SwingFrame() {


		LeftPanel leftPanel = new LeftPanel();
		MenuTest menuTest = new MenuTest(leftPanel);
		Container c = this.getContentPane();
		this.setJMenuBar(menuTest);
		getContentPane().setLayout(null);


		leftPanel.setBounds(0, 10, 400, 70);
		getContentPane().add(leftPanel);

		btnNextWord = new JButton("Next Word");
		btnNextWord.setEnabled(false);
		btnNextWord.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				//ICI call pickCard
				String s1 = null,s2 = null;
				f.pickCardV2();
				answerField.setEnabled(true);
				questionField.setText(f.getS1());
				answerField.setText(null);
				textField_2.setText(null);
				textField_3.setText(null);

				if(countDone == countTot){
					double c = new Double(countTot);
					double result = countSuc/c;
					double resultFinal = result*100;
					DecimalFormat df = new DecimalFormat("###");
					String finalS = df.format(resultFinal) + " %";

					jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Your score is "+finalS,
							"End of game",
							JOptionPane.INFORMATION_MESSAGE);

					try {
						f.writeFile(path);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//Boîte du message d'information
					jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "Data saved", "Closure",
							JOptionPane.INFORMATION_MESSAGE);

					dispose();
					System.exit(0);


				}

			}
		});


		btnNextWord.setBounds(637, 393, 117, 25);
		getContentPane().add(btnNextWord);

		questionField = new JTextField();
		questionField.setEditable(false);
		questionField.setBounds(34, 213, 193, 36);
		getContentPane().add(questionField);
		questionField.setColumns(10);


		answerField = new JTextField();
		answerField.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent arg0) {
				String enteredString = answerField.getText();
				
				String basicString = questionField.getText();
				
				if(basicString.isEmpty()) 	{//no txt file loaded
					jop1 = new JOptionPane();
					JOptionPane.showMessageDialog(null, "No txt file loaded", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				//textField_2.setText("moimoi\n");
				
				if(f.getCard(basicString).getDone() == false){//if the card has not been played yet
					countDone++;
				}

				if(enteredString.equals(f.getS2())){//if answer is true

					//increment tries & success

					f.setCard(basicString, 1);//case of success
					String result = f.getCard(basicString).getRate();
					textField_2.setText("True");
					textField_3.setText(result);
					answerField.setEnabled(false);
					if(f.getCard(basicString).getDone() == false){//if the card has not been played yet
						countSuc++;
					}

				}
				else{
					f.setCard(basicString, 0);//case of miss
					String result = f.getCard(basicString).getRate();
					answerField.setText(f.getS2());
					textField_2.setText("False");
					textField_3.setText(result);
					answerField.setEnabled(false);
				}
				
					
				f.setCard(basicString, true);//the card has been played
				

				countField.setText("Sucess: "+countSuc+"\n"
						+ "Cards: "+countDone+"/"+countTot);


			}
		});
		answerField.setColumns(10);
		answerField.setBounds(330, 213, 157, 36);
		getContentPane().add(answerField);

		txtPleaseImport = new JTextField();
		txtPleaseImport.setEditable(false);
		txtPleaseImport.setText("Please import a \".txt\" file");
		txtPleaseImport.setBounds(64, 77, 182, 35);
		txtPleaseImport.setBorder(null);
		getContentPane().add(txtPleaseImport);
		txtPleaseImport.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(508, 213, 258, 36);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		textField_2.setBorder(null);
		Font font = new Font("Serif", Font.PLAIN, 36); 
		textField_2.setFont(font);


		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(187, 388, 141, 36);
		textField_3.setBorder(null);

		getContentPane().add(textField_3);

		countField = new JTextField();
		countField.setEditable(false);
		countField.setBounds(519, 31, 235, 25);
		getContentPane().add(countField);
		countField.setColumns(10);
		countField.setBorder(null);

		this.addWindowListener(new WindowAdapter() {
			public void WindowClosing(WindowEvent e) throws IOException {

				//				f.writeFile(fileName);
				//				System.out.println("MOIII");
				//				//Boîte du message d'information
				//				jop1 = new JOptionPane();
				//				JOptionPane.showMessageDialog(null, "Data saved", "Information",
				//				JOptionPane.INFORMATION_MESSAGE);

				dispose();
				System.exit(0);
			}
		});
		setSize(800, 600);
		setTitle("Language Learner");
		setUndecorated(false);
		setLocation(400, 300);
		this.setVisible(true);
	}


	class MenuTest extends JMenuBar {

		public MenuTest(final LeftPanel leftPanel) {
			JMenu fileMenu = new JMenu("file");
			JMenuItem exitMenuItem = new JMenuItem("exit", KeyEvent.VK_E);
			final JMenuItem recoverMenuItem =  new JMenuItem("restore txt file",
					KeyEvent.VK_A);
			final JMenuItem importMenuItem = new JMenuItem("import a txt file",
					KeyEvent.VK_A);
			final JMenuItem saveMenuItem =  new JMenuItem("save",
					KeyEvent.VK_A);
				
			fileMenu.add(importMenuItem);
			fileMenu.add(saveMenuItem);
			fileMenu.add(recoverMenuItem);
			fileMenu.add(exitMenuItem);	
			
			saveMenuItem.setEnabled(false);
			recoverMenuItem.setEnabled(false);
			this.add(fileMenu);

			// exit
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SwingWorker worker = new SwingWorker() {
						protected Object doInBackground() throws Exception {
							return "ha";
						}

					};

					try {
						if(fileName != null){
							f.writeFile(fileName);

							//Boîte du message d'information
							jop1 = new JOptionPane();
							JOptionPane.showMessageDialog(null, "Data saved", "Information",
									JOptionPane.INFORMATION_MESSAGE);
						}

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					dispose();
					System.exit(0);
					worker.execute();
				}

			});


			// save
			saveMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						if(fileName != null){
							f.writeFile(fileName);

							//Boîte du message d'information
							jop1 = new JOptionPane();
							JOptionPane.showMessageDialog(null, "Data saved", "Information",
									JOptionPane.INFORMATION_MESSAGE);
						}

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			});

			// recover
						recoverMenuItem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								try {
									f.recoverFile(fileName);
									jop1 = new JOptionPane();
									JOptionPane.showMessageDialog(null, "file restored", "Information",
											JOptionPane.INFORMATION_MESSAGE);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
							}

						});
			
			// import
			importMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					JFileChooser file = new JFileChooser();
					 	//file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
			         	//file.setDialogType(JFileChooser.SAVE_DIALOG);
					file.setCurrentDirectory(new File("."));
					file.setFileFilter(new FileFilter() {
						public boolean accept(File f) {

							return f.getName().toLowerCase().endsWith(".txt")
									|| f.isDirectory();
						}

						public String getDescription() {
							return "txt Files";
						}
					});
					int r = file.showOpenDialog(new JPanel());
					if (r == JFileChooser.APPROVE_OPTION) {
						fileName = file.getSelectedFile().getName();
									//txtPleaseImport.setText(file.getSelectedFile().getAbsolutePath());
						try {
							path = file.getSelectedFile().getPath();
							f = new FileManager(path);
															
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							
							jop1 = new JOptionPane();
							JOptionPane.showMessageDialog(null, "file loaded", "Information",
									JOptionPane.INFORMATION_MESSAGE);

							importMenuItem.setEnabled(false);

												txtPleaseImport.setText(fileName);
							f.BuildFile();

												txtPleaseImport.setText("yoyo");
							countTot = f.cardList.size();
							f.pickCardV2();
							questionField.setText(f.getS1());
							
							textField_2.setText(null);
						
						} catch (IOException e) {
							
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						txtPleaseImport.setText(null);
						btnNextWord.setEnabled(true);
						recoverMenuItem.setEnabled(true);
						saveMenuItem.setEnabled(true);
					}
				}
			});
		}
	}

	class LeftPanel extends JPanel {
		public int i = 0;

		public LeftPanel() {
		}
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}