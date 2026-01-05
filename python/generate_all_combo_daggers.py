from PIL import Image
from itertools import product, combinations
import os

texture_folder = "textures"
output_folder = "output"
os.makedirs(output_folder, exist_ok=True)

types = ["botanical", "alchemical", "cannibal"]

textures = {}
for t in types:
    textures[t] = {
        "left": Image.open(os.path.join(texture_folder, f"{t}_left.png")).convert("RGBA"),
        "right": Image.open(os.path.join(texture_folder, f"{t}_right.png")).convert("RGBA")
    }

choices = ["left", "right"]

for type_pair in combinations(types, 2):
    for combination in product(choices, repeat=2):
        if combination[0] == combination[1]:
            continue
        base = Image.new("RGBA", textures[type_pair[0]]["left"].size, (0, 0, 0, 0))
        for t, choice in zip(type_pair, combination):
            base = Image.alpha_composite(base, textures[t][choice])
        combo_name = "_".join([f"{t}_{choice}" for t, choice in zip(type_pair, combination)])
        base.save(os.path.join(output_folder, f"{combo_name}.png"))

print("generated all 2-type combinations with left/right, excluding left+left and right+right")
