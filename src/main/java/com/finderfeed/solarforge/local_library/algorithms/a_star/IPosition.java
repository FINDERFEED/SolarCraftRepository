package com.finderfeed.solarforge.local_library.algorithms.a_star;

public record IPosition(int x, int y, int z){

    public double distTo(IPosition another){
        double x1 = another.x - x;
        double y1 = another.y - y;
        double z1 = another.z - z;
        return Math.sqrt(x1*x1 + y1*y1 + z1*z1);
    }

    public IPosition add(IPosition another){
        return new IPosition(x + another.x,y + another.y,z + another.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPosition iPosition = (IPosition) o;
        return x == iPosition.x && y == iPosition.y && z == iPosition.z;
    }

    @Override
    public String toString() {
        return "{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
