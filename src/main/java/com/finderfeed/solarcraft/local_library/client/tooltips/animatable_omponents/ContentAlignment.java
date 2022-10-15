package com.finderfeed.solarcraft.local_library.client.tooltips.animatable_omponents;

public enum ContentAlignment {
    NO_ALIGNMENT((c,x,y)->new int[]{x,y}),
    CENTER((c,x,y)->new int[]{x + c.getXSize()/2,y + c.getYSize()/2}),
    CENTER_X((c,x,y)->new int[]{x + c.getXSize()/2,y}),
    CENTER_Y((c,x,y)->new int[]{x,y + c.getYSize()/2});


    private AlignmentFunction toXYCoordsFunction;

    ContentAlignment(AlignmentFunction toXY){
        this.toXYCoordsFunction = toXY;
    }

    public int[] getCoords(BaseComponent component,int x,int y){
        return this.toXYCoordsFunction.getCoords(component,x,y);
    }

    @FunctionalInterface
    interface AlignmentFunction{

        int[] getCoords(BaseComponent c,int x,int y);

    }
}
