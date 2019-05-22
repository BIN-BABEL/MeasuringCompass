Measuring Compass
=
Adds functionality to the compass, which allows you to make measurements and visualize them. This is done by selecting two "corner" blocks in the world; the mod will then render the outlines of the cuboid (or a shaded cuboid). There will be floating numbers at the middle of the edges which indicate the length of that edge.

You can specify a different item through the config file `measuringcompass.cfg`.

This is a client-side mod for Minecraft 1.12.2. You can use this mod on servers that do not have this mod (it doesn't do anything on a server anyway).

#### Usage
With the compass in hand, right-click on a block to select the first corner. It will now be highlighted, and the offset distance to that block from the player will be shown near the center of the screen. This can be useful, for example, for making measurements when building foundations for a long wall. Right-click on another block to set it as the second corner, and you should see the box.

Right-click in the air to bring up the GUI. Here you can set values (0-255) for red, green, blue, and alpha (opacity). There are a few presets for convenience. `Delete All` will delete all boxes, and `Delete Previous` will delete the most recent box. While holding the compass, you can also **right-click while sneaking** to delete the most recent box.

Boxes are cleared whenever you join a world.
