package nju.moon.lhc.reconstruction.gui;
import com.cloudgarden.layout.AnchorLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

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
    private JLabel jLabel3;
    private JButton jButtonRemove;
    private JButton jButtonAdd;
    private JButton jButtonChooseFile;
    private JLabel jLabel2;
    private JTextField jTextField1;
    private JButton jButtonDefaultHome;
    private JPanel jPanel3;
    private JPanel jPanel2;
    private JRadioButton jRadioButton2;
    private JTextArea jTextArea1;
    private JRadioButton jRadioButton1;
    private JLabel jLabel1;
    private JTextField jTextFieldHome;
    private JMenuItem jMenuItem4;
    private JMenuItem jMenuItem3;
    private JMenuItem jMenuItem2;
    private JMenuItem jMenuItem1;
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
	    	getMainFrame().setSize(718, 574);
    	}
    	{
    		jMenuBar1 = new JMenuBar();
    		getMainFrame().setJMenuBar(jMenuBar1);
    		jMenuBar1.setName("jMenuBar1");
    		{
    			jMenu1 = new JMenu();
    			jMenuBar1.add(jMenu1);
    			jMenu1.setName("jMenu1");
    			{
    				jMenuItemDefault = new JMenuItem();
    				jMenu1.add(jMenuItemDefault);
    				jMenuItemDefault.setName("jMenuItemDefault");
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
        FlowLayout topPanelLayout = new FlowLayout();
        topPanelLayout.setAlignment(FlowLayout.LEFT);
        topPanelLayout.setHgap(10);
        topPanelLayout.setVgap(10);
        topPanel.setLayout(topPanelLayout);
        topPanel.setPreferredSize(new java.awt.Dimension(708, 294));
        {
        	jPanel1 = new JPanel();
        	getMainFrame().getContentPane().add(jPanel1, BorderLayout.NORTH);
        	FlowLayout jPanel1Layout = new FlowLayout();
        	jPanel1.setLayout(jPanel1Layout);
        	jPanel1.setPreferredSize(new java.awt.Dimension(708, 177));
        	jPanel1.add(getJPanel3());
        	jPanel1.add(getJPanel2());
        }
        topPanel.add(getJLabel4());
        topPanel.add(getJLabel5());
        topPanel.add(getJTextArea1());
        topPanel.add(getJList1());
        show(topPanel);
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
    		jTextFieldHome.setPreferredSize(new java.awt.Dimension(430, 28));
    	}
    	return jTextFieldHome;
    }
    
    private JLabel getJLabel1() {
    	if(jLabel1 == null) {
    		jLabel1 = new JLabel();
    		jLabel1.setLayout(null);
    		jLabel1.setName("jLabel1");
    		jLabel1.setPreferredSize(new java.awt.Dimension(278, 25));
    	}
    	return jLabel1;
    }
    
    private JRadioButton getJRadioButton1() {
    	if(jRadioButton1 == null) {
    		jRadioButton1 = new JRadioButton();
    		jRadioButton1.setName("jRadioButton1");
    		jRadioButton1.setPreferredSize(new java.awt.Dimension(85, 24));
    	}
    	return jRadioButton1;
    }
    
    private JTextArea getJTextArea1() {
    	if(jTextArea1 == null) {
    		jTextArea1 = new JTextArea();
    		jTextArea1.setName("jTextArea1");
    		jTextArea1.setPreferredSize(new java.awt.Dimension(440, 290));
    	}
    	return jTextArea1;
    }

    private JRadioButton getJRadioButton2() {
    	if(jRadioButton2 == null) {
    		jRadioButton2 = new JRadioButton();
    		jRadioButton2.setName("jRadioButton2");
    		jRadioButton2.setPreferredSize(new java.awt.Dimension(85, 24));
    	}
    	return jRadioButton2;
    }
    
    private JPanel getJPanel2() {
    	if(jPanel2 == null) {
    		jPanel2 = new JPanel();
    		jPanel2.setPreferredSize(new java.awt.Dimension(119, 138));
    		jPanel2.add(getJRadioButton1());
    		jPanel2.add(getJRadioButton2());
    		jPanel2.add(getJButtonAdd());
    		jPanel2.add(getJButtonRemove());
    	}
    	return jPanel2;
    }

    private JPanel getJPanel3() {
    	if(jPanel3 == null) {
    		jPanel3 = new JPanel();
    		FlowLayout jPanel3Layout = new FlowLayout();
    		jPanel3Layout.setAlignment(FlowLayout.LEFT);
    		jPanel3.setLayout(jPanel3Layout);
    		jPanel3.setPreferredSize(new java.awt.Dimension(562, 139));
    		jPanel3.add(getJLabel1());
    		jPanel3.add(getJLabel3());
    		jPanel3.add(getJTextFieldHome());
    		jPanel3.add(getJButton1());
    		jPanel3.add(getJLabel2());
    		jPanel3.add(getJTextField1());
    		jPanel3.add(getJButtonChooseFile());
    	}
    	return jPanel3;
    }
    
    private JButton getJButton1() {
    	if(jButtonDefaultHome == null) {
    		jButtonDefaultHome = new JButton();
    		jButtonDefaultHome.setName("jButtonDefaultHome");
    		jButtonDefaultHome.setPreferredSize(new java.awt.Dimension(116, 30));
    	}
    	return jButtonDefaultHome;
    }
    
    private JTextField getJTextField1() {
    	if(jTextField1 == null) {
    		jTextField1 = new JTextField();
    		jTextField1.setName("jTextField1");
    		jTextField1.setPreferredSize(new java.awt.Dimension(430, 28));
    	}
    	return jTextField1;
    }
    
    private JLabel getJLabel2() {
    	if(jLabel2 == null) {
    		jLabel2 = new JLabel();
    		jLabel2.setName("jLabel2");
    		jLabel2.setPreferredSize(new java.awt.Dimension(179, 25));
    	}
    	return jLabel2;
    }
    
    private JButton getJButtonChooseFile() {
    	if(jButtonChooseFile == null) {
    		jButtonChooseFile = new JButton();
    		jButtonChooseFile.setName("jButtonChooseFile");
    		jButtonChooseFile.setPreferredSize(new java.awt.Dimension(116, 30));
    	}
    	return jButtonChooseFile;
    }
    
    private JButton getJButtonAdd() {
    	if(jButtonAdd == null) {
    		jButtonAdd = new JButton();
    		jButtonAdd.setName("jButtonAdd");
    		jButtonAdd.setPreferredSize(new java.awt.Dimension(85, 30));
    	}
    	return jButtonAdd;
    }
    
    private JButton getJButtonRemove() {
    	if(jButtonRemove == null) {
    		jButtonRemove = new JButton();
    		jButtonRemove.setName("jButtonRemove");
    		jButtonRemove.setPreferredSize(new java.awt.Dimension(85, 30));
    	}
    	return jButtonRemove;
    }
    
    private JLabel getJLabel3() {
    	if(jLabel3 == null) {
    		jLabel3 = new JLabel();
    		jLabel3.setName("jLabel3");
    		jLabel3.setPreferredSize(new java.awt.Dimension(238, 25));
    	}
    	return jLabel3;
    }
    
    private JLabel getJLabel4() {
    	if(jLabel4 == null) {
    		jLabel4 = new JLabel();
    		jLabel4.setName("jLabel4");
    		jLabel4.setPreferredSize(new java.awt.Dimension(445, 18));
    	}
    	return jLabel4;
    }
    
    private JLabel getJLabel5() {
    	if(jLabel5 == null) {
    		jLabel5 = new JLabel();
    		jLabel5.setName("jLabel5");
    	}
    	return jLabel5;
    }
    
    private JList getJList1() {
    	if(jList1 == null) {
    		ListModel jList1Model = 
    				new DefaultComboBoxModel(
    						new String[] { "Item One", "Item Two" });
    		jList1 = new JList();
    		jList1.setModel(jList1Model);
    		jList1.setPreferredSize(new java.awt.Dimension(224, 290));
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
    	if(jMenuItem1 == null) {
    		jMenuItem1 = new JMenuItem();
    		jMenuItem1.setName("jMenuItem1");
    	}
    	return jMenuItem1;
    }
    
    private JMenuItem getJMenuItem2() {
    	if(jMenuItem2 == null) {
    		jMenuItem2 = new JMenuItem();
    		jMenuItem2.setName("jMenuItem2");
    	}
    	return jMenuItem2;
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

}
