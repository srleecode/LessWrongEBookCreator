package github.lessWrongApps.lessWrongBookCreator.GUI;

import github.lessWrongApps.lessWrongBookCreator.utilities.LogTextAreaAppender;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.*;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(MainFrame.class);
    private CreateEbookSwingWorker createEbookWorker = null;
    public MainFrame() {
        initComponents();
        pack();
        
        // Sets log4j to write to logTextArea as well
        LogTextAreaAppender appender = new LogTextAreaAppender(logTextArea);
        LogManager.getRootLogger().addAppender(appender);
    }
                         
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        topPanel = new JPanel();
        frameTitlePanel = new JPanel();
        frameTitleLabel = new JLabel();
        selectFilesPanel = new JPanel();
        inputDataPanel = new JPanel();
        inputDataTextFieldPanel = new JPanel();
        inputDataTextField = new JTextField();
        inputDataSelectFilePanel = new JPanel();
        inputDataSelectFileButton = new JButton();
        outputDataPanel = new JPanel();
        outputDataTextFieldPanel = new JPanel();
        outputDataTextField = new JTextField();
        outputDataSelectFilePanel = new JPanel();
        outputDataSelectFileButton = new JButton();
        coverPagePanel = new JPanel();
        coverPageTextFieldPanel = new JPanel();
        coverPageTextField = new JTextField();
        coverPageSelectFilePanel = new JPanel();
        coverPageSelectFileButton = new JButton();
        commentSettingsPanel = new JPanel();
        commentCheckBoxesPanel = new JPanel();
        includeCommentsPanel = new JPanel();
        includeCommentsCheckBox = new JCheckBox();
        includeChildThresholdCommentsCheckBoxPanel = new JPanel();
        includeChildThresholdCommentsCheckBox = new JCheckBox();
        thresholdAndIncludePostsPanel = new JPanel();
        commentOptionsLabelsPanel = new JPanel();
        thresholdLabel = new JLabel();
        includePostsLabel = new JLabel();
        commentOptionsSelectionPanel = new JPanel();
        thesholdTextFieldPanel = new JPanel();
        thresholdTextField = new JFormattedTextField(0);
        includePostsPanel = new JPanel();
        includeParentPostsCheckBox = new JRadioButton();
        includeChildrenPostsCheckBox = new JRadioButton();
        centerPanel = new JPanel();
        createEbookPanel = new JPanel();
        createEbookButtonPanel = new JPanel();
        createEbookButton = new JButton();
        logTextAreaPanel = new JPanel();
        createEbookProgressBar = new JProgressBar();
        logTextAreaScrollPane = new JScrollPane();
        logTextArea = new JTextArea();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Less Wrong EBook Creator");

        topPanel.setLayout(new BorderLayout());
        frameTitlePanel.setLayout(new BorderLayout());
        frameTitleLabel.setFont(new Font("Times New Roman", 1, 24)); // NOI18N
        frameTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frameTitleLabel.setVerticalAlignment(SwingConstants.CENTER);
        frameTitleLabel.setText("Less Wrong Ebook Creator");
        frameTitlePanel.add(frameTitleLabel, BorderLayout.PAGE_START);
        
        outputDataPanel.setBorder(BorderFactory.createTitledBorder(null, "Output File Location", TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", 0, 12))); // NOI18N
        outputDataPanel.setLayout(new BoxLayout(outputDataPanel, BoxLayout.Y_AXIS));
        outputDataTextFieldPanel.setLayout(new GridBagLayout());

        outputDataTextField.setColumns(20);
        outputDataTextField.setEditable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        outputDataTextFieldPanel.add(outputDataTextField, gridBagConstraints);

        outputDataPanel.add(outputDataTextFieldPanel);

        outputDataSelectFileButton.setText("Select Ouput File");
        outputDataSelectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                outputDataSelectFileButtonActionPerformed(evt);
            }
        });
        outputDataSelectFilePanel.add(outputDataSelectFileButton);

        outputDataPanel.add(outputDataSelectFilePanel);
        frameTitlePanel.add(outputDataPanel, BorderLayout.CENTER);
        topPanel.add(frameTitlePanel, BorderLayout.PAGE_START);

        selectFilesPanel.setLayout(new GridLayout(2, 1));

        inputDataPanel.setBorder(BorderFactory.createTitledBorder(null, "Input Data", TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", 0, 12))); // NOI18N
        inputDataPanel.setLayout(new BoxLayout(inputDataPanel, BoxLayout.Y_AXIS));
        inputDataTextFieldPanel.setLayout(new GridBagLayout());

        inputDataTextField.setColumns(20);
        inputDataTextField.setEditable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        inputDataTextFieldPanel.add(inputDataTextField, gridBagConstraints);

        inputDataPanel.add(inputDataTextFieldPanel);

        inputDataSelectFileButton.setText("Select File");
        inputDataSelectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                inputDataSelectFileButtonActionPerformed(evt);
            }
        });
        inputDataSelectFilePanel.add(inputDataSelectFileButton);

        inputDataPanel.add(inputDataSelectFilePanel);

        selectFilesPanel.add(inputDataPanel);

        coverPagePanel.setBorder(BorderFactory.createTitledBorder(null, "Cover Page", TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", 0, 12))); // NOI18N
        coverPagePanel.setLayout(new BoxLayout(coverPagePanel, BoxLayout.Y_AXIS));
        
        coverPageTextField.setEditable(false);
        
        coverPageTextFieldPanel.setLayout(new GridBagLayout());

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        coverPageTextFieldPanel.add(coverPageTextField, gridBagConstraints);

        coverPagePanel.add(coverPageTextFieldPanel);

        coverPageSelectFileButton.setText("Select File");
        coverPageSelectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                coverPageSelectFileButtonActionPerformed(evt);
            }
        });
        coverPageSelectFilePanel.add(coverPageSelectFileButton);

        coverPagePanel.add(coverPageSelectFilePanel);

        selectFilesPanel.add(coverPagePanel);

        topPanel.add(selectFilesPanel, BorderLayout.CENTER);

        commentSettingsPanel.setBorder(BorderFactory.createTitledBorder(null, "Comments Settings", TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", 0, 12))); // NOI18N
        commentSettingsPanel.setLayout(new BorderLayout());

        commentCheckBoxesPanel.setLayout(new GridLayout(2, 1));

        includeCommentsPanel.setLayout(new GridLayout());

        includeCommentsCheckBox.setSelected(true);
        includeCommentsCheckBox.setText("Include Comments");
        includeCommentsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                includeCommentsCheckBoxActionPerformed(evt);
            }
        });
        includeCommentsPanel.add(includeCommentsCheckBox);

        commentCheckBoxesPanel.add(includeCommentsPanel);

        includeChildThresholdCommentsCheckBoxPanel.setLayout(new GridLayout());

        includeChildThresholdCommentsCheckBox.setText("Include Children Comments >= Threshold");
        includeChildThresholdCommentsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                includeChildThresholdCommentsCheckBoxActionPerformed(evt);
            }
        });
        includeChildThresholdCommentsCheckBoxPanel.add(includeChildThresholdCommentsCheckBox);

        commentCheckBoxesPanel.add(includeChildThresholdCommentsCheckBoxPanel);

        commentSettingsPanel.add(commentCheckBoxesPanel, BorderLayout.LINE_START);

        thresholdAndIncludePostsPanel.setLayout(new BorderLayout());

        commentOptionsLabelsPanel.setLayout(new GridLayout(2, 1));

        thresholdLabel.setText("Threshold: ");
        commentOptionsLabelsPanel.add(thresholdLabel);

        includePostsLabel.setText("Include Posts:");
        commentOptionsLabelsPanel.add(includePostsLabel);

        thresholdAndIncludePostsPanel.add(commentOptionsLabelsPanel, BorderLayout.WEST);

        commentOptionsSelectionPanel.setLayout(new GridLayout(2, 1));

        thesholdTextFieldPanel.setLayout(new GridBagLayout());

        thresholdTextField.setColumns(10);
        thresholdTextField.setValue(0);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        thesholdTextFieldPanel.add(thresholdTextField, gridBagConstraints);

        commentOptionsSelectionPanel.add(thesholdTextFieldPanel);

        includeParentPostsCheckBox.setText("Parent");
        includeParentPostsCheckBox.setEnabled(false);
        includePostsPanel.add(includeParentPostsCheckBox);

        includeChildrenPostsCheckBox.setText("Children");
        includeChildrenPostsCheckBox.setEnabled(false);
        includePostsPanel.add(includeChildrenPostsCheckBox);

        commentOptionsSelectionPanel.add(includePostsPanel);

        thresholdAndIncludePostsPanel.add(commentOptionsSelectionPanel, BorderLayout.CENTER);

        commentSettingsPanel.add(thresholdAndIncludePostsPanel, BorderLayout.PAGE_END);

        topPanel.add(commentSettingsPanel, BorderLayout.EAST);
        
        getContentPane().add(topPanel, BorderLayout.PAGE_START);

        centerPanel.setLayout(new BorderLayout());

        createEbookPanel.setLayout(new BorderLayout());

        createEbookButton.setText("Create Ebook");
        createEbookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                createEbookButtonActionPerformed(evt);
            }
        });
        createEbookButtonPanel.add(createEbookButton);

        createEbookPanel.add(createEbookButtonPanel, BorderLayout.PAGE_START);

        logTextAreaPanel.setBorder(BorderFactory.createTitledBorder(null, "Log", TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", 0, 12))); // NOI18N
        logTextAreaPanel.setLayout(new BoxLayout(logTextAreaPanel, BoxLayout.Y_AXIS));
        createEbookProgressBar.setValue(0);
        createEbookProgressBar.setStringPainted(true);

        logTextAreaPanel.add(createEbookProgressBar);

        logTextArea.setColumns(20);
        logTextArea.setLineWrap(true);
        logTextArea.setRows(5);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setEditable(false);
        DefaultCaret caret = (DefaultCaret)logTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        logTextAreaScrollPane.setViewportView(logTextArea);

        logTextAreaPanel.add(logTextAreaScrollPane);

        createEbookPanel.add(logTextAreaPanel, BorderLayout.CENTER);

        centerPanel.add(createEbookPanel, BorderLayout.CENTER);
        
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        pack();
    }                       

    private void includeChildThresholdCommentsCheckBoxActionPerformed(ActionEvent evt) {                                                                      
        if (includeChildThresholdCommentsCheckBox.isSelected()) {
            includeParentPostsCheckBox.setEnabled(true);
            includeChildrenPostsCheckBox.setEnabled(true);
        } else {
            includeParentPostsCheckBox.setEnabled(false);
            includeChildrenPostsCheckBox.setEnabled(false);
        } 
    }                                                                     

    private void includeCommentsCheckBoxActionPerformed(ActionEvent evt) {                                                        
        if(!includeCommentsCheckBox.isSelected()) {
            includeChildThresholdCommentsCheckBox.setEnabled(false);
            thresholdTextField.setEnabled(false);
            includeParentPostsCheckBox.setEnabled(false);
            includeChildrenPostsCheckBox.setEnabled(false);
        } else {
            includeChildThresholdCommentsCheckBox.setEnabled(true);
            thresholdTextField.setEnabled(true);
            includeParentPostsCheckBox.setEnabled(!includeChildThresholdCommentsCheckBox.isSelected());
            includeChildrenPostsCheckBox.setEnabled(!includeChildThresholdCommentsCheckBox.isSelected());
        }
    }                                                       
    private void outputDataSelectFileButtonActionPerformed(ActionEvent evt) {                                                          
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Epub (.epub)", "epub");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String extension = "";
            String fileName = selectedFile.getAbsolutePath().toLowerCase();
            int lastDotIdx = fileName.lastIndexOf('.');
            int lastSlashIdx = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

            if (lastDotIdx > lastSlashIdx) {
                extension = fileName.substring(lastDotIdx+1);
            }
            if (extension.equals("epub")) {
                outputDataTextField.setText(selectedFile.getAbsolutePath());
            } else {
                outputDataTextField.setText(selectedFile.getAbsolutePath() + ".epub");
            } 
        } 
    } 
    private void inputDataSelectFileButtonActionPerformed(ActionEvent evt) {                                                          
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File (.xlsx, .xls, .csv)", "xlsx", "xls", "csv");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            inputDataTextField.setText(selectedFile.getAbsolutePath());
        }
    }                                                                                                        

    private void coverPageSelectFileButtonActionPerformed(ActionEvent evt) {                                                          
        JFileChooser fileChooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setCurrentDirectory(workingDirectory);
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Html File (.html)", "html");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            coverPageTextField.setText(selectedFile.getAbsolutePath());
        }
    }                                                         

    private void createEbookButtonActionPerformed(ActionEvent evt) {  
    	String inputFileString = inputDataTextField.getText();
        String outputFileString = outputDataTextField.getText();
        String coverPageFileString = coverPageTextField.getText();
        logTextArea.setText("");
    	final File inputFile = new File(inputFileString);
        File outputFile = new File(outputFileString); 
        outputFile.setWritable(true);
        
        if (inputFileString.isEmpty()) {
            logger.info("Input data field must be filled before the ebook can be created.");
        } else if (outputFileString.isEmpty()) {
            logger.info("Output data field must be filled before the ebook can be created.");
        } else if (!inputFile.exists()) {
            logger.info("Input file does not exist");
        } else if (!outputFile.canWrite()) {
            try {
                outputFile.createNewFile();
            } catch (IOException ex) {
                logger.info(outputFile.getAbsolutePath() + " cannot be written to. Check that the path is not read only.");
            }
        } else {
            createEbookProgressBar.setValue(0);
            createEbookButton.setEnabled(false);
            try {
                thresholdTextField.commitEdit();
            } catch (ParseException e) {
                logger.error("", e);
            }
            
            int commentsThreshold = (Integer) thresholdTextField.getValue();
            boolean isCommentsIncluded = includeCommentsCheckBox.isSelected();
            boolean isChildPostsIncluded = includeChildrenPostsCheckBox.isSelected();
            boolean isParentPostsIncluded = includeParentPostsCheckBox.isSelected();
            boolean isThresholdAppliesToChildPosts = includeChildThresholdCommentsCheckBox.isSelected();
            createEbookWorker = new CreateEbookSwingWorker(logTextArea, createEbookButton, inputFile, 
                    outputFile, coverPageFileString, commentsThreshold, isCommentsIncluded, isChildPostsIncluded, 
                    isParentPostsIncluded, isThresholdAppliesToChildPosts){
            };
            createEbookWorker.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public  void propertyChange(PropertyChangeEvent evt) {
                    if ("progress".equals(evt.getPropertyName())) {
                        createEbookProgressBar.setValue((Integer)evt.getNewValue());
                    }
                }
            });

            createEbookWorker.execute();
        } 
    }                                                 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            logger.error("", ex);
        } catch (InstantiationException ex) {
        	logger.error("", ex);
        } catch (IllegalAccessException ex) {
        	logger.error("", ex);
        } catch (UnsupportedLookAndFeelException ex) {
        	logger.error("", ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
            
        });
       /* Properties props = new Properties();
        try {
            props.load(MainFrame.class.getResourceAsStream("/log4j.xml"));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        PropertyConfigurator.configure(props);*/
    }
                
    private JPanel centerPanel;
    private JPanel commentCheckBoxesPanel;
    private JPanel commentOptionsLabelsPanel;
    private JPanel commentOptionsSelectionPanel;
    private JPanel commentSettingsPanel;
    private JPanel coverPagePanel;
    private JButton coverPageSelectFileButton;
    private JPanel coverPageSelectFilePanel;
    private JTextField coverPageTextField;
    private JPanel coverPageTextFieldPanel;
    private JButton createEbookButton;
    private JPanel createEbookButtonPanel;
    private JPanel createEbookPanel;
    private JProgressBar createEbookProgressBar;
    private JLabel frameTitleLabel;
    private JPanel frameTitlePanel;
    private JCheckBox includeChildThresholdCommentsCheckBox;
    private JPanel includeChildThresholdCommentsCheckBoxPanel;
    private JRadioButton includeChildrenPostsCheckBox;
    private JCheckBox includeCommentsCheckBox;
    private JPanel includeCommentsPanel;
    private JRadioButton includeParentPostsCheckBox;
    private JLabel includePostsLabel;
    private JPanel includePostsPanel;
    private JPanel inputDataPanel;
    private JButton inputDataSelectFileButton;
    private JPanel inputDataSelectFilePanel;
    private JTextField inputDataTextField;
    private JPanel inputDataTextFieldPanel;
    private JPanel outputDataPanel;
    private JButton outputDataSelectFileButton;
    private JPanel outputDataSelectFilePanel;
    private JTextField outputDataTextField;
    private JPanel outputDataTextFieldPanel;
    private JTextArea logTextArea;
    private JPanel logTextAreaPanel;
    private JScrollPane logTextAreaScrollPane;
    private JPanel selectFilesPanel;
    private JPanel thesholdTextFieldPanel;
    private JPanel thresholdAndIncludePostsPanel;
    private JLabel thresholdLabel;
    private JFormattedTextField thresholdTextField;
    private JPanel topPanel;                 
}


