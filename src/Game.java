
import java.awt.Image;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * This is the 'Game' swing form class of Deal Or No Deal.
 */
public class Game extends javax.swing.JFrame {

    /**
     * Creates new form Game
     */
    private final DecimalFormat fdecimal = new DecimalFormat("##");//Declare DecimalFormat
    private final DecimalFormat fdec = new DecimalFormat("##.00");//Declare DecimalFormat
    private int roundOffer = 6; //Declare instance round as integer.
    private int round = 0;// Declare instance round as integer.
    private int Cases = 25; //Declare instance cases as integer.
    private double money = 0.0; //Declare instance money as double.
    private double paymentCase = 0.0; //Declare instance paymentCase as double.
    private double moneyOffer = 0; //Declare instance moneyOffer as double.
    private int turnCount = 1; //Declare instance turnCount as integer.
    protected Algorithm logic; //Declare instance object Algorithm.
    private boolean openCase; //Declare instance openCase as boolean.
    private int dialogButton; //Declare instance dialogButton as integer.
    private int result; //Declare instance result as integer.
    private boolean acceptOffer = false; //Declare instance acceptOffer as boolean.
    private String playerName = null; //Declare instance playerName as String.
    private DatabaseDND dndDB = new DatabaseDND(); //Initalize Object DatabaseDND
//Default constructor to initalize

    public Game() {
        initComponents();
        this.round = this.roundOffer;//Integer round equal to the integer offeround.
        this.logic = new Algorithm();//
        //dialogButton = JOptionPane.YES_NO_OPTION;
        this.openCase = false;
    }

