package com.finderfeed.solarcraft.local_library.algorithms.a_star;

public record DPosition(double x, double y, double z){

    public double distTo(DPosition another){
        double x1 = another.x - x;
        double y1 = another.y - y;
        double z1 = another.z - z;
        return Math.sqrt(x1*x1 + y1*y1 + z1*z1);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DPosition dPosition = (DPosition) o;
        return x == dPosition.x && y == dPosition.y && z == dPosition.z;
    }
    @Override
    public String toString() {
        return "{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
