# Chinese-Painting-Restoration

There are three branches

# Brighten-Whole
* this brightens the entire painting by a factor of .10 for non-black color(line 63) and by a factor of .01 for darker colors(line 69), to brighten tap on image


# Selector-Black-excluder
* When clicking on a color, analyzes the colors of its neighbors and loops through the rest of the painting, changing pixels similar to the colors picked to be the color defined in Line 35, if a color is found to be too dark it will not be changed

# Selector
* This branch is similar to the above except it does not check for darkness is colors
