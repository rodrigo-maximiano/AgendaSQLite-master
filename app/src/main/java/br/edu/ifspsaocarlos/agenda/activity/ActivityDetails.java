package br.edu.ifspsaocarlos.agenda.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.edu.ifspsaocarlos.agenda.data.ContactDAO;
import br.edu.ifspsaocarlos.agenda.model.Contact;
import br.edu.ifspsaocarlos.agenda.R;


public class ActivityDetails extends AppCompatActivity {
    private Contact contact;
    private ContactDAO contactDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("contato"))
        {
            this.contact = (Contact) getIntent().getSerializableExtra("contato");
            EditText nameText = findViewById(R.id.editTextName);
            nameText.setText(contact.getName());
            EditText foneText = findViewById(R.id.editTextPhone);
            foneText.setText(contact.getPhone());
            EditText fone2Text = findViewById(R.id.editTextPhoneOne);
            fone2Text.setText(contact.getPhoneOne());
            EditText emailText = findViewById(R.id.editTextEmail);
            emailText.setText(contact.getEmail());
            EditText birthDayText = findViewById(R.id.editTextBirthday);
            birthDayText.setText(contact.getBirthday());
            int pos = contact.getName().indexOf(" ");
            if (pos==-1)
                pos= contact.getName().length();
            setTitle(contact.getName().substring(0,pos));
        }
        contactDAO = new ContactDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu_, menu);
        if (!getIntent().hasExtra("contato"))
        {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                apagar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void apagar()
    {
        contactDAO.apagaContato(contact);
        Intent resultIntent = new Intent();
        setResult(3,resultIntent);
        finish();
    }

    private void salvar()
    {
        String name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();
        String fone2 = ((EditText) findViewById(R.id.editTextPhoneOne)).getText().toString();
        String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
        String birthday = ((EditText) findViewById(R.id.editTextBirthday)).getText().toString();

        if (contact ==null)
            contact = new Contact();

        contact.setName(name);
        contact.setPhone(fone);
        contact.setPhoneOne(fone2);
        contact.setEmail(email);
        contact.setBirthday(birthday);

        contactDAO.salvaContato(contact);
        Intent resultIntent = new Intent();
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}

