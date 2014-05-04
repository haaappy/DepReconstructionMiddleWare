package nju.moon.lhc.reconstruction.gui;
import com.cloudgarden.layout.AnchorLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;
import nju.moon.lhc.reconstruction.util.FileOperator;
import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;
import nju.moon.lhc.reconstruction.util.XMLWriter;

import org.jdesktop.application.SingleFrameApplication;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * 
 */
public class DepRestructionMiddleWareApplication extends SingleFrameApplication {
    private JPanel topPanel;
    private JMenuBar jMenuBar1; 
    private JLabel jLabel5;
    private JLabel jLabel4;
    private JLabel jLabelState;
    private JButton jButtonRemove;
    private JButton jButtonDeploy;
    private JButton jButtonChooseFile;
    private JLabel jLabel2;
    private JTextField jTextFieldFilePath;
    private JRadioButton jRadioButton2;
    private JTextArea jTextAreaConsole;
    private JRadioButton jRadioButton1;
    private JLabel jLabel1;
    private JTextField jTextFieldHome;
    private JButton jButtonStop;
    private JButton jButtonStart;
    private ButtonGroup buttonGroup1;
    private JButton jButtonChooseHome;
    private JComboBox jComboBox1;
    private JLabel jLabel7;
    private JLabel jLabel6;
    private JMenuItem jMenuItemDefault;
    private JMenuItem jMenuItemEdit;
    private JMenuItem jMenuItemHelp;
    private JMenuItem jMenuItemUserGuide;
    private JMenuItem jMenuItemStart;
    private JMenuItem jMenuItemStop;
    private JScrollPane jScrollPane_IL;
    private JScrollPane jScrollPane_IL1;
    private JTable jTable1;
    private JComboBox jComboBox2;
    private JLabel jLabel3;  
    private JMenu jMenu3;
    private JPanel jPanel1;
    private JMenu jMenu2;
    
    private JMenu jMenu1;

    @Override
    protected void startup() {
    	{
	    	getMainFrame().setSize(793, 620);
    	}
    	{
    		jMenuBar1 = new JMenuBar();
    		getMainFrame().setJMenuBar(jMenuBar1);
    		jMenuBar1.setName("jMenuBar1");
    		{
    			jMenuBar1.add(getJMenu1());
    			jMenuBar1.add(getJMenu2());
    			jMenuBar1.add(getJMenu3());
    			
    		}
    	}
        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setPreferredSize(new java.awt.Dimension(783, 319));
        {
        	jPanel1 = new JPanel();
        	getMainFrame().getContentPane().add(jPanel1, BorderLayout.NORTH);
        	jPanel1.setLayout(null);
        	jPanel1.setPreferredSize(new java.awt.Dimension(783, 255));
        	getButtonGroup1();
        	jPanel1.add(getJRadioButton1());
        	jPanel1.add(getJRadioButton2());
        	jPanel1.add(getJButtonDeploy());
        	jPanel1.add(getJButtonRemove());
        	jPanel1.add(getJLabel1());
        	jPanel1.add(getJLabel3());
        	jPanel1.add(getJLabel6());
        	jPanel1.add(getJTextFieldHome());
        	jPanel1.add(getJLabel2());
        	jPanel1.add(getJTextField1());
        	jPanel1.add(getJButtonChooseFile());
        	jPanel1.add(getJLabel7());
        	jPanel1.add(getJComboBox1());
        	jPanel1.add(getjButtonChooseHome());
        	jPanel1.add(getJButtonStart());
        	jPanel1.add(getJButtonStop());
        	jPanel1.add(getJLabel3x());
        	jPanel1.add(getJComboBox2());
        }
        topPanel.add(getJLabel4());
        topPanel.add(getJLabel5());
        topPanel.add(getJScrollPane_IL1());
        topPanel.add(getJScrollPane_IL());
        show(topPanel);
        
        initDefaultValue();
        MiddleWareMain.setApplication(this);   
    }
    
