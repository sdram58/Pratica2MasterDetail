package com.catata.pratica2masterdetail;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.catata.pratica2masterdetail.constantes.Constantes;
import com.catata.pratica2masterdetail.modelo.GestionLibros;
import com.catata.pratica2masterdetail.modelo.LibroItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        //Ponemos la foto de banlibros de fondo.
        toolbar.setBackground(getDrawable(R.drawable.banlibros));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.push_load), Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.cargar), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Cargamos los libros
                                GestionLibros.cargarLibros(ItemListActivity.this);

                                //Inicializamos el recyclerView
                                View recyclerView = findViewById(R.id.item_list);
                                assert recyclerView !=null;
                                setupRecyclerView((RecyclerView) recyclerView);
                            }
                        }).show();
            }
        });

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, GestionLibros.LIBROS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<LibroItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibroItem item = (LibroItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();

                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<LibroItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mTitulo.setText(mValues.get(position).title);
            holder.mAutor.setText(mValues.get(position).author);


            /*Conseguimos la imagen del libro a partir del string url_image*/

            /*
            String nombreImagen = mValues.get(position).url_image.split("\\.")[0]; //Sin extensión
            Context context = mParentActivity;

            int id = context.getResources().getIdentifier(
                    nombreImagen,
                    "drawable",
                    context.getPackageName()
            );

            holder.mPortada.setImageResource(id);

            */

            /*Lo cargamos con Glide*/
            Glide.with(mParentActivity)
                 .load(mValues.get(position).url_image)
                 .centerCrop()
                 .placeholder(R.drawable.loading)
                 .into(holder.mPortada);



            //Le pasamos en un Tag el LibroItem correspondiente.
            holder.mContenedor.setTag(mValues.get(position));
            //Hacemos que al pulsar la imagen maneje el listener
            holder.mContenedor.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mTitulo;
            final TextView mAutor;
            final ImageView mPortada;
            final LinearLayout mContenedor;

            ViewHolder(View view) {
                super(view);
                mAutor = (TextView) view.findViewById(R.id.tvAutor);
                mTitulo = (TextView) view.findViewById(R.id.tvTitulo);
                mPortada = (ImageView) view.findViewById(R.id.ivPortada);
                mContenedor = (LinearLayout) view.findViewById(R.id.llContenedor);
            }
        }
    }
}