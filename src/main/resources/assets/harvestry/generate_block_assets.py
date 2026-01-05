import os
import json

MODID = "harvestry"

BLOCKS = [
    "compacted_strange_silk",
    "black_compacted_strange_silk",
    "purple_compacted_strange_silk",
    "light_blue_compacted_strange_silk",
    "pink_compacted_strange_silk",
    "white_compacted_strange_silk",
    "magenta_compacted_strange_silk",
    "blue_compacted_strange_silk",
    "orange_compacted_strange_silk",
    "lime_compacted_strange_silk",
    "red_compacted_strange_silk",
    "light_gray_compacted_strange_silk",
    "yellow_compacted_strange_silk",
    "brown_compacted_strange_silk",
    "green_compacted_strange_silk",
    "dark_gray_compacted_strange_silk",
    "cyan_compacted_strange_silk",
]

BASE_PATH = os.path.dirname(os.path.abspath(__file__))

PATHS = {
    "block_models": os.path.join(BASE_PATH, "models", "block"),
    "item_models": os.path.join(BASE_PATH, "models", "item"),
    "blockstates": os.path.join(BASE_PATH, "blockstates"),
}

for path in PATHS.values():
    os.makedirs(path, exist_ok=True)

for block in BLOCKS:
    # block model
    with open(os.path.join(PATHS["block_models"], f"{block}.json"), "w") as f:
        json.dump({
            "parent": "minecraft:block/cube_all",
            "textures": {
                "all": f"{MODID}:block/{block}"
            }
        }, f, indent=2)

    # blockstate
    with open(os.path.join(PATHS["blockstates"], f"{block}.json"), "w") as f:
        json.dump({
            "variants": {
                "": {
                    "model": f"{MODID}:block/{block}"
                }
            }
        }, f, indent=2)

    # item model
    with open(os.path.join(PATHS["item_models"], f"{block}.json"), "w") as f:
        json.dump({
            "parent": f"{MODID}:block/{block}"
        }, f, indent=2)

print("✅ Compacted Strange Silk block models, blockstates, and item models generated.")
