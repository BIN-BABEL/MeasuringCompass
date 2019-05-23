package ndori.measuringcompass.util;

import net.minecraft.util.math.BlockPos;

public class WorldCoordinate {
    private final BlockPos pos;
    private final int dimension;

    public WorldCoordinate(BlockPos pos, int dimension) {
        this.pos = pos;
        this.dimension = dimension;
    }

    public BlockPos getBlockPos() {
        return pos;
    }

    public String getOffsetsString(WorldCoordinate other) {
        if (this.dimension != other.dimension) return "";
        BlockPos p1 = this.pos;
        BlockPos p2 = other.pos;
        int dx = Math.abs(p1.getX() - p2.getX());
        int dy = Math.abs(p1.getY() - p2.getY());
        int dz = Math.abs(p1.getZ() - p2.getZ());
        return "X:" + dx + " Y:" + dy + " Z:" + dz;
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldCoordinate that = (WorldCoordinate) o;
        if (dimension != that.dimension) return false;
        if (pos != null) return pos.equals(that.pos);
        return that.pos == null;
    }

    @Override
    public int hashCode() {
        int result = pos != null ? pos.hashCode() : 0;
        result = 31 * result + dimension;
        return result;
    }

    /*public int getDimension() {
        return dimension;
    }

    public double getDistanced(WorldCoordinate other) { // Euclidean distance
        if (this.dimension == other.dimension) {
            return posToVec3d(this.pos).distanceTo(posToVec3d(other.pos));
        }
        return -1;
    }

    private Vec3d posToVec3d(BlockPos pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }*/
}
