package ndori.measuringcompass.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WorldCoordinate {
    private final BlockPos coordinate;
    private final int dimension;

    public WorldCoordinate(BlockPos coordinate, int dimension) {
        this.coordinate = coordinate;
        this.dimension = dimension;
    }

    public BlockPos getCoordinate() {
        return coordinate;
    }

    public int getDimension() {
        return dimension;
    }

    public String getCoordDistanceString(WorldCoordinate other) {
        if (this.dimension != other.dimension) {
            return "";
        }
        BlockPos p1 = this.coordinate;
        BlockPos p2 = other.coordinate;
        int dx = Math.abs(p1.getX() - p2.getX());
        int dy = Math.abs(p1.getY() - p2.getY());
        int dz = Math.abs(p1.getZ() - p2.getZ());
        return "X:" + dx + " Y:" + dy + " Z:" + dz;
    }

    public double getDistance(WorldCoordinate other) { // Euclidean distance
        if (this.dimension == other.dimension) {
            return vec3dCoord(this.coordinate).distanceTo(vec3dCoord(other.coordinate));
        }
        return -1;
    }

    private Vec3d vec3dCoord(BlockPos pos) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorldCoordinate that = (WorldCoordinate) o;
        if (dimension != that.dimension) return false;
        if (coordinate != null) return coordinate.equals(that.coordinate);
        return that.coordinate == null;
    }

    @Override
    public int hashCode() {
        int result = coordinate != null ? coordinate.hashCode() : 0;
        result = 31 * result + dimension;
        return result;
    }
}
