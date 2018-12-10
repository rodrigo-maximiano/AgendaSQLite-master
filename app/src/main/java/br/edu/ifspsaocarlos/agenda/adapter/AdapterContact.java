package br.edu.ifspsaocarlos.agenda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ifspsaocarlos.agenda.model.Contact;
import br.edu.ifspsaocarlos.agenda.R;

import java.util.List;


public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    private Context context;
    private List<Contact> contacts;

    private static ItemClickListener clickListener;


    public AdapterContact(List<Contact> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_phone_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }


    public  class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView name;
        final ImageView imgFavorite;

        ContactViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            imgFavorite = (ImageView) view.findViewById(R.id.imgFavorite);
            view.setOnClickListener(this);
            imgFavorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Contact contact = contacts.get(getAdapterPosition());
            if(view == imgFavorite) {
                if (clickListener != null) {
                    clickListener.onFavoriteClick(contact);
                }
            } else {
                if (clickListener != null) {
                    clickListener.onItemClick(contact);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.name.setText(contact.getName());
        if (contact.isFavorite()){
            holder.imgFavorite.setImageResource(R.drawable.ic_star_blue_24dp);
        }else{
            holder.imgFavorite.setImageResource(R.drawable.ic_star_border_blue_24dp);
        }
    }

    public interface ItemClickListener {
        void onItemClick(Contact contact);
        void onFavoriteClick(Contact contact);
    }

}