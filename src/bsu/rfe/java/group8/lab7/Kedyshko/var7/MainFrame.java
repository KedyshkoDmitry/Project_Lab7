package bsu.rfe.java.group8.lab7.Kedyshko.var7;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MainFrame extends JFrame {

    private static final String FRAME_TITLE = "Клиент мгновенных сообщений";

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final JTextPane textAreaIncoming;
    private final JTextArea textAreaOutgoing;

    //private static final int INCOMING_AREA_DEFAULT_ROWS = 10;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;

    //private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;

    private JLabel textFieldFrom;
    private final JTextField textFieldTo;

    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final int LARGE_GAP = 15;

    private InstantMessenger instantMessenger;
    private Peer peer;
    private boolean isLog = false;

    public MainFrame() {
        super(FRAME_TITLE);
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH) / 2, (kit.getScreenSize().height - HEIGHT) / 2);

        textAreaIncoming = new JTextPane();
        textAreaIncoming.addMouseMotionListener(new TextMotionListener());
        textAreaIncoming.setEditable(false);

        final JScrollPane scrollPaneIncoming = new JScrollPane(textAreaIncoming);

        final JLabel labelFrom = new JLabel("Подпись:");
        final JLabel labelTo = new JLabel("Получатель");

        textFieldTo = new JTextField(TO_FIELD_DEFAULT_COLUMNS);

        textAreaOutgoing = new JTextArea(OUTGOING_AREA_DEFAULT_ROWS, 0);
        final JScrollPane scrollPaneOutgoing = new JScrollPane(textAreaOutgoing);

        final JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createTitledBorder("Сообщение"));

        final JButton sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                peer = new Peer();
                peer.setPeer(textFieldFrom.getText(), textFieldTo.getText());
                instantMessenger.sendMessage(peer);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu chatMenu = new JMenu("Меню");

        instantMessenger = new InstantMessenger(this);

        Action logInAction = new AbstractAction("Вход") {

            public void actionPerformed(ActionEvent e) {
                if (isLog == false) {
                    isLog = true;
                    chatMenu.setVisible(false);
                }
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите имя для общения", "Вход", JOptionPane.QUESTION_MESSAGE);
                instantMessenger.setSender(value);
                textFieldFrom = new JLabel(instantMessenger.getPort().toString());
                Font f2 = new Font(Font.SANS_SERIF, Font.BOLD, 10);
                textFieldFrom.setFont(f2);
                final GroupLayout layout1 = new GroupLayout(getContentPane());
                setLayout(layout1);
                layout1.setHorizontalGroup(layout1.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout1.createParallelGroup()
                                .addComponent(scrollPaneIncoming)
                                .addComponent(messagePanel))
                        .addContainerGap());
                layout1.setVerticalGroup(layout1.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scrollPaneIncoming)
                        .addGap(MEDIUM_GAP)
                        .addComponent(messagePanel)
                        .addContainerGap());

                final GroupLayout layout = new GroupLayout(messagePanel);
                messagePanel.setLayout(layout);

                layout.setHorizontalGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelFrom)
                                        .addGap(SMALL_GAP)
                                        .addComponent(textFieldFrom)
                                        .addGap(LARGE_GAP)
                                        .addComponent(labelTo)
                                        .addGap(SMALL_GAP)
                                        .addComponent(textFieldTo))
                                .addComponent(scrollPaneOutgoing)
                                .addComponent(sendButton))
                        .addContainerGap());
                layout.setVerticalGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(labelFrom)
                                .addComponent(textFieldFrom)
                                .addComponent(labelTo)
                                .addComponent(textFieldTo))
                        .addGap(MEDIUM_GAP)
                        .addComponent(scrollPaneOutgoing)
                        .addGap(MEDIUM_GAP)
                        .addComponent(sendButton)
                        .addContainerGap());
            }
        };
        menuBar.add(chatMenu);
        chatMenu.add(logInAction);


    }

    private static boolean available(int port_, String address) {
        try (Socket ignored = new Socket(address, port_)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    private class TextMotionListener extends MouseInputAdapter {
        StyledDocument doc = textAreaIncoming.getStyledDocument();
        public void mouseMoved(MouseEvent e) {

            Element elem = doc.getCharacterElement(textAreaIncoming.viewToModel(e.getPoint()));
            AttributeSet as = elem.getAttributes();
            if (StyleConstants.isUnderline(as))
                textAreaIncoming.setCursor(new Cursor(Cursor.HAND_CURSOR));
            else
                textAreaIncoming.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }


    public JTextArea getTextAreaOutgoing() {
        return textAreaOutgoing;
    }

    public JTextPane getTextAreaIncoming() {
        return textAreaIncoming;
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}