import os
import json

MODID = "harvestry"
BASE_ITEM = "strange_silk"
COMPACT_SUFFIX = "compacted_strange_silk"

COLOURS = {
    "black": "black_dye",
    "purple": "purple_dye",
    "light_blue": "light_blue_dye",
    "pink": "pink_dye",
    "white": "white_dye",
    "magenta": "magenta_dye",
    "blue": "blue_dye",
    "orange": "orange_dye",
    "lime": "lime_dye",
    "red": "red_dye",
    "light_gray": "light_gray_dye",
    "yellow": "yellow_dye",
    "brown": "brown_dye",
    "green": "green_dye",
    "dark_gray": "gray_dye",
    "cyan": "cyan_dye",
}

BASE_PATH = os.getcwd()  # safer: use current working directory
RECIPE_PATH = os.path.join(BASE_PATH, "recipe")
os.makedirs(RECIPE_PATH, exist_ok=True)

def save_json(data, filename):
    path = os.path.join(RECIPE_PATH, filename)
    with open(path, "w") as f:
        json.dump(data, f, indent=2)
    print(f"Generated: {filename}")

compact_base = {
    "type": "minecraft:crafting_shaped",
    "category": "misc",
    "pattern": ["XXX","XXX","XXX"],
    "key": {"X": {"item": f"{MODID}:{BASE_ITEM}"}},
    "result": {"id": f"{MODID}:{COMPACT_SUFFIX}", "count": 1}
}
save_json(compact_base, f"{COMPACT_SUFFIX}.json")

uncompact_base = {
    "type": "minecraft:crafting_shapeless",
    "category": "misc",
    "ingredients": [{"item": f"{MODID}:{COMPACT_SUFFIX}"}],
    "result": {"id": f"{MODID}:{BASE_ITEM}", "count": 9}
}
save_json(uncompact_base, f"{COMPACT_SUFFIX}_to_{BASE_ITEM}.json")

for colour, dye in COLOURS.items():
    coloured = f"{colour}_{BASE_ITEM}"
    coloured_compact = f"{colour}_{COMPACT_SUFFIX}"

    dye_silk = {
        "type": "minecraft:crafting_shapeless",
        "category": "misc",
        "ingredients": [
            {"item": f"{MODID}:{BASE_ITEM}"},
            {"item": f"minecraft:{dye}"}
        ],
        "result": {"id": f"{MODID}:{coloured}", "count": 1}
    }
    save_json(dye_silk, f"{coloured}_from_dye.json")

    compact_coloured = {
        "type": "minecraft:crafting_shaped",
        "category": "misc",
        "pattern": ["XXX","XXX","XXX"],
        "key": {"X": {"item": f"{MODID}:{coloured}"}},
        "result": {"id": f"{MODID}:{coloured_compact}", "count": 1}
    }
    save_json(compact_coloured, f"{coloured}_to_{coloured_compact}.json")

    dye_compact = {
        "type": "minecraft:crafting_shapeless",
        "category": "misc",
        "ingredients": [
            {"item": f"{MODID}:{COMPACT_SUFFIX}"},
            {"item": f"minecraft:{dye}"}
        ],
        "result": {"id": f"{MODID}:{coloured_compact}", "count": 1}
    }
    save_json(dye_compact, f"{COMPACT_SUFFIX}_to_{coloured_compact}.json")

    uncompact_coloured = {
        "type": "minecraft:crafting_shapeless",
        "category": "misc",
        "ingredients": [{"item": f"{MODID}:{coloured_compact}"}],
        "result": {"id": f"{MODID}:{coloured}", "count": 9}
    }
    save_json(uncompact_coloured, f"{coloured_compact}_to_{coloured}.json")
