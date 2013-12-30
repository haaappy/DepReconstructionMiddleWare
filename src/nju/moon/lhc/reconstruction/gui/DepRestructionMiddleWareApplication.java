package nju.moon.lhc.reconstruction.gui;
import com.cloudgarden.layout.AnchorLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
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

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;
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
    private JMenuItem jMenuItemEdit;
    private JLabel jLabel5;
    private JLabel jLabel4;
    private JLabel jLabelState;
    private JButton jButtonRemove;
    private JButton jButtonAdd;
    private JButton jButtonChooseFile;
    private JLabel jLabel2;
    private JTextField jTextFieldFilePath;
    private JRadioButton jRadioButton2;
    private JTextArea jTextArea1;
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
    private JMenuItem jMenuItem4;
    private JMenuItem jMenuItem3;
    private JMenuItem jMenuItemStop;
    private JMenuItem jMenuItemStart;
    private JMenu jMenu3;
    private JList jList1;
    private JSplitPane jSplitPane1;
    private JPanel jPanel1;
    private JMenu jMenu2;
    private JMenuItem jMenuItemDefault;
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
    			jMenu1 = new JMenu();
    			jMenuBar1.add(jMenu1);
    			jMenu1.setName("jMenu1");
    			jMenu1.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
    			{
    				jMenuItemDefault = new JMenuItem();
    				jMenu1.add(jMenuItemDefault);
    				jMenuItemDefault.setName("jMenuItemDefault");
    				jMenuItemDefault.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
    			}
    			{
    				jMenuItemEdit = new JMenuItem();
    				jMenu1.add(jMenuItemEdit);
    				jMenuItemEdit.setName("jMenuItemEdit");
    			}
    		}
    		{
    			jMenu2 = new JMenu();
    			jMenuBar1.add(jMenu2);
    			jMenuBar1.add(getJMenu3());
    			jMenu2.setName("jMenu2");
    			jMenu2.add(getJMenuItem3());
    			jMenu2.add(getJMenuItem4());
    		}
    	}
        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setPreferredSize(new java.awt.Dimension(708, 284));
        {
        	jPanel1 = new JPanel();
        	getMainFrame().getContentPane().add(jPanel1, BorderLayout.NORTH);
        	jPanel1.setLayout(null);
        	jPanel1.setPreferredSize(new java.awt.Dimension(708, 221));
        	getButtonGroup1();
        	jPanel1.add(getJRadioButton1());
        	jPanel1.add(getJRadioButton2());
        	jPanel1.add(getJButtonAdd());
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
        }
        topPanel.add(getJLabel4());
        topPanel.add(getJLabel5());
        topPanel.add(getJTextArea1());
        topPanel.add(getJList1());
        show(topPanel);
        
        initDefaultValue();
    }
    
    private void initDefaultValue() {
		// add the default values
    	String lastHome = XMLReader.readInfoByConfigXMLFile("lasthome.xml", "LastHome");
    	if (lastHome == null || lastHome.length() == 0 ){
    		lastHome = MiddleWareConfig.DEFAULT_HOME;
    	}
    	jTextFieldHome.setText(lastHome);
    	jTextFieldFilePath.setText(lastHome);

    	String[] configResults = XMLReader.readAllInfoByConfigXMLFile(lastHome + "config.xml");
    	if (configResults != null && configResults.length == 6){
    		if (configResults[4].equals("Jar")){
    			jRadioButton2.setSelected(true);
    		}
    		else{
    			jRadioButton1.setSelected(true);
    		}
    		
    		if (configResults[5].equals("ExtReconstructionClassLoader")){
    			jComboBox1.setSelectedItem("ExtReconstructionClassLoader");
    		}
    		else{
    			jComboBox1.setSelectedItem("ReconstructionClassLoader");
    		}
    	}
    	else{
    		jRadioButton1.setSelected(true);
    		jComboBox1.setSelectedItem("ReconstructionClassLoader");	
    	}
    	
    	
		
	}



	@Override
    public void shutdown(){
    	System.out.println("shut down!!!");
    	super.shutdown();
    }

    public static void main(String[] args) {
        launch(DepRestructionMiddleWareApplication.class, args);
    }
    
    private JSplitPane getJSplitPane1() {
    	if(jSplitPane1 == null) {
    		jSplitPane1 = new JSplitPane();
    	}
    	return jSplitPane1;
    }
    
    private JTextField getJTextFieldHome() {
    	if(jTextFieldHome == null) {
    		jTextFieldHome = new JTextField();
    		jTextFieldHome.setName("jTextFieldHome");
    		jTextFieldHome.setBounds(16, 82, 622, 28);
    		jTextFieldHome.setEditable(false);
    	}
    	return jTextFieldHome;
    }
    
    private JLabel getJLabel1() {
    	if(jLabel1 == null) {
    		jLabel1 = new JLabel();
    		jLabel1.setLayout(null);
    		jLabel1.setName("jLabel1");
    		jLabel1.setBounds(16, 51, 158, 25);
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
    
    private JTextArea getJTextArea1() {
    	if(jTextArea1 == null) {
    		jTextArea1 = new JTextArea();
    		jTextArea1.setName("jTextArea1");
    		jTextArea1.setBounds(10, 34, 440, 296);
    	}
    	return jTextArea1;
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
    		jTextFieldFilePath.setBounds(16, 147, 621, 28);
    	}
    	return jTextFieldFilePath;
    }
    
    private JLabel getJLabel2() {
    	if(jLabel2 == null) {
    		jLabel2 = new JLabel();
    		jLabel2.setName("jLabel2");
    		jLabel2.setBounds(16, 117, 83, 25);
    	}
    	return jLabel2;
    }
    
    private JButton getJButtonChooseFile() {
    	if(jButtonChooseFile == null) {
    		jButtonChooseFile = new JButton();
    		jButtonChooseFile.setName("jButtonChooseFile");
    		jButtonChooseFile.setBounds(655, 146, 110, 30);
    		jButtonChooseFile.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				System.out.println("jButtonChooseFile.actionPerformed, event="+evt);
    				//TODO add your code for jButtonChooseFile.actionPerformed
    				JFileChooser chooser = new JFileChooser();
    				if (jTextFieldFilePath.getText().length() != 0){
    					chooser.setCurrentDirectory(new File(jTextFieldFilePath.getText()));
    				}
    				
    				FileNameExtensionFilter filter;
    				if (jLabelState.getText().lastIndexOf("running") < 0){
    					filter = new FileNameExtensionFilter("class文件、Jar文件", "class", "jar");				
    				}
    				else{
    					if (MiddleWareConfig.getInstance().getCurDeploymentWay() == MiddleWareConfig.DEPLOY_WAY_NORMAL){
    						filter = new FileNameExtensionFilter("class文件", "class");		
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
    
    private JButton getJButtonAdd() {
    	if(jButtonAdd == null) {
    		jButtonAdd = new JButton();
    		jButtonAdd.setName("jButtonAdd");
    		jButtonAdd.setBounds(16, 182, 85, 30);
    	}
    	return jButtonAdd;
    }
    
    private JButton getJButtonRemove() {
    	if(jButtonRemove == null) {
    		jButtonRemove = new JButton();
    		jButtonRemove.setName("jButtonRemove");
    		jButtonRemove.setBounds(120, 182, 85, 30);
    	}
    	return jButtonRemove;
    }
    
    private JLabel getJLabel3() {
    	if(jLabelState == null) {
    		jLabelState = new JLabel();
    		jLabelState.setName("jLabelState");
    		jLabelState.setBounds(18, 16, 181, 25);
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
    
    private JList getJList1() {
    	if(jList1 == null) {
    		ListModel jList1Model = 
    				new DefaultComboBoxModel(
    						new String[] { "ReconstructionClassLoader", "ExtReconstructionClassLoader" });
    		jList1 = new JList();
    		jList1.setModel(jList1Model);
    		jList1.setBounds(460, 38, 311, 290);
    	}
    	return jList1;
    }
    
    private JMenu getJMenu3() {
    	if(jMenu3 == null) {
    		jMenu3 = new JMenu();
    		jMenu3.setName("jMenu3");
    		jMenu3.add(getJMenuItem1());
    		jMenu3.add(getJMenuItem2());
    	}
    	return jMenu3;
    }
    
    private JMenuItem getJMenuItem1() {
    	if(jMenuItemStart == null) {
    		jMenuItemStart = new JMenuItem();
    		jMenuItemStart.setName("jMenuItemStart");
    	}
    	return jMenuItemStart;
    }
    
    private JMenuItem getJMenuItem2() {
    	if(jMenuItemStop == null) {
    		jMenuItemStop = new JMenuItem();
    		jMenuItemStop.setName("jMenuItemStop");
    	}
    	return jMenuItemStop;
    }
    
    private JMenuItem getJMenuItem3() {
    	if(jMenuItem3 == null) {
    		jMenuItem3 = new JMenuItem();
    		jMenuItem3.setName("jMenuItem3");
    	}
    	return jMenuItem3;
    }
    
    private JMenuItem getJMenuItem4() {
    	if(jMenuItem4 == null) {
    		jMenuItem4 = new JMenuItem();
    		jMenuItem4.setName("jMenuItem4");
    	}
    	return jMenuItem4;
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
    						new String[] { "ReconstructionClassLoader", "ExtReconstructionClassLoader" });
    		jComboBox1 = new JComboBox();
    		jComboBox1.setModel(jComboBox1Model);
    		jComboBox1.setBounds(479, 41, 292, 28);
    	}
    	return jComboBox1;
    }
    
    private JButton getjButtonChooseHome() {
    	if(jButtonChooseHome == null) {
    		jButtonChooseHome = new JButton();
    		jButtonChooseHome.setBounds(655, 79, 107, 30);
    		jButtonChooseHome.setName("jButtonChooseHome");
    		jButtonChooseHome.setSize(110, 30);
    		jButtonChooseHome.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent evt) {
    				System.out.println("jButtonChooseHome.actionPerformed, event="+evt);
    				//TODO add your code for jButtonChooseHome.actionPerformed
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
    				System.out.println("jButtonStart.actionPerformed, event="+evt);
    				//TODO add your code for jButtonStart.actionPerformed
    				jButtonStart.setEnabled(false);
    				jButtonStop.setEnabled(true);
    				jMenuItemStart.setEnabled(false);
    				jMenuItemStop.setEnabled(true);
    				
    				jLabelState.setText("State: Running...");
    				
    				String curHomeValue = jTextFieldHome.getText();
    				String curWayValue = jRadioButton1.isSelected() ? "Normal" : "Jar";
    				String curClassLoaderValue = jComboBox1.getSelectedItem().toString();
    				
    				XMLWriter.xmlUpdateCurValue(curHomeValue + "config.xml", curHomeValue, curWayValue, curClassLoaderValue);
    				XMLWriter.xmlUpdateLastHomeValue("lasthome.xml", curHomeValue);
    				
					try {
						MiddleWareMain.main(new String[]{curHomeValue});
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
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
    				System.out.println("jButtonStop.actionPerformed, event="+evt);
    				//TODO add your code for jButtonStop.actionPerformed
    				jButtonStart.setEnabled(true);
    				jButtonStop.setEnabled(false);
    				jMenuItemStart.setEnabled(true);
    				jMenuItemStop.setEnabled(false);
    				jLabelState.setText("State: Stop!");
    				
    				MiddleWareMain.stop();
    				
    			}
    		});
    	}
    	return jButtonStop;
    }

}