    public void getPrice(double price) {
        //If number of cases is 25, it will ask user to get first case
        if (this.Cases == 25) {
            this.money = price;//The money equal to the price parameter.
            //removeprice(price);//Calling method to disappear one label.
            this.Cases--;//Reduce case by 1
            this.NotifyPlayer.setText("Please remove case: " + this.round);//Once the first case is selected, it asks the player to remove other case.
            this.openCase = true;//Once the first case is selected, set as True.
            this.NumCase.setText("Num of Case: " + this.Cases);//When number of cases is reduce, set the label to see how many left.
        } else//Once the player select the case, asking player to remove case to get price offer
         if (this.Cases <= 24 || this.Cases <= 18 || this.Cases <= 13 || this.Cases <= 9
                    || this.Cases == 6) {

                removeprice(price);//Calling method to disappear one label.
                this.round--;//Reduce round by on1
                this.NotifyPlayer.setText("Please remove case: " + this.round);
                this.Cases--;//Reduce case by 1
                this.NumCase.setText("Num of Case: " + this.Cases);//When number of cases is reduce, set the label to see how many left.

                if (this.round == 0 || this.Cases == 0) {//If case or case removing round is zero.

                    this.turnCount++;//Increase turnCount by 1.
                    logic.setOffer(this.money, this.turnCount);//Passing money and turnCount to calculate price offer
                    this.moneyOffer = logic.getOffer();//The price offer will equal to getOffer() double method
                    //Asking player whether he/she want to accept price offer
                    this.result = JOptionPane.showConfirmDialog(null,
                            "You bank offer is $" + fdec.format(this.moneyOffer) + "\nDo you want to accept? ", null, JOptionPane.YES_NO_OPTION);
                    if (this.result == JOptionPane.YES_OPTION) {//If player accepts, it will display the message by saying that,
                        //the player won the price offer and get his/her own case.
                        this.acceptOffer = true;//Set the acceptOffer as true after accepting bank offer
                        JOptionPane.showMessageDialog(rootPane, "You accepted the bank offer and your offer is $" + fdec.format(this.moneyOffer),
                                "Offer Info", JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(rootPane, "Congratulation. You won: $ " + fdec.format(this.moneyOffer) + 
                                "Your case contained: $" + this.money, "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                        playerName = JOptionPane.showInputDialog(rootPane, "What's your name?");
                        dndDB.setCompetitorName(this.playerName);
                        dndDB.setDollar(this.moneyOffer);
                        //dndDB.setMoneyOffer(this.moneyOffer);
                        //dndDB.setAccept(this.acceptOffer);
                        dndDB.insert();//Call the method to insert into the database
                        //Calling DealOrNoDealMain to return the main form.
                        DealOrNoDealMain main = new DealOrNoDealMain();
                        main.setVisible(true);
                        this.dispose();
                    } else if (this.result == JOptionPane.NO_OPTION) {//Otherwise, the player continues to remove other cases.
                        if (this.Cases > 0) {//If the number of cases is not equal to 0, it will ask player to continue removing other case
                            this.roundOffer--;//Reduce round by 1
                            if (this.roundOffer == 0) {//If round is 0, it will equal to 1.
                                this.roundOffer = 1;
                            }
                            this.round = this.roundOffer;//Integer round equal to the integer offeround.
                            this.NotifyPlayer.setText("Please remove case: " + this.round);
                            return;
                        } else {//Otherwise, if player doesn't accept the offer, it will notify the player that he/she reject and the price of player's selected case.
                            JOptionPane.showMessageDialog(rootPane, "You reject $" + fdecimal.format(this.moneyOffer), "Reject", JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(rootPane, "Congratulation. You win your case: $" + this.money, "Congratulation", JOptionPane.INFORMATION_MESSAGE);
                            playerName = JOptionPane.showInputDialog(rootPane, "What's your name?");
                            dndDB.setCompetitorName(this.playerName);
                            dndDB.setDollar(this.money);
                            //dndDB.setMoneyOffer(this.moneyOffer);
                            //dndDB.setAccept(this.acceptOffer);
                            dndDB.insert();
                            DealOrNoDealMain main = new DealOrNoDealMain();
                            main.setVisible(true);
                            this.dispose();
                        }
                    }

                }
            }

    }
//This method will make one label disappear after clicking the button.

    private void removeprice(double price) {
        if (this.Price1.getText().equals(fdecimal.format(price))) {
            this.Price1.setVisible(false);
        } else if (this.Price2.getText().equals(fdecimal.format(price))) {
            this.Price2.setVisible(false);
        } else if (this.Price3.getText().equals(fdecimal.format(price))) {
            this.Price3.setVisible(false);
        } else if (this.Price4.getText().equals(fdecimal.format(price))) {
            this.Price4.setVisible(false);
        } else if (this.Price5.getText().equals(fdecimal.format(price))) {
            this.Price5.setVisible(false);
        } else if (this.Price6.getText().equals(fdecimal.format(price))) {
            this.Price6.setVisible(false);
        } else if (this.Price7.getText().equals(fdecimal.format(price))) {
            this.Price7.setVisible(false);
        } else if (this.Price8.getText().equals(fdecimal.format(price))) {
            this.Price8.setVisible(false);
        } else if (this.Price9.getText().equals(fdecimal.format(price))) {
            this.Price9.setVisible(false);
        } else if (this.Price10.getText().equals(fdecimal.format(price))) {
            this.Price10.setVisible(false);
        } else if (this.Price11.getText().equals(fdecimal.format(price))) {
            this.Price11.setVisible(false);
        } else if (this.Price12.getText().equals(fdecimal.format(price))) {
            this.Price12.setVisible(false);
        } else if (this.Price13.getText().equals(fdecimal.format(price))) {
            this.Price13.setVisible(false);
        } else if (this.Price14.getText().equals(fdecimal.format(price))) {
            this.Price14.setVisible(false);
        } else if (this.Price15.getText().equals(fdecimal.format(price))) {
            this.Price15.setVisible(false);
        } else if (this.Price16.getText().equals(fdecimal.format(price))) {
            this.Price16.setVisible(false);
        } else if (this.Price17.getText().equals(fdecimal.format(price))) {
            this.Price17.setVisible(false);
        } else if (this.Price18.getText().equals(fdecimal.format(price))) {
            this.Price18.setVisible(false);
        } else if (this.Price19.getText().equals(fdecimal.format(price))) {
            this.Price19.setVisible(false);
        } else if (this.Price20.getText().equals(fdecimal.format(price))) {
            this.Price20.setVisible(false);
        } else if (this.Price21.getText().equals(fdecimal.format(price))) {
            this.Price21.setVisible(false);
        } else if (this.Price22.getText().equals(fdecimal.format(price))) {
            this.Price22.setVisible(false);
        } else if (this.Price23.getText().equals(fdecimal.format(price))) {
            this.Price23.setVisible(false);
        } else if (this.Price24.getText().equals(fdecimal.format(price))) {
            this.Price24.setVisible(false);
        } else if (this.Price25.getText().equals(fdecimal.format(price))) {
            this.Price25.setVisible(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        NumCase = new javax.swing.JLabel();
        Price3 = new javax.swing.JLabel();
        Price1 = new javax.swing.JLabel();
        Price13 = new javax.swing.JLabel();
        Price2 = new javax.swing.JLabel();
        jBtExit = new javax.swing.JButton();
        Price4 = new javax.swing.JLabel();
        Price5 = new javax.swing.JLabel();
        Price6 = new javax.swing.JLabel();
        Price7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        BriefCase1 = new javax.swing.JButton();
        BriefCase2 = new javax.swing.JButton();
        BriefCase3 = new javax.swing.JButton();
        BriefCase4 = new javax.swing.JButton();
        BriefCase5 = new javax.swing.JButton();
        BriefCase6 = new javax.swing.JButton();
        BriefCase7 = new javax.swing.JButton();
        BriefCase8 = new javax.swing.JButton();
        BriefCase9 = new javax.swing.JButton();
        BriefCase10 = new javax.swing.JButton();
        BriefCase11 = new javax.swing.JButton();
        BriefCase12 = new javax.swing.JButton();
        BriefCase13 = new javax.swing.JButton();
        BriefCase14 = new javax.swing.JButton();
        BriefCase15 = new javax.swing.JButton();
        BriefCase16 = new javax.swing.JButton();
        BriefCase17 = new javax.swing.JButton();
        BriefCase18 = new javax.swing.JButton();
        BriefCase19 = new javax.swing.JButton();
        BriefCase20 = new javax.swing.JButton();
        BriefCase21 = new javax.swing.JButton();
        BriefCase22 = new javax.swing.JButton();
        BriefCase23 = new javax.swing.JButton();
        BriefCase24 = new javax.swing.JButton();
        BriefCase25 = new javax.swing.JButton();
        Price8 = new javax.swing.JLabel();
        Price9 = new javax.swing.JLabel();
        Price10 = new javax.swing.JLabel();
        Price11 = new javax.swing.JLabel();
        Price12 = new javax.swing.JLabel();
        Price14 = new javax.swing.JLabel();
        Price15 = new javax.swing.JLabel();
        Price16 = new javax.swing.JLabel();
        Price17 = new javax.swing.JLabel();
        Price25 = new javax.swing.JLabel();
        Price18 = new javax.swing.JLabel();
        Price19 = new javax.swing.JLabel();
        Price20 = new javax.swing.JLabel();
        Price21 = new javax.swing.JLabel();
        Price22 = new javax.swing.JLabel();
        Price23 = new javax.swing.JLabel();
        Price24 = new javax.swing.JLabel();
        NotifyPlayer = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        Price26 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Game");
        setBackground(new java.awt.Color(236, 236, 38));
        setForeground(java.awt.Color.yellow);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabel4.setText("500");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 325, -1, -1));

        NumCase.setFont(new java.awt.Font("Agency FB", 0, 24)); // NOI18N
        NumCase.setForeground(new java.awt.Color(255, 255, 255));
        NumCase.setText("Num of Case:");
        getContentPane().add(NumCase, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 170, 30));

        Price3.setBackground(new java.awt.Color(51, 51, 255));
        Price3.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price3.setText("10");
        getContentPane().add(Price3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 115, 110, -1));

        Price1.setBackground(new java.awt.Color(51, 51, 255));
        Price1.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price1.setText("1");
        getContentPane().add(Price1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 65, 110, -1));

        Price13.setBackground(new java.awt.Color(51, 51, 255));
        Price13.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price13.setText("4000");
        getContentPane().add(Price13, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 65, 110, -1));

