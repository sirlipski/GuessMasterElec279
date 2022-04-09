package com.example.guessmaster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    Country usa = new Country("United States", new Date("July", 4, 1776), "Washingston DC", 0.1);
    Person myCreator = new Person("myCreator", new Date("May", 6, 1800), "Male", 1);
    Politician trudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);
    Singer dion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dien", new Date("November", 6, 1981), 0.5);

    private int numOfEntities;
    private Entity[] entities;
    //Stores Entity Name
    String entName;
    int entityid = 0;
    int currentTicketWon = 0;


    public void addEntity(Entity entity) {  // TODO Replace this function to mine
        //Increases for obvious reasons.
        this.numOfEntities++;
        //A new array is created with an additional index.
        Entity[] array = new Entity[this.numOfEntities];
        //All values are copied into the new array.
        for (int i = 0; i < array.length - 1; i++) {
            array[i] = this.entities[i];
        }
        //
        this.entities = array;
        //New entity is inserted at the end of the entities array.
        this.entities[numOfEntities - 1] = entity;
    }

    public void playGame(int entityId) {
        playGame(entities[entityId]);
    }

    public void playGame() {
        playGame(entities[entityid]);
    }

    public void playGame(Entity entity) {
        //try {

            // Name of the entity to be guessed in the entityName textview
            entityName.setText(entity.getName());

            // Get input from the EditText
            answer = userIn.getText().toString();
            answer = answer.replace("\n", "").replace("\r", "");

            if (answer.equals("quit")) {
                System.exit(0);
            }

            Date date = new Date(answer);

            if (date.precedes(entity.getBorn())) {
                //System.out.println("Incorrect. Try a later date.");
                AlertDialog.Builder earlyAlert = new AlertDialog.Builder(GuessMaster.this);
                earlyAlert.setTitle("Incorrect");
                earlyAlert.setMessage("Try a later date.");
                earlyAlert.setCancelable(false);

                earlyAlert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {   //  TODO Not sure what to do with this one
                        //Toast.makeText(getBaseContext(), "Game is Starting... \nEnjoy", Toast.LENGTH_SHORT).show();
                    }
                });

                // Show Dialog
                AlertDialog dialog = earlyAlert.create();
                dialog.show();
            } else if (entity.getBorn().precedes(date)) {
                AlertDialog.Builder lateAlert = new AlertDialog.Builder(GuessMaster.this);
                lateAlert.setTitle("Incorrect");
                lateAlert.setMessage("Try an earlier date.");
                lateAlert.setCancelable(false);

                lateAlert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {   //  TODO Not sure what to do with this one
                        //Toast.makeText(getBaseContext(), "Game is Starting... \nEnjoy", Toast.LENGTH_SHORT).show();
                    }
                });

                // Show Dialog
                AlertDialog dialog = lateAlert.create();
                dialog.show();
            } else {

                currentTicketWon += entity.getAwardedTicketNumber();

                // Update total ticket number
                ticketsum.setText("Tickets: " + currentTicketWon);


                AlertDialog.Builder winAlert = new AlertDialog.Builder(GuessMaster.this);
                winAlert.setTitle("You Won");
                winAlert.setMessage("BINGO! " + entity.closingMessage());
                winAlert.setCancelable(false);

                winAlert.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {   //  TODO Not sure what to do with this one
                        Toast.makeText(getBaseContext(), entity.getAwardedTicketNumber() + " more tickets!", Toast.LENGTH_SHORT).show();
                        ContinueGame();
                    }
                });

                // Show Dialog
                AlertDialog dialog = winAlert.create();
                dialog.show();

            }

        /**
        } catch (Exception e) {
            AlertDialog.Builder error = new AlertDialog.Builder(GuessMaster.this);

            error.setTitle("ERROR");
            error.setMessage("Please input answer in the format: \"MM/DD/YYYY\"");
            error.setCancelable(false);
            error.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }

            });

            AlertDialog dialog = error.create();
            dialog.show();
        }*/
    }

        // Continue Game Method     //TODO Add more comments
        public void ContinueGame(){
            entityid = genRandomEntityId();
            Entity entity = entities[entityid];

            entName = entity.getName();

            // Call the ImageSetter method
            ImageSetter();

            //Print the name of the entity to be guessed in the entityName TextView
            entityName.setText(entName);

            //Clear previous entry
            userIn.getText().clear();


        }

        public int genRandomEntityId() {
            Random randomNumber = new Random();
            return randomNumber.nextInt(numOfEntities);
        }

        public Entity changeEntity(){
            userIn.getText().clear();
            entityid = genRandomEntityId();
            Entity entity = entities[entityid];
            entName = entity.getName();
            entityName.setText(entity.getName());
            ImageSetter();
            ticketsum.setText("Tickets: " + currentTicketWon);
            return entity;
        }

        // TODO Change to 'equals' method
        public void ImageSetter(){
            if(entName.equals("United States"))
                    entityImage.setImageResource(R.drawable.usaflag);
            else if(entName.equals("Celine Dion"))
                    entityImage.setImageResource(R.drawable.celidion);
            else if(entName.equals("Justin Trudeau"))
                    entityImage.setImageResource(R.drawable.justint);
            else
                    entityImage.setImageResource(R.drawable.creator);

        }

        // Welcome alert
        public void welcomeToGame(Entity entity){
            AlertDialog.Builder welcomeAlert = new AlertDialog.Builder(GuessMaster.this);
            welcomeAlert.setTitle("GuessMaster Game V3");
            welcomeAlert.setMessage(entity.welcomeMessage());
            welcomeAlert.setCancelable(false);

            welcomeAlert.setNegativeButton("START_GAME", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    Toast.makeText(getBaseContext(), "Game is Starting... \nEnjoy", Toast.LENGTH_SHORT).show();
                }
            });

            // Show Dialog

            AlertDialog dialog = welcomeAlert.create();
            dialog.show();
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

        this.addEntity(usa);
        this.addEntity(dion);
        this.addEntity(trudeau);
        this.addEntity(myCreator);

        changeEntity();

        // OnClick Listener action for clear button
        btnclearContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEntity();
            }
        });

        // OnClick Listener action for guess button
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Playing game
                playGame();
            }
        });
    }
}