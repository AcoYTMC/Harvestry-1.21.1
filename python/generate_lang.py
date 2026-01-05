import os
import json

MODID = "harvestry"

BLOCKS = [
    "wither_ribcage",
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

ITEMS = [
    "shear_daggers",
    "weird_wax",
    "cursed_flesh",
    "baffling_crystals",
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

BASE_PATH = os.getcwd()
LANG_PATH = os.path.join(BASE_PATH, "assets", MODID, "lang")
os.makedirs(LANG_PATH, exist_ok=True)

def to_display_name(name):
    return " ".join(word.capitalize() for word in name.split("_"))

lang_data = {}

for block in BLOCKS:
    key = f"block.{MODID}.{block}"
    lang_data[key] = to_display_name(block)

for item in ITEMS:
    key = f"item.{MODID}.{item}"
    lang_data[key] = to_display_name(item)

with open(os.path.join(LANG_PATH, "en_us.json"), "w", encoding="utf-8") as f:
    json.dump(lang_data, f, indent=2)

