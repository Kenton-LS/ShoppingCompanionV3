package com.example.shoppingcompanionv3;

import com.google.firebase.database.Exclude;

//Mother class
public class Upload
{
    private String mName;
    private String mImageUrl;
    private String mKey; // For deleting from FireBase

    // Make work with Firebase
    public Upload()
    {
        // Empty constructor for FireBase - do not delete
    }

    // Take name and image url when creating items
    public Upload(String name, String imageUrl)
    {
        // If there is no name typed by accident
        if (name.trim().equals(""))
        {
            name = "No name";
        }

        // Set values
        mName = name;
        mImageUrl = imageUrl;
    }

    // Getters and Setters
    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public String getImageUrl()
    {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey()
    {
        return mKey;
    }

    @Exclude
    public void setKey(String key)
    {
        mKey = key;
    }
}
