import java.net.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Server extends JFrame
{
    ServerSocket server;
    Socket socket;
    Runnable r1,r2;

    BufferedReader br;
    PrintWriter out;

    // Declare Components
    private JLabel heading = new JLabel("Server Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new FontUIResource("Times New Roman", Font.PLAIN, 20);

    //Constrctor
    public Server()
    {
       try
       {
            server = new ServerSocket(7777); 
            System.out.println("Server is ready to accept connection");
            System.out.println("Waiting...");
            socket = server.accept();   
            
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();
            startReading();
            // startWriting();
        }
       catch(Exception e)
       {
        e.printStackTrace();;
       }
    }

    private void createGUI()
    {
        //GUI code
        this.setTitle("Server Messanger");
        this.setSize(650, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Coding for component
        heading.setFont(font);
        messageInput.setFont(font);
        messageArea.setFont(font);

        heading.setIcon(new ImageIcon("clogo.png"));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.LEFT);

        //Setting frame layout
        this.setLayout(new BorderLayout());

        //Adding the component to frame
        this.add(heading, BorderLayout.NORTH);
        JScrollPane jScrollPane = new JScrollPane(messageArea);
        this.add(jScrollPane, BorderLayout.CENTER);
        this.add(messageInput, BorderLayout.SOUTH);

        this.setVisible(true);

    }

    private void handleEvents()
    {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                // System.out.println(e.getKeyCode());
                if(e.getKeyCode() == 10)
                {
                    // System.out.println("you have pressed enter button");
                    String contentToString = messageInput.getText();
                    messageArea.append("Me : " + contentToString + "\n");
                    out.println(contentToString);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }
           
            
        });
    }

    // Start Reading Method
    public void startReading()
    {
       //thread - Keeps reading the data and providing it 
       Runnable r1 = () ->
       {
            System.out.println("Reader started..");
            try
            {
                while(!socket.isClosed())
                {
                    
                        String msg = br.readLine();
                        if(msg.equals("exit"))
                        {
                            System.out.println("Client terminated the chat");
                            JOptionPane.showMessageDialog(this, "Client terminated the chat");
                            socket.close();
                            // break;
                            System.exit(0);
                        }
                        // System.out.println("Client : " + msg);
                        messageArea.append("Client : " + msg);
                    }

                    // System.out.println("Client : " + msg);
            }
            
            catch(Exception e)
            {
                    // e.printStackTrace();
                System.out.println("Closed");
            }
            
       };
       new Thread(r1).start();
    }

    // Start Writing Method
    public void startWriting()
    {
        //thrad - Takes data from the user and then sends it to the client.
        Runnable r2 = () ->{
            System.out.println("Writer started...");
            try 
            {
                while(!socket.isClosed())
                {
                    
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                        String content = br1.readLine();

                        out.println(content);
                        out.flush();
                        if(content.equals("exit"))
                        {
                            socket.close();
                            break;
                        }
                }
            }
                 catch (Exception e) {
                    // TODO: handle exception
                    // e.printStackTrace();
                    System.out.println("CLosed");
                }
        };
        new Thread(r2).start();
    }

    
    
        public static void main(String[] args) {
        System.out.println("Server started...");
        
        new Server();
    }
}