    private void initDefaultValue() {
		// add the default values
    	String lastHome = XMLReader.readInfoByConfigXMLFile("lasthome.xml", XMLFinalField.LAST_HOME);
    	if (lastHome == null || lastHome.length() == 0 ){
    		lastHome = MiddleWareConfig.DEFAULT_HOME;
    	}
    	jTextFieldHome.setText(lastHome);
    	jTextFieldFilePath.setText(lastHome);

    	String[] configResults = XMLReader.readAllInfoByConfigXMLFile(lastHome + "config.xml");
    	if (configResults != null && configResults.length == 8){
    		if (configResults[5].equals("Jar")){
    			jRadioButton2.setSelected(true);
    		}
    		else{
    			jRadioButton1.setSelected(true);
    		}
    		
    		jComboBox1.setSelectedItem(configResults[6]);
    		jComboBox2.setSelectedItem(configResults[7]);
    		
    	}
    	else{
    		jRadioButton1.setSelected(true);
    		jComboBox1.setSelectedItem("ReconstructionClassLoader");
    		jComboBox2.setSelectedItem("5000ms");
    	}
    	
    	jTextAreaConsole.setText("");
		
	}
    
    public void setLoadedClass(String className, Date date){
    	DefaultTableModel tm = (DefaultTableModel)jTable1.getModel();  	
    	for (int i=0; i<tm.getRowCount(); i++){
    		if (tm.getValueAt(i, 0).equals(className)){
    			tm.setValueAt(date.toLocaleString(), i, 1);
    			return;
    		}
    	}
    	tm.addRow(new Object[]{className, date.toLocaleString()});	
    }
    
    public void addTextAreaConsole(String consoleMessage){
    	String message = getJTextAreaConsole().getText();
    	getJTextAreaConsole().setText(message + consoleMessage + "\n");
    }
    	
	@Override
    public void shutdown(){
    	System.out.println("shut down!!!");
    	super.shutdown();
    }

    public static void main(String[] args) {
        launch(DepRestructionMiddleWareApplication.class, args);
    }
    
    private JTextField getJTextFieldHome() {
    	if(jTextFieldHome == null) {
    		jTextFieldHome = new JTextField();
    		jTextFieldHome.setName("jTextFieldHome");
    		jTextFieldHome.setBounds(16, 117, 606, 28);
    		jTextFieldHome.setEditable(false);
    	}
    	return jTextFieldHome;
    }
    
    private JLabel getJLabel1() {
    	if(jLabel1 == null) {
    		jLabel1 = new JLabel();
    		jLabel1.setLayout(null);
    		jLabel1.setName("jLabel1");
    		jLabel1.setBounds(16, 86, 158, 25);
    	}
    	return jLabel1;
    }
    
    private JRadioButton getJRadioButton1() {
    	if(jRadioButton1 == null) {
    		jRadioButton1 = new JRadioButton("Noraml", true);
    		jRadioButton1.setName("jRadioButton1");
    		jRadioButton1.setBounds(479, 12, 85, 24);
    	}
    	return jRadioButton1;
    }
    
    private JTextArea getJTextAreaConsole() {
    	if(jTextAreaConsole == null) {
    		jTextAreaConsole = new JTextArea();
    		jTextAreaConsole.setName("jTextAreaConsole");
    		jTextAreaConsole.setBounds(10, 34, 440, 262);
    	}
    	return jTextAreaConsole;
    }

    private JRadioButton getJRadioButton2() {
    	if(jRadioButton2 == null) {
    		jRadioButton2 = new JRadioButton("Jar", false);
    		jRadioButton2.setName("jRadioButton2");
    		jRadioButton2.setBounds(627, 12, 85, 24);
    	}
    	return jRadioButton2;
    }

    private JTextField getJTextField1() {
    	if(jTextFieldFilePath == null) {
    		jTextFieldFilePath = new JTextField();
    		jTextFieldFilePath.setName("jTextFieldFilePath");
    		jTextFieldFilePath.setBounds(16, 182, 606, 28);
    	}
    	return jTextFieldFilePath;
    }
    
    private JLabel getJLabel2() {
    	if(jLabel2 == null) {
    		jLabel2 = new JLabel();
    		jLabel2.setName("jLabel2");
    		jLabel2.setBounds(16, 152, 83, 25);
    	}
    	return jLabel2;
    }
    
