package com.webproject.goutham.pizzaorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int PIZZA_PRICE = 5;
    final int ONION_PRICE = 1;
    final int CHICKEN_PRICE = 2;
    final int OLIVE_PRICE = 1;
    int quantity = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check if whipped cream is selected
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checked);
        boolean hasWhippedCream = whippedCream.isChecked();

        // check if chocolate is selected
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checked);
        boolean hasChocolate = chocolate.isChecked();

        // check if OLives cream is selected
        CheckBox olives = (CheckBox) findViewById(R.id.olives_checked);
        boolean hasOlives = olives.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasWhippedCream, hasChocolate,hasOlives);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasWhippedCream, hasChocolate, hasOlives,totalPrice);
        Intent redirect = new Intent(HomePage.this, SummaryPage.class);
        redirect.putExtra("summary", orderSummaryMessage);
        startActivity(redirect);
        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents

    }

    public void sendEmail(View view) {
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        String userInputName = userInputNameView.getText().toString();

        // check if whipped cream is selected
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_checked);
        boolean hasWhippedCream = whippedCream.isChecked();

        // check if chocolate is selected
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checked);
        boolean hasChocolate = chocolate.isChecked();

        CheckBox olives = (CheckBox) findViewById(R.id.olives_checked);
        boolean hasOlives = olives.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasWhippedCream, hasChocolate,hasOlives);

        // create and store the order summary
        String orderSummaryMessage = createOrderSummary(userInputName, hasWhippedCream, hasChocolate, hasOlives,totalPrice);
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"goutham946@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT   , orderSummaryMessage);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HomePage.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasWhippedCream, boolean hasChocolate,boolean hasOlives, float price) {
        String orderSummaryMessage = "Customer Name : "+userInputName + "\n" +
                "Topping's added to your pizza"+"\n"+
                "ONION's - "+boolToString(hasWhippedCream) + "\n" +
                "CHICKEN - "+boolToString(hasChocolate) + "\n" +
                "OLIVE's - "+boolToString(hasOlives) + "\n" +
                "Total Number of PIZZA's : "+quantity + "\n" +
                "Total : $ "+ price + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasWhippedCream, boolean hasChocolate,boolean hasOlives) {
        int basePrice = PIZZA_PRICE;
        if (hasWhippedCream) {
            basePrice += ONION_PRICE;
        }
        if (hasChocolate) {
            basePrice += CHICKEN_PRICE;
        }
        if (hasOlives) {
            basePrice += OLIVE_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of pizza  by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 10) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "You can order only 10 pizzas at once ");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of toppings cups by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select at least one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}