        Price2.setBackground(new java.awt.Color(51, 51, 255));
        Price2.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price2.setText("5");
        getContentPane().add(Price2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 110, -1));

        jBtExit.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        jBtExit.setText("Exit");
        jBtExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtExitActionPerformed(evt);
            }
        });
        getContentPane().add(jBtExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 420, -1, -1));

        Price4.setBackground(new java.awt.Color(51, 51, 255));
        Price4.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price4.setText("25");
        getContentPane().add(Price4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 110, -1));

        Price5.setBackground(new java.awt.Color(51, 51, 255));
        Price5.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price5.setText("50");
        getContentPane().add(Price5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 110, -1));

        Price6.setBackground(new java.awt.Color(51, 51, 255));
        Price6.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price6.setText("75");
        getContentPane().add(Price6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 195, 110, -1));

        Price7.setBackground(new java.awt.Color(51, 51, 255));
        Price7.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price7.setText("100");
        getContentPane().add(Price7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 110, -1));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel1.setLayout(null);

        BriefCase1.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase1.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase1.setText("1");
        BriefCase1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase1ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase1);
        BriefCase1.setBounds(10, 20, 60, 55);

        BriefCase2.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase2.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase2.setText("2");
        BriefCase2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase2ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase2);
        BriefCase2.setBounds(100, 20, 60, 55);

        BriefCase3.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase3.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase3.setText("3");
        BriefCase3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BriefCase3MouseClicked(evt);
            }
        });
        BriefCase3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase3ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase3);
        BriefCase3.setBounds(190, 20, 60, 55);

        BriefCase4.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase4.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase4.setText("4");
        BriefCase4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase4ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase4);
        BriefCase4.setBounds(280, 20, 60, 55);

        BriefCase5.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase5.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase5.setText("5");
        BriefCase5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase5ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase5);
        BriefCase5.setBounds(370, 20, 60, 55);

        BriefCase6.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase6.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase6.setText("6");
        BriefCase6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase6ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase6);
        BriefCase6.setBounds(10, 80, 60, 55);

        BriefCase7.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase7.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase7.setText("7");
        BriefCase7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase7ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase7);
        BriefCase7.setBounds(100, 80, 60, 55);

        BriefCase8.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase8.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase8.setText("8");
        BriefCase8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase8ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase8);
        BriefCase8.setBounds(190, 80, 60, 55);

        BriefCase9.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase9.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase9.setText("9");
        BriefCase9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase9ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase9);
        BriefCase9.setBounds(280, 80, 60, 55);

        BriefCase10.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase10.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase10.setText("10");
        BriefCase10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase10ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase10);
        BriefCase10.setBounds(370, 80, 60, 55);

        BriefCase11.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase11.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase11.setText("11");
        BriefCase11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase11ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase11);
        BriefCase11.setBounds(10, 140, 60, 55);

        BriefCase12.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase12.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase12.setText("12");
        BriefCase12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase12ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase12);
        BriefCase12.setBounds(100, 140, 60, 55);

        BriefCase13.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase13.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase13.setText("13");
        BriefCase13.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase13ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase13);
        BriefCase13.setBounds(190, 140, 60, 55);

        BriefCase14.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase14.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase14.setText("14");
        BriefCase14.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase14ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase14);
        BriefCase14.setBounds(280, 140, 60, 55);

        BriefCase15.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase15.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase15.setText("15");
        BriefCase15.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase15ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase15);
        BriefCase15.setBounds(370, 140, 60, 55);

        BriefCase16.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase16.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase16.setText("16");
        BriefCase16.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase16ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase16);
        BriefCase16.setBounds(10, 200, 60, 55);

        BriefCase17.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase17.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase17.setText("17");
        BriefCase17.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase17ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase17);
        BriefCase17.setBounds(100, 200, 60, 55);

        BriefCase18.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase18.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase18.setText("18");
        BriefCase18.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase18ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase18);
        BriefCase18.setBounds(190, 200, 60, 55);

        BriefCase19.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase19.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase19.setText("19");
        BriefCase19.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase19ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase19);
        BriefCase19.setBounds(280, 200, 60, 55);

        BriefCase20.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase20.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase20.setText("20");
        BriefCase20.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase20ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase20);
        BriefCase20.setBounds(370, 200, 60, 55);

        BriefCase21.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase21.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase21.setText("21");
        BriefCase21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase21ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase21);
        BriefCase21.setBounds(10, 260, 60, 55);

        BriefCase22.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase22.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase22.setText("22");
        BriefCase22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase22ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase22);
        BriefCase22.setBounds(100, 260, 60, 55);

        BriefCase23.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase23.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase23.setText("23");
        BriefCase23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase23ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase23);
        BriefCase23.setBounds(190, 260, 60, 55);

        BriefCase24.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase24.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase24.setText("24");
        BriefCase24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase24ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase24);
        BriefCase24.setBounds(280, 260, 60, 55);

        BriefCase25.setBackground(new java.awt.Color(0, 0, 0));
        BriefCase25.setFont(new java.awt.Font("Agency FB", 3, 18)); // NOI18N
        BriefCase25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/C1.png"))); // NOI18N
        BriefCase25.setText("25");
        BriefCase25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BriefCase25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BriefCase25ActionPerformed(evt);
            }
        });
        jPanel1.add(BriefCase25);
        BriefCase25.setBounds(370, 260, 60, 55);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 440, 330));

        Price8.setBackground(new java.awt.Color(51, 51, 255));
        Price8.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price8.setText("200");
        getContentPane().add(Price8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 245, 110, -1));

        Price9.setBackground(new java.awt.Color(51, 51, 255));
        Price9.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price9.setText("300");
        getContentPane().add(Price9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 275, 110, -1));

        Price10.setBackground(new java.awt.Color(51, 51, 255));
        Price10.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price10.setText("400");
        getContentPane().add(Price10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 110, -1));

        Price11.setBackground(new java.awt.Color(51, 51, 255));
        Price11.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price11.setText("1000");
        getContentPane().add(Price11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 110, -1));

        Price12.setBackground(new java.awt.Color(51, 51, 255));
        Price12.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price12.setText("2000");
        getContentPane().add(Price12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 110, -1));

        Price14.setBackground(new java.awt.Color(51, 51, 255));
        Price14.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price14.setText("5000");
        getContentPane().add(Price14, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 90, 110, -1));

        Price15.setBackground(new java.awt.Color(51, 51, 255));
        Price15.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price15.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price15.setText("7500");
        getContentPane().add(Price15, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 116, 110, -1));

        Price16.setBackground(new java.awt.Color(51, 51, 255));
        Price16.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price16.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price16.setText("10000");
        getContentPane().add(Price16, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 145, 110, -1));

        Price17.setBackground(new java.awt.Color(51, 51, 255));
        Price17.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price17.setText("25000");
        getContentPane().add(Price17, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, 110, -1));

        Price25.setBackground(new java.awt.Color(51, 51, 255));
        Price25.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price25.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price25.setText("1000000");
        getContentPane().add(Price25, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 380, 110, 22));

        Price18.setBackground(new java.awt.Color(51, 51, 255));
        Price18.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price18.setText("50000");
        getContentPane().add(Price18, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 195, 110, -1));

        Price19.setBackground(new java.awt.Color(51, 51, 255));
        Price19.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price19.setText("60000");
        getContentPane().add(Price19, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 220, 110, -1));

        Price20.setBackground(new java.awt.Color(51, 51, 255));
        Price20.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price20.setText("75000");
        getContentPane().add(Price20, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 245, 110, -1));

        Price21.setBackground(new java.awt.Color(51, 51, 255));
        Price21.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price21.setText("100000");
        getContentPane().add(Price21, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 275, 110, -1));

        Price22.setBackground(new java.awt.Color(51, 51, 255));
        Price22.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price22.setText("250000");
        getContentPane().add(Price22, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 300, 110, -1));

        Price23.setBackground(new java.awt.Color(51, 51, 255));
        Price23.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price23.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price23.setText("500000");
        getContentPane().add(Price23, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 325, 110, -1));

        Price24.setBackground(new java.awt.Color(51, 51, 255));
        Price24.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        Price24.setText("750000");
        getContentPane().add(Price24, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 350, 110, -1));

        NotifyPlayer.setBackground(new java.awt.Color(51, 255, 51));
        NotifyPlayer.setFont(new java.awt.Font("Agency FB", 0, 24)); // NOI18N
        NotifyPlayer.setForeground(new java.awt.Color(255, 255, 255));
        NotifyPlayer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        NotifyPlayer.setText("Please Select Your Case.");
        NotifyPlayer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(NotifyPlayer, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 420, 450, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/SIDE.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 140, 360));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/SIDE.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 50, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Game Background.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 480));

        Price26.setBackground(new java.awt.Color(51, 51, 255));
        Price26.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        Price26.setText("1000");
        getContentPane().add(Price26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 110, -1));

        jMenu1.setText("Menu");

        jMenuItem4.setText("Home");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem1.setText("New Game");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("High Score");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

