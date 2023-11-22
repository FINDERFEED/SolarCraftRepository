package com.finderfeed.solarcraft.local_library.client.screens;

public abstract class DefaultScreenWithPages extends DefaultScreen{


    protected int currentPage = 0;

    public DefaultScreenWithPages(){

    }



    public abstract int getPagesCount();

    public void nextPage(){
        if (currentPage + 1 < this.getPagesCount()){
            currentPage++;
            this.onPageChanged(currentPage);
        }
    }

    public void previousPage(){
        if (currentPage > 0){
            currentPage--;
            this.onPageChanged(currentPage);
        }
    }


    public abstract void onPageChanged(int newPage);
}
