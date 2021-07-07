package com.example.shoppingcompanionv3;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Extracts the data from Images_View in the form of an Upload items list
public class Image_Adapter extends RecyclerView.Adapter<Image_Adapter.ImageViewHolder>
{
    private Context mContext; // Save the context
    private List<Upload> mUploads; // The list of single upload items
    private OnItemClickListener mListener; // For managing clicking on folders

    public Image_Adapter(Context context, List<Upload> uploads)
    {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v); // Occupy the imageViewHolder with the image_Items for each upload
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position)
    {
        // Get data from Upload items into holder
        Upload currentUpload = mUploads.get(position);
        holder.textViewName.setText(currentUpload.getName());
        // Picasso loads images from the url, centerCrop() crops the images neatly
        Picasso.get().load(currentUpload.getImageUrl()).placeholder(R.mipmap.ic_launcher) // Mipmap creates default placeholder image while real images load
        .fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount()
    {
        return mUploads.size(); // Show as many uploads as we have in uploads list
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
          View.OnCreateContextMenuListener, // Due to creating context window
            MenuItem.OnMenuItemClickListener
    {
        public TextView textViewName;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this); // Create context menu
        }

        @Override
        // If clicking on NORMAL ITEM
        public void onClick(View v) // Allows handling click events on image_items view
        {
            if (mListener != null) // Null check in case a listener wasn't set
            {
                int position = getAdapterPosition(); // Get position of clicked item

                if (position != RecyclerView.NO_POSITION)
                {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
        {
            contextMenu.setHeaderTitle("-Options-");
            MenuItem doWhatever = contextMenu.add(Menu.NONE, 1, 1, "Statistics");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "Delete");

            // Handle clicks
            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }

        @Override
        // If clicking on MENU ITEM
        public boolean onMenuItemClick(MenuItem menuItem)
        {
            if (mListener != null)
            {
                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION)
                {
                   switch (menuItem.getItemId())
                   {
                       // Check which item in menu was clicked
                       case 1:
                           mListener.onWhatEverClick(position);
                           return true;
                       case 2:
                           mListener.onDeleteClick(position);
                           return true;
                   }
                }
            }
            return false;
        }
    }

    // Create interface that manages clicks
    public interface OnItemClickListener
    {
        void onItemClick(int position); // Default clicks

        void onWhatEverClick(int position); // Click on other menu item

        void onDeleteClick(int position); // Removing items
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener; // Set listener to the one specially created FOR THIS INTERFACE
    }
}