//All button actions below is used to choose the case to remove or as your own.
    private void BriefCase23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase23ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));
        this.BriefCase23.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase23.setIcon(new ImageIcon(img));
    }//GEN-LAST:event_BriefCase23ActionPerformed

    private void BriefCase1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase1ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));
        this.BriefCase1.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase1.setIcon(new ImageIcon(img));
    }//GEN-LAST:event_BriefCase1ActionPerformed

    private void BriefCase2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase2ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase2.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase2.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase2ActionPerformed

    private void BriefCase3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase3ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase3.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase3.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase3ActionPerformed

    private void BriefCase4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase4ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase4.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase4.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase4ActionPerformed

    private void BriefCase5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase5ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase5.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase5.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase5ActionPerformed

    private void BriefCase6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase6ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase6.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase6.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase6ActionPerformed

    private void BriefCase7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase7ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase7.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase7.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase7ActionPerformed

    private void BriefCase8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase8ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase8.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase8.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase8ActionPerformed

    private void BriefCase9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase9ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase9.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase9.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase9ActionPerformed

    private void BriefCase10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase10ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase10.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase10.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase10ActionPerformed

    private void BriefCase11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase11ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase11.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase11.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase11ActionPerformed

    private void BriefCase12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase12ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase12.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase12.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase12ActionPerformed

    private void BriefCase13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase13ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase13.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase13.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase13ActionPerformed

    private void BriefCase14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase14ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase14.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase14.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase14ActionPerformed

    private void BriefCase15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase15ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase15.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase15.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase15ActionPerformed

    private void BriefCase16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase16ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase16.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase16.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase16ActionPerformed

    private void BriefCase17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase17ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase17.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase17.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase17ActionPerformed

    private void BriefCase18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase18ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase18.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase18.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase18ActionPerformed

    private void BriefCase19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase19ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase19.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase19.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase19ActionPerformed

    private void BriefCase20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase20ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase20.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase20.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase20ActionPerformed

    private void BriefCase21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase21ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase21.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase21.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase21ActionPerformed

    private void BriefCase22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase22ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase22.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase22.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase22ActionPerformed

    private void BriefCase24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase24ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase24.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase24.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase24ActionPerformed

    private void BriefCase25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BriefCase25ActionPerformed
        // TODO add your handling code here:
        logic.getPrice(Integer.parseInt(evt.getActionCommand()));
        this.BriefCase25.setEnabled(false);
        Image img = null;
        try {
            img = ImageIO.read(getClass().getResource("picture/c2.png"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        BriefCase25.setIcon(new ImageIcon(img));
        getPrice(logic.getPrice(Integer.parseInt(evt.getActionCommand())));

    }//GEN-LAST:event_BriefCase25ActionPerformed
//This exit button will return to main form class.
    private void jBtExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtExitActionPerformed
        // TODO add your handling code here:
        DealOrNoDealMain main = new DealOrNoDealMain();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jBtExitActionPerformed
//When the form is opened, the label show the number of case.
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.NumCase.setText("Num of Case: " + this.Cases);
    }//GEN-LAST:event_formWindowOpened

    private void BriefCase3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BriefCase3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BriefCase3MouseClicked

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        DealOrNoDealMain main = new DealOrNoDealMain();
        main.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        Game dndGame = new Game();
        dndGame.setVisible(true);
        dndGame.setSize(800, 498);
        dndGame.setLocation(300, 200);
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        this.setVisible(false);
        HighScoreDnD highscoreform = new HighScoreDnD();
        highscoreform.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Game.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Game().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BriefCase1;
    private javax.swing.JButton BriefCase10;
    private javax.swing.JButton BriefCase11;
    private javax.swing.JButton BriefCase12;
    private javax.swing.JButton BriefCase13;
    private javax.swing.JButton BriefCase14;
    private javax.swing.JButton BriefCase15;
    private javax.swing.JButton BriefCase16;
    private javax.swing.JButton BriefCase17;
    private javax.swing.JButton BriefCase18;
    private javax.swing.JButton BriefCase19;
    private javax.swing.JButton BriefCase2;
    private javax.swing.JButton BriefCase20;
    private javax.swing.JButton BriefCase21;
    private javax.swing.JButton BriefCase22;
    private javax.swing.JButton BriefCase23;
    private javax.swing.JButton BriefCase24;
    private javax.swing.JButton BriefCase25;
    private javax.swing.JButton BriefCase3;
    private javax.swing.JButton BriefCase4;
    private javax.swing.JButton BriefCase5;
    private javax.swing.JButton BriefCase6;
    private javax.swing.JButton BriefCase7;
    private javax.swing.JButton BriefCase8;
    private javax.swing.JButton BriefCase9;
    private javax.swing.JLabel NotifyPlayer;
    private javax.swing.JLabel NumCase;
    private javax.swing.JLabel Price1;
    private javax.swing.JLabel Price10;
    private javax.swing.JLabel Price11;
    private javax.swing.JLabel Price12;
    private javax.swing.JLabel Price13;
    private javax.swing.JLabel Price14;
    private javax.swing.JLabel Price15;
    private javax.swing.JLabel Price16;
    private javax.swing.JLabel Price17;
    private javax.swing.JLabel Price18;
    private javax.swing.JLabel Price19;
    private javax.swing.JLabel Price2;
    private javax.swing.JLabel Price20;
    private javax.swing.JLabel Price21;
    private javax.swing.JLabel Price22;
    private javax.swing.JLabel Price23;
    private javax.swing.JLabel Price24;
    private javax.swing.JLabel Price25;
    private javax.swing.JLabel Price26;
    private javax.swing.JLabel Price3;
    private javax.swing.JLabel Price4;
    private javax.swing.JLabel Price5;
    private javax.swing.JLabel Price6;
    private javax.swing.JLabel Price7;
    private javax.swing.JLabel Price8;
    private javax.swing.JLabel Price9;
    private javax.swing.JButton jBtExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
