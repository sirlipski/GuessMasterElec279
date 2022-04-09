package com.example.guessmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

import java.util.Random;

public class GuessMaster extends AppCompatActivity {

    private TextView entityName;
    private TextView ticketsum;
    private Button guessButton;
    private EditText userIn;
    private Button btnclearContent;
    private String user_input;
    private ImageView entityImage;

    String answer;

    private int numOfEntities;
    private Entity[] entities;
    private int[] tickets;
    private int numOfTickets;
    //Stores Entity Name
    String entName;
    int entityid = 0;
    int currentTicketWon = 0;

    Country usa= new Country("United States", new Date("July", 4, 1776), "Washingston DC", 0.1);
    Person myCreator= new Person("myCreator", new Date("May", 6, 1800), "Male",1);
    Politician trudeau = new Politician("Justin Trudeau", new Date("December",25,1971),"Male", "Liberal", 0.25);
    Singer dion= new Singer("Celine Dion", new Date("March", 30, 1961), "Female","La voix du bon Dien", new Date("November",6,1981),0.5);
    final GuessMaster gm = new GuessMaster();


        public GuessMaster() {
            numOfEntities = 0;
            entities = new Entity[10];
        }

        public void addEntity(Entity entity) {
//		entities[numOfEntities++] = new Entity(entity);
//		entities[numOfEntities++] = entity;//////
            entities[numOfEntities++] = entity.clone();
        }

        public void playGame(int entityId) {
            Entity entity = entities[entityId];
            playGame(entity);
        }

        public void playGame(Entity entity) {
            System.out.println("***************************");////
            System.out.printf(entity.welcomeMessage());////
            if(entity.getName() != "Myself")
                System.out.printf("\n\nGuess %s's birthday\n", entity.getName());
            else
                System.out.printf("\n\nGuess my birthday\n", entity.getName());
            System.out.println("(mm/dd/yyyy)");


                answer = answer.replace("\n", "").replace("\r", "");

                if (answer.equals("quit")) {
                    System.exit(0);
                }

                Date date = new Date(answer);
//			System.out.println("you guess is: " + date);

                if (date.precedes(entity.getBorn())) {
                    System.out.println("Incorrect. Try a later date.");
                } else if (entity.getBorn().precedes(date)) {
                    System.out.println("Incorrect. Try an earlier date.");
                } else {
                    Random random = new Random();
                    int rd = random.nextInt(50);
                    rd += 50;
//				for(int i=0; i<=50*100; i++)
//					System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$\n");
                    System.out.println("*************Bingo!***************");
//				System.out.printf("***Your bonus is %d$****\n", rd*100);
                    System.out.printf("You won %d tickets in this round.\n", entity.getAwardedTicketNumber());
                    currentTicketWon += entity.getAwardedTicketNumber();
                    System.out.printf("The total number of your tickets is %d.\n", currentTicketWon);
                    System.out.printf("**********************************\n", rd * 100);
                    System.out.printf(entity.closingMessage());////
                }
        }

        public void playGame() {
                int entityId = genRandomEntityId();
                playGame(entityId);
        }

        public int genRandomEntityId() {
            Random randomNumber = new Random();
            return randomNumber.nextInt(numOfEntities);
        }

        public void changeEntity(){
            userIn.setText("");
            entityid = genRandomEntityId();
        }

        // TODO Change to 'equals' method
        public void ImageSetter(){
            String imageName;
            switch(entities[entityid].getName()) {
                case "United States":
                    imageName = "usaflag.gif";
                    break;
                case "Celine Dion":
                    imageName = "celidion.jpg";
                    break;
                case "Justin Trudeau":
                    imageName = "justint.jpg";
                    break;
                default:
                    imageName = "creator.jpg";
            }
            entityImage.setImageURI(Uri.parse(imageName));  //TODO Fix image string
        }
        

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_activity);

        //Button for date submission
        guessButton = (Button) findViewById(R.id.btnGuess);
        //Button for input date erase
        btnclearContent = (Button) findViewById(R.id.btnClear);
        //EditText for user input
        userIn = (EditText) findViewById(R.id.guessInput);
        //TextView for total tickets
        ticketsum = (TextView) findViewById(R.id.ticket);
        //TextView for entity name
        entityName = (TextView) findViewById(R.id.entityName);
        //ImageView for entity image
        entityImage = (ImageView) findViewById(R.id.entityImage);

        // OnClick Listener action for clear button
        btnclearContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEntity();
            }
        });

        // OnClick Listener action for submit button
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Playing game
                playGame();
            }
        });
    }
}