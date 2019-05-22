package ndori.measuringcompass.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class BoundingBox extends AxisAlignedBB {

    public int r;
    public int g;
    public int b;
    public int a;

    public int dim;

    private int xLen;
    private int yLen;
    private int zLen;

    public BoundingBox(BlockPos pos) {
        super(pos);

        this.r = 255;
        this.g = 255;
        this.b = 255;
        this.a = 255;
        this.dim = 0;

        this.xLen = pos.getX() + 1;
        this.yLen = pos.getY() + 1;
        this.zLen = pos.getZ() + 1;
    }

    public BoundingBox(BlockPos pos1, BlockPos pos2) {
        super(pos1, pos2);

        this.r = 255;
        this.g = 255;
        this.b = 255;
        this.a = 255;
        this.dim = 0;

        this.xLen = Math.abs(pos1.getX() - pos2.getX()) + 1;
        this.yLen = Math.abs(pos1.getY() - pos2.getY()) + 1;
        this.zLen = Math.abs(pos1.getZ() - pos2.getZ()) + 1;
    }

    public BoundingBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(x1, y1, z1, x2, y2, z2);
        this.r = 255;
        this.g = 255;
        this.b = 255;
        this.a = 255;
        this.dim = 0;
        this.xLen = (int) Math.abs(x1 - x2);
        this.yLen = (int) Math.abs(y1 - y2);
        this.zLen = (int) Math.abs(z1 - z2);
    }

    public BoundingBox expand(double x, double y, double z) {
        double d0 = this.minX;
        double d1 = this.minY;
        double d2 = this.minZ;
        double d3 = this.maxX;
        double d4 = this.maxY;
        double d5 = this.maxZ;

        if (x < 0.0D)
        {
            d0 += x;
        }
        else if (x > 0.0D)
        {
            d3 += x;
        }

        if (y < 0.0D)
        {
            d1 += y;
        }
        else if (y > 0.0D)
        {
            d4 += y;
        }

        if (z < 0.0D)
        {
            d2 += z;
        }
        else if (z > 0.0D)
        {
            d5 += z;
        }

        return new BoundingBox(d0, d1, d2, d3, d4, d5);
    }

    public BoundingBox setDim(int dim) {
        this.dim = dim;
        return this;
    }

    public int getxLen() {
        return xLen;
    }

    public int getyLen() {
        return yLen;
    }

    public int getzLen() {
        return zLen;
    }

    public int getVolume() {
        return xLen * yLen * zLen;
    }

    public int getColorInt() {
        return (r << 16) + (g << 8) + b + (a << 24);
    }

    public boolean equals(Object that)
    {
        if (this == that)
        {
            return true;
        }
        else if (!(that instanceof BoundingBox))
        {
            return false;
        }
        else
        {
            BoundingBox box = (BoundingBox)that;

            if (Double.compare(box.minX, this.minX) != 0)
            {
                return false;
            }
            else if (Double.compare(box.minY, this.minY) != 0)
            {
                return false;
            }
            else if (Double.compare(box.minZ, this.minZ) != 0)
            {
                return false;
            }
            else if (Double.compare(box.maxX, this.maxX) != 0)
            {
                return false;
            }
            else if (Double.compare(box.maxY, this.maxY) != 0)
            {
                return false;
            }
            else
            {
                return Double.compare(box.maxZ, this.maxZ) == 0;
            }
        }
    }
}