    private JButton getJButtonChooseFile() {
    	if(jButtonChooseFile == null) {
    		jButtonChooseFile = new JButton();
    		jButtonChooseFile.setName("jButtonChooseFile");
    		jButtonChooseFile.setBounds(639, 181, 126, 30);
    		jButtonChooseFile.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				System.out.println("jButtonChooseFile.actionPerformed, event="+evt);
    				JFileChooser chooser = new JFileChooser();
    				if (jTextFieldFilePath.getText().length() != 0){
    					chooser.setCurrentDirectory(new File(jTextFieldFilePath.getText()));
    				}
    				
    				FileNameExtensionFilter filter;
    				if (jLabelState.getText().lastIndexOf("running") < 0){
    					filter = new FileNameExtensionFilter("class文件、Jar文件、xml文件", "class", "jar", "xml");				
    				}
    				else{
    					if (MiddleWareConfig.getInstance().getCurDeploymentWay() == MiddleWareConfig.DEPLOY_WAY_NORMAL){
    						filter = new FileNameExtensionFilter("class文件, xml文件", "class", "xml");		
    					}
    					else{
    						filter = new FileNameExtensionFilter("Jar文件", "jar");
    					}
    								
    				}
    				chooser.setFileFilter(filter);
    				int returnVal = chooser.showOpenDialog(new JPanel());
    				if (returnVal == JFileChooser.APPROVE_OPTION){
    					jTextFieldFilePath.setText(chooser.getSelectedFile().getPath());
    				}
    			}
    		});
    	}
    	return jButtonChooseFile;
    }
    
    private JButton getJButtonDeploy() {
    	if(jButtonDeploy == null) {
    		jButtonDeploy = new JButton();
    		jButtonDeploy.setName("jButtonDeploy");
    		jButtonDeploy.setBounds(16, 217, 85, 30);
    		jButtonDeploy.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				// TODO
    				String filePath = jTextFieldFilePath.getText();
    				String destPath = jTextFieldHome.getText();
    				FileOperator.copyFile(new File(filePath), new File(destPath), new File(filePath).getName());
    				
    			}
    		});
    	}
    	return jButtonDeploy;
    }
    
    private JButton getJButtonRemove() {
    	if(jButtonRemove == null) {
    		jButtonRemove = new JButton();
    		jButtonRemove.setName("jButtonRemove");
    		jButtonRemove.setBounds(120, 217, 85, 30);
    		jButtonRemove.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				// TODO
    				String filePath = jTextFieldFilePath.getText();
    				boolean flag = FileOperator.removeFile(filePath);
    				if (flag){
    					jTextFieldFilePath.setText(jTextFieldHome.getText());
    				}
    			}
    		});
    	}
    	return jButtonRemove;
    }
    
    private JLabel getJLabel3() {
    	if(jLabelState == null) {
    		jLabelState = new JLabel();
    		jLabelState.setName("jLabelState");
    		jLabelState.setBounds(16, 21, 181, 25);
    	}
    	return jLabelState;
    }
    
    private JLabel getJLabel4() {
    	if(jLabel4 == null) {
    		jLabel4 = new JLabel();
    		jLabel4.setName("jLabel4");
    		jLabel4.setPreferredSize(new java.awt.Dimension(445, 18));
    		jLabel4.setBounds(10, 10, 445, 18);
    	}
    	return jLabel4;
    }
    
    private JLabel getJLabel5() {
    	if(jLabel5 == null) {
    		jLabel5 = new JLabel();
    		jLabel5.setName("jLabel5");
    		jLabel5.setBounds(465, 10, 107, 18);
    	}
    	return jLabel5;
    }

    
    private JMenu getJMenu1(){
    	if(jMenu1 == null) {
    		jMenu1 = new JMenu();
    		jMenu1.setName("jMenu1");
    		jMenu1.add(getJMenuItem1_1());
    		jMenu1.add(getJMenuItem1_2());
    	}
    	return jMenu1;
    }
    
    
    private JMenu getJMenu2(){
    	if(jMenu2 == null) {
    		jMenu2 = new JMenu();
    		jMenu2.setName("jMenu2");
    		jMenu2.add(getJMenuItem2_1());
    		jMenu2.add(getJMenuItem2_2());
    	}
    	return jMenu2;
    }
    
    private JMenu getJMenu3() {
    	if(jMenu3 == null) {
    		jMenu3 = new JMenu();
    		jMenu3.setName("jMenu3");
    		jMenu3.add(getJMenuItem3_1());
    		jMenu3.add(getJMenuItem3_2());
    	}
    	return jMenu3;
    }
    
    private JMenuItem getJMenuItem1_1() {
    	if(jMenuItemDefault == null) {
    		jMenuItemDefault = new JMenuItem();
    		jMenuItemDefault.setName("jMenuItemDefault");
    		jMenuItemDefault.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				// TODO jButtonStart.doClick();
    				initDefaultValue();				
    			}
    		});
    	}
    	return jMenuItemDefault;
    }
    
    private JMenuItem getJMenuItem1_2() {
    	if(jMenuItemEdit == null) {
    		jMenuItemEdit = new JMenuItem();
    		jMenuItemEdit.setName("jMenuItemEdit");
    		jMenuItemEdit.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				// TODO jButtonStart.doClick();
    				String defaultHomeValue = jTextFieldHome.getText();
    				String defaultWayValue = jRadioButton1.isSelected() ? "Normal" : "Jar";
    				String defaultClassLoaderValue = jComboBox1.getSelectedItem().toString();
    				String defaultPollingTimeValue = jComboBox2.getSelectedItem().toString();
    				XMLWriter.xmlUpdateDefaultValue(defaultHomeValue + "config.xml", defaultHomeValue, defaultWayValue, defaultClassLoaderValue, defaultPollingTimeValue);
    			}
    		});
    	}
    	return jMenuItemEdit;
    }
    
    private JMenuItem getJMenuItem2_1() {
    	if(jMenuItemUserGuide == null) {
    		jMenuItemUserGuide = new JMenuItem();
    		jMenuItemUserGuide.setName("jMenuItemUserGuide");
    		jMenuItemUserGuide.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				JOptionPane.showMessageDialog(null, "User Guide:\nThis is a system for restruct dependency in hot deployment.");
    			}
    		});
    	}
    	return jMenuItemUserGuide;
    }
    
    private JMenuItem getJMenuItem2_2() {
    	if(jMenuItemHelp == null) {
    		jMenuItemHelp = new JMenuItem();
    		jMenuItemHelp.setName("jMenuItemHelp");
    		jMenuItemHelp.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				JOptionPane.showMessageDialog(null, "Help:\nYou can edit configuration and run the system.\nThen you can deploy and remove files when system is running.");
    			}
    		});
    	}
    	return jMenuItemHelp;
    }
    
    
    private JMenuItem getJMenuItem3_1() {
    	if(jMenuItemStart == null) {
    		jMenuItemStart = new JMenuItem();
    		jMenuItemStart.setName("jMenuItemStart");
    		jMenuItemStart.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				jButtonStart.doClick();
    			}
    		});
    	}
    	return jMenuItemStart;
    }
    
    private JMenuItem getJMenuItem3_2() {
    	if(jMenuItemStop == null) {
    		jMenuItemStop = new JMenuItem();
    		jMenuItemStop.setName("jMenuItemStop");
    		jMenuItemStop.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				jButtonStop.doClick();
    			}
    		});
    	}
    	return jMenuItemStop;
    }

    private JLabel getJLabel6() {
    	if(jLabel6 == null) {
    		jLabel6 = new JLabel();
    		jLabel6.setName("jLabel6");
    		jLabel6.setBounds(354, 50, 89, 18);
    	}
    	return jLabel6;
    }
    
    private JLabel getJLabel7() {
    	if(jLabel7 == null) {
    		jLabel7 = new JLabel();
    		jLabel7.setBounds(362, 16, 90, 18);
    		jLabel7.setName("jLabel7");
    	}
    	return jLabel7;
    }
    
    private JComboBox getJComboBox1() {
    	if(jComboBox1 == null) {
    		ComboBoxModel jComboBox1Model = 
    				new DefaultComboBoxModel(
    						new String[] { "ReconstructionClassLoader", "ExtReconstructionClassLoader", "CircleExtReconstructionClassLoader", "AdaptDepClassLoader"});
    		jComboBox1 = new JComboBox();
    		jComboBox1.setModel(jComboBox1Model);
    		jComboBox1.setBounds(479, 41, 292, 28);
    	}
    	return jComboBox1;
    }
    
    private JButton getjButtonChooseHome() {
    	if(jButtonChooseHome == null) {
    		jButtonChooseHome = new JButton();
    		jButtonChooseHome.setBounds(639, 114, 123, 30);
    		jButtonChooseHome.setName("jButtonChooseHome");
    		jButtonChooseHome.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				System.out.println("jButtonChooseHome.actionPerformed, event="+evt);
    				JFileChooser chooser = new JFileChooser();
    				if (jTextFieldHome.getText().length() != 0){
    					chooser.setCurrentDirectory(new File(jTextFieldHome.getText()));
    				}
    				
    				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    				chooser.setAcceptAllFileFilterUsed(false);
    				int returnVal = chooser.showOpenDialog(new JPanel());
    				if (returnVal == JFileChooser.APPROVE_OPTION){
    					jTextFieldHome.setText(chooser.getSelectedFile().getPath());
    				}
    			}
    		});
    	}
    	return jButtonChooseHome;
    }
    
    private ButtonGroup getButtonGroup1() {
    	if(buttonGroup1 == null) {
    		buttonGroup1 = new ButtonGroup();
    		buttonGroup1.add(getJRadioButton1());
    		buttonGroup1.add(getJRadioButton2());
    	}
    	return buttonGroup1;
    }
    
    private JButton getJButtonStart() {
    	if(jButtonStart == null) {
    		jButtonStart = new JButton(new ImageIcon("start.png"));
    		jButtonStart.setBounds(232, 13, 46, 30);
    		jButtonStart.setName("jButtonStart");
    		jButtonStart.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    		jButtonStart.setSize(40, 40);
    		jButtonStart.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				jButtonStart.setEnabled(false);
    				jButtonStop.setEnabled(true);
    				jMenuItemStart.setEnabled(false);
    				jMenuItemStop.setEnabled(true);
    				
    				jLabelState.setText("State: Running...");
    				
    				((DefaultTableModel)getJTable1().getModel()).getDataVector().removeAllElements(); 				
    				
    				String curHomeValue = jTextFieldHome.getText();
    				String curWayValue = jRadioButton1.isSelected() ? "Normal" : "Jar";
    				String curClassLoaderValue = jComboBox1.getSelectedItem().toString();
    				String curPollingTimeValue = jComboBox2.getSelectedItem().toString();
    				
    				XMLWriter.xmlUpdateCurValue(curHomeValue + "config.xml", curHomeValue, curWayValue, curClassLoaderValue, curPollingTimeValue);
    				XMLWriter.xmlUpdateLastHomeValue("lasthome.xml", curHomeValue);
    				
					try {
						MiddleWareMain.main(new String[]{curHomeValue});
						jTextAreaConsole.setText("The Middle Server is Running...\n");
  
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
									
    			}
    		});
    	}
    	return jButtonStart;
    }
    
    private JButton getJButtonStop() {
    	if(jButtonStop == null) {
    		jButtonStop = new JButton(new ImageIcon("stop.png"));
    		jButtonStop.setBounds(288, 13, 44, 30);
    		jButtonStop.setName("jButtonStop");
    		jButtonStop.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    		jButtonStop.setSize(40, 40);
    		jButtonStop.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				jButtonStart.setEnabled(true);
    				jButtonStop.setEnabled(false);
    				jMenuItemStart.setEnabled(true);
    				jMenuItemStop.setEnabled(false);
    				jLabelState.setText("State: Stop!");
    				
    				MiddleWareMain.stop();
    				addTextAreaConsole("The Middle Server is stopped!");
    			}
    		});
    	}
    	return jButtonStop;
    }
    
    private JLabel getJLabel3x() {
    	if(jLabel3 == null) {
    		jLabel3 = new JLabel();
    		jLabel3.setBounds(359, 83, 98, 18);
    		jLabel3.setName("jLabel3");
    	}
    	return jLabel3;
    }
    
    private JComboBox getJComboBox2() {
    	if(jComboBox2 == null) {
    		ComboBoxModel jComboBox2Model = 
    				new DefaultComboBoxModel(
    						new String[] { "2000ms", "5000ms", "8000ms" });
    		jComboBox2 = new JComboBox();
    		jComboBox2.setModel(jComboBox2Model);
    		jComboBox2.setBounds(479, 78, 292, 28);
    	}
    	return jComboBox2;
    }
    
    private JTable getJTable1() {
    	if(jTable1 == null) {
    		TableModel jTable1Model = 
    				new DefaultTableModel(
    						new String[][] {},
    						new String[] { "ClassName", "DateTime" });
    		jTable1 = new JTable();
    		jTable1.setModel(jTable1Model);
    		jTable1.setBounds(465, 36, 306, 260);
    	}
    	return jTable1;
    }
    
    private JScrollPane getJScrollPane_IL() {
    	if(jScrollPane_IL == null) {
    		jScrollPane_IL = new JScrollPane(getJTable1());
    		jScrollPane_IL.setBounds(462, 34, 309, 262);
    	}
    	return jScrollPane_IL;
    }
    
    private JScrollPane getJScrollPane_IL1() {
    	if(jScrollPane_IL1 == null) {
    		jScrollPane_IL1 = new JScrollPane(getJTextAreaConsole());
    		jScrollPane_IL1.setBounds(12, 34, 438, 262);
    	}
    	return jScrollPane_IL1;
    }

}
