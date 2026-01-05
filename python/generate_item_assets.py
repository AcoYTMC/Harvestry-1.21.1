import os
import json

MODID = "harvestry"

# Base item + colour variants
ITEMS = [
    "strange_silk",
    "black_strange_silk",
    "purple_strange_silk",
    "light_blue_strange_silk",
    "pink_strange_silk",
    "white_strange_silk",
    "magenta_strange_silk",
    "blue_strange_silk",
    "orange_strange_silk",
    "lime_strange_silk",
    "red_strange_silk",
    "light_gray_strange_silk",
    "yellow_strange_silk",
    "brown_strange_silk",
    "green_strange_silk",
    "dark_gray_strange_silk",
    "cyan_strange_silk",
]

BASE_PATH = os.path.dirname(os.path.abspath(__file__))

ITEM_MODELS_PATH = os.path.join(BASE_PATH, "models", "item")
os.makedirs(ITEM_MODELS_PATH, exist_ok=True)
for item in ITEMS:
    item_model = {
        "parent": "minecraft:item/generated",
        "textures": {
            "layer0": f"{MODID}:item/{item}"
        }
    }

    with open(os.path.join(ITEM_MODELS_PATH, f"{item}.json"), "w") as f:
        json.dump(item_model, f, indent=2)