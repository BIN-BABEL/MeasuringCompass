package ndori.measuringcompass.util;

import ndori.measuringcompass.client.ClientInfo;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class BoundingBox extends AxisAlignedBB {

    public int r;
    public int g;
    public int b;
    public int a;

    public int dimension;

    private int lengthX;
    private int lengthY;
    private int lengthZ;

    public BoundingBox(BlockPos pos) {
        super(pos);

        this.a = 255;
        this.r = 255;
        this.g = 255;
        this.b = 255;
        this.dimension = 0;

        this.lengthX = 1;
        this.lengthY = 1;
        this.lengthZ = 1;
    }

    public BoundingBox(BlockPos pos1, BlockPos pos2) {
        super(pos1, pos2);

        this.a = 255;
        this.r = 255;
        this.g = 255;
        this.b = 255;
        this.dimension = 0;

        this.lengthX = Math.abs(pos1.getX() - pos2.getX()) + 1;
        this.lengthY = Math.abs(pos1.getY() - pos2.getY()) + 1;
        this.lengthZ = Math.abs(pos1.getZ() - pos2.getZ()) + 1;
    }

    public BoundingBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(x1, y1, z1, x2, y2, z2);
        this.a = 255;
        this.r = 255;
        this.g = 255;
        this.b = 255;
        this.dimension = 0;
        this.lengthX = (int) Math.abs(x1 - x2);
        this.lengthY = (int) Math.abs(y1 - y2);
        this.lengthZ = (int) Math.abs(z1 - z2);
    }

    public Vec3d[] nearestEdges() {
        RenderManager renderManager = ClientInfo.mc.getRenderManager();
        double x1 = this.minX;
        double x2 = this.maxX;
        double y1 = this.minY;
        double y2 = this.maxY;
        double z1 = this.minZ;
        double z2 = this.maxZ;

        double mx = (x1 + x2) / 2;
        double my = (y1 + y2) / 2;
        double mz = (z1 + z2) / 2;

        Vec3d camera = new Vec3d(renderManager.viewerPosX, renderManager.viewerPosY, renderManager.viewerPosZ);
        Vec3d minX = null;
        Vec3d minY = null;
        Vec3d minZ = null;

        Vec3d[] vArr = new Vec3d[]{new Vec3d(mx, y1, z1), new Vec3d(mx, y1, z2), new Vec3d(mx, y2, z1), new Vec3d(mx, y2, z2)};
        for (Vec3d v : vArr) {
            if (minX == null || camera.distanceTo(minX) > camera.distanceTo(v)) minX = v;
        }

        vArr = new Vec3d[]{new Vec3d(x1, my, z1), new Vec3d(x2, my, z1), new Vec3d(x2, my, z2), new Vec3d(x1, my, z2)};
        for (Vec3d v : vArr) {
            if (minY == null || camera.distanceTo(minY) > camera.distanceTo(v)) minY = v;
        }

        vArr = new Vec3d[]{new Vec3d(x1, y1, mz), new Vec3d(x1, y2, mz), new Vec3d(x2, y2, mz), new Vec3d(x2, y1, mz)};
        for (Vec3d v : vArr) {
            if (minZ == null || camera.distanceTo(minZ) > camera.distanceTo(v)) minZ = v;
        }

        return new Vec3d[]{minX, minY, minZ};
    }

    public BoundingBox expand(double x, double y, double z) {
        double d0 = this.minX;
        double d1 = this.minY;
        double d2 = this.minZ;
        double d3 = this.maxX;
        double d4 = this.maxY;
        double d5 = this.maxZ;

        if (x < 0.0D) {
            d0 += x;
        } else if (x > 0.0D) {
            d3 += x;
        }

        if (y < 0.0D) {
            d1 += y;
        } else if (y > 0.0D) {
            d4 += y;
        }

        if (z < 0.0D) {
            d2 += z;
        } else if (z > 0.0D) {
            d5 += z;
        }

        return new BoundingBox(d0, d1, d2, d3, d4, d5);
    }

    public BoundingBox setDimension(int dimension) {
        this.dimension = dimension;
        return this;
    }

    public int getLengthX() {
        return lengthX;
    }

    public int getLengthY() {
        return lengthY;
    }

    public int getLengthZ() {
        return lengthZ;
    }

    public int getVolume() {
        return lengthX * lengthY * lengthZ;
    }

    public int getColorInt() {
        return (r << 16) + (g << 8) + b + (a << 24);
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (!(that instanceof BoundingBox)) {
            return false;
        } else {
            BoundingBox box = (BoundingBox)that;

            if (Double.compare(box.minX, this.minX) != 0) {
                return false;
            } else if (Double.compare(box.minY, this.minY) != 0) {
                return false;
            } else if (Double.compare(box.minZ, this.minZ) != 0) {
                return false;
            } else if (Double.compare(box.maxX, this.maxX) != 0) {
                return false;
            } else if (Double.compare(box.maxY, this.maxY) != 0) {
                return false;
            } else {
                return Double.compare(box.maxZ, this.maxZ) == 0;
            }
        }
    }
}